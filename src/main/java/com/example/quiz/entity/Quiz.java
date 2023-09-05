package com.example.quiz.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="quiz")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

	@Id
	private Integer Id;
	private String question;
	private Boolean answer;
	private String author;
}
