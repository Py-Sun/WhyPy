package com.example.whypyprojdect.entity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "q_id")
    private int id;

    @Column(name="q_category")
    private int category;

    @Column(name = "q_title")
    private String title;

    @Column(name = "q_level")
    private int level;

    @Column(name = "q_contents")
    private String contents;

    @Column(name = "q_answer")
    private String answer;

    @Column(name = "q_solved")
    private Boolean solved;

    @Column(name = "q_example")
    private String example;

}
