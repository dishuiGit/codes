package com.eyeieye.koto.dao.cache.file;

import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.dao.cache.BytesIdImgCache;
import com.eyeieye.koto.dao.cache.CachedImgBlockSatistics;
import com.eyeieye.koto.dao.cache.CachedImgStatistics;
import com.eyeieye.koto.dao.cache.IdKey;
import com.eyeieye.koto.dao.cache.file.ImgCacheFileStoreImplBak.IndexBlockNode;
import com.eyeieye.koto.domain.StoredImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class ImgCacheFileStoreImplBak implements BytesIdImgCache, CachedImgStatistics, InitializingBean, DisposableBean {
	private static final Log logger = LogFactory.getLog(ImgCacheFileStoreImplBak.class);
	private static final int ImgBlockIndexDefalutCapacity = 10240;
	private static final float ImgBlockIndexDefaultLoadFactor = 0.85F;
	private ImgCacheStoreConfig config;
	private ImageFileAccess imageFileAccess;
	private ImgBlock[] currentImgIndexs;
	private volatile List<IndexRow> appendImgIndex;
	private IndexBlockCircle indexBlockCircle;
	private Timer timer;

	public ImgCacheFileStoreImplBak() {
		this.appendImgIndex = new LinkedList();
	}

	public ImgCacheStoreConfig getConfig() {
		return this.config;
	}

	public void setConfig(ImgCacheStoreConfig config) {
		this.config = config;
	}

	public ImageFileAccess getImageFileAccess() {
		return this.imageFileAccess;
	}

	public void afterPropertiesSet() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("init with:" + this.config);
		}
		this.imageFileAccess = new ImageFileAccess(this.config);

		this.currentImgIndexs = new ImgBlock[this.config.getBlocks()];
		for (int i = 0; i < this.config.getBlocks(); i++) {
			this.currentImgIndexs[i] = new ImgBlock(i);
			this.imageFileAccess.loadIndex(i, this.currentImgIndexs[i].map);
			for (IndexRow row : this.currentImgIndexs[i].map.values()) {
				this.currentImgIndexs[i].initRowAdd(row);
			}
		}

		this.indexBlockCircle = new IndexBlockCircle(this.currentImgIndexs);

		startFlushIndexTimer();
	}

	public void destroy() throws Exception {
		finishFlushIndexTimer();
		flushIndex();
	}

	private static final Map<IdKey, IndexRow> createNewMap() {
		return new ConcurrentHashMap(10240, 0.85F);
	}

	public void flushIndex() {
		List temp = null;
		temp = this.appendImgIndex;
		this.appendImgIndex = new LinkedList();
		if (temp.isEmpty()) {
			return;
		}
		if (logger.isDebugEnabled())
			logger.debug("flushIndex with size:" + temp.size());
		try {
			this.imageFileAccess.appendWriteIndex(temp);
		} catch (IOException e) {
			logger.error("error then appendWriteIndex", e);
		}
	}

	public void put(IdKey idKey, StoredImage image) {
		if (idKey == null) {
			throw new NullPointerException("key can't be null.");
		}
		if (image == null) {
			throw new NullPointerException("image can't be null.");
		}

		if (idKey.getBytesLength() > 127) {
			if (logger.isDebugEnabled()) {
				logger.debug("key is too long:" + idKey);
			}
			return;
		}
		IndexRow find = findIndex(idKey);
		if (find != null) {
			return;
		}
		IndexRow create = null;
		try {
			create = storeImage(image);
		} catch (IOException e) {
			logger.error("error then store image", e);
			return;
		}
		create.setId(idKey.getBytes());

		synchronized (this) {
			find = findIndex(idKey);
			if (find != null) {
				return;
			}
			this.currentImgIndexs[create.getBlock()].map.put(idKey, create);
			this.currentImgIndexs[create.getBlock()].initRowAdd(create);
			this.appendImgIndex.add(create);
		}
	}

	private IndexRow findIndex(IdKey idKey) {
		IndexRow row = null;
		IndexBlockNode startNode = indexBlockCircle.getCurrent();
		IndexBlockNode node = startNode;
		do {
			row = (IndexRow) currentImgIndexs[node.block].map.get(idKey);
			if (row != null)
				return row;
			node = node.front;
		} while (node != startNode);
		return row;
	}

	public IndexRow storeImage(StoredImage image) throws IOException {
		int length = image.getLength();
		long[] find = findFitBlock(length);
		if (find == null) {
			return null;
		}

		int block = (int) find[0];
		long position = find[1];
		File blockFile = this.config.getImgBlock(block);
		synchronized (blockFile) {
			this.imageFileAccess.writeImageBody(blockFile, position, image);
		}
		return new IndexRow(image, (short) block, position);
	}

	public boolean contains(IdKey key) {
		IndexRow row = findIndex(key);
		return row != null;
	}

	public StoredImage get(IdKey key) {
		IndexRow row = findIndex(key);
		if (row == null) {
			return null;
		}
		FileChannelStoredImage image = new FileChannelStoredImage();
		image.setFormat(row.getImgType().name());
		image.setFileChannel(this.config.getImgFileChannel(row.getBlock()));
		image.setPosition(row.getPosition());
		image.setLength(row.getLength());
		return image;
	}

	private long[] findFitBlock(int imgLength) {
		for (int i = 0; i < this.config.getBlocks();) {
			ImgBlock get = this.indexBlockCircle.getCurrent().current;
			long lastRowEndPosition = get.lastRowEndPosition.get();
			if (this.config.getDataFileLength() - lastRowEndPosition >= imgLength) {
				long newLastRowEndPosition = lastRowEndPosition + imgLength;
				if (get.lastRowEndPosition.compareAndSet(lastRowEndPosition, newLastRowEndPosition)) {
					return new long[] { get.block, lastRowEndPosition };
				}
			} else {
				IndexBlockNode nowNode = this.indexBlockCircle.getCurrent();
				IndexBlockNode next = nowNode.next;
				if (this.indexBlockCircle.currentNode.compareAndSet(nowNode, next)) {
					newBlockInWorking(next);
				}
			}
		}
		return null;
	}

	private void newBlockInWorking(IndexBlockNode nowWorking) {
		ImgBlock next = nowWorking.next.current;
		if (next.isEmpty()) {
			return;
		}
		final int block = next.block;
		logger.debug("next block " + block + " is not empty,so clean it.");

		new Thread("cleanBlock_" + block) {
			public void run() {
				try {
					ImgCacheFileStoreImplBak.this.cleanBlock(block);
				} catch (IOException e) {
					ImgCacheFileStoreImplBak.logger.error("error then cleanBlock:" + block, e);
				}
			}
		}.start();
	}

	private void cleanBlock(int block) throws IOException {
		flushIndex();

		this.imageFileAccess.cleanBlock(block);

		ImgBlock indexBlock = this.currentImgIndexs[block];
		indexBlock.reset();
	}

	private void startFlushIndexTimer() {
		this.timer = new Timer("flush index", true);
		this.timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				ImgCacheFileStoreImplBak.this.flushIndex();
			}
		}, this.config.getFlushIndexPeriod(), this.config.getFlushIndexPeriod());
	}

	private void finishFlushIndexTimer() {
		this.timer.cancel();
		this.timer.purge();
	}

	public List<CachedImgBlockSatistics> getBlockStatistics() {
		List back = new ArrayList(this.config.getBlocks());

		for (ImgBlock block : this.currentImgIndexs) {
			CachedImgBlockSatistics sat = new CachedImgBlockSatistics();
			sat.setId(block.block);
			sat.setRowCount(block.rowCount.get());
			sat.setTotalSpace(this.config.getDataFileLength());
			sat.setUsedSpace(block.lastRowEndPosition.get());
			sat.setFreeSpace(block.getFreeSpace());
			if (this.indexBlockCircle.getCurrent().current == block) {
				sat.setCurrentStore(true);
			}
			back.add(sat);
		}
		return back;
	}

	private final class IndexBlockCircle {
		private AtomicReference<ImgCacheFileStoreImplBak.IndexBlockNode> currentNode = new AtomicReference();

		IndexBlockCircle(ImgCacheFileStoreImplBak.ImgBlock[] blocks) {
			ImgCacheFileStoreImplBak.IndexBlockNode[] temp = new ImgCacheFileStoreImplBak.IndexBlockNode[blocks.length];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = new ImgCacheFileStoreImplBak.IndexBlockNode(blocks[i]);
			}

			for (int i = 1; i < temp.length; i++) {
				temp[i].front = temp[i - 1];
			}
			temp[0].front = temp[temp.length - 1];

			for (int i = 0; i < temp.length - 1; i++) {
				temp[i].next = temp[i + 1];
			}
			temp[temp.length - 1].next = temp[0];

			this.currentNode.set(findCurrentNode(temp));
		}

		private ImgCacheFileStoreImplBak.IndexBlockNode findCurrentNode(ImgCacheFileStoreImplBak.IndexBlockNode[] temp) {
			ImgCacheFileStoreImplBak.IndexBlockNode find = null;
			for (int i = 0; i < temp.length - 1; i++) {
				if (temp[i].current.isNotEmpty() && temp[i + 1].current.isEmpty()) {
					return temp[i];
				}
			}

			if (temp[temp.length - 1].current.isNotEmpty() && temp[0].current.isEmpty()) {
				return temp[(temp.length - 1)];
			}
			ImgCacheFileStoreImplBak.logger
					.debug("can't find block current working block,so find max last modified index.");

			File lastModifiedIndex = null;
			for (int i = 0; i < temp.length; i++) {
				ImgCacheFileStoreImplBak.IndexBlockNode node = temp[i];
				File index = ImgCacheFileStoreImplBak.this.config.getImgIndex(i);
				if ((lastModifiedIndex == null) || (index.lastModified() > lastModifiedIndex.lastModified())) {
					find = node;
					lastModifiedIndex = index;
				}
			}

			ImgCacheFileStoreImplBak.this.newBlockInWorking(find);
			return find;
		}

		private ImgCacheFileStoreImplBak.IndexBlockNode getCurrent() {
			return (ImgCacheFileStoreImplBak.IndexBlockNode) this.currentNode.get();
		}
	}

	public static final class IndexBlockNode {
		private ImgCacheFileStoreImplBak.ImgBlock current;
		private int block;
		private IndexBlockNode front;
		private IndexBlockNode next;

		IndexBlockNode(ImgCacheFileStoreImplBak.ImgBlock now) {
			this.current = now;
			this.block = now.block;
		}
	}

	private final class ImgBlock {
		private volatile Map<IdKey, IndexRow> map = ImgCacheFileStoreImplBak.createNewMap();
		private final int block;
		private AtomicInteger rowCount = new AtomicInteger(0);

		private AtomicLong lastRowEndPosition = new AtomicLong(0L);

		public ImgBlock(int block) {
			this.block = block;
		}

		private void initRowAdd(IndexRow row) {
			this.rowCount.incrementAndGet();
			long rowEnd = row.getEndPosition();
			while (true) {
				long getPosition = this.lastRowEndPosition.get();
				if (rowEnd > getPosition) {
					if (this.lastRowEndPosition.compareAndSet(getPosition, rowEnd)) {
						break;
					}
				}
			}
		}

		private long getFreeSpace() {
			return ImgCacheFileStoreImplBak.this.config.getDataFileLength() - this.lastRowEndPosition.get();
		}

		private boolean isEmpty() {
			return this.rowCount.get() == 0;
		}

		private boolean isNotEmpty() {
			return this.rowCount.get() != 0;
		}

		public void reset() {
			rowCount.set(0);
			lastRowEndPosition.set(0L);
			map = ImgCacheFileStoreImplBak.createNewMap();
		}
	}
}