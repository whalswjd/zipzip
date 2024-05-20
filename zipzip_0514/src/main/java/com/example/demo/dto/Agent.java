package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
	
	private String agentId;				// 아이디
	
	private String agentPwd;			// 비밀번호
	
	private String agentName;			// 이름
	
	private String agentOfficeName;		// 사무소 이름
	
	private String agnetPhone;			// 전화번호
	
	private String agentEmail;			// 이메일
	
	private String agentIntro;			// 소개
	
	private String agentAddress;		// 사무소 주소
	
	private String agentAddressDetail;	// 사무소 상세주소
	
	private String agentRight;			// 사무소 인증.결제여부 (Y:승인 | N:승인대기 | F:승인실패 | P:결제완료)
	
	private String role;				// 권한
	
	private String status;				// 상태(Y:활동 N:정지 Q:탈퇴)
	
	private String regDate;				// 가입일자
	
}
