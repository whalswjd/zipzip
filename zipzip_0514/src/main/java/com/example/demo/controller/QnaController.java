package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QnaController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/board/qna")
	public String qna() {
		return "board/qna";
	}
}
