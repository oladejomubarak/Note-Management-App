package com.example.notemanagement.data.dtos.request;

import lombok.Data;

@Data
public class NoteUpdateRequest {
    private int id;
    private String noteName;
}
