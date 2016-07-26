package com.eyeieye.koto.service.impl;

import com.eyeieye.koto.domain.BytesStoredImage;
import com.eyeieye.koto.domain.StoredImage;
import com.eyeieye.koto.remote.UploadSource;
import com.eyeieye.koto.service.ImageThumbService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class JvmImageThumbServiceImpl
   implements ImageThumbService
 {
   private float jpgQuality = 0.95F;

   public StoredImage getImageThumb(StoredImage source, Integer newWidth, Integer newHeight)
     throws IOException
   {
     if (source == null) {
       throw new NullPointerException("image source can't be null.");
     }
     if (newWidth.intValue() < 0) {
       throw new IllegalArgumentException("newWidth must greate than 0.");
     }
     if ((newHeight != null) && (newHeight.intValue() < 0)) {
       throw new IllegalArgumentException("newHeight must greate than 0.");
     }
     String imgFormat = source.getFormat();

     Image src = ImageIO.read(source.getBodyInputStream());
     int srcWidth = src.getWidth(null);
     int srcHeight = src.getHeight(null);

     int[] draw = getDrawSize(srcWidth, srcHeight, newWidth, newHeight);
     if (newHeight == null) {
       newHeight = Integer.valueOf(draw[1]);
     }

     Image get = src.getScaledInstance(draw[0], draw[1], 16);

     BufferedImage tag = new BufferedImage(newWidth.intValue(), newHeight.intValue(), 1);

     Graphics2D graphics = tag.createGraphics();

     if (isSupportTransparent(imgFormat)) {
       tag = graphics.getDeviceConfiguration().createCompatibleImage(newWidth.intValue(), newHeight.intValue(), 3);

       graphics.dispose();
       graphics = tag.createGraphics();
     } else {
       graphics.setColor(Color.WHITE);
       graphics.fillRect(0, 0, newWidth.intValue(), newHeight.intValue());
     }

     graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

     graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

     graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

     graphics.drawImage(get, getCenterPos(newWidth.intValue(), draw[0]), getCenterPos(newHeight.intValue(), draw[1]), null);

     graphics.dispose();

     float[] kernelData2 = { -0.125F, -0.125F, -0.125F, -0.125F, 2.0F, -0.125F, -0.125F, -0.125F, -0.125F };

     Kernel kernel = new Kernel(3, 3, kernelData2);
     ConvolveOp cOp = new ConvolveOp(kernel, 1, null);
     tag = cOp.filter(tag, null);

     ByteArrayOutputStream out = new ByteArrayOutputStream();
     writeImg(tag, imgFormat, out);

     BytesStoredImage back = new BytesStoredImage();
     back.setBody(out.toByteArray());
     back.setFormat(source.getFormat());
     return back;
   }

   private void writeImg(BufferedImage image, String imgFormat, ByteArrayOutputStream out)
     throws IOException
   {
     if ((imgFormat.equals("jpg")) || (imgFormat.equals("jpeg"))) {
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
       JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
       param.setQuality(this.jpgQuality, true);
       encoder.setJPEGEncodeParam(param);
       encoder.encode(image);
       return;
     }
     ImageIO.write(image, imgFormat, out);
   }

   private int[] getDrawSize(int srcWidth, int srcHeight, Integer newWidth, Integer newHeight)
   {
     if (newHeight == null)
     {
       newHeight = Integer.valueOf((int)(srcHeight * newWidth.intValue() / srcWidth));
       return new int[] { newWidth.intValue(), newHeight.intValue() };
     }

     double widthScale = srcWidth / newWidth.intValue();

     double heightScale = srcHeight / newHeight.intValue();
     if (widthScale == heightScale)
     {
       return new int[] { newWidth.intValue(), newWidth.intValue() };
     }
     if (widthScale > heightScale) {
       return new int[] { newWidth.intValue(), divide(srcHeight, widthScale) };
     }
     return new int[] { divide(srcWidth, heightScale), newHeight.intValue() };
   }

   private static final int divide(int a, double b) {
     return Double.valueOf(a / b).intValue();
   }

   private static final boolean isSupportTransparent(String format) {
     return format.equals("png");
   }

   private static final int getCenterPos(int size, int drawSzie) {
     if (size == drawSzie) {
       return 0;
     }
     return (size - drawSzie) / 2;
   }

   public float getJpgQuality()
   {
     return this.jpgQuality;
   }

   public void setJpgQuality(float jpgQuality) {
     this.jpgQuality = jpgQuality;
   }

   public String preHanleAndGetFormat(UploadSource imgFile) throws IOException
   {
     if (imgFile == null) {
       throw new NullPointerException("imgFile can't be null.");
     }
     byte[] body = imgFile.getBody();
     if ((body == null) || (body.length == 0)) {
       throw new IllegalArgumentException("image body can't be null or empty.");
     }

     ByteArrayInputStream imgInputStream = new ByteArrayInputStream(body);
     ImageInputStream iis = null;
     try {
       iis = ImageIO.createImageInputStream(imgInputStream);
       Iterator ir = ImageIO.getImageReaders(iis);
       String format;
       while (ir.hasNext()) {
         format = ((ImageReader)ir.next()).getFormatName();
         if (format != null) {
           return format.trim().toLowerCase();
         }
       }
       return null;
     } finally {
       if (iis != null)
         try {
           iis.close();
         }
         catch (IOException ignore)
         {
         }
     }
   }

   public StoredImage cutImage(StoredImage source, Integer width, Integer height, String isGray)
   {
     return null;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.service.impl.JvmImageThumbServiceImpl
 * JD-Core Version:    0.6.2
 */