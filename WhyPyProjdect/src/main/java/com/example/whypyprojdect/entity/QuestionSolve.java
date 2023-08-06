package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Qsolve")
public class QuestionSolve {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "solve_id")
    private int solveId;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "q_solved")
    private boolean solved;

    @Column(name = "user_answer")
    private String answer;

    public boolean isSolved() {
        return solved;
    }
    public void setSolved(boolean solved){this.solved = solved;}
}
