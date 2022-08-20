package com.example.volunteer.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyRes {
    private boolean success;
    private Data data;
    private String message;
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Data{
        private String openid;
        private String sessionid;
    }
}
