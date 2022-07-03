package com.eden.diary.services;

import com.eden.diary.models.Entry;
import com.eden.diary.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;


    @Override
    public String addEntry(Entry entry) {
        if (entry.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (entry.getBody().isEmpty()) {
            throw new IllegalArgumentException("Body cannot be empty");
        }
        var existingEntry = entryRepository.findEntryByTitle(entry.getTitle());
        if (existingEntry.isPresent()) {
            throw new IllegalArgumentException("This title " + entry.getTitle() + " already exists!");
        }
        entryRepository.save(entry);
        return "Saved successfully";
    }

    @Override
    public String deleteEntry(String id) {
        var validatedEntry = checkOpt(id);
        entryRepository.delete(validatedEntry);
        return "Entry with id number " + id + " has been deleted successfully!";
    }

    @Override
    public void updateEntryTitle(String id, String title) {
        var validatedEntry = checkOpt(id);
        validatedEntry.setTitle(title);
        entryRepository.save(validatedEntry);

    }

    private Entry checkOpt(String id) {
        var entryOptional = entryRepository.findById(id);
        if (entryOptional.isPresent()) {
            return entryOptional.get();
        } else {
            throw new IllegalArgumentException("No entry found with id");
        }
    }

    @Override
    public Entry updateEntryBody(String id, String body) {
        var validatedEntry = checkOpt(id);
        validatedEntry.setBody(body);
        return entryRepository.save(validatedEntry);
    }

    @Override
    public List<Entry> searchEntries(String phrase) {
        return entryRepository.findEntriesByBodyContainingIgnoreCase(phrase);
    }

    @Override
    public Entry getEntryById(String id) {
        return checkOpt(id);
    }

    @Override
    public Entry getEntryByTitle(String title) {
        var anEntry = entryRepository.findEntryByTitle(title);
        if (anEntry.isPresent()) {
            return anEntry.get();
        } else {
            throw new IllegalArgumentException("Entry with title " + title + " does not exist!");
        }

    }


    @Override
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }
}
