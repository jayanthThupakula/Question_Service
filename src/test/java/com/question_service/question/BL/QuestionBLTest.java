package com.question_service.question.BL;

import com.question_service.question.DL.ServiceImpl.QuestionServiceImpl;
import com.question_service.question.Pojo.Question;
import com.question_service.question.QuestionApplication;
import com.question_service.question.Repositories.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//static Imports
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionBLTest {
    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    QuestionServiceImpl questionService;

    @Test
    void addQuestionShouldaddQuestionSuccessfully(){

        Question question=new Question();
        question.setId(1);
        question.setCategory("Java");
        question.setQuestionTitle("Streams are introduced in which version of java");
        question.setOption1("8");
        question.setOption2("9");
        question.setOption3("21");
        question.setOption4("25");
        question.setDifficultyLevel("medium");
        question.setRightAnswer("8");

        when(questionRepository.save(question)).thenReturn(question);

        Question addedquestion=questionService.addQuestion(question);

        assertEquals(question.getId(),addedquestion.getId());
        assertNotNull(addedquestion);
        assertEquals(question.getQuestionTitle(),addedquestion.getQuestionTitle());
        assertTrue(question.getId()==1);
    }

    @Test
    void addQuestionShouldThrowExceptionWhenCategoryIsInvalid(){

        Question question=new Question();
        question.setId(1);
        question.setCategory("");
        question.setQuestionTitle("Streams are introduced in which version of java");
        question.setOption1("8");
        question.setOption2("9");
        question.setOption3("21");
        question.setOption4("25");
        question.setDifficultyLevel("medium");
        question.setRightAnswer("8");

//        assertThrows(RuntimeException.class,()->{
//            questionService.addQuestion(question);
//        });

        RuntimeException runtimeException=assertThrows(RuntimeException.class,()->{
            questionService.addQuestion(question);
        });

        assertEquals("invalid category",runtimeException.getMessage());
        verify(questionRepository,never()).save(any(Question.class));
    }

    @Test
    void deleteQuestionShouldDeleteQuestionSuccessfully(){

        //arrange
        Question question=new Question();
        question.setId(1);


        when(questionRepository.findById(1)).thenReturn(Optional.of(question));

        //for void methods we use doNothing() and verify
        doNothing().when(questionRepository).deleteById(1);

        //act
        String response=questionService.deleteQuestionById(1);

        //assert
        verify(questionRepository,times(1)).deleteById(1);
        assertEquals("question deleted",response);
    }

    @Test
    void validateCategory() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateCategory=QuestionServiceImpl.class.getDeclaredMethod("validateQuestionCategory",String.class);
        validateCategory.setAccessible(true);
        Boolean java= (Boolean) validateCategory.invoke(questionService,"java");
        assertTrue(java);
    }

    @Test
    void validateCategoryWhenCategoryIsInvalid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateCategory=QuestionServiceImpl.class.getDeclaredMethod("validateQuestionCategory",String.class);
        validateCategory.setAccessible(true);
        Boolean java= (Boolean) validateCategory.invoke(questionService,"");
        assertFalse(java);
    }
}