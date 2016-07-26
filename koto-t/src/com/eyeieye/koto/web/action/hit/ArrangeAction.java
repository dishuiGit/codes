package com.eyeieye.koto.web.action.hit;

import com.eyeieye.koto.dao.cache.hotsopt.HotspotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

 @Controller
 @RequestMapping({"/hit"})
public class ArrangeAction
 {

   @Autowired
   private HotspotManager hotspotManager;

   @RequestMapping({"/arrange"})
   public String arrange()
   {
     this.hotspotManager.arrange();
     return "redirect:/index.htm";
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.action.hit.ArrangeAction
 * JD-Core Version:    0.6.2
 */