package com.question_service.question.DL.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.question_service.question.DL.Service.QuestionService;
import com.question_service.question.Dto.QuizDto;
import com.question_service.question.Dto.ResponseDto;
import com.question_service.question.Pojo.Question;
import com.question_service.question.Repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public List<Question> getAllQuestions() {
		// TODO Auto-generated method stub
		return questionRepository.findAll();
	}

	@Override
	public List<Question> getByCategory(String category) {
		// TODO Auto-generated method stub
		return questionRepository.findByCategory(category);
	}

	@Override
	public String deleteQuestionById(int id) {
		// TODO Auto-generated method stub
		Optional<Question> ques = questionRepository.findById(id);
		if (ques.isPresent()) {
			questionRepository.deleteById(id);
			return "question deleted";
		}
		return "question not available with given id " + id;
	}

	@Override
	public Question addQuestion(Question question) {
		// TODO Auto-generated method stub
        boolean validate=validateQuestionCategory(question.getCategory());
        if(validate)
        {
            return questionRepository.save(question);
        }
        else {
            throw new RuntimeException("invalid category");
        }

	}

	@Override
	public String updateQuestion(Question question, int id) {
		// TODO Auto-generated method stub
		Optional<Question> ques = questionRepository.findById(id);

		if (ques.isPresent()) {
			questionRepository.save(question);
			return "question updated";
		}

		return "question not available with given id " + id;
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		// TODO Auto-generated method stub
		List<Integer> list=questionRepository.generateQuestions(categoryName,PageRequest.of(0, numQuestions));
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	public ResponseEntity<List<QuizDto>> getQuestionsFromId(List<Integer> questionIds) {
		// TODO Auto-generated method stub
		List<QuizDto> list=new ArrayList<>();
		List<Question> questions=new ArrayList<>();
		
		for(int id:questionIds) {
			questions.add(questionRepository.findById(id).get());
		}
		
		for (Question question : questions) {
			QuizDto q=new QuizDto();
			q.setId(question.getId());
			q.setQuestionTitle(question.getQuestionTitle());
			q.setOption1(question.getOption1());
			q.setOption2(question.getOption2());
			q.setOption3(question.getOption3());
			q.setOption4(question.getOption4());
			list.add(q);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<ResponseDto> responses) {
		// TODO Auto-generated method stub
		int score=0;
		for (int i = 0; i < responses.size(); i++) {
			Question question=questionRepository.findById(responses.get(i).getId()).get();
			if(responses.get(i).getResponse().equals(question.getRightAnswer()))
			{				
				score++;
			}
		}
		return new ResponseEntity<>(score,HttpStatus.OK);
	}

    private boolean validateQuestionCategory(String category){
        return category!=null && !category.isEmpty();
    }
}
