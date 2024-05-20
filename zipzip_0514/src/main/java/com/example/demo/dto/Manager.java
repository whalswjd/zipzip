package com.example.demo.dto;

import lombok.Data;

@Data
public class Manager {
	private String managerId;		// 아이디
	private String managerPwd;		// 비밀번호
	private String managerName;		// 이름
	private String role;			// 권한
}
