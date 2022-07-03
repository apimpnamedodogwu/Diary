package com.eden.diary.controllers;

import com.eden.diary.models.Entry;
import com.eden.diary.services.DiaryExceptions.InvalidIdentityException;
import com.eden.diary.services.DiaryExceptions.NullParameterException;
import com.eden.diary.services.EntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/entry")
@Slf4j
public class EntryController {
    @Autowired
    EntryService entryService;

    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody Entry entry) {
        try {
            var message = entryService.addEntry(entry);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/edit/title/{id}")
    public ResponseEntity<?> editTitle(@PathVariable String id, @RequestBody Entry entry) {
        System.out.println("id is " + id);
        try {
            entryService.updateEntryTitle(id, entry.getTitle());
            var message = "Successful!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/edit/body/{id}")
    public ResponseEntity<?> editBody(@PathVariable String id, @RequestBody Entry entry) {
        log.info("edit body -->{}", entry);
        try {
            var message = entryService.updateEntryBody(id, entry.getBody());
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } catch (InvalidIdentityException | NullParameterException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public List<Entry> searchEntries(@RequestParam String phrase) {
        return entryService.searchEntries(phrase);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnEntry(@PathVariable String id) {
        try {
            entryService.deleteEntry(id);
            var message = "Your entry has been successfully deleted!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InvalidIdentityException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findEntry/{id}")
    public ResponseEntity<?> findAnEntryById(@PathVariable String id) {
        try {
            var entry = entryService.getEntryById(id);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

    }

    @GetMapping("/findEntry/title")
    public ResponseEntity<?> findAnEntryByTitle(@RequestParam String title) {
        try {
            var result = entryService.getEntryByTitle(title);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllEntries")
    public ResponseEntity<?> getAllEntries() {
        var entries = entryService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

}
