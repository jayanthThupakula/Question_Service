package com.question_service.question.BL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.question_service.question.DL.ServiceImpl.QuestionServiceImpl;
import com.question_service.question.Dto.QuizDto;
import com.question_service.question.Dto.ResponseDto;
import com.question_service.question.Pojo.Question;

@Component
public class QuestionBL {
	
	@Autowired
	QuestionServiceImpl questionServiceImpl;
	
	public Question addQuestion(Question question) {
            return questionServiceImpl.addQuestion(question);
	}
	
	public String deleteQuestionById(int id) {
		return questionServiceImpl.deleteQuestionById(id);
	}
	
	public String updateQuestion(Question question,int id) {
		return questionServiceImpl.updateQuestion(question, id);
	}
	
	public List<Question> getAllQuestions(){
		return questionServiceImpl.getAllQuestions();
	}
	
	public List<Question> getQuestionByCategory(String category){
		return questionServiceImpl.getByCategory(category);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		// TODO Auto-generated method stub
		return questionServiceImpl.getQuestionsForQuiz(categoryName,numQuestions);
	}

	public ResponseEntity<List<QuizDto>> getQuestionsFromId(List<Integer> questionIds) {
		// TODO Auto-generated method stub
		return questionServiceImpl.getQuestionsFromId(questionIds);
	}

	public ResponseEntity<Integer> getScore(List<ResponseDto> responses) {
		// TODO Auto-generated method stub
		return questionServiceImpl.getScore(responses);
	}

	
}
