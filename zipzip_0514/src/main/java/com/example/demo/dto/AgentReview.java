package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentReview {
	private long reviewNum;
	private String userId;
	private String agentId;
	private String reviewContent;
	private int reviewScore;
	private String regDate;
	
	// 리뷰게시판 검색
	private String searchType;
	private String searchValue;    // 리뷰내용, 중개사이름 다 searchValue로 받기
	
	// 페이징 
	private long startRow;
	private long endRow;
	
	// 중개사 정보
	private String agentName;
	private String agentOfficeName;
	
	
}
