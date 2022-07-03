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

    }

    @Override
    public void updateADiary(Diary diary) {

    }

    @Override
    public void updateAnEntryInADiary(Diary diary, Entry entry) {

    }

    @Override
    public Diary getAdiary(Diary diaryId) {
        return null;
    }

    @Override
    public Entry getAnEntryInADiary(Diary diaryId, Entry entryId) {
        return null;
    }

    @Override
    public List<Diary> getAllDiaries() {
        return null;
    }


}
