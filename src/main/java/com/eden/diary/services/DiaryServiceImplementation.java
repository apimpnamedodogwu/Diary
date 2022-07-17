package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;
import com.eden.diary.repositories.DiaryRepository;
import com.eden.diary.repositories.EntryRepository;
import com.eden.diary.services.DiaryExceptions.InvalidIdentityException;
import com.eden.diary.services.DiaryExceptions.NullParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DiaryServiceImplementation implements DiaryService {

    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    EntryRepository entryRepository;


    @Override
    public void createADiary(String name) {
        nameValidation(name);
        var existingDiary = diaryRepository.findDiaryByName(name);
        if (existingDiary.isPresent()) {
            throw new IllegalArgumentException("A diary with " + name + " as name already exists!");
        }
        Diary myDiary = new Diary();
        myDiary.setName(name);
        diaryRepository.save(myDiary);
    }

    @Override
    public String deleteADiary(String id) {
        var foundId = idValidation(id);
        diaryRepository.delete(foundId);
        return "Diary with id number " + id + " has been deleted successfully!";
    }

    @Override
    public void addAnEntryInADiary(Entry anEntry, String diaryId) {

        var diary = diaryRepository.findDiaryById(diaryId)
                .orElseThrow(() -> new InvalidIdentityException("No diary found with id " + diaryId));

        if (anEntry.getBody().isEmpty() || anEntry.getTitle().isEmpty()) {
            throw new NullParameterException("Empty parameter for title or body");
        }
        var savedEntry = entryRepository.save(anEntry);
        diary.getEntries().add(savedEntry);
        diaryRepository.save(diary);
    }


    private void nameValidation(String name) {
        if (name.isEmpty()) {
            throw new NullParameterException("This field cannot be empty!");
        }
    }

    private Diary idValidation(String id) {
        var enteredId = diaryRepository.findById(id);
        if (enteredId.isPresent()) {
            return enteredId.get();
        } else throw new InvalidIdentityException("No diary matches this " + id + " number!");
    }

    @Override
    public Entry getAnEntry(String entryId, String diaryId) {
        var findDiary = diaryRepository.findDiaryById(diaryId);
        if (findDiary.isPresent()) {
            var diary = findDiary.get();
            var entries = diary.getEntries();
            for (int index = 0; index < entries.size(); index++) {
                if (entries.get(index).getId().equals(entryId)) {
                    return entries.get(index);
                }

            }
            throw new InvalidIdentityException("Entry with id " + entryId + " not found in diary!");
        }
        throw new InvalidIdentityException("No diary found with id " + diaryId + "!");
    }

    @Override
    public void deleteAnEntryInADiary(String entryId, String diaryId) {
        var diary = diaryRepository.findDiaryById(diaryId).orElseThrow(() -> new InvalidIdentityException("Diary with id number " + diaryId + " does not exist!"));
        var entries = diary.getEntries();
        for (int index = 0; index < entries.size(); index++) {
            if (entries.get(index).getId().equals(entryId)) {
                entryRepository.deleteById(entryId);
                return;
            }
        }
        throw new InvalidIdentityException("Entry with id " + entryId + " not found in diary!");

    }


    @Override
    public List<Entry> getAllEntriesInADiary(String diaryId) {
        var diary = diaryRepository.findDiaryById(diaryId).orElseThrow(() -> new InvalidIdentityException("Diary with id number " + diaryId + " does not exist!"));
        return diary.getEntries();
    }

    @Override
    public void updateAnEntryInADiary(String entryId, String diaryId, Entry entry) {
        var diary = diaryRepository.findDiaryById(diaryId).orElseThrow(() -> new InvalidIdentityException("Diary with id number " + diaryId + " does not exist!"));
        var entries = entryRepository.findById(entryId).orElseThrow(() -> new InvalidIdentityException("Entry with id number " + entryId + " does not exist!"));
        if (entry.getTitle().isEmpty() || entry.getBody().isEmpty()) {
            throw new NullParameterException("Title and Body of diary cannot be empty!");
        }
        var listOfEntries = diary.getEntries();
        for (Entry myEntry : listOfEntries) {
            if (Objects.equals(entries.getId(), entry.getId())) {
                myEntry.setTitle(entry.getTitle());
                myEntry.setBody(entry.getBody());
                entryRepository.save(myEntry);
                return;
            }
        }
        throw new IllegalArgumentException("Oops, your entry was not found in your diary!");

    }
}
