package com.example.whypyprojdect.dto;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;

@Data
public class QuestionDto {
    private int category;
    private String title;
    private int level;
    private String answer;
    private Boolean solved;

    public void setQCategory(int category) {
        this.category = category;
    }

    public void setQTitle(String title) {
        this.title = title;
    }

    public void setQLevel(int level) {
        this.level = level;
    }

    public void setQSolved(Boolean solved) {
        this.solved = solved;
    }
}
