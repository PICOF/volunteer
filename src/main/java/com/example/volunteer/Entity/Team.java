package com.example.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private String teamid;
    private String teamname;
    private String introduction;
    private int member;
    private int teamscore;
    private String leader;
    private String leaderNickname;
}
