package com.question_service.question.Repositories;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.question_service.question.Pojo.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	List<Question> findByCategory(String category);

	@Query(value = """
						SELECT id
			FROM question
			WHERE category =:category
			ORDER BY DBMS_RANDOM.VALUE
						""", nativeQuery = true)
	List<Integer> generateQuestions(@Param("category") String category, Pageable pageable);
}
