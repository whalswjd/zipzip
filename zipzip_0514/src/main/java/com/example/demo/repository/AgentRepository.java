package com.example.demo.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Agent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AgentRepository {
	
	private final SqlSessionTemplate sql;
	
	// 회원가입
	public void insert(Agent agent) {
		sql.insert("Agent.insert", agent);
	}
	
	// 로그인
	public Agent findById(String agentId) {
		return sql.selectOne("Agent.findById", agentId);
	}
	
	// 중개인 정보 조회
	public Agent agentSelect(String agentId) {
		return sql.selectOne("Agent.agentSelect", agentId);
	}
}
