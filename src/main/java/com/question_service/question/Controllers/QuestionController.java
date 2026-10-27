package com.question_service.question.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.question_service.question.BL.QuestionBL;
import com.question_service.question.Dto.QuizCreateDto;
import com.question_service.question.Dto.QuizDto;
import com.question_service.question.Dto.ResponseDto;
import com.question_service.question.Pojo.Question;
import com.question_service.question.Repositories.QuestionRepository;

@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	QuestionBL questionBL;
	
	@Autowired
	Environment environment;
	
	@PostMapping("/add")
	public Question addQuestion(@RequestBody Question question) {
		return questionBL.addQuestion(question);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteQuestionById(@PathVariable int id) {
		return questionBL.deleteQuestionById(id);
	}
	
	@PutMapping("/update/{id}")
	public String updateQuestion(@RequestBody Question question,@PathVariable int id) {
		return questionBL.updateQuestion(question, id);
	}
	
	@GetMapping("/getall")
	public List<Question> getAllQuestions(){
		return questionBL.getAllQuestions();
	}
	
	@GetMapping("/getbycategory/{category}")
	public List<Question> getQuestionByCategory(@PathVariable String category){
		return questionBL.getQuestionByCategory(category);
	}
	
	//microservice
	@GetMapping("/generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,@RequestParam int noQ){
		return questionBL.getQuestionsForQuiz(category,noQ);
	}
	
	
	@PostMapping("/getQuestions")
	public ResponseEntity<List<QuizDto>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
		System.out.println(environment.getProperty("local.server.port"));
		return questionBL.getQuestionsFromId(questionIds);
	}
	
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<ResponseDto> responses){
		return questionBL.getScore(responses);
	}
}
