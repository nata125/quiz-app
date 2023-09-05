package com.example.quiz;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;

@SpringBootApplication
public class QuizAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args)
		.getBean(QuizAppApplication.class).execute();
	}
	
	@Autowired
	QuizRepository repository;
	
	public void execute() {
//		setup();
//		showList();
//		showOne();
//		updateQuiz();
//		deleteQuiz();
	}
	
	private void setup() {
		//エンティティ作成
		Quiz quiz1 = new Quiz(null,"「Spring」はフレームワークですかね？", true, "登録太郎");
		Quiz quiz2 = new Quiz(null,"おやつは300円までですか？", true, "沢山食朗");
		Quiz quiz3 = new Quiz(null,"元気ですか？", true, "暗戸仁尾猪木");
		//saveメソッドがINSERTとUPDATE自動でやってくれるみたいなので実験
		Quiz quiz4 = new Quiz(3,"ばななはおやつに入りますか？", false, "ばなな大好き君"); 
		
		//登録処理
		quiz1 = repository.save(quiz1);
		quiz2 = repository.save(quiz2);
		quiz3 = repository.save(quiz3);
		quiz4 = repository.save(quiz4);
		
		System.out.println("登録したのは" + quiz1 +"だよ");
		System.out.println("登録したのは" + quiz2 +"だよ");
		System.out.println("登録したのは" + quiz3 +"だよ");
		System.out.println("登録したのは" + quiz4 +"だよ");
	}
	
	private void showList() {
		System.out.println("---全件取得開始---");
		Iterable<Quiz> quizzes = repository.findAll();
		for(Quiz quiz : quizzes) {
			System.out.println(quiz);
		}
		System.out.println("---全件取得終了---");
	}
	
	private void showOne() {
		System.out.println("---1件取得開始---");
		Optional<Quiz> quizOpt = repository.findById(1);
		
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
		quiz1 = repository.save(quiz1);
		System.out.println("更新したのは" + quiz1 +"だよ");
		System.out.println("---更新処理終了---");
	}
	
	private void deleteQuiz() {
		System.out.println("---削除処理開始---");
		repository.deleteById(2);
		System.out.println("---削除処理終了---");
	}
}
