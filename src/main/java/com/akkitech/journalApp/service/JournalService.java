package com.akkitech.journalApp.service;

import com.akkitech.journalApp.entity.JournalEntry;
import com.akkitech.journalApp.entity.User;
import com.akkitech.journalApp.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public String addJournalEntry(JournalEntry entry, String username) {
        try {
            User user = userService.findUserByUserName(username);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepo.save(entry);
            user.getJournals().add(saved);
            userService.addUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return "Journal entry added";
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalRepo.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id) {
        return journalRepo.findById(id);
    }

    public String updateJournalEntry(ObjectId id, JournalEntry newEntry) {
        JournalEntry oldEntry = journalRepo.findById(id).orElse(null);
        if(oldEntry!=null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
        }
        journalRepo.save(oldEntry);
        return "Journal entry updated.";
    }

    @Transactional
    public String deleteJournal(ObjectId id, String username) {
        User user = userService.findUserByUserName(username);
        user.getJournals().removeIf(entry -> entry.getId().equals(id));
        userService.addUser(user);
        journalRepo.deleteById(id);
        return "Journal entry deleted";
    }
}
