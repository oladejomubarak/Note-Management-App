package com.example.notemanagement.data.dtos.request;

import lombok.Data;

@Data
public class CreateEntriesRequest {
    private String title;
    private  String body;
}
