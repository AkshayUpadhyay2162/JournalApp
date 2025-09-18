package com.akkitech.journalApp.controller;

import com.akkitech.journalApp.entity.JournalEntry;
import com.akkitech.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @GetMapping("/all")
    public List<JournalEntry> getAll(){
        return journalService.getAllJournalEntries();
    }

    @GetMapping("/{id}")
    public JournalEntry getJournalById(@PathVariable ObjectId id){
        return journalService.getJournalById(id).orElse(null);
    }

    @PostMapping("/create")
    public String createJournalEntry(@RequestBody JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        return journalService.addJournalEntry(entry);
        }

    @PutMapping("/update/{id}")
    public String updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry entry){
        return journalService.updateJournalEntry(id, entry);
    }

    @DeleteMapping("/{id}")
    public String deleteJournalById(@PathVariable ObjectId id){
        return journalService.deleteJournal(id);
    }
}
