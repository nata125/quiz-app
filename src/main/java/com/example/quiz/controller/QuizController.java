package com.example.quiz.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	/** 登録機能 */
	@PostMapping("/insert")
	public String insert(@Validated QuizForm quizForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		
		Quiz quiz = new Quiz();
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		
		if(!bindingResult.hasErrors()) {
			service.insertQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "登録完了!");
			return "redirect:/quiz";
		}else {
			return showList(quizForm, model);
		}
	}
	
	/** クイズ1件取得し、フォーム内に表示 */
	@GetMapping("/{id}")
	public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
		
		Optional<Quiz> quizOpt = service.selectOneById(id);
		Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t)); 
		
		if(quizFormOpt.isPresent()) {
			quizForm = quizFormOpt.get();
		}
		
		/* 更新用のmodelを作成 */
		makeUpdateModel(quizForm, model);
		return "crud";
	}
	
	/** 更新用モデル作成 */
	private void makeUpdateModel(QuizForm quizForm, Model model) {
		model.addAttribute("id", quizForm.getId());
		quizForm.setNewQuiz(false);
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("title", "更新用フォーム");
	}
	
	/** データ更新 */
	@PostMapping("/update")
	public String update(@Validated QuizForm quizForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		Quiz quiz = makeQuiz(quizForm);
		
		if(!bindingResult.hasErrors()) {
			service.updateQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "更新完了");
			return "redirect:/quiz/" + quiz.getId();
		}else {
			makeUpdateModel(quizForm, model);
			return "crud";
		}
	}
	
	/** QuizFormからQuizに詰めなおして返す。 */
	private Quiz makeQuiz(QuizForm quizForm) {
		Quiz quiz = new Quiz();
		quiz.setId(quizForm.getId());
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		return quiz;
	}
	
	/** QuizからQuizFormに詰めなおして返す。 */
	private QuizForm makeQuizForm(Quiz quiz) {
		QuizForm form = new QuizForm();
		form.setId(quiz.getId());
		form.setQuestion(quiz.getQuestion());
		form.setAnswer(quiz.getAnswer());
		form.setAuthor(quiz.getAuthor());
		form.setNewQuiz(false);
		return form;
	}
	
	/** 削除機能 */
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
		service.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除完了!");
		return "redirect:/quiz";
	}
	
	/** クイズで遊ぶ */
	@GetMapping("/play")
	public String showQuiz(QuizForm quizForm, Model model) {
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		if(quizOpt.isPresent()) {
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
		}else {
			model.addAttribute("msg", "問題がないよーん");
			return "play";
		}
		
		model.addAttribute("quizForm", quizForm);
		return "play";
	}
	
	@PostMapping("/check")
	public String checkQuiz(QuizForm quizForm, @RequestParam Boolean answer, Model model) {
		
		if(service.checkQuiz(quizForm.getId(), answer)) {
			model.addAttribute("msg", "正解じゃ");
		}else {
			model.addAttribute("msg", "不正解だ");
		}
		
		return "answer";
	}
}
