package com.example.demo.dto;

import lombok.Data;

@Data
public class Suggest {
	private long suggestNum;
	private String userId;
	private String suggestTitle;
	private String suggestContent;
	private long suggestHit;
	private String regDate;
}
