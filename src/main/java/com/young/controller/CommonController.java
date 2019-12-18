package com.young.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/toSuccess")
    public ModelAndView toSuccess(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/success");
    }

    @RequestMapping("/toError")
    public ModelAndView toError(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/error");
    }

    @RequestMapping("/visit")
    public ModelAndView visit(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/case");
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map uploadFile(MultipartFile fileUpload, HttpServletRequest request) {
        Map result = new HashMap();
        String basePath = request.getServletContext().getRealPath("/data/");
        File temp = new File(basePath);
        if (!temp.exists()) temp.mkdirs();
        String fileName = fileUpload.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String saveFileName = System.currentTimeMillis() + suffix;
        File saveFile = new File(basePath + File.separator + saveFileName);
        try {
            fileUpload.transferTo(saveFile);
            FileUtils.copyFile(saveFile, new File("D:" + File.separator + "imgFile" + File.separator + saveFileName));
            result.put("code", 1);
            result.put("fileName", saveFileName);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
        }
        return result;
    }


}
