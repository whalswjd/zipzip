package com.example.demo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final JavaMailSender javaMailSender;
	private static final String senderEmail= "zipzip";
	private static int number;
	
	public static void createNumber(){
		// (int) Math.random() * (최댓값-최소값+1) + 최소값
		number = (int)(Math.random() * (90000)) + 100000;
	}
	
	 public MimeMessage CreateMail(String userEmail){
		log.info("MailService => createMail");
		System.out.println(userEmail);
		System.out.println(number);
		
		createNumber();
		MimeMessage message = javaMailSender.createMimeMessage();
		
		
		try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, userEmail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");           
        } 
		
		catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
	}
	
	 public int sendMail(String userEmail){
		log.info("MailService => sendMail");
		
		MimeMessage message = CreateMail(userEmail);
		javaMailSender.send(message);
		
		return number;
	}
}
