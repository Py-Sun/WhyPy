package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.LectureDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.entity.Lecture;
import com.example.whypyprojdect.entity.Question;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDto> getAllQuestion() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionsDtos = new ArrayList<>();

        for (Question question : questions) {
            questionsDtos.add(convertToDto(question));
        }
        return questionsDtos;
    }
    public QuestionDto getQuestionById(int questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        Question question = questionOptional.orElseThrow(() -> new NotFoundException("Question not found"));

        return convertToDto(question);
    }

    private QuestionDto convertToDto(Question question) {
        QuestionDto questionDto = new QuestionDto();

        questionDto.setQId(question.getId());
        questionDto.setQCategory(question.getCategory());
        questionDto.setQTitle(question.getTitle());
        questionDto.setQLevel(question.getLevel());
        questionDto.setQSolved(question.getSolved());
        questionDto.setQContents(question.getContents());
        questionDto.setQAnswer(question.getAnswer());
        questionDto.setQExample(question.getExample());

        return questionDto;
    }

    public Question convertToEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setCategory(questionDto.getCategory());
        question.setTitle(questionDto.getTitle());
        question.setLevel(questionDto.getLevel());
        question.setContents(questionDto.getContents());
        question.setAnswer(questionDto.getAnswer());
        question.setSolved(questionDto.getSolved());
        question.setExample(questionDto.getExample());
        return question;
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // 검색하기
    @Transactional
    public List<QuestionDto> searchQuestion(String keyword)
    {
        List<Question> questions = questionRepository.findByTitleContaining(keyword);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        if(questions.isEmpty()) return questionDtoList;

        for(Question question : questions){
            questionDtoList.add(this.convertToDto(question));
        }
        return questionDtoList;
    }

    public List<QuestionDto> getAllQuestionForFilter(Specification<Question> spec) {
        List<Question> questions = questionRepository.findAll(spec);
        return questions.stream()
                .map(QuestionDto::fromEntity)
                .collect(Collectors.toList());
    }

    /*
    public List<QuestionDto> getFilteredQuestionDtos(boolean solved, int category, int level) {
        List<Question> filteredQuestions = questionRepository.findBySolvedAndCategoryAndLevel(solved, category, level);
        return mapQuestionsToDtos(filteredQuestions);
    }
     */
    /*
    private List<QuestionDto> mapQuestionsToDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> {
                    QuestionDto dto = new QuestionDto();
                    dto.setQId(question.getId());
                    dto.setQCategory(question.getCategory());
                    dto.setQTitle(question.getTitle());
                    dto.setQLevel(question.getLevel());
                    dto.setQSolved(question.getSolved());
                    return dto;
                })
                .collect(Collectors.toList());
    }
     */
}
