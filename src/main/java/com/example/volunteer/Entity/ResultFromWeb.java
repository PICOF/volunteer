package com.example.volunteer.Entity;

import lombok.Data;

import java.util.List;

@Data
public class ResultFromWeb {
    private int code;
    private List<AFromWeb> data;
}
