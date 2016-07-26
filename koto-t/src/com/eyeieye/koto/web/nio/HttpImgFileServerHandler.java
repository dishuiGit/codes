package com.eyeieye.koto.web.nio;

import com.eyeieye.koto.common.FixsizeByteArrayOutputStream;
import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.dao.cache.ImageCache;
import com.eyeieye.koto.dao.store.StoreService;
import com.eyeieye.koto.domain.BytesStoredImage;
import com.eyeieye.koto.domain.StoredEntry;
import com.eyeieye.koto.domain.StoredImage;
import com.eyeieye.koto.service.ImageStatisticsService;
import com.eyeieye.koto.service.ImageThumbService;
import com.eyeieye.koto.validate.RefererVaildator;
import com.eyeieye.koto.validate.ShapeSizeValidator;
import com.eyeieye.melody.util.HostUtil;
import com.eyeieye.melody.util.HostUtil.HostInfo;
import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.im4java.core.IM4JavaException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

 @Component("imgFileServerHandler")
public final class HttpImgFileServerHandler extends SimpleChannelUpstreamHandler
 {
   public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
   public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
   private static final String AllowHttpMethod = new StringBuilder().append(HttpMethod.GET).append(",").append(HttpMethod.OPTIONS).toString();

   @Value("${img.nio.http.cache:315360000}")
   public int httpCacheSecodns = 315360000;
   private String cacheControl;
   private static final String ServerTag = new StringBuilder().append("koto-1.0_").append(HostUtil.getHostInfo().getName()).toString();

   @Autowired
   @Qualifier("imgStoreService")
   private StoreService imgStoreService;

   @Autowired
   private ImageCache imageCache;

   @Autowired
   private ImageThumbService imageThumbService;

   @Autowired
   private ShapeSizeValidator shapeSizeValidator;

   @Autowired
   private RefererVaildator refererVaildator;

   @Value("${web.encoding}")
   private String webEncoding;

   @Autowired
   @Qualifier("fileStoreService")
   private StoreService fileStoreService;

   @Autowired
   private ImageStatisticsService imageStatisticsService;
   private static final Pattern imgUrlPattern = Pattern.compile("^/img/([a-zA-Z\\d]+)(\\.img_(\\d+)_?(\\d*))?\\.img$");

   private static final Pattern fileUrlPattern = Pattern.compile("^/fs/([a-zA-Z\\d]+)\\.[a-zA-Z\\d]+$");

   private static final Pattern imgUrlPattern2 = Pattern.compile("^/img/([a-zA-Z\\d]+)(\\.img_(\\d+)_?(\\d*)?_(\\d))?\\.img$");

   private static final Pattern imgUrlPattern3 = Pattern.compile("^/img/([a-zA-Z\\d]+)(\\.img_(\\d+)_?(\\d*)?_(\\d)?_(\\d))?\\.img$");
   private static final String UnknowMimeType = "application/octet-stream";
   private MimeDetector mimeDetector = new ExtensionMimeDetector();
   private Map<String, String> extension2MimeType = new ConcurrentHashMap();

   @PostConstruct
   public void init()
   {
     this.cacheControl = new StringBuilder().append("max-age=").append(this.httpCacheSecodns).toString();
   }

   public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
     throws Exception
   {
     HttpRequest request = (HttpRequest)e.getMessage();

     if (request.getMethod() == HttpMethod.OPTIONS) {
       sendBackAllowed(request, ctx);
       return;
     }
     if (request.getMethod() != HttpMethod.GET) {
       sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
       return;
     }
     if (StringUtils.isNotBlank(request.getHeader("If-Modified-Since")))
     {
       sendNotModified(request, ctx);
       return;
     }
     String path = sanitizeUri(request.getUri());
     if (path == null) {
       sendError(ctx, HttpResponseStatus.FORBIDDEN);
       return;
     }

     Matcher m = imgUrlPattern.matcher(path);
     Matcher m2 = imgUrlPattern2.matcher(path);
     Matcher m3 = imgUrlPattern3.matcher(path);
     Boolean b = Boolean.valueOf(m.find());
     Boolean b2 = Boolean.valueOf(m2.find());
     Boolean b3 = Boolean.valueOf(m3.find());
     if ((!b.booleanValue()) && (!b2.booleanValue()) && (!b3.booleanValue())) {
       Matcher m4 = fileUrlPattern.matcher(path);
       if (!m4.find()) {
         sendError(ctx, HttpResponseStatus.FORBIDDEN);
         return;
       }
       fileDownload(m4.group(1), ctx, e);
       return;
     }

     if (this.refererVaildator.isForbiddenRequest(request)) {
       sendError(ctx, HttpResponseStatus.FORBIDDEN);
       return;
     }
     ChannelFuture writeFuture = null;
     if (b.booleanValue())
     {
       String id = m.group(1);
       String widthString = m.group(3);
       String isCutImage = null;
       String isGray = null;
       if (StringUtils.isBlank(widthString))
       {
         writeFuture = original(id, ctx, e);
       } else {
         Integer width = Integer.valueOf(Integer.parseInt(widthString));
         String heightString = m.group(4);
         Integer height = StringUtils.isBlank(heightString) ? null : Integer.valueOf(Integer.parseInt(m.group(4)));

         writeFuture = thumbnail(id, width, height, ctx, e);
       }

     }
     else if (b2.booleanValue())
     {
       String id = m2.group(1);
       String widthString = m2.group(3);
       String isCutImage = m2.group(5);
       String isGray = null;
       if (StringUtils.isBlank(widthString))
       {
         writeFuture = original(id, ctx, e);
       } else {
         Integer width = Integer.valueOf(Integer.parseInt(widthString));
         String heightString = m2.group(4);
         Integer height = StringUtils.isBlank(heightString) ? null : Integer.valueOf(Integer.parseInt(m2.group(4)));

         if (this.shapeSizeValidator.isAllowed(width, height)) {
           writeFuture = thumbnail2(id, width, height, ctx, e, isCutImage, isGray);
         }
         else
         {
           sendError(ctx, HttpResponseStatus.BAD_REQUEST);
           return;
         }
       }
     }
     else if (b3.booleanValue())
     {
       String id = m3.group(1);
       String widthString = m3.group(3);
       String isCutImage = m3.group(5);
       String isGray = m3.group(6);
       if (StringUtils.isBlank(widthString))
       {
         writeFuture = original(id, ctx, e);
       } else {
         Integer width = Integer.valueOf(Integer.parseInt(widthString));
         String heightString = m3.group(4);
         Integer height = StringUtils.isBlank(heightString) ? null : Integer.valueOf(Integer.parseInt(m3.group(4)));

         if (this.shapeSizeValidator.isAllowed(width, height)) {
           writeFuture = thumbnail2(id, width, height, ctx, e, isCutImage, isGray);
         }
         else
         {
           sendError(ctx, HttpResponseStatus.BAD_REQUEST);
           return;
         }
       }

     }

     closeIfNeed(request, writeFuture);
   }

   private void closeIfNeed(HttpRequest request, ChannelFuture writeFuture)
   {
     if (writeFuture != null)
     {
       if (!HttpHeaders.isKeepAlive(request))
       {
         writeFuture.addListener(ChannelFutureListener.CLOSE);
       }
     }
   }

   private ChannelFuture tryBufferedImage(String id, ChannelHandlerContext ctx, MessageEvent e) throws IOException
   {
     StoredImage image = this.imageCache.get(id);
     if (image == null) {
       return null;
     }

     this.imageStatisticsService.hitInCache(image.getLength());

     HttpResponse response = buildImgResponse(image.getLength(), image.getFormat());

     Channel ch = e.getChannel();

     ch.write(response);

     return ch.write(image.getNettyChannelWrited());
   }

   private ChannelFuture writeStoredImage(StoredImage ci, ChannelHandlerContext ctx, MessageEvent e)
   {
     this.imageStatisticsService.hitInStore(ci.getLength());
     HttpResponse response = buildImgResponse(ci.getLength(), ci.getFormat());
     Channel ch = e.getChannel();

     ch.write(response);

     return ch.write(ci.getNettyChannelWrited());
   }

   private ChannelFuture original(String id, ChannelHandlerContext ctx, MessageEvent e) throws IOException
   {
     ChannelFuture back = tryBufferedImage(id, ctx, e);
     if (back != null)
     {
       return back;
     }

     StoredImage ci = findImageDirect(id, true);
     if (ci == null) {
       sendError(ctx, HttpResponseStatus.NOT_FOUND);
       return null;
     }

     return writeStoredImage(ci, ctx, e);
   }

   private ChannelFuture thumbnail(String id, Integer width, Integer height, ChannelHandlerContext ctx, MessageEvent e) throws IOException
   {
     StringBuilder key = new StringBuilder(id).append('_').append(width);

     if (height != null) {
       key.append('_').append(height);
     }
     String thumbnailKey = key.toString();

     ChannelFuture back = tryBufferedImage(thumbnailKey, ctx, e);
     if (back != null)
     {
       return back;
     }

     StoredImage source = this.imageCache.get(id);
     if (source == null)
     {
       source = findImageDirect(id, false);
       if (source == null)
       {
         sendError(ctx, HttpResponseStatus.FORBIDDEN);
         return null;
       }
     }
     StoredImage thumb = this.imageThumbService.getImageThumb(source, width, height);

     this.imageCache.put(thumbnailKey, thumb);
     return writeStoredImage(thumb, ctx, e);
   }

   private ChannelFuture thumbnail2(String id, Integer width, Integer height, ChannelHandlerContext ctx, MessageEvent e, String isCutImage, String isGray)
     throws IOException, InterruptedException, IM4JavaException
   {
     StringBuilder key = new StringBuilder(id).append('_').append(width);

     if (height != null) {
       key.append('_').append(height);
     }
     if ("1".equalsIgnoreCase(isCutImage)) {
       key.append('_').append("1");
     }
     if ("1".equalsIgnoreCase(isGray)) {
       key.append('_').append("1");
     }
     String thumbnailKey = key.toString();

     ChannelFuture back = tryBufferedImage(thumbnailKey, ctx, e);
     if (back != null)
     {
       return back;
     }

     StoredImage source = this.imageCache.get(id);
     if (source == null)
     {
       source = findImageDirect(id, false);
       if (source == null)
       {
         sendError(ctx, HttpResponseStatus.FORBIDDEN);
         return null;
       }
     }
     StoredImage thumb = this.imageThumbService.cutImage(source, width, height, isGray);

     this.imageCache.put(thumbnailKey, thumb);
     return writeStoredImage(thumb, ctx, e);
   }

   private StoredImage findImageDirect(String id, boolean putToCache)
     throws IOException
   {
     StoredEntry sf = this.imgStoreService.getStoredEntry(id);
     if (sf == null) {
       return null;
     }
     BytesStoredImage ci = new BytesStoredImage();
     ci.setFormat(sf.getAppend("i_tp").toString());
     FixsizeByteArrayOutputStream bos = new FixsizeByteArrayOutputStream(sf.getLength());

     sf.writeTo(bos);
     ci.setBody(bos.toByteArray());
     if (putToCache) {
       this.imageCache.put(id, ci);
     }
     return ci;
   }

   public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
     throws Exception
   {
     Channel ch = e.getChannel();
     Throwable cause = e.getCause();

     if ((cause instanceof TooLongFrameException)) {
       sendError(ctx, HttpResponseStatus.BAD_REQUEST);
       return;
     }
     if (ch.isConnected())
       sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
   }

   private static String sanitizeUri(String uri)
   {
     try
     {
       return URLDecoder.decode(uri, "UTF-8");
     } catch (UnsupportedEncodingException e) {
       try {
         return URLDecoder.decode(uri, "ISO-8859-1"); } catch (UnsupportedEncodingException e1) {  }
     }
     throw new Error();
   }

   private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status)
   {
     HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);

     response.setHeader("Server", ServerTag);
     response.setHeader("Content-Type", "text/plain; charset=UTF-8");

     response.setContent(ChannelBuffers.copiedBuffer(new StringBuilder().append("Failure: ").append(status.toString()).append("\r\n").toString(), CharsetUtil.UTF_8));

     ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
   }

   private HttpResponse buildImgResponse(int length, String format)
   {
     HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

     response.setHeader("Server", ServerTag);
     response.setHeader("Connection", "keep-alive");

     response.setHeader("Content-Length", Integer.valueOf(length));
     response.setHeader("Content-Type", ImgType.valueOf(format).getMineType());

     setCacheHeaders(response);
     return response;
   }

   private void setCacheHeaders(HttpResponse response) {
     SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

     dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

     Calendar time = new GregorianCalendar();
     response.setHeader("Last-Modified", dateFormatter.format(time.getTime()));

     time.add(13, this.httpCacheSecodns);
     response.setHeader("Expires", dateFormatter.format(time.getTime()));

     response.setHeader("Cache-Control", this.cacheControl);
   }

   private void fileDownload(String id, ChannelHandlerContext ctx, MessageEvent e)
     throws IOException
   {
     StoredEntry sf = this.fileStoreService.getStoredEntry(id);
     if (sf == null) {
       sendError(ctx, HttpResponseStatus.NOT_FOUND);
       return;
     }
     Object nameAttribute = sf.getAppend("i_fn");
     String filename = nameAttribute == null ? id : nameAttribute.toString();
     HttpRequest request = (HttpRequest)e.getMessage();
     HttpResponse response = buildFileResponse(request, sf.getLength(), filename);

     Channel ch = e.getChannel();

     ch.write(response);

     FixsizeByteArrayOutputStream os = new FixsizeByteArrayOutputStream(sf.getLength());

     sf.writeTo(os);
     ChannelBuffer channelBuffer = ChannelBuffers.wrappedBuffer(os.toByteArray());

     ChannelFuture writeFuture = ch.write(channelBuffer);
     writeFuture.addListener(ChannelFutureListener.CLOSE);
   }

   private HttpResponse buildFileResponse(HttpRequest request, int length, String fileName) throws IOException
   {
     HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

     response.setHeader("Server", ServerTag);
     response.setHeader("Connection", "close");

     response.setHeader("Content-Length", Integer.valueOf(length));
     response.setHeader("Content-Type", getMimeType(fileName));

     response.setHeader("Content-Disposition", getFileDownlaodHead(request, fileName));

     return response;
   }

   private String getFileDownlaodHead(HttpRequest request, String fileName) throws IOException
   {
     String browser = request.getHeader("User-Agent");
     if (browser != null) {
       if ((browser.indexOf("MSIE 7.0") >= 0) || (browser.indexOf("MSIE 8.0") >= 0))
       {
         return new StringBuilder().append("attachment; filename=").append(URLEncoder.encode(fileName, this.webEncoding)).toString();
       }

       if (browser.indexOf("Safari") >= 0) {
         return new StringBuilder().append("attachment; filename=").append(fileName).toString();
       }
     }
     return new StringBuilder().append("attachment; filename*=").append(this.webEncoding).append("''").append(URLEncoder.encode(fileName, this.webEncoding)).toString();
   }

   private String getMimeType(String name)
   {
     String extensionWithDot = getExtensionWithDot(name);
     if (extensionWithDot == null) {
       return "application/octet-stream";
     }
     String type = (String)this.extension2MimeType.get(extensionWithDot);
     if (type != null) {
       return type;
     }

     Collection c = this.mimeDetector.getMimeTypes(name);
     if ((c == null) || (c.isEmpty())) {
       return type = "application/octet-stream";
     }
     type = c.iterator().next().toString();

     this.extension2MimeType.put(extensionWithDot, type);
     return type;
   }

   private String getExtensionWithDot(String name) {
     int index = name.indexOf(".");
     return index < 0 ? null : name.substring(index);
   }

   private void sendBackAllowed(HttpRequest request, ChannelHandlerContext ctx) {
     HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

     response.setHeader("Server", ServerTag);
     response.setHeader("Allow", AllowHttpMethod);
     ChannelFuture future = ctx.getChannel().write(response);
     closeIfNeed(request, future);
   }

   private void sendNotModified(HttpRequest request, ChannelHandlerContext ctx) {
     HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);

     response.setHeader("Cache-Control", this.cacheControl);
     response.setHeader("Connection", "keep-alive");

     response.setHeader("Server", ServerTag);
     ChannelFuture future = ctx.getChannel().write(response);
     closeIfNeed(request, future);
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.nio.HttpImgFileServerHandler
 * JD-Core Version:    0.6.2
 */