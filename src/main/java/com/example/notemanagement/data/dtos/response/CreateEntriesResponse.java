package com.example.notemanagement.data.dtos.response;

import lombok.Data;

@Data
public class CreateEntriesResponse {
    private int id;
    private int statusCode;
    private String message;
}
