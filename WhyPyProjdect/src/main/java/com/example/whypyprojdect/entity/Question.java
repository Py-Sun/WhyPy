package com.example.whypyprojdect.entity;
import com.example.whypyprojdect.dto.QuestionDto;
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


    public void setId(int id){this.id = id;}
    public void setCategory(int category){this.category = category;}
    public void setTitle(String title){this.title = title;}
    public void setLevel(int level){this.level = level;}
    public void setContents(String contents) {this.contents = contents;}
    public void setAnswer(String answer){this.answer=answer;}
    public void setSolved(Boolean solved){this.solved=solved;}
    public void setExample(String example){this.example=example;}

}
