package com.eden.diary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document //marks this entry as something that can be saved in te database
public class Entry {
    @Id // marks this field as pk
    private String id;
    private String title;
    private String body;
}
