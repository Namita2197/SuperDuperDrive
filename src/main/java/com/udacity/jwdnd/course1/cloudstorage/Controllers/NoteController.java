package com.udacity.jwdnd.course1.cloudstorage.Controllers;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam int noteid){
            int val = noteService.deleteNote(noteid);
            if(val>0){
                return "redirect:/result?success";
            }else{
                return "redirect:/result?error";
            }
    }

    @PostMapping("/note")
    public String postNotes(Note note, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        note.setUserid(user.getUserid());
        if (note.getNoteid()!= null) {
            int val = noteService.updateNote(note);
            if (val > 0) {
                return "redirect:/result?success";
            }else if(val==0){
                return "redirect:/result?errorLimit";
            }else return "redirect:/result?error";
        } else {
            int val = noteService.addNote(note);
            if ( val> 0) {
                return "redirect:/result?success";
            }else if( val==0){
                return "redirect:/result?errorLimit";
            } else return "redirect:/result?error";

        }
    }
}
