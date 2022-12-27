package com.example.notemanagement.data.dtos.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddEntriesRequest {
    private int id;
    private String title;
    private String body;
    private LocalDateTime localDateTime;
}
