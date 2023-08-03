package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.QuestionSolve;
import com.example.whypyprojdect.entity.Recmd;
import com.example.whypyprojdect.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionSolveService {
    private final QuestionSolveRepository questionSolveRepository;
    private final QuestionRepository postRepository;
    private final MemberRepository memberRepository;


    public QuestionSolve getQuestionSolveById(Integer solveId) {
        return questionSolveRepository.findById(solveId)
                .orElseThrow(() -> new NoSuchElementException("SolveId not found with id: " + solveId));
    }

    public Optional<QuestionSolve> getQuestionSolveBySolveIdAndMemberId(Integer solveId, Long memberId) {
        Optional<QuestionSolve> questionSolve = questionSolveRepository.findByQuestionIdAndMemberId(solveId, memberId);
        if(questionSolve.isPresent()) {
            return questionSolve;
        }
        return null;
    }

    public List<QuestionSolve> getQuestionSolveByMemberId(Long memberId) {
        List<QuestionSolve> questionSolve = questionSolveRepository.findAllByMemberId(memberId);
        if(!questionSolve.isEmpty()) {
            return questionSolve;
        }
        return null;
    }

    public QuestionSolve saveSolveData(QuestionSolve questionSolve) {
        QuestionSolve qustionSolveEntity = questionSolveRepository.save(questionSolve);
        return qustionSolveEntity;
    }

    public void deleteSolveData(Integer solveId) {
        questionSolveRepository.deleteById(solveId);
    }

    public void setMemberID(QuestionSolve questionSolve, Object memberId) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) memberId);
        if(memberEntity.isPresent()) {
            MemberDto member = MemberDto.toMemberDto(memberEntity.get());
            questionSolve.setMemberId(member.getId());
        }
    }

    public void setSolveID(QuestionSolve questionSolve, int solveId) {
        questionSolve.setSolveId(solveId);
    }


}
