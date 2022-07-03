package com.eden.diary.controllers;

import com.eden.diary.models.Entry;
import com.eden.diary.services.DiaryExceptions.InvalidIdentityException;
import com.eden.diary.services.DiaryExceptions.NullParameterException;
import com.eden.diary.services.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diary")
@Slf4j

public class DiaryController {
    @Autowired
    DiaryService diaryService;

    @PostMapping("/create")
    public ResponseEntity<?> createDiary(@RequestParam String name) {
        try {
            diaryService.createADiary(name);
            var message = "Diary created successfully!";
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (NullParameterException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDiary(@PathVariable String id) {
        try {
            diaryService.deleteADiary(id);
            var message = "Diary was deleted successfully!";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } catch (InvalidIdentityException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addAnEntry/{diaryId}")
    public ResponseEntity<?> addAnEntryToADiary(@RequestBody Entry anEntry, @PathVariable String diaryId) {
        try {
            diaryService.addAnEntryInADiary(anEntry, diaryId);
            var message = "Your entry has been added to your diary successfully!";
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (InvalidIdentityException | NullParameterException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAnEntry/{entryId}/{diaryId}")
    public ResponseEntity<?> getAnEntryInADiary(@PathVariable String entryId, @PathVariable String diaryId) {
        try {
            var entry = diaryService.getAnEntry(entryId, diaryId);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (InvalidIdentityException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAnEntry/{entryId}/{diaryId}")
    public ResponseEntity<?> deleteAnEntryInADiary(@PathVariable String entryId, @PathVariable String diaryId) {
        try {
            diaryService.deleteAnEntryInADiary(entryId, diaryId);
            var message = "Your entry has been deleted successfully!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InvalidIdentityException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllEntries/{diaryId}")
    public ResponseEntity<?> getAllEntriesInADiary(@PathVariable String diaryId) {
        try {
            var entry = diaryService.getAllEntriesInADiary(diaryId);
            return new ResponseEntity<>(entry, HttpStatus.FOUND);
        } catch (InvalidIdentityException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateAnEntry/{entryId}/{diaryId}")
    public ResponseEntity<?> updateAnEntryInADiary(@PathVariable String entryId, @PathVariable String diaryId, @RequestBody Entry entry) {
        try {
            log.info("update an entry -->{}/{}", entry, diaryId);
            diaryService.updateAnEntryInADiary(entryId, diaryId, entry);
            var message = "Your Entry has been successfully updated!";
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } catch (InvalidIdentityException | NullParameterException e) {
            var message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}