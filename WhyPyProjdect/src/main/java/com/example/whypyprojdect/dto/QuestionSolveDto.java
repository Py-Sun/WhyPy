package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.QuestionSolve;
import lombok.Data;

@Data
public class QuestionSolveDto {
    private int solveId;
    private int questionId;
    private long memberId;
    private boolean solved;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setSolveId(int solveId) {this.solveId = solveId;}

    public void SetQuestionId(int questionId) {this.questionId = questionId;}

    public void setMemberId(long memberId) {this.memberId = memberId;}

    public void setSolved(boolean solved){this.solved = solved;}

    public QuestionSolveDto()
    {

    }

    public QuestionSolveDto(int questionId, long memberId) {
        this.questionId = questionId;
        this.memberId = memberId;
    }

    public QuestionSolve toEntity() {
        return QuestionSolve.builder()
                .solveId(solveId)
                .questionId(questionId)
                .memberId(memberId)
                .solved(solved)
                .build();
    }
}
