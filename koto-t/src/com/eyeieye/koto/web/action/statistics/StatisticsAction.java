package com.eyeieye.koto.web.action.statistics;

import com.eyeieye.koto.service.ImageStatisticsService;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

 @Controller
 @RequestMapping({"/statistics"})
public class StatisticsAction
 {

   @Autowired
   private ImageStatisticsService imageStatisticsService;

   @RequestMapping({"/statistics.htm"})
   public void statistics(ModelMap model)
   {
     model.addAttribute("reports", this.imageStatisticsService.getRecentStatistics());

     model.addAttribute("format", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
   }

   @RequestMapping({"/record.htm"})
   public String record() {
     this.imageStatisticsService.record();
     return "redirect:statistics.htm";
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.action.statistics.StatisticsAction
 * JD-Core Version:    0.6.2
 */