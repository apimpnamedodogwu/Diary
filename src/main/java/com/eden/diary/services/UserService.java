package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;

import java.util.List;

public interface UserService {
    void createAUser(String name);

    void createADiary(String diaryName, String userId);

    void deleteADiary(String userId, String diaryId);

    void createAnEntryInADiary(Entry entry, String diaryId, String userId);

    void deleteAnEntryInADiary(String entryId, String diaryId, String userId);

    void updateADiary(Diary diary, String diaryId, String userId);

    void updateAnEntryInADiary(String diaryId, String entryId, String userId, Entry myEntry);

    Diary getAdiary(String diaryId, String userId);

    Entry getAnEntryInADiary(String diaryId, String entryId, String userId);

    List<Diary> getAllDiaries(String userId);
}
