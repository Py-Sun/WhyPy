package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.*;
import com.example.whypyprojdect.repository.*;
import jakarta.transaction.Transactional;
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

    public List<QuestionSolve> getQuestionSolveByMemberId(long memberId) {
        return questionSolveRepository.findAllByMemberId(memberId);
    }

    public Optional<QuestionSolve> getQuestionSolveBySolveIdAndMemberId(Integer solveId, Long memberId) {
        Optional<QuestionSolve> questionSolve = questionSolveRepository.findByQuestionIdAndMemberId(solveId, memberId);
        if(questionSolve.isPresent()) {
            return questionSolve;
        }
        return null;
    }

    public long getQuestionSolveSolvedCountByMemberId(Long memberId) {
        List<QuestionSolve> questionSolve = questionSolveRepository.findAllByMemberId(memberId);
        long countOfSolved = 0;
        for (QuestionSolve qs : questionSolve) {
            if (qs.isSolved()) {
                countOfSolved++;
            }
        }
        return countOfSolved;
    }

    // 체크박스 상태에 따라 qsolve 테이블의 qSolved 값이 달라지는 함수
    public void saveOrUpdateSolveData(QuestionSolve questionSolve, boolean qSolved) {

        Optional<QuestionSolve> existingSolve = questionSolveRepository.findByQuestionIdAndMemberId(questionSolve.getQuestionId(), questionSolve.getMemberId());

        if (existingSolve.isPresent()) {
            // 이미 레코드가 존재하면 업데이트
            QuestionSolve existingSolveEntity = existingSolve.get();
            existingSolveEntity.setSolved(qSolved);
            questionSolveRepository.save(existingSolveEntity);
        } else {
            // 없으면 새 레코드 추가
            questionSolve.setSolved(qSolved);
            questionSolveRepository.save(questionSolve);
        }
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

    public void setQuestionID(QuestionSolve questionSolve, int questionId) {
        //System.out.println("questionId" + questionId);
        questionSolve.setQuestionId(questionId);
    }


}
