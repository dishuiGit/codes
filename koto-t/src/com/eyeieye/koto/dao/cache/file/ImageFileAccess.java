package com.eyeieye.koto.dao.cache.file;

import com.eyeieye.koto.dao.cache.IdKey;
import com.eyeieye.koto.domain.StoredImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.ReadableByteChannel;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImageFileAccess {
	private static final byte[] empty = new byte[0];

	private static final ByteOrder IndexByteOrder = ByteOrder.BIG_ENDIAN;
	private ImgCacheStoreConfig config;

	public ImageFileAccess(ImgCacheStoreConfig config) {
		this.config = config;
	}

	public void loadIndex(int blocks, Map<IdKey, IndexRow> map) throws IOException {
		File indexFile = this.config.getImgIndex(blocks);
		long indexFileSize = indexFile.length();
		if (indexFileSize != 0L) {
			ByteBuffer byteReader = ByteBuffer.allocate(1);
			ByteBuffer longReader = ByteBuffer.allocate(8).order(IndexByteOrder);

			FileInputStream fis = new FileInputStream(indexFile);
			ReadableByteChannel channel = fis.getChannel();
			while (true) {
				byte[] id = readIdBytes(channel, byteReader);
				if (id == null) {
					break;
				}
				IndexRow row = new IndexRow();
				row.setId(id);
				row.setType(readByte(channel, byteReader));
				row.setBlock((short) blocks);
				row.setPositionLength(readLong(channel, longReader));
				map.put(new IdKey(row.getId()), row);
			}
			channel.close();
			fis.close();
		}
	}

	private byte[] readIdBytes(ReadableByteChannel channel, ByteBuffer byteReader) throws IOException {
		byteReader.clear();
		if (channel.read(byteReader) == -1) {
			return null;
		}
		byteReader.flip();
		int length = byteReader.get();
		byte[] back = new byte[length];
		ByteBuffer idReader = ByteBuffer.wrap(back).order(IndexByteOrder);
		channel.read(idReader);
		return back;
	}

	private byte readByte(ReadableByteChannel channel, ByteBuffer byteReader) throws IOException {
		byteReader.clear();
		channel.read(byteReader);
		byteReader.flip();
		return byteReader.get();
	}

	private long readLong(ReadableByteChannel channel, ByteBuffer longReader) throws IOException {
		longReader.clear();
		channel.read(longReader);
		longReader.flip();
		return longReader.getLong();
	}

	public void appendWriteIndex(Collection<IndexRow> append) throws IOException {
		if (append.isEmpty()) {
			return;
		}

		Set<Integer> blocks = new HashSet<Integer>();
		for (IndexRow row : append) {
			blocks.add(Integer.valueOf(row.getBlock()));
		}
		for (Integer i : blocks)
			writeAppendIndex(append, i.intValue());
	}

	private synchronized void writeAppendIndex(Collection<IndexRow> allIndex, int block) throws IOException {
		File indexFile = this.config.getImgIndex(block);
		ByteBuffer buffer = ByteBuffer.allocate(137).order(IndexByteOrder);

		FileOutputStream fos = new FileOutputStream(indexFile, true);
		try {
			FileChannel channel = fos.getChannel();
			long newPosition = channel.size() - 1L;
			if (newPosition < 0L) {
				newPosition = 0L;
			}
			channel.position(newPosition);
			for (IndexRow row : allIndex)
				if (row.getBlock() == block) {
					buffer.clear();
					writeIdBytes(buffer, row.getId());
					buffer.put(row.getType());
					buffer.putLong(row.getPositionLength());
					buffer.flip();
					channel.write(buffer);
				}
			channel.force(false);
			channel.close();
		} finally {
			try {
				fos.close();
			} catch (IOException ignore) {
			}
		}
	}

	private void writeIdBytes(ByteBuffer buffer, byte[] bs) {
		int length = bs.length;
		if (length > 127) {
			return;
		}
		buffer.put((byte) length);
		buffer.put(bs);
	}

	public void cleanBlock(int block) throws IOException {
		File indexFile = this.config.getImgIndex(block);
		FileOutputStream fos = new FileOutputStream(indexFile, false);
		try {
			fos.write(empty);
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	public void writeImageBody(File blockFile, long position, StoredImage image) throws IOException {
		InputStream is = image.getBodyInputStream();
		byte[] buffer = new byte[''];

		RandomAccessFile raf = new RandomAccessFile(blockFile, "rw");
		try {
			FileChannel channel = raf.getChannel();
			MappedByteBuffer bb = channel.map(FileChannel.MapMode.READ_WRITE, position, image.getLength());
			int readed;
			while ((readed = is.read(buffer)) != -1) {
				bb.put(buffer, 0, readed);
			}
			channel.close();
		} finally {
			try {
				raf.close();
			} catch (IOException ignore) {
			}
		}
	}

	public byte[] getImageBody(IndexRow row) throws IOException {
		byte[] body = new byte[row.getLength()];
		ByteBuffer buffer = ByteBuffer.wrap(body);
		File blockFile = this.config.getImgBlock(row.getBlock());
		FileInputStream fis = new FileInputStream(blockFile);
		FileChannel channel = fis.getChannel();
		channel.position(row.getPosition());
		channel.read(buffer);
		channel.close();
		fis.close();
		return body;
	}
}

/*
 * Location: E:\codes\work\koto\WEB-INF\classes\ Qualified Name:
 * com.eyeieye.koto.dao.cache.file.ImageFileAccess JD-Core Version: 0.6.2
 */