package com.eyeieye.koto.dao.cache.hotsopt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.eyeieye.koto.dao.cache.BytesIdImgCache;
import com.eyeieye.koto.dao.cache.IdKey;
import com.eyeieye.koto.dao.cache.KeysAwareImageCache;
import com.eyeieye.koto.domain.StoredImage;

public class GDSFSimilarHotspotManagerImpl implements HotspotManager, InitializingBean, DisposableBean {
	private static Log logger = LogFactory.getLog(GDSFSimilarHotspotManagerImpl.class);
	private KeysAwareImageCache memoryCache;
	private BytesIdImgCache diskCache;
	private Executor executor;
	private ScheduledExecutorService scheduledExecutor;
	private final ConcurrentHashMap<IdKey, ImageEntry> id2Entry;
	private long maxMemory;
	private long expiredTime;
	private long arrangeIntervalMinute;

	public GDSFSimilarHotspotManagerImpl() {
		this.id2Entry = new ConcurrentHashMap();

		this.maxMemory = 2147483647L;

		this.expiredTime = 86400000L;

		this.arrangeIntervalMinute = 10L;
	}

	public void setMemoryCache(KeysAwareImageCache memoryCache) {
		this.memoryCache = memoryCache;
	}

	public void setDiskCache(BytesIdImgCache diskCache) {
		this.diskCache = diskCache;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public void setMaxMemoryMB(long maxMemoryMb) {
		this.maxMemory = (maxMemoryMb * 1024L * 1024L);
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public void setExpiredDay(long expiredDay) {
		this.expiredTime = (expiredDay * 24L * 60L * 60L * 1000L);
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public void setArrangeIntervalMinute(long arrangeIntervalMinute) {
		this.arrangeIntervalMinute = arrangeIntervalMinute;
	}

	public void afterPropertiesSet() throws Exception {
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		threadFactory = new LogExceptionTheadFactory(threadFactory);
		this.scheduledExecutor = Executors.newScheduledThreadPool(1, threadFactory);

		this.scheduledExecutor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				GDSFSimilarHotspotManagerImpl.this.arrange();
			}
		}, this.arrangeIntervalMinute / 2L, this.arrangeIntervalMinute, TimeUnit.MINUTES);
	}

	public void destroy() throws Exception {
		this.scheduledExecutor.shutdown();
	}

	public void hit(final IdKey key, final StoredImage image) {
		this.executor.execute(new Runnable() {
			public void run() {
				GDSFSimilarHotspotManagerImpl.this.innerHit(key, image);
			}
		});
	}

	private void innerHit(IdKey key, StoredImage image) {
		ImageEntry entry = (ImageEntry) this.id2Entry.get(key);
		if (entry == null) {
			entry = new ImageEntry(key, image.getLength());
			ImageEntry exists = (ImageEntry) this.id2Entry.putIfAbsent(key, entry);
			if (exists != null) {
				entry = exists;
			}
		}
		entry.hit();
	}

	public synchronized void arrange() {
		logger.debug("begin arrange");
		TopRankFinder finder = new TopRankFinder();
		long now = finder.now;
		for (Iterator it = id2Entry.entrySet().iterator(); it.hasNext();) {
			ImageEntry en = (ImageEntry) ((java.util.Map.Entry) it.next()).getValue();
			if (en.getUnHitTime(now) > expiredTime) {
				it.remove();
			} else {
				finder.addEntry(en);
				en.resetHit();
			}
		}

		boolean debugEnable = logger.isDebugEnabled();
		Set topImages = finder.getTops();
		finder = null;
		if (debugEnable)
			logger.debug((new StringBuilder()).append("find top images:").append(topImages).toString());
		Set currentHold = memoryCache.getKeys();
		Iterator i$ = currentHold.iterator();
		do {
			if (!i$.hasNext())
				break;
			IdKey key = (IdKey) i$.next();
			if (!topImages.contains(key)) {
				memoryCache.remove(key);
				if (debugEnable)
					logger.debug((new StringBuilder()).append("remove from memory id:").append(key).toString());
			}
		} while (true);
		i$ = topImages.iterator();
		do {
			if (!i$.hasNext())
				break;
			IdKey key = (IdKey) i$.next();
			if (!currentHold.contains(key)) {
				if (debugEnable)
					logger.debug((new StringBuilder()).append("put to memory id:").append(key).toString());
				try {
					putToMemory(key);
				} catch (Throwable e) {
					logger.error("error then putToMemory", e);
				}
			}
		} while (true);
	}

	private void putToMemory(IdKey key) {
		StoredImage fromDisk = this.diskCache.get(key);
		if (fromDisk == null) {
			logger.debug("put id to memory but can't find in disk cache.");
			return;
		}
		this.memoryCache.put(key, fromDisk);
	}

	private final class TopRankFinder {
		private final long max = GDSFSimilarHotspotManagerImpl.this.maxMemory;

		private final long now = System.currentTimeMillis();

		private long sum = 0L;

		private double minRank = 0.0D;

		private SortedMap<Double, GDSFSimilarHotspotManagerImpl.ImageEntry> topRanks = new TreeMap();

		private TopRankFinder() {
		}

		private void addEntry(ImageEntry entry) {
			double r = entry.getRank(now);
			if (r < minRank)
				if (sum > max) {
					return;
				} else {
					topRanks.put(Double.valueOf(r), entry);
					sum += entry.size;
					minRank = r;
					return;
				}
			if (sum < max) {
				topRanks.put(Double.valueOf(r), entry);
				sum += entry.size;
				minRank = ((Double) topRanks.firstKey()).doubleValue();
				return;
			} else {
				topRanks.put(Double.valueOf(r), entry);
				sum += entry.size;
				fitToMaxMemory();
				return;
			}
		}

		private void fitToMaxMemory() {
			ImageEntry minEntry;
			for (; sum > max; sum -= minEntry.size) {
				Double minRank = (Double) topRanks.firstKey();
				minEntry = (ImageEntry) topRanks.remove(minRank);
			}

			this.minRank = ((Double) topRanks.firstKey()).doubleValue();
		}

		private Set getTops() {
			fitToMaxMemory();
			Set back = new HashSet(topRanks.size());
			ImageEntry entry;
			for (Iterator i$ = topRanks.values().iterator(); i$.hasNext(); back.add(entry.key))
				entry = (ImageEntry) i$.next();

			return back;
		}
	}

	private static final class LogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			if (GDSFSimilarHotspotManagerImpl.logger.isErrorEnabled())
				GDSFSimilarHotspotManagerImpl.logger.error("caught exception in thrad" + t.getName()
						+ " exception class:" + e.getClass().getName() + " message:" + e.getMessage(), e);
		}
	}

	private static final class LogExceptionTheadFactory implements ThreadFactory {

		public Thread newThread(Runnable r) {
			Thread t = factory.newThread(r);
			t.setName("scheduled_arrange");
			t.setUncaughtExceptionHandler(new LogUncaughtExceptionHandler());
			return t;
		}

		private ThreadFactory factory;

		public LogExceptionTheadFactory(ThreadFactory factory) {
			this.factory = factory;
		}
	}

	private static final class ImageEntry {
		private static final int OneHour = 3600000;
		private final IdKey key;
		private int size;
		private final long startTime = System.currentTimeMillis();
		private volatile long lastTime;
		private AtomicInteger hits = new AtomicInteger(0);

		private ImageEntry(IdKey key, int size) {
			this.key = key;
			this.size = size;
		}

		private void resetHit() {
			this.hits.set(0);
		}

		private void hit() {
			this.hits.incrementAndGet();
			this.lastTime = System.currentTimeMillis();
		}

		private double getRank(long now) {
			double rank = this.hits.get();
			rank = rank / this.size + getAge(now);
			return rank;
		}

		private int getAge(long now) {
			return (int) ((now - this.startTime) / 3600000L);
		}

		private long getUnHitTime(long now) {
			return now - this.lastTime;
		}
	}
}

/*
 * Location: E:\codes\work\koto\WEB-INF\classes\ Qualified Name:
 * com.eyeieye.koto.dao.cache.hotsopt.GDSFSimilarHotspotManagerImpl JD-Core
 * Version: 0.6.2
 */