package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;

import java.util.List;

public interface UserService {
    void createAUser(String name);
    void createADiary(String diaryName, String userId);
    void deleteADiary(String userId, Diary diaryId);
    void createAnEntryInADiary(Entry entryId, Diary diaryId);
    void deleteAnEntryInADiary(Entry entryId, Diary diaryId);
    void updateADiary(Diary diary);
    void updateAnEntryInADiary(Diary diary, Entry entry);
    Diary getAdiary(Diary diaryId);
    Entry getAnEntryInADiary(Diary diaryId, Entry entryId);
    List<Diary> getAllDiaries();
}
