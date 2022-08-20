package com.example.volunteer.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class Msg {
    private String message;
    private long date;
    private String sender;
    private String senderName;
}
