package com.eden.diary.repositories;

import com.eden.diary.models.Diary;
import com.eden.diary.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends MongoRepository<Diary, String> {
    Optional<Diary> findDiaryByName(String name);
    Optional<Diary> findDiaryById(String id);
}
