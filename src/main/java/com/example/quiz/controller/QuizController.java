package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	/**DI対象*/
	@Autowired
	QuizService service;
	
	/**form-backing beanの初期化*/
	@ModelAttribute
	public QuizForm setUpForm() {
		QuizForm form = new QuizForm();
		form.setAnswer(true);
		return form;
	}
	
	@GetMapping
	public String showList(QuizForm quizForm, Model model) {
		quizForm.setNewQuiz(true);
		Iterable<Quiz> list = service.selectAll();
		
		model.addAttribute("list", list);
		model.addAttribute("title","登録用フォーム");
		return "crud";
	}
}
