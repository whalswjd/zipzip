package com.example.demo.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	private long noticeNum;			// 공지 번호
	private String userId;			// 아이디 (관리자)
	private String noticeTitle;		// 공지 제목
	private String noticeContent;	// 공지 내용
	private String regDate;			// 생성 일자
	private long noticeHit;			// 조회수
	
	private List<MultipartFile> noticeFile;
}
