package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.AgentReview;

@Repository
@Mapper
public interface ReviewBoardRepository {
	
	// 리뷰게시판 리스트
	public List<AgentReview> reviewList(AgentReview agentReview);
	
	// 전체 리스트 건 수 
	public int reviewListCount(AgentReview agentReview);
	
	// 리뷰 등록
	public int reviewInsert(AgentReview agentReview);
	
	// 리뷰 1건 삭제
	public int reviewDelete(long reviewNum);
	
	
	// 나의 총 리뷰 수
	public int myReviewCount(String agentId);
	
	
}
