package com.udacity.jwdnd.course1.cloudstorage.Controllers;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;


    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(Note note, Credential credential, Model model, Authentication authentication, EncryptionService encryptionService) throws Exception {
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("allNotes", noteService.getNotes(user));
        model.addAttribute("allCredentials", credentialService.getCredentials(user));
        model.addAttribute("encryptionService",encryptionService);
        model.addAttribute("allFiles",fileService.getAllFilesByUser(user.getUserid()));
        return "home";
    }

}
