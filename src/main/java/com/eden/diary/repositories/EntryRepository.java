package com.eden.diary.repositories;

import com.eden.diary.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// when extending mongo repository, we must provide the type of the entity and te pk for that entity
public interface EntryRepository extends MongoRepository<Entry, String> {
    Optional<Entry> findEntryByTitle(String title);
    List<Entry> findEntriesByBodyContainingIgnoreCase(String phrase);


}
