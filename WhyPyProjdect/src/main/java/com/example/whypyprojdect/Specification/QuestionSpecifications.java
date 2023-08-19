package com.example.whypyprojdect.Specification;

import com.example.whypyprojdect.entity.Question;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class QuestionSpecifications {

    public static Specification<Question> hasLevelIn(List<Integer> levels) {
        return (root, query, criteriaBuilder) ->
                root.get("level").in(levels);
    }

    public static Specification<Question> isSolvedIn(List<Boolean> solvedList) {
        return (root, query, criteriaBuilder) ->
                root.get("solved").in(solvedList);
    }

    public static Specification<Question> hasCategoryIn(List<Integer> categories) {
        return (root, query, criteriaBuilder) ->
                root.get("category").in(categories);
    }
}
