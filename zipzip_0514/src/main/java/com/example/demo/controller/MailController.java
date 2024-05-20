package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final MailService mailService;
	
	@ResponseBody
	@GetMapping("/mailCheck")
	public String MailSend(String userEmail) {
		log.info("MailController => MailSend");
		
		int number = mailService.sendMail(userEmail);
		
		String num = "" + number;
		
		return num;
	}
}
