package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Graph;
import com.example.demo.dto.Manager;
import com.example.demo.dto.Report;
import com.example.demo.dto.Search;
import com.example.demo.dto.User;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.util.Paging;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final ManagerRepository managerRepository;
	private final BCryptPasswordEncoder managerPasswordEncoder;
	
	private static final long LIST_CNT = 5;
	private static final long PAGE_CNT = 10;
	
	// 로그인
	public Manager findById(String managerId) {
		return managerRepository.findById(managerId);
	}
	
	//report list
	public List<Report> selectReportList (long curPage, Report report){
		List<Report> list = null;
		try {
			long totalCnt = listTotalCnt(report);
			if(totalCnt>0) {
				log.info("totalCnt: {}"+totalCnt);
				Paging paging = new Paging("/Manager/report", totalCnt, LIST_CNT, PAGE_CNT, curPage,"curPage");
				report.setStartRnum(paging.getStartRow());
				report.setEndRnum(paging.getEndRow());
				//log.info("totalCnt: {}"+paging.getStartRow());
				//log.info("totalCnt: {}"+paging.getEndRow());
				list= managerRepository.selectReportList(report);
			}
		}
		catch(Exception e) {
			log.debug("[ManagerService] selectReportList Exception",e);
		}
		return list;
	}
	
	//totalCnt
	public long listTotalCnt (Report report) {
		long totalCnt = 0;
		try {
			totalCnt = managerRepository.reportTotalCnt(report);
		}
		catch(Exception e) {
			log.debug("[ManagerService] listTotalCnt Exception ",e);
		}
		return totalCnt;
	}
	
	//update
	public int updateReportStatus (Report report) {
		int cnt=0;
		try {
			cnt = managerRepository.updateReportStatus(report);
		}
		catch(Exception e) {
			log.debug("[ManagerService] updateReportStatus Exception", e);
		}
		return cnt;
	}

	
	//userSelectList
	public List<User> selectUserList (long curPage, Search search){
		
		List<User> list = null;
		
		try {
			long totalCnt = selectUserTotal(search);
			if(totalCnt>0) {
				Paging paging = new Paging("/Manager/user", totalCnt, LIST_CNT, PAGE_CNT, curPage,"curPage");
				search.setStartRnum(paging.getStartRow());
				search.setEndRnum(paging.getEndRow());
			}
			list = managerRepository.selectUserList(search);
		}
		catch(Exception e) {
			log.debug("[ManagerService] selectUserList Exception",e);
		}
		return list;
	}
	
	public long selectUserTotal (Search search) {
		long cnt = 0;
		try {
			cnt = managerRepository.selectUserTotal(search);
		}
		catch(Exception e) {
			log.debug("[ManagerService] selectUserTotal Exception",e);
		}
		return cnt;
	}
	
	//유저 업데이트
	public int updateUserStatus (User user) {
		int cnt = 0;
		
		try {
			cnt = managerRepository.updateUserStatus(user);
		}
		catch(Exception e) {
			log.debug("[ManagerService] updateUserStatus Excpetion ",e);
		}
		return cnt;
	}
	
	//graph
	public List<Graph> userChange (){
		List<Graph> list = null;
		try {
			list= managerRepository.userChange();
		}
		catch(Exception e) {
			log.debug("[ManagerService] userChange Exception",e);
		}
		return list;
	}
}
