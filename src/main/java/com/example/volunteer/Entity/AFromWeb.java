package com.example.volunteer.Entity;

import lombok.Data;

@Data
public class AFromWeb {
    private int id;
    private String title;
    private String types;
    private Long workStart;
    private Long workEnd;
    private String address;
    private String preVolunteers;
    private String actVolunteers;
    private String logo;
}
