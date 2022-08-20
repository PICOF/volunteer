package com.example.volunteer.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Announce {
    private int annid;
    private String img;
    private Date Ptime;
    private String content;
}
