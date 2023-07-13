package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.entity.Question;
import com.example.whypyprojdect.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDto> getAllQuestionDtos() {
        List<Question> questions = questionRepository.findAll();
        return mapQuestionsToDtos(questions);
    }

    public List<QuestionDto> getFilteredQuestionDtos(boolean solved, int category, int level) {
        List<Question> filteredQuestions = questionRepository.findBySolvedAndCategoryAndLevel(solved, category, level);
        return mapQuestionsToDtos(filteredQuestions);
    }

    private List<QuestionDto> mapQuestionsToDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> {
                    QuestionDto dto = new QuestionDto();
                    dto.setQCategory(question.getCategory());
                    dto.setQTitle(question.getTitle());
                    dto.setQLevel(question.getLevel());
                    dto.setQSolved(question.getSolved());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
