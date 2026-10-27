package com.question_service.question.DL.Service;

import java.util.List;

import com.question_service.question.Pojo.Question;

public interface QuestionService {
	List<Question> getAllQuestions();
	List<Question> getByCategory(String category);
	String deleteQuestionById(int id);
	Question addQuestion(Question question);
	String updateQuestion(Question question,int id);
}
