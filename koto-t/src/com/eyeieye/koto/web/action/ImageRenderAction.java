package com.eyeieye.koto.web.action;

import com.eyeieye.koto.common.FixsizeByteArrayOutputStream;
import com.eyeieye.koto.common.ImgType;
import com.eyeieye.koto.dao.cache.ImageCache;
import com.eyeieye.koto.dao.store.StoreService;
import com.eyeieye.koto.domain.BytesStoredImage;
import com.eyeieye.koto.domain.StoredEntry;
import com.eyeieye.koto.domain.StoredImage;
import com.eyeieye.koto.service.ImageThumbService;
import com.eyeieye.koto.validate.ShapeSizeValidator;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/**/img/*.img"})
public class ImageRenderAction {

    @Autowired
    @Qualifier("imgStoreService")
    private StoreService storeService;

    @Autowired
    private ImageCache imageCache;

    @Autowired
    private ImageThumbService imageThumbService;

    @Autowired
    private ShapeSizeValidator shapeSizeValidator;
    private static final Pattern urlPattern = Pattern.compile("^([a-zA-Z\\d]+)(\\.img_(\\d+)_?(\\d*))?$");

    private static final Pattern urlPattern2 = Pattern.compile("^([a-zA-Z\\d]+)(\\.img_(\\d+)_?(\\d*)?_(\\d))?$");

    private static final Pattern urlPattern3 = Pattern.compile("^([a-zA-Z\\d]+)(\\.img_(\\d+)_?(\\d*)?_(\\d)?_(\\d))?$");

    @RequestMapping({"/**/img/{key}.img"})
    public void useId(@PathVariable("key") String key, HttpServletResponse response)
            throws IOException, InterruptedException, IM4JavaException {
        Matcher m = urlPattern.matcher(key);
        Matcher m2 = urlPattern2.matcher(key);
        Matcher m3 = urlPattern3.matcher(key);
        Boolean b = Boolean.valueOf(m.find());
        Boolean b2 = Boolean.valueOf(m2.find());
        Boolean b3 = Boolean.valueOf(m3.find());
        if ((!b.booleanValue()) && (!b2.booleanValue()) && (!b3.booleanValue())) {
            response.sendError(400);
            return;
        }
        if (b.booleanValue()) {
            String id = m.group(1);
            String widthString = m.group(3);
            String isCutImage = null;
            String isGray = null;
            if (StringUtils.isBlank(widthString)) {
                original(id, response);
            } else {
                Integer width = Integer.valueOf(Integer.parseInt(widthString));
                String heightString = m.group(4);
                Integer height = StringUtils.isBlank(heightString) ? null : Integer.valueOf(Integer.parseInt(m.group(4)));

                thumbnail(id, width, height, response, isCutImage, isGray);
            }

        } else if (b2.booleanValue()) {
            String id = m2.group(1);
            String widthString = m2.group(3);
            String isCutImage = m2.group(5);
            String isGray = null;
            if (StringUtils.isBlank(widthString)) {
                original(id, response);
            } else {
                Integer width = Integer.valueOf(Integer.parseInt(widthString));
                String heightString = m2.group(4);
                Integer height = StringUtils.isBlank(heightString) ? null : Integer.valueOf(Integer.parseInt(m2.group(4)));

                if (this.shapeSizeValidator.isAllowed(width, height)) {
                    thumbnail(id, width, height, response, isCutImage, isGray);
                } else {
                    response.sendError(400);
                    return;
                }
            }

        } else if (b3.booleanValue()) {
            String id = m3.group(1);
            String widthString = m3.group(3);
            String isCutImage = m3.group(5);
            String isGray = m3.group(6);

            if (StringUtils.isBlank(widthString)) {
                original(id, response);
            } else {
                Integer width = Integer.valueOf(Integer.parseInt(widthString));
                String heightString = m3.group(4);
                Integer height = StringUtils.isBlank(heightString) ? null : Integer.valueOf(Integer.parseInt(m3.group(4)));

                if (this.shapeSizeValidator.isAllowed(width, height)) {
                    thumbnail(id, width, height, response, isCutImage, isGray);
                } else {
                    response.sendError(400);
                    return;
                }
            }
        }
    }

    private void original(String id, HttpServletResponse response)
            throws IOException {
        StoredImage image = getImageById(id, true);
        if (image == null) {
            response.sendError(404);
            return;
        }
        response.setContentType(ImgType.valueOf(image.getFormat()).getMineType());

        image.write(response.getOutputStream());
    }

    private StoredImage getImageById(String key, boolean putToCache) throws IOException {
        StoredImage ci = this.imageCache.get(key);
        if (ci != null) {
            return ci;
        }

        StoredEntry sf = this.storeService.getStoredEntry(key);
        if (sf == null) {
            return null;
        }
        BytesStoredImage bsi = new BytesStoredImage();
        bsi.setFormat(sf.getAppend("i_tp").toString());
        FixsizeByteArrayOutputStream bos = new FixsizeByteArrayOutputStream(sf.getLength());

        sf.writeTo(bos);
        bsi.setBody(bos.toByteArray());
        if (putToCache) {
            this.imageCache.put(key, bsi);
        }
        return bsi;
    }

    private void thumbnail(String id, Integer width, Integer height, HttpServletResponse response, String isCutImage, String isGray)
            throws IOException, InterruptedException, IM4JavaException {
        StringBuilder key = new StringBuilder(id).append('_').append(width);

        if (height != null) {
            key.append('_').append(height);
        }
        String thumbnailKey = key.toString();

        StoredImage ci = this.imageCache.get(thumbnailKey);
        if (ci != null) {
            response.setContentType(ImgType.valueOf(ci.getFormat()).getMineType());

            ci.write(response.getOutputStream());
            return;
        }

        StoredImage source = this.imageCache.get(id);
        if (source == null) {
            source = getImageById(id, false);
            if (source == null) {
                response.sendError(404);
                return;
            }
        }

        if (("1".equalsIgnoreCase(isCutImage)) && ("1".equalsIgnoreCase(isGray))) {
            StoredImage thumb = this.imageThumbService.cutImage(source, width, height, isGray);

            this.imageCache.put(thumbnailKey, thumb);
            response.setContentType(ImgType.valueOf(thumb.getFormat()).getMineType());

            thumb.write(response.getOutputStream());
        } else if ("1".equalsIgnoreCase(isCutImage)) {
            StoredImage thumb = this.imageThumbService.cutImage(source, width, height, null);

            this.imageCache.put(thumbnailKey, thumb);
            response.setContentType(ImgType.valueOf(thumb.getFormat()).getMineType());

            thumb.write(response.getOutputStream());
        } else {
            StoredImage thumb = this.imageThumbService.getImageThumb(source, width, height);

            this.imageCache.put(thumbnailKey, thumb);
            response.setContentType(ImgType.valueOf(thumb.getFormat()).getMineType());

            thumb.write(response.getOutputStream());
        }
    }
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.action.ImageRenderAction
 * JD-Core Version:    0.6.2
 */