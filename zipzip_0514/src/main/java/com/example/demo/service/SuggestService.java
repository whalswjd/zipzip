package com.example.demo.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Suggest;
import com.example.demo.repository.SuggestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuggestService {

	private final SuggestRepository suggestRepository;
	
	public void insert(Suggest suggest) {
		
		Suggest data = new Suggest();
		
		
	}
	
}
