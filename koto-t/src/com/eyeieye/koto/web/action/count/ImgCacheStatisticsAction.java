package com.eyeieye.koto.web.action.count;

import com.eyeieye.koto.dao.cache.CachedImgBlockSatistics;
import com.eyeieye.koto.dao.cache.CachedImgStatistics;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

 @Controller
 @RequestMapping({"/count"})
public class ImgCacheStatisticsAction
 {

   @Autowired
   @Qualifier("memoryCache")
   private CachedImgStatistics memoryStatistics;

   @Autowired
   @Qualifier("diskCache")
   private CachedImgStatistics fileStatistics;

   @RequestMapping({"/img_sta"})
   public void imgStatistics(ModelMap model)
   {
     model.addAttribute("memory", this.memoryStatistics.getBlockStatistics());
     List sats = this.fileStatistics.getBlockStatistics();

     CachedImgBlockSatistics sum = new CachedImgBlockSatistics(sats);
     model.addAttribute("stas", sats);
     model.addAttribute("sum", sum);
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.action.count.ImgCacheStatisticsAction
 * JD-Core Version:    0.6.2
 */