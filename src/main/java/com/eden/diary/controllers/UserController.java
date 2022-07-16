package com.eden.diary.controllers;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;
import com.eden.diary.services.DiaryExceptions.InvalidIdentityException;
import com.eden.diary.services.DiaryExceptions.NullParameterException;
import com.eden.diary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j

public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/createAUser")
    public ResponseEntity<?> createAUser(@RequestParam String userName) {
        try {
            userService.createAUser(userName);
            var mess = userName + "'s" + " account has been created successfully!";
            return new ResponseEntity<>(mess, HttpStatus.CREATED);
        } catch (NullParameterException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createADiary/{userId}")
    public ResponseEntity<?> createADiary(@RequestParam String diaryName, @PathVariable String userId) {
        try {
            userService.createADiary(diaryName, userId);
            var mess = "Your diary with name " + diaryName + " has been created successfully!";
            return new ResponseEntity<>(mess, HttpStatus.CREATED);
        } catch (NullParameterException | InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteADiary/{userId}/{diaryId}")
    public ResponseEntity<?> deleteADiary(@PathVariable String userId, @PathVariable String diaryId) {
        try {
            userService.deleteADiary(userId, diaryId);
            var mess = "Your diary with id " + diaryId + " has been deleted successfully!";
            return new ResponseEntity<>(mess, HttpStatus.OK);
        } catch (InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createAnEntryInADiary/{diaryId}/{userId}")
    public ResponseEntity<?> createAnEntryInADiary(@RequestBody Entry anEntry, @PathVariable String diaryId, @PathVariable String userId) {
        try {
            userService.createAnEntryInADiary(anEntry, diaryId, userId);
            var message = "Your entry has been created successfully!";
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (NullParameterException | InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAnEntryInADiary/{entryId}/{diaryId}/{userId}")
    public ResponseEntity<?> deleteAnEntryInADiary(@PathVariable String entryId, @PathVariable String diaryId, @PathVariable String userId) {
        try {
            userService.deleteAnEntryInADiary(entryId, diaryId, userId);
            var message = "Your entry has been deleted successfully!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateADiary/{diaryId}/{userId}")
    public ResponseEntity<?> updateADiary(@RequestBody Diary aDiary, @PathVariable String diaryId, @PathVariable String userId) {
        try {
            userService.updateADiary(aDiary, diaryId, userId);
            var message = "Your diary has been updated successfully!";
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } catch (NullParameterException | InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateAnEntryInADiary{diaryId}/{entryId}/{userId}")
    public ResponseEntity<?> updateAnEntryInADiary(@PathVariable String diaryId, @PathVariable String entryId, @PathVariable String userId, @RequestBody Entry anEntry) {
        try {
            userService.updateAnEntryInADiary(diaryId, entryId, userId, anEntry);
            var message = "Your entry has been updated successfully!";
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } catch (NullParameterException | InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getADiary/{diaryId}/{userId}")
    public ResponseEntity<?> getADiary(@PathVariable String diaryId, @PathVariable String userId) {
        try {
            var diary = userService.getADiary(diaryId, userId);
            return new ResponseEntity<>(diary, HttpStatus.OK);
        } catch (InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAnEntryInADiary/{diaryId}/{entryId}/{userId}")
    public ResponseEntity<?> getAnEntryInADiary(@PathVariable String diaryId, @PathVariable String entryId, @PathVariable String userId) {
        try {
            var entry = userService.getAnEntryInADiary(diaryId, entryId, userId);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllDiaries/{userId}")
    public ResponseEntity<?> getAllDiaries(@PathVariable String userId) {
        try {
            var list = userService.getAllDiaries(userId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (InvalidIdentityException mess) {
            var message = mess.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }
}


