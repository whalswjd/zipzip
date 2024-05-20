package com.example.demo.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Graph;
import com.example.demo.dto.Manager;
import com.example.demo.dto.Report;
import com.example.demo.dto.Search;
import com.example.demo.dto.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {
	
	private final SqlSessionTemplate sql;
	
	// 로그인
	public Manager findById(String managerId) {
		return sql.selectOne("Manager.findById", managerId);
	}
	
	public List<Report> selectReportList (Report report){
		return sql.selectList("Manager.selectReportList", report);
	};
	
	public long reportTotalCnt (Report report) {
		return sql.selectOne("Manager.reportTotalCnt", report);
	}
	
	public int updateReportStatus (Report report) {
		return sql.update("updateReportStatus",report);
	}
	
	public List<User> selectUserList (Search search){
		return sql.selectList("selectUserList",search);
	}
	
	public long selectUserTotal (Search search) {
		return sql.selectOne("selectUserTotal",search);
	}
	
	public int updateUserStatus (User user) {
		return sql.update("updateUserStatus",user);
	}
	
	//graph
	public List<Graph> userChange (){
		return sql.selectList("userChange");
	}
	
}
