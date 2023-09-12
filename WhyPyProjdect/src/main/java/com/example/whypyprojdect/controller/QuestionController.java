package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.Specification.QuestionSpecifications;
import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.dto.QuestionSolveDto;
import com.example.whypyprojdect.entity.*;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.MemberService;
import com.example.whypyprojdect.service.QuestionService;
import com.example.whypyprojdect.service.QuestionSolveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final QuestionSolveService questionSolveService;

    @Autowired
    public QuestionController(QuestionService questionService, MemberRepository memberRepository, MemberService memberService, QuestionSolveService questionSolveService) {
        this.questionService = questionService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.questionSolveService = questionSolveService;
    }

    @GetMapping("/questions")
    public String getAllQuestions(
            @RequestParam(name = "level1", required = false) List<Integer> levels,
            @RequestParam(name = "status1", required = false) List<Boolean> solvedList,
            @RequestParam(name = "type1", required = false) List<Integer> categories,
            Model model, HttpSession session) {
        Specification<Question> spec = Specification.where(null);

        if (levels != null && !levels.isEmpty()) {
            spec = spec.and(QuestionSpecifications.hasLevelIn(levels));
        }

        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(QuestionSpecifications.hasCategoryIn(categories));
        }
        List<QuestionDto> questionDtos = questionService.getAllQuestionForFilter(spec);

        //List<QuestionDto> questionDtos = questionService.getAllQuestion();
        //model.addAttribute("questions", questionDtos);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));

        if(memberEntity.isPresent()) {

            MemberDto memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);

            long solveCount = questionSolveService.getQuestionSolveSolvedCountByMemberId(memberDto.getId());
            model.addAttribute("SolveCount", solveCount);

            for (QuestionDto question : questionDtos) {
                boolean isSolved = questionSolveService.isQuestionSolvedByMemberId(question.getId(), memberDto.getId());

                // QuestionDto -> Question 변환
                Question questionEntity = questionService.convertToEntity(question);

                // 엔티티 업데이트
                questionEntity.setSolved(isSolved);

                questionService.saveQuestion(questionEntity);
            }

            if (solvedList != null && !solvedList.isEmpty()) {
              spec = spec.and(QuestionSpecifications.isSolvedIn(solvedList));
              questionDtos = questionService.getAllQuestionForFilter(spec);
            }

            for(QuestionDto question : questionDtos){
                boolean isSolved = questionSolveService.isQuestionSolvedByMemberId(question.getId(), memberDto.getId());
                question.setSolved(isSolved);
            }
        }
        else {
            // 없으면, 모두 false로 표시 set all qsolve values to false
            for (QuestionDto question : questionDtos) {
                Question que = questionService.convertToEntity(question);
                // 엔티티 업데이트
                que.setSolved(false);
                questionService.saveQuestion(que);
            }
        }

        model.addAttribute("questions", questionDtos);
        return "Problem/problem_list";
    }

    @GetMapping("/questions/{questionId}")
    public String getQuestionById(@PathVariable int questionId, Model model, HttpSession session) {

        QuestionDto questionDto = questionService.getQuestionById(questionId);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();

        model.addAttribute("question", questionDto);

        if(memberEntity.isPresent()) {
            memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);

            Optional<QuestionSolve> questionSolveOpt = questionSolveService.getQuestionSolveBySolveIdAndMemberId(questionId, memberDto.getId());
            if(questionSolveOpt != null && questionSolveOpt.isPresent() ){
                String answer = questionSolveOpt.get().getAnswer();
                System.out.println("userAnswer : "  + answer);
                model.addAttribute("useranswer", answer);
            }
            else model.addAttribute("useranswer", "");
        }
        else model.addAttribute("useranswer", "");

        return "Problem/problem_solving";
    }

    @PostMapping("/questions/{questionId}")
    public ResponseEntity<String> saveUserAnswer(@PathVariable int questionId, @RequestParam String userAnswer, HttpSession session) {
        QuestionDto questionDto = questionService.getQuestionById(questionId);

        if (questionDto != null) {
            QuestionSolve questionSolve = new QuestionSolve();
            Object member = session.getAttribute("loginName");
            //System.out.println("questionId" + questionId);
            questionSolveService.setQuestionID(questionSolve, questionId);
            questionSolveService.setMemberID(questionSolve, member);
            questionSolveService.getQuestionUserAnswerByMemberId(questionSolve, userAnswer);
        }

        String url = "/questions/" + questionId + "/answer";
        System.out.println("URL" + url);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/questions/{questionId}/answer")
    public String getQuestionAnswer(@PathVariable int questionId, Model model, HttpSession session) {
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        model.addAttribute("question", questionDto);
        //System.out.println("Answer : "  + questionDto.getAnswer());
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();

        if(memberEntity.isPresent()) {
            memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);

            // 해당 질문과 회원에 대한 QuestionSolve 조회
            Optional<QuestionSolve> questionSolveOpt = questionSolveService.getQuestionSolveBySolveIdAndMemberId(questionId, memberDto.getId());

            // qsolve 값이 이미 존재하는 경우, 해당 값에 따라 체크박스 초기 상태 결정
            boolean isSolved = questionSolveOpt.isPresent() && questionSolveOpt.get().isSolved();
            
            // 작성한 답안 보여주기
            if(questionSolveOpt.isPresent()){
                String answer = questionSolveOpt.get().getAnswer();
                System.out.println("userAnswer : "  + answer);
                model.addAttribute("useranswer", answer);
            }
            else model.addAttribute("useranswer", "");

            model.addAttribute("qsolve", isSolved);

        }
        return "Problem/problem_answer";
    }

    @PostMapping("/questions/{questionId}/answer")
    public ResponseEntity<?> saveQuestion(@PathVariable int questionId, @RequestParam boolean qSolved, HttpSession session) {
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        if (questionDto != null) {
            QuestionSolve questionSolve = new QuestionSolve();
            Object member = session.getAttribute("loginName");
            //System.out.println("questionId" + questionId);
            questionSolveService.setQuestionID(questionSolve, questionId);
            questionSolveService.setMemberID(questionSolve, member);
            questionSolveService.saveOrUpdateSolveData(questionSolve, qSolved);
            return ResponseEntity.ok("QuestionSolve added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


/*``````
    @GetMapping("/questionList/filtered")
    public String getFilteredQuestions(
            @RequestParam(defaultValue = "false") boolean isSolved,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer level,
            Model model) {

        // level이 null인 경우에 대한 처리
        Integer levelValue = (level != null) ? level : -1;
        Integer categoryValue = (category!=null)? category : -1;

        List<QuestionDto> filteredQuestions = questionService.getFilteredQuestionDtos(isSolved, categoryValue, levelValue);
        long solvedCount = filteredQuestions.stream().filter(QuestionDto::getSolved).count();

        model.addAttribute("questions", filteredQuestions);
        model.addAttribute("solvedCount", solvedCount);

        return "problem_list";
    }
*/

}
