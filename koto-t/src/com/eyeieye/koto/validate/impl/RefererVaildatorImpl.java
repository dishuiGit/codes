package com.eyeieye.koto.validate.impl;

import com.eyeieye.koto.validate.RefererVaildator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

 @Component
public class RefererVaildatorImpl
   implements RefererVaildator
 {
   private static final Log logger = LogFactory.getLog(RefererVaildatorImpl.class);

   @Value("${img.out.link.null.allow:true}")
   private boolean allowNullReferer = true;

   private String[] allowedDomain = new String[0];

   private Pattern[] allowedDomanPattern = new Pattern[0];

   @Value("${img.out.link.allows:}")
   public void setAllows(String allows)
   {
     if (StringUtils.isBlank(allows)) {
       return;
     }
     String[] allowStrings = StringUtils.split(allows, ',');
     if (allowStrings.length == 0) {
       return;
     }
     List domains = new ArrayList();
     List patterns = new ArrayList();
     for (String as : allowStrings)
       if (as.indexOf(42) == -1)
       {
         domains.add(as);
       }
       else
       {
         as = StringUtils.replace(as, "*", "[a-zA-Z0-9-]+");
         as = StringUtils.replace(as, ".", "\\.");
         as = "^" + as + "$";
         Pattern p = Pattern.compile(as);
         patterns.add(p);
       }
     if (!domains.isEmpty()) {
       this.allowedDomain = ((String[])domains.toArray(new String[domains.size()]));
     }
     if (!patterns.isEmpty()) {
       this.allowedDomanPattern = ((Pattern[])patterns.toArray(new Pattern[patterns.size()]));
     }

     if (logger.isDebugEnabled()) {
       logger.debug("allowdDomains:" + Arrays.asList(this.allowedDomain));
       logger.debug("allowedDomanPattern:" + Arrays.asList(this.allowedDomanPattern));
     }
   }

   public boolean isForbiddenRequest(HttpRequest request)
   {
     String referer = request.getHeader("Referer");
     if (StringUtils.isBlank(referer))
     {
       return !this.allowNullReferer;
     }
     URL url = null;
     try {
       url = new URL(referer);
     } catch (MalformedURLException ignore) {
       return false;
     }
     String host = url.getHost();
     for (String domain : this.allowedDomain) {
       if (domain.equals(host)) {
         return false;
       }
     }
     for (Pattern p : this.allowedDomanPattern) {
       if (p.matcher(host).find()) {
         return false;
       }
     }
     return true;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.validate.impl.RefererVaildatorImpl
 * JD-Core Version:    0.6.2
 */