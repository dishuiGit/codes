package com.eyeieye.koto.web.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eyeieye.koto.dao.store.StoreService;
import com.eyeieye.koto.domain.StoredEntry;

import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

@Controller
@RequestMapping({"/**/fs/*"})
public class FileDownloadAction {
    private static final String UnknowMimeType = "application/octet-stream";
    private MimeDetector mimeDetector = new ExtensionMimeDetector();

    @Value("${web.encoding}")
    private String webEncoding;

    @Autowired
    @Qualifier("fileStoreService")
    private StoreService storeService;
    private Map<String, String> extension2MimeType = new ConcurrentHashMap<String, String>();

    @RequestMapping({"/**/fs/{key}.*"})
    public void useRawId(@PathVariable("key") String key, HttpServletResponse response)
            throws IOException {
        StoredEntry sf = this.storeService.getStoredEntry(key);
        if (sf == null) {
            response.sendError(404);
            return;
        }

        Object nameAttribute = sf.getAppend("i_fn");
        String filename = nameAttribute == null ? key : nameAttribute.toString();

        response.setContentType(getMimeType(filename));

        String contentDisposition = "attachment; filename=" + URLEncoder.encode(filename, this.webEncoding);

        response.setHeader("Content-Disposition", contentDisposition);
        sf.writeTo(response.getOutputStream());
    }

    @RequestMapping({"/**/fs/{key}"})
    public void useId(@PathVariable("key") String key, HttpServletResponse response) throws IOException {
        useRawId(getId(key), response);
    }

    private String getId(String key) {
        int lastDot = key.lastIndexOf(46);
        if (lastDot == -1) {
            return key;
        }
        return key.substring(0, lastDot);
    }

    private String getMimeType(String name) {
        String extensionWithDot = getExtensionWithDot(name);
        if (extensionWithDot == null) {
            return "application/octet-stream";
        }
        String type = (String) this.extension2MimeType.get(extensionWithDot);
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
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.action.FileDownloadAction
 * JD-Core Version:    0.6.2
 */