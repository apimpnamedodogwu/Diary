package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;
import com.eden.diary.models.User;
import com.eden.diary.repositories.DiaryRepository;
import com.eden.diary.repositories.EntryRepository;
import com.eden.diary.repositories.UserRepository;
import com.eden.diary.services.DiaryExceptions.InvalidIdentityException;
import com.eden.diary.services.DiaryExceptions.NullParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;
    DiaryRepository diaryRepository;
    EntryRepository entryRepository;


    private Diary validateDiaryId(Diary diaryId) {
        return diaryRepository.findDiaryById(diaryId).orElseThrow(() -> new InvalidIdentityException("Diary with " + diaryId + " does not exist!"));
    }

    private User validateUserId(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(() -> new NullParameterException("User with " + userId + " does not exist!"));
    }

    private Entry validateEntry(Entry entry) {
        if (entry.getTitle().isEmpty() | entry.getBody().isEmpty()) {
            throw new NullParameterException("This field cannot be empty!");
        } else return entry;
    }

    private String nameValidation(String name) {
        if (name.isEmpty()) {
            throw new NullParameterException("This field cannot be empty!");
        } else return name;
    }

    private Entry validateEntryId(Entry entryId) {
        return entryRepository.findEntryById(entryId).orElseThrow(() -> new InvalidIdentityException("Entry with " + entryId + " does not exist!"));
    }

    private Diary validateDiary(Diary diary) {
        if (diary.getName().isEmpty()) {
            throw new NullParameterException("This field cannot be empty!");
        } else return diary;
    }

    @Override
    public void createAUser(String userName) {
        var name = nameValidation(userName);
        User myUser = new User();
        myUser.setName(name);
        userRepository.save(myUser);
    }

    @Override
    public void createADiary(String name, String userId) {
        var diaryName = nameValidation(name);
        var user = validateUserId(userId);
        if (Objects.equals(user.getUserId(), userId)) {
            Diary myDiary = new Diary();
            myDiary.setName(diaryName);
            diaryRepository.save(myDiary);
        }

    }

    @Override
    public void deleteADiary(String userId, Diary diaryId) {
        var foundDiary = validateDiaryId(diaryId);
        var userToDel = validateUserId(userId);
        var userDiary = userToDel.getDiaries();
        for (int index = 0; index < userDiary.size(); index++) {
            if (Objects.equals(foundDiary.getId(), diaryId.getId()) && Objects.equals(userToDel.getUserId(), userId)) {
                diaryRepository.delete(foundDiary);
                return;
            }
        }
        throw new InvalidIdentityException("Diary with " + diaryId + " does not exist!");
    }

    @Override
    public void createAnEntryInADiary(Entry entry, Diary diaryId, String userId) {
        var user = validateUserId(userId);
        var foundDiary = validateDiaryId(diaryId);
        var userEntry = validateEntry(entry);
        var entryList = foundDiary.getEntries();
        for (int index = 0; index < entryList.size(); index++) {
            if (Objects.equals(user.getUserId(), userId) && Objects.equals(foundDiary.getId(), diaryId.getId())) {
                var savEntry = entryRepository.save(userEntry);
                foundDiary.getEntries().add(savEntry);
                diaryRepository.save(foundDiary);
                return;
            }
        }
        throw new InvalidIdentityException("Diary with " + diaryId + " does not exist!");
    }

    @Override
    public void deleteAnEntryInADiary(Entry entryId, Diary diaryId, String userId) {
        var entryExists = validateEntryId(entryId);
        var diaryExists = validateDiaryId(diaryId);
        var userExists = validateUserId(userId);
        var listOfDiaries = diaryExists.getEntries();
        for (int index = 0; index < listOfDiaries.size(); index++) {
            if (Objects.equals(diaryExists.getId(), diaryId.getId()) && Objects.equals(userExists.getUserId(), userId) && Objects.equals(entryExists.getId(), entryId.getId())) {
                entryRepository.delete(entryId);
                return;
            }
        }
        throw new InvalidIdentityException("Entry with " + entryId + " does not exist!");
    }

    @Override
    public void updateADiary(Diary diary, Diary diaryId, String userId) {
        var userExists = validateUserId(userId);
        var diaryExists = validateDiaryId(diaryId);
        var listOfUsersDiaries = diaryExists.getEntries();
        for (int index = 0; index < listOfUsersDiaries.size(); index++) {
            if (Objects.equals(diaryExists.getId(), diaryId.getId()) && Objects.equals(userExists.getUserId(), userId)) {
                var userDiary = validateDiary(diary);
                userDiary.setName(diary.getName());
                diaryRepository.save(userDiary);
                return;
            }
        }
        throw new InvalidIdentityException("Diary with " + diaryId + " does not exist!");

    }

    @Override
    public void updateAnEntryInADiary(Diary diaryId, Entry entry, String userId) {
        var userExists = validateUserId(userId);
        var diaryExists = validateDiaryId(diaryId);
        var entryExists = validateEntryId(entry);
        var listOfEntries = diaryExists.getEntries();
        for (int index = 0; index < listOfEntries.size(); index++) {
            if (Objects.equals(userExists.getUserId(), userId) && Objects.equals(diaryExists.getId(), diaryId.getId()) && Objects.equals(entryExists.getId(), entry.getId())) {
                entryExists.setTitle(entry.getTitle());
                entryExists.setBody(entry.getBody());
                entryRepository.save(entryExists);
            }
        }

    }

    @Override
    public Diary getAdiary(Diary diaryId, String userId) {
        var userExists = validateUserId(userId);
        var diaryExits = validateDiary(diaryId);
        var listOfDiaries = userExists.getDiaries();
        for (int index = 0; index < listOfDiaries.size(); index++) {
            if (Objects.equals(userExists.getUserId(), userId) && Objects.equals(diaryExits.getId(), diaryId.getId())) {
                return listOfDiaries.get(index);
            }
        }
        throw new InvalidIdentityException("Diary with " + diaryId + " does not exist!");
    }

    @Override
    public Entry getAnEntryInADiary(Diary diaryId, Entry entryId, String userId) {
        var userExists = validateUserId(userId);
        var diaryExists = validateDiaryId(diaryId);
        var entryExists = validateEntryId(entryId);
        var listOfEntries = diaryExists.getEntries();
        for (int index = 0; index < listOfEntries.size(); index++) {
            if (Objects.equals(userExists.getUserId(), userId) && Objects.equals(diaryExists.getId(), diaryId.getId()) && Objects.equals(entryExists.getId(), entryId.getId())) {
                return listOfEntries.get(index);
            }
        }
        throw new InvalidIdentityException("Entry with " + entryId + " does not exist!");
    }

    @Override
    public List<Diary> getAllDiaries(String userId) {
        var userExists = validateUserId(userId);
        if (Objects.equals(userExists.getUserId(), userId)) {
            return userExists.getDiaries();
        }
        throw new InvalidIdentityException("User with " + userId + " does not exist!");
    }


}
