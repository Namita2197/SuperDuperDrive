package com.udacity.jwdnd.course1.cloudstorage.Controllers;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credential")
    public String postCredential(Credential credential, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        credential.setUserid(user.getUserid());
        credential.setKey(credentialService.createKey());
        String encryptedPassword = credentialService.encryptPassword(credential.getPassword(), credential.getKey());

        credential.setPassword(encryptedPassword);
        if(credential.getCredentialid()!=null){
            if(credentialService.updateCredential(credential)>0){
                return "redirect:/result?success";
            } else return "redirect:/result?error";
        }else{
            if(credentialService.addCredential(credential)>0){
                return "redirect:/result?success";
            } else return "redirect:/result?error";
        }
    }

    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam int credentialid){
        int val = credentialService.deleteCredential(credentialid);
        if(val>0){
            return "redirect:/result?success";
        }else{
            return "redirect:/result?error";
        }
    }

}
