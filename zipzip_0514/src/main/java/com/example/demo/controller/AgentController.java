package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Agent;
import com.example.demo.service.AgentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AgentController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final AgentService agentService;
	
	// 중개인 테스트
//	@GetMapping("/agent")
//	public @ResponseBody String agent() {
//		return "중개인";
//	}
	
	// 중개인 홈
	@GetMapping("/agent/home")
	public String home(Model model) {
		log.info("AgentController main");
		
		return "agent/home";
	}
	
	// 회원가입 페이지
	@GetMapping("/agent/join")
	public String join() {
		return "agent/join";
	}
	
	// 회원가입
	@PostMapping("/agent/insert")
	public String insert(Agent agent) {

		agentService.insert(agent);
		
		log.info("insert success : " + agent);
		
		return "redirect:agent/login";
		
	}
	
	// 로그인 페이지
	@GetMapping("/agent/login")
	public String login() {
		return "agent/login";
	}
	

}
