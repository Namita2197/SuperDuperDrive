package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note){
        if(note.getNotedescription().length()>1000){
            return 0;
        }
        return this.noteMapper.insert(note);
    }
    public int updateNote(Note note){
        if(note.getNotedescription().length()>1000){
            return 0;
        }
        return this.noteMapper.update(note);
    }
    public int deleteNote(int noteid){
        return this.noteMapper.delete(noteid);
    }



    public List<Note> getNotes(User user) throws Exception {
        List<Note> notes = noteMapper.getAllNotesFromUser(user);
        if (notes == null) {
            throw new Exception();
        }
        return notes;
    }
}
