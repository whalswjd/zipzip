package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Report;

@Mapper
public interface ManagerInterRepo {
	
	public List<Report> selectReportList (Report report);

}
