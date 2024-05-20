package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Agent;
import com.example.demo.repository.AgentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final AgentRepository agentRepository;
	private final BCryptPasswordEncoder agentPasswordEncoder;
	
	// 회원가입
	public void insert(Agent agent) {
		log.info("AgentService => insert");
		
		Agent data = new Agent();
		
		data.setAgentId(agent.getAgentId());
		data.setAgentPwd(agentPasswordEncoder.encode(agent.getAgentPwd()));
		data.setAgentName(agent.getAgentName());
		data.setAgentOfficeName(agent.getAgentOfficeName());
		data.setAgnetPhone(agent.getAgnetPhone());
		data.setAgentEmail(agent.getAgentEmail());
		data.setAgentIntro(agent.getAgentIntro());
		data.setAgentAddress(agent.getAgentAddress());
		data.setAgentAddressDetail(agent.getAgentAddressDetail());
		data.setAgentRight("Y");
		data.setRole("ROLE_AGENT");
		data.setStatus("Y");
		
		log.info("data : " + data);
		
		agentRepository.insert(data);
	}
	
	// 로그인
	public Agent findById(String agentId) {
		return agentRepository.findById(agentId);
	}
	
	public Agent agentSelect(String agentId) {
		return agentRepository.agentSelect(agentId);
	}
}
