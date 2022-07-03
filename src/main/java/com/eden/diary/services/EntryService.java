package com.eden.diary.services;

import com.eden.diary.models.Entry;

import java.util.List;

public interface EntryService {
    String addEntry(Entry entry);

    String deleteEntry(String id);

    void updateEntryTitle(String id, String title);

    Entry updateEntryBody(String id, String body);

    List<Entry> searchEntries(String phrase);

    Entry getEntryById(String id);

    Entry getEntryByTitle(String title);

    List<Entry> getAllEntries();
}
