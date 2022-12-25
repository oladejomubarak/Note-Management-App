package com.example.notemanagement.data.dtos.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateEntriesRequest {
    private String title;
    private  String body;
    private LocalDateTime localDateTime;
}
