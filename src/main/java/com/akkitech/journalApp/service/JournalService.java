package com.akkitech.journalApp.service;

import com.akkitech.journalApp.entity.JournalEntry;
import com.akkitech.journalApp.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private JournalRepo journalRepo;

    public String addJournalEntry(JournalEntry entry) {
        journalRepo.save(entry);
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

    public String deleteJournal(ObjectId id) {
        journalRepo.deleteById(id);
        return "Journal entry deleted";
    }
}
