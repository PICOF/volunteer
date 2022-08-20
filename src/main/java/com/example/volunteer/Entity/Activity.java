package com.example.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Activity {
    private String acti_id;
    private String acti_name;
    private String introduction;
    private Date start_time;
    private Date finish_time;
    private int score;
    private String place;
    private int joined;
    private int total;
    private String img;
    private String publisher;
}
