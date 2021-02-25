package com.udacity.jwdnd.course1.cloudstorage.Controllers;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String postFile(@RequestParam("fileUpload") MultipartFile fileUpload,Authentication authentication) throws IOException {
        if(fileUpload.isEmpty()){
            return "redirect:/result?error";
        }else{
            String name = authentication.getName();
            User user = userService.getUser(name);
            return fileService.addFile(fileUpload, user.getUserid());
        }
    }
    @GetMapping("/file/download")
    public ResponseEntity viewFile(@RequestParam String filename){
        File file = fileService.getFileByName(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(  file.getContenttype() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));

    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam int fileId){
        int val = fileService.deleteFile(fileId);
        if(val>0){
            return "redirect:/result?success";
        }else{
            return "redirect:/result?error";
        }
    }
}
