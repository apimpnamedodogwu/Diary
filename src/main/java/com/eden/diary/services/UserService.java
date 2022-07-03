package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;

import java.util.List;

public interface UserService {
    void createAUser(String name);

    void createADiary(String diaryName, String userId);

    void deleteADiary(String userId, Diary diaryId);

    void createAnEntryInADiary(Entry entry, Diary diaryId, String userId);

    void deleteAnEntryInADiary(Entry entryId, Diary diaryId, String userId);

    void updateADiary(Diary diary, Diary diaryId, String userId);

    void updateAnEntryInADiary(Diary diary, Entry entry, String userId);

    Diary getAdiary(Diary diaryId, String userId);

    Entry getAnEntryInADiary(Diary diaryId, Entry entryId, String userId);

    List<Diary> getAllDiaries(String userId);
}
