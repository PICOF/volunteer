package com.example.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OpenidMsg {
    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errmsg;
}
