package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AgentReview;
import com.example.demo.repository.ReviewBoardRepository;

@Service
public class ReviewBoardService {
	
	private static Logger logger = LoggerFactory.getLogger(ReviewBoardService.class);
	
	@Autowired
	private ReviewBoardRepository reviewBoardRepository;
	
	// 리뷰게시판 리스트
	public List<AgentReview> reviewList(AgentReview agentReview){
		List<AgentReview> reviewList = null;
		
		try {
			reviewList = reviewBoardRepository.reviewList(agentReview);
		} catch(Exception e) {
			logger.error("[ReviewBoardService] reviewList Exception", e);
		}
		
		return reviewList;
	}
	
	// 전체 리스트 건 수 
	public int reviewListCount(AgentReview agentReview) {
		int count = 0;
		
		try {
			count = reviewBoardRepository.reviewListCount(agentReview);
		} catch(Exception e) {
			logger.error("[ReviewBoardService] reviewListCount Exception", e);
		}
		
		return count;
	}
	
	// 리뷰 등록
	public int reviewInsert(AgentReview agentReview) {
		int count = 0;
		
		try {
			count = reviewBoardRepository.reviewInsert(agentReview);
		} catch(Exception e) {
			logger.error("[ReviewBoardService] reviewInsert Exception", e);
		}
		
		return count;
	}

	// 리뷰 1건 삭제
	public int reviewDelete(long reviewNum) {
		int count = 0;
		
		try {
			count = reviewBoardRepository.reviewDelete(reviewNum);
		} catch(Exception e) {
			logger.error("[ReviewBoardService] reviewDelete Exception", e);
		}
		
		return count;
	}
	
	
	
	
	
	// 나의 총 리뷰 수
	public int myReviewCount(String agentId) {
		int count = 0;
		
		try {
			count = reviewBoardRepository.myReviewCount(agentId);
		} catch(Exception e) {
			logger.error("[ReviewBoardService] myReviewCount Exception", e);
		}
		
		return count;
	}
}
