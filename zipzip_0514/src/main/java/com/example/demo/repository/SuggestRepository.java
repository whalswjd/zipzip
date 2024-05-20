package com.example.demo.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Suggest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SuggestRepository {
	
	private final SqlSessionTemplate sql;
	
	public Suggest insert(Suggest suggest) {
		sql.insert("Suggest.insert", suggest);
		return suggest;
	}
	
	public List<Suggest> selectAll() {
		return sql.selectList("Suggest.selectAll");
	}
}
