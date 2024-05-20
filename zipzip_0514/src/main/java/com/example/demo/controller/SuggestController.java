package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.Suggest;
import com.example.demo.repository.SuggestRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SuggestController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private final SuggestRepository suggestRepository;
	
	@GetMapping("/board/suggestInsert")
	public String insert(Model model) {
		return "board/suggestInsert";
	}
	
	@PostMapping("/board/suggestInsert")
	public String insert(Suggest suggest) {
		
		
		
		return "board/	";
	}
	
	@GetMapping("/board/suggest")
	public String selectAll(Model model) {
		return "board/suggest";
	}
}
