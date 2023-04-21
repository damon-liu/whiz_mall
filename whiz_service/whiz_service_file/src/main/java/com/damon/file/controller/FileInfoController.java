package com.damon.file.controller;

import com.damon.file.pojo.FileInfo;
import com.damon.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * <pre>
 *  前端控制器
 * </pre>
 *
 * @author damon.liu
 * @since 2021-04-20
 */

@RestController
@RequestMapping("/file")
public class FileInfoController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public FileInfo upload(@RequestParam("file") MultipartFile file) throws Exception {
        return fileService.upload(file);
    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        fileService.delete(id);
    }

    // @GetMapping("/info/{id}")
    // public FileInfo getFileInfo(@PathVariable("id") String id) {
    //     return fileService.getFileInfo(id);
    // }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            OutputStream out = response.getOutputStream();
            FileInfo fileInfoDm = fileService.out(id, out);
            response.setContentType("application/octet-stream");
            String resourceName = fileInfoDm.getName();
            int index = resourceName.lastIndexOf(".");
            String fileName = resourceName.substring(0, index);
            String fileSuffix = resourceName.substring(index);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + fileSuffix);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

