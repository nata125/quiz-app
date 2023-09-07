package com.example.quiz.service;

import java.util.Optional;

import com.example.quiz.entity.Quiz;

public interface QuizService {
	Iterable<Quiz> selectAll();
	Optional<Quiz> selectOneById(Integer Id);
	Optional<Quiz> selectOneRandomQuiz();
	Boolean checkQuiz(Integer Id, Boolean myAnswer);
	void insertQuiz(Quiz quiz);
	void updateQuiz(Quiz quiz);
	void deleteQuizById(Integer id);
}
