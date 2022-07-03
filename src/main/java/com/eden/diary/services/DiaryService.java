package com.eden.diary.services;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;

import java.util.List;

public interface DiaryService {
    void createADiary(String name);

    String deleteADiary(String id);

    void addAnEntryInADiary(Entry anEntry, String diaryId);

    Entry getAnEntry(String id, String diaryId);

    void deleteAnEntryInADiary(String id, String diaryId);

    List<Entry> getAllEntriesInADiary(String diaryId);

    void updateAnEntryInADiary(String entryId, String diaryId, Entry entry);
}
