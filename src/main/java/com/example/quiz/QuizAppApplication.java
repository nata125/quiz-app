package com.example.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
import com.example.quiz.service.QuizServicelmpl;

@SpringBootApplication
public class QuizAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args)
		.getBean(QuizAppApplication.class).execute();
	}
	
	@Autowired
	QuizServicelmpl service;
	
	public void execute() {
//		setup();
//		showList();
//		showOne();
//		updateQuiz();
//		deleteQuiz();
		doQuiz();
	}
	
	private void setup() {
		
		System.out.println("---登録処理完了---");
		//エンティティ作成
		Quiz quiz1 = new Quiz(null,"「Spring」はフレームワークですかね？", true, "登録太郎");
		Quiz quiz2 = new Quiz(null,"おやつは300円までですか？", true, "沢山食朗");
		Quiz quiz3 = new Quiz(null,"元気ですか？", true, "暗戸仁尾猪木");
		Quiz quiz4 = new Quiz(null,"ばななはおやつに入りますか？", false, "ばなな大好き君"); 
		Quiz quiz5 = new Quiz(null,"Javaはオブジェクト指向言語である", true, "まじめくん");
		
		List<Quiz> quizList = new ArrayList<>();
		Collections.addAll(quizList, quiz1,quiz2,quiz3,quiz4,quiz5);
		
		//登録処理
		for(Quiz quiz: quizList) {
			service.insertQuiz(quiz);
		}
		
		System.out.println("---登録処理完了---");
	}
	
	private void showList() {
		System.out.println("---全件取得開始---");
		Iterable<Quiz> quizzes = service.selectAll();
		for(Quiz quiz : quizzes) {
			System.out.println(quiz);
		}
		System.out.println("---全件取得終了---");
	}
	
	private void showOne() {
		System.out.println("---1件取得開始---");
		Optional<Quiz> quizOpt = service.selectOneById(1);
		
		if(quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		}else {
			System.out.println("該当する問題がないよー");
		}
		System.out.println("---1件取得終了---");
	}
	
	private void updateQuiz() {
		System.out.println("---更新処理開始---");
		Quiz quiz1 = new Quiz(1,"「すぷりんぐ」はフレームワークですかね？", true, "登録太郎");
		//saveメソッドのアップデートここでやるんかーい
		service.updateQuiz(quiz1);
		System.out.println("---更新処理終了---");
	}
	
	private void deleteQuiz() {
		System.out.println("---削除処理開始---");
		service.deleteQuizById(2);
		System.out.println("---削除処理終了---");
	}
	
	private void doQuiz() {
		System.out.println("---クイズ1件取得開始---");
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		
		if(quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		}else {
			System.out.println("該当するクイズがないですわ");
		}
		System.out.println("---クイズ1件取得完了");
		
		Boolean myAnswer = false;
		Integer id = quizOpt.get().getId();
		
		if(service.checkQuiz(id, myAnswer)){
			System.out.println("正解");
		}else {
			System.out.println("残念wwwww");
		}
	}
}
