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
    public void deleteADiary(String userId, String diaryId) {
        var findUser = validateUserId(userId);
        var userDiaries = findUser.getDiaries();
        for (int index = 0; index < userDiaries.size(); index++) {
            if (Objects.equals(userDiaries.get(index).getId(), diaryId)) {
                diaryRepository.deleteById(diaryId);
                return;
            }
        }
        throw new InvalidIdentityException("Diary with diary id " + diaryId + " does not exist!");
    }

    @Override
    public void createAnEntryInADiary(Entry entry, String diaryId, String userId) {
        var userExists = validateUserId(userId);
        var listOfDiaries = userExists.getDiaries();
        for (int index = 0; index < listOfDiaries.size(); index++) {
            if (Objects.equals(listOfDiaries.get(index).getId(), diaryId)) {
                var aDiary = listOfDiaries.get(index);
                var userEntry = validateEntry(entry);
                var savedEntry = entryRepository.save(userEntry);
                aDiary.getEntries().add(savedEntry);
                diaryRepository.save(aDiary);
                return;
            }
        }
        throw new InvalidIdentityException("Diary with diary id " + diaryId + " does not exist!");
    }

    @Override
    public void deleteAnEntryInADiary(String entryId, String diaryId, String userId) {
        var findUser = validateUserId(userId);
        var userListOfDiaries = findUser.getDiaries();
        for (int index = 0; index < userListOfDiaries.size(); index++) {
            if (Objects.equals(userListOfDiaries.get(index).getId(), diaryId)) {
                var userListOfEntries = userListOfDiaries.get(index).getEntries();
                for (int indexEntry = 0; indexEntry < userListOfEntries.size(); indexEntry++) {
                    if (Objects.equals(userListOfEntries.get(indexEntry).getId(), entryId)) {
                        entryRepository.deleteById(entryId);
                        return;
                    }
                }
                throw new InvalidIdentityException("Entry with entry id " + entryId + " does not exist!");
            }
        }
        throw new InvalidIdentityException("Diary with diary id " + diaryId + " does not exist!");
    }

    @Override
    public void updateADiary(Diary diary, String diaryId, String userId) {
        var userExists = validateUserId(userId);
        var listOfUsersDiaries = userExists.getDiaries();
        for (int index = 0; index < listOfUsersDiaries.size(); index++) {
            if (Objects.equals(listOfUsersDiaries.get(index).getId(), diaryId)) {
                var userDiary = validateDiary(diary);
                userDiary.setName(diary.getName());
                diaryRepository.save(userDiary);
                return;
            }
        }
        throw new InvalidIdentityException("Diary with " + diaryId + " does not exist!");

    }

    @Override
    public void updateAnEntryInADiary(String diaryId, String entryId, String userId, Entry myEntry) {
        var findUser = validateUserId(userId);
        var userListOfDiaries = findUser.getDiaries();
        for (Diary aDiary : userListOfDiaries) {
            if (Objects.equals(aDiary.getId(), diaryId)) {
                var userListOfEntries = aDiary.getEntries();
                for (Entry anEntry : userListOfEntries) {
                    if (Objects.equals(anEntry.getId(), entryId)) {
                        anEntry.setTitle(myEntry.getTitle());
                        anEntry.setBody(myEntry.getBody());
                        return;
                    }
                }
                throw new InvalidIdentityException("Entry with entry id " + entryId + " does not exist!");
            }
        }
        throw new InvalidIdentityException("Diary with diary id " + diaryId + " does not exist!");

    }

    @Override
    public Diary getADiary(String diaryId, String userId) {
        var userExists = validateUserId(userId);
        var userDiaries = userExists.getDiaries();
        for (int index = 0; index < userDiaries.size(); index++) {
            if (Objects.equals(userDiaries.get(index).getId(), diaryId)) {
                return userDiaries.get(index);
            }
        }
        throw new InvalidIdentityException("Diary with diary id " + diaryId + " does not exist!");
    }

    @Override
    public Entry getAnEntryInADiary(String diaryId, String entryId, String userId) {
        var userExists = validateUserId(userId);
        var userDiaries = userExists.getDiaries();
        for (int index = 0; index < userDiaries.size(); index++) {
            if (Objects.equals(userDiaries.get(index).getId(), diaryId)) {
                var userEntries = userDiaries.get(index).getEntries();
                for (int indexEntry = 0; indexEntry < userEntries.size(); indexEntry++) {
                    if (Objects.equals(userEntries.get(indexEntry).getId(), entryId)) {
                        return userEntries.get(indexEntry);
                    }
                }
            }
        }
        throw new InvalidIdentityException("Diary with diary id " + diaryId + " does not exist!");
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
