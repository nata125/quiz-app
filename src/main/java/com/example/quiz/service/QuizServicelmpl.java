package com.example.quiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;

@Service
@Transactional
public class QuizServicelmpl implements QuizService {

	@Autowired
	QuizRepository repository;
	@Override
	public Iterable<Quiz> selectAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Quiz> selectOneById(Integer Id) {
		return repository.findById(Id);
	}

	@Override
	public Optional<Quiz> selectOneRandomQuiz() {
		Integer randomId = repository.getRandomId();
		
		//Integerだからnullありえる
		if(randomId == null) {
			return Optional.empty();
		}
		
		return repository.findById(randomId);
	}

	@Override
	public Boolean checkQuiz(Integer Id, Boolean myAnswer) {
		Boolean checkResult = false;
		Optional<Quiz> optQuiz = repository.findById(Id);
		
		if(optQuiz.isPresent()) {
			Quiz quiz = optQuiz.get();
			
			if(quiz.getAnswer().equals(myAnswer)) {
				checkResult = true;
			}
		}
		return checkResult;
	}

	@Override
	public void insertQuiz(Quiz quiz) {
		repository.save(quiz);
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		repository.save(quiz);
	}

	@Override
	public void deleteQuizById(Integer id) {
		repository.deleteById(id);
	}

}
