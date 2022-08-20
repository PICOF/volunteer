package com.example.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    private String user_id;
    private String nick_name;
    private String user_name;
    private String email;
    private String phone;
    private Date birth;
    private int score;
    private String teamid;
    private int auth;
}
