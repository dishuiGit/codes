package com.eyeieye.koto.web.action.upload;

import com.eyeieye.koto.remote.UploadResult;
import com.eyeieye.koto.remote.UploadSource;
import com.eyeieye.koto.remote.file.FileService;
import com.eyeieye.koto.remote.img.ImageService;
import com.eyeieye.koto.remote.img.StoredImgResult;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

 @Controller
 @RequestMapping({"/upload"})
public class UploadAction
 {

   @Autowired
   private ImageService imageService;

   @Autowired
   private FileService fileService;

   @RequestMapping(value={"/upimg"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public void img()
     throws IOException
   {
   }

   @RequestMapping(value={"/upimg"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String upImg(@RequestParam("upfile") MultipartFile upfile, ModelMap model)
     throws IOException
   {
     UploadSource source = new UploadSource();
     source.setBody(upfile.getBytes());
     source.setFilename(upfile.getOriginalFilename());

     source.append("upload_by", "/upload/img.htm");

     UploadResult result = this.imageService.store(source);
     model.addAttribute("result", result);
     return "/upload/show_imgs";
   }

   @RequestMapping({"/removeimg"})
   public String removeImg(@RequestParam("path") String url) {
     this.imageService.removeByPath(url);
     return "redirect:../index.htm";
   }

   @RequestMapping({"/images"})
   public void images(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="size", defaultValue="20") int pageSize, ModelMap model)
   {
     int start = (page - 1) * pageSize;
     int end = page * pageSize;
     StoredImgResult result = this.imageService.findStoredImages(null, start, end);

     model.addAttribute("result", result);
     int totalPage = result.getTotal() / pageSize;
     if (result.getTotal() % pageSize > 0) {
       totalPage++;
     }
     model.addAttribute("totalPage", Integer.valueOf(totalPage));
     model.addAttribute("page", Integer.valueOf(page));
     model.addAttribute("pageSize", Integer.valueOf(pageSize));
   }

   @RequestMapping(value={"/upfile"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public void file() throws IOException
   {
   }

   @RequestMapping(value={"/upfile"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String upFile(@RequestParam("upfile") MultipartFile upfile, ModelMap model) throws IOException
   {
     UploadSource source = new UploadSource();
     source.setBody(upfile.getBytes());
     source.setFilename(upfile.getOriginalFilename());
     UploadResult result = this.fileService.store(source);
     model.addAttribute("result", result);
     return "/upload/show_files";
   }

   @RequestMapping({"/removefile"})
   public String removeFile(@RequestParam("path") String url) {
     this.fileService.remove(url);
     return "redirect:../index.htm";
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.action.upload.UploadAction
 * JD-Core Version:    0.6.2
 */