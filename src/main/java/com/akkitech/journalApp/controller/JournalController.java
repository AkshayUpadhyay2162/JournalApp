package com.akkitech.journalApp.controller;

import com.akkitech.journalApp.entity.JournalEntry;
import com.akkitech.journalApp.entity.User;
import com.akkitech.journalApp.service.JournalService;
import com.akkitech.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

//  Get all journal entries of a user by username
    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.findUserByUserName(username);
        List<JournalEntry> allEntries = user.getJournals();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalService.getJournalById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create/{username}")
    public ResponseEntity<?> createJournalEntry(@PathVariable String username, @RequestBody JournalEntry entry){
        try {
            return new ResponseEntity<>(journalService.addJournalEntry(entry, username), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{username}/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry entry, @PathVariable String username){
        try {
            return new ResponseEntity<>(journalService.updateJournalEntry(id, entry), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id, @PathVariable String username){
        try {
            return new ResponseEntity<>(journalService.deleteJournal(id, username), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
