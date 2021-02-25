package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public String addFile(MultipartFile fileUpload, int userid) throws IOException {
        if(findfileByName(fileUpload.getOriginalFilename())){
            try{
                File file = new File(null,fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), userid, fileUpload.getBytes());
                if(fileMapper.insertFile(file)>0){
                    return "redirect:/result?success";
                }else return "redirect:/result?error";
            }catch(IOException e){
                throw e;
            }
        }else{
            return "redirect:/result?already";
        }
    }

    public File getFileByName(String filename){
        return fileMapper.findFileByName(filename);
    }

    public boolean findfileByName(String filename){
        if(fileMapper.findFileByName(filename)==null){
            return true;
        }else{
            return false;
        }
    }

    public int deleteFile(int fileId){
        return this.fileMapper.deleteFile(fileId);
    }
    public List<File> getAllFilesByUser(int userid){
        return this.fileMapper.findFileForUser(userid);
    }

}
