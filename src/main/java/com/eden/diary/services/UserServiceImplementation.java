package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;
import com.eden.diary.models.User;
import com.eden.diary.repositories.DiaryRepository;
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

    @Override
    public void createAUser(String name) {
        if (name.isEmpty()) {
            throw new NullParameterException("This field cannot be empty!");
        }
        User myUser = new User();
        myUser.setName(name);
        userRepository.save(myUser);
    }

    @Override
    public void createADiary(String diaryName, String userId) {
        if (diaryName.isEmpty()) {
            throw new NullParameterException("This field cannot be empty!");
        }
        var user = userRepository.findUserByUserId(userId).orElseThrow(() -> new NullParameterException("User with " + userId + " does not exist!"));
        if (Objects.equals(user.getUserId(), userId)) {
            Diary myDiary = new Diary();
            myDiary.setName(diaryName);
            diaryRepository.save(myDiary);
        }

    }

    @Override
    public void deleteADiary(String userId, Diary diaryId) {
        var foundDiary = diaryRepository.findDiaryById(diaryId).orElseThrow(() -> new InvalidIdentityException("Diary with " + diaryId + " does not exist!"));
        var userToDel = userRepository.findUserByUserId(userId).orElseThrow(() -> new InvalidIdentityException("User with " + userId + " does not exist!"));
        if (userToDel.getUserId() == userId) {
            diaryRepository.delete(foundDiary);
        }


    }

    @Override
    public void createAnEntryInADiary(Entry entryId, Diary diaryId) {

    }

    @Override
    public void deleteAnEntryInADiary(Entry entryId, Diary diaryId) {

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
