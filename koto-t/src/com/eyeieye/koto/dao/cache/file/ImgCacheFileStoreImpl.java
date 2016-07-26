// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 2016/7/26 10:15:31
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImgCacheFileStoreImpl.java

package com.eyeieye.koto.dao.cache.file;

import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.dao.cache.*;
import com.eyeieye.koto.dao.cache.file.ImgCacheFileStoreImpl.ImgBlock;
import com.eyeieye.koto.dao.cache.file.ImgCacheFileStoreImpl.IndexBlockNode;
import com.eyeieye.koto.domain.StoredImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// Referenced classes of package com.eyeieye.koto.dao.cache.file:
//            ImageFileAccess, IndexRow, FileChannelStoredImage, ImgCacheStoreConfig

public class ImgCacheFileStoreImpl implements BytesIdImgCache, CachedImgStatistics, InitializingBean, DisposableBean {
	private final class IndexBlockCircle {

		private IndexBlockNode findCurrentNode(IndexBlockNode temp[]) {
			IndexBlockNode find = null;
			for (int i = 0; i < temp.length - 1; i++)
				if (temp[i].current.isNotEmpty() && temp[i + 1].current.isEmpty())
					return temp[i];

			if (temp[temp.length - 1].current.isNotEmpty() && temp[0].current.isEmpty())
				return temp[temp.length - 1];
			ImgCacheFileStoreImpl.logger
					.debug("can't find block current working block,so find max last modified index.");
			File lastModifiedIndex = null;
			for (int i = 0; i < temp.length; i++) {
				IndexBlockNode node = temp[i];
				File index = config.getImgIndex(i);
				if (lastModifiedIndex == null || index.lastModified() > lastModifiedIndex.lastModified()) {
					find = node;
					lastModifiedIndex = index;
				}
			}

			newBlockInWorking(find);
			return find;
		}

		private IndexBlockNode getCurrent() {
			return (IndexBlockNode) currentNode.get();
		}

		private AtomicReference currentNode;
		final ImgCacheFileStoreImpl this$0;

		IndexBlockCircle(ImgBlock blocks[]) {
			this$0 = ImgCacheFileStoreImpl.this;

			currentNode = new AtomicReference();
			IndexBlockNode temp[] = new IndexBlockNode[blocks.length];
			for (int i = 0; i < temp.length; i++)
				temp[i] = new IndexBlockNode(blocks[i]);

			for (int i = 1; i < temp.length; i++)
				temp[i].front = temp[i - 1];

			temp[0].front = temp[temp.length - 1];
			for (int i = 0; i < temp.length - 1; i++)
				temp[i].next = temp[i + 1];

			temp[temp.length - 1].next = temp[0];
			currentNode.set(findCurrentNode(temp));
		}
	}

	public static final class IndexBlockNode {

		private ImgBlock current;
		private int block;
		private IndexBlockNode front;
		private IndexBlockNode next;

		IndexBlockNode(ImgBlock now) {
			current = now;
			block = now.block;
		}
	}

	public final class ImgBlock {

		private void initRowAdd(IndexRow row) {
			rowCount.incrementAndGet();
			long rowEnd = row.getEndPosition();
			long getPosition;
			do
				getPosition = lastRowEndPosition.get();
			while (rowEnd > getPosition && !lastRowEndPosition.compareAndSet(getPosition, rowEnd));
		}

		private long getFreeSpace() {
			return config.getDataFileLength() - lastRowEndPosition.get();
		}

		private boolean isEmpty() {
			return rowCount.get() == 0;
		}

		private boolean isNotEmpty() {
			return rowCount.get() != 0;
		}

		public void reset() {
			rowCount.set(0);
			lastRowEndPosition.set(0L);
			map = ImgCacheFileStoreImpl.createNewMap();
		}

		private volatile Map map;
		private final int block;
		private AtomicInteger rowCount;
		private AtomicLong lastRowEndPosition;
		final ImgCacheFileStoreImpl this$0;

		public ImgBlock(int block) {
			this$0 = ImgCacheFileStoreImpl.this;

			map = ImgCacheFileStoreImpl.createNewMap();
			rowCount = new AtomicInteger(0);
			lastRowEndPosition = new AtomicLong(0L);
			this.block = block;
		}
	}

	public ImgCacheFileStoreImpl() {
		appendImgIndex = new LinkedList();
	}

	public ImgCacheStoreConfig getConfig() {
		return config;
	}

	public void setConfig(ImgCacheStoreConfig config) {
		this.config = config;
	}

	public ImageFileAccess getImageFileAccess() {
		return imageFileAccess;
	}

	public void afterPropertiesSet() throws Exception {
		if (logger.isDebugEnabled())
			logger.debug((new StringBuilder()).append("init with:").append(config).toString());
		imageFileAccess = new ImageFileAccess(config);
		currentImgIndexs = new ImgBlock[config.getBlocks()];
		for (int i = 0; i < config.getBlocks(); i++) {
			currentImgIndexs[i] = new ImgBlock(i);
			imageFileAccess.loadIndex(i, currentImgIndexs[i].map);
			IndexRow row;
			for (Iterator i$ = currentImgIndexs[i].map.values().iterator(); i$.hasNext(); currentImgIndexs[i]
					.initRowAdd(row))
				row = (IndexRow) i$.next();

		}

		indexBlockCircle = new IndexBlockCircle(currentImgIndexs);
		startFlushIndexTimer();
	}

	public void destroy() throws Exception {
		finishFlushIndexTimer();
		flushIndex();
	}

	private static final Map createNewMap() {
		return new ConcurrentHashMap(10240, 0.85F);
	}

	public void flushIndex() {
		List temp = null;
		temp = appendImgIndex;
		appendImgIndex = new LinkedList();
		if (temp.isEmpty())
			return;
		if (logger.isDebugEnabled())
			logger.debug((new StringBuilder()).append("flushIndex with size:").append(temp.size()).toString());
		try {
			imageFileAccess.appendWriteIndex(temp);
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
		long find[] = findFitBlock(length);
		if (find == null)
			return null;
		int block = (int) find[0];
		long position = find[1];
		File blockFile = config.getImgBlock(block);
		synchronized (blockFile) {
			imageFileAccess.writeImageBody(blockFile, position, image);
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
		} else {
			FileChannelStoredImage image = new FileChannelStoredImage();
			image.setFormat(row.getImgType().name());
			image.setFileChannel(config.getImgFileChannel(row.getBlock()));
			image.setPosition(row.getPosition());
			image.setLength(row.getLength());
			return image;
		}
	}

	private long[] findFitBlock(int imgLength) {
		int i = 0;
		do {
			if (i >= config.getBlocks())
				break;
			ImgBlock get = indexBlockCircle.getCurrent().current;
			long lastRowEndPosition = get.lastRowEndPosition.get();
			if (config.getDataFileLength() - lastRowEndPosition >= (long) imgLength) {
				long newLastRowEndPosition = lastRowEndPosition + (long) imgLength;
				if (get.lastRowEndPosition.compareAndSet(lastRowEndPosition, newLastRowEndPosition))
					return (new long[] { (long) get.block, lastRowEndPosition });
			} else {
				IndexBlockNode nowNode = indexBlockCircle.getCurrent();
				IndexBlockNode next = nowNode.next;
				if (indexBlockCircle.currentNode.compareAndSet(nowNode, next))
					newBlockInWorking(next);
			}
		} while (true);
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
					ImgCacheFileStoreImpl.this.cleanBlock(block);
				} catch (IOException e) {
					ImgCacheFileStoreImpl.logger.error("error then cleanBlock:" + block, e);
				}
			}
		}.start();
	}

	private void cleanBlock(int block) throws IOException {
		flushIndex();
		imageFileAccess.cleanBlock(block);
		ImgBlock indexBlock = currentImgIndexs[block];
		indexBlock.reset();
	}

	private void startFlushIndexTimer() {
		timer = new Timer("flush index", true);
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				flushIndex();
			}

			final ImgCacheFileStoreImpl this$0;

			{
				this$0 = ImgCacheFileStoreImpl.this;
			}
		}, config.getFlushIndexPeriod(), config.getFlushIndexPeriod());
	}

	private void finishFlushIndexTimer() {
		timer.cancel();
		timer.purge();
	}

	public List getBlockStatistics() {
		List back = new ArrayList(config.getBlocks());
		ImgBlock arr$[] = currentImgIndexs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			ImgBlock block = arr$[i$];
			CachedImgBlockSatistics sat = new CachedImgBlockSatistics();
			sat.setId(block.block);
			sat.setRowCount(block.rowCount.get());
			sat.setTotalSpace(config.getDataFileLength());
			sat.setUsedSpace(block.lastRowEndPosition.get());
			sat.setFreeSpace(block.getFreeSpace());
			if (indexBlockCircle.getCurrent().current == block)
				sat.setCurrentStore(true);
			back.add(sat);
		}

		return back;
	}

	private static final Log logger = LogFactory.getLog(ImgCacheFileStoreImpl.class);
	private static final int ImgBlockIndexDefalutCapacity = 10240;
	private static final float ImgBlockIndexDefaultLoadFactor = 0.85F;
	private ImgCacheStoreConfig config;
	private ImageFileAccess imageFileAccess;
	private ImgBlock currentImgIndexs[];
	private volatile List appendImgIndex;
	private IndexBlockCircle indexBlockCircle;
	private Timer timer;

}
