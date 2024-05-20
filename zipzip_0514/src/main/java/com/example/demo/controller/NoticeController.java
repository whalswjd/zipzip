package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.Manager;
import com.example.demo.dto.Notice;
import com.example.demo.dto.NoticeFile;
import com.example.demo.dto.User;
import com.example.demo.service.ManagerService;
import com.example.demo.service.NoticeService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	private final NoticeService noticeService;
	
	// 생성 페이지
	@GetMapping("/board/noticeInsert")
	public String insert(Model model) {
		return "board/noticeInsert";
	}
	
	// 생성
	@PostMapping("/board/noticeInsert")
	public String insert(Notice notice) throws IOException {
		log.info("notice {} ", notice);
		
		noticeService.insert(notice);
		
		return "redirect:/board/notice";
	}
	
	// 목록 페이지
	@GetMapping("/board/notice")
	public String selectAll(Model model) {
		List<Notice> noticeList = noticeService.selectAll();
		model.addAttribute("notice", noticeList);
		
		return "board/notice";
	}
	
	// 상세 페이지
	@GetMapping("/board/notice/{noticeNum}")
	public String selectOne(@PathVariable("noticeNum") long noticeNum, Model model) {
		
		// 조회수
		noticeService.updateHit(noticeNum);
		
		// 상세내용
		Notice notice = noticeService.selectOne(noticeNum);
		
		List<NoticeFile> noticeFileList = noticeService.selectFile(noticeNum);
		
		model.addAttribute("notice", notice);
		model.addAttribute("noticeFileList", noticeFileList);
		System.out.println("notice : " + notice);
		
		return "board/noticeDetail";
	}
	
	// 수정 페이지
	@GetMapping("/board/noticeUpdate/{noticeNum}")
	public String update(@PathVariable("noticeNum") long noticeNum, Model model) {
		Notice notice = noticeService.selectOne(noticeNum);
		
		model.addAttribute("notice", notice);
		
		return "board/noticeUpdate";
	}
	
	// 수정
	@PostMapping("/board/noticeUpdate/{noticeNum}")
	public String update(Notice notice, Model model) {
		noticeService.update(notice);
		Notice noticeNum = noticeService.selectOne(notice.getNoticeNum());
		
		model.addAttribute("notice", noticeNum);
		
		return "board/noticeDetail";
	}
	
	// 삭제
	@GetMapping("/board/noticeDelete/{noticeNum}")
	public String delete(@PathVariable("noticeNum") long noticeNum) {
		noticeService.delete(noticeNum);
		
		return "redirect:/board/notice";
	}
	
}
