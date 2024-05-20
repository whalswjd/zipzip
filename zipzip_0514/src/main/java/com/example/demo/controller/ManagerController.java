package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Graph;
import com.example.demo.dto.Manager;
import com.example.demo.dto.Report;
import com.example.demo.dto.Search;
import com.example.demo.dto.User;
import com.example.demo.service.ManagerService;
import com.example.demo.util.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ManagerController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final ManagerService managerService;
	
	private static final long LIST_CNT = 5;
	private static final long PAGE_CNT = 10;
	
	// 관리자 테스트
//	@GetMapping("/manager")
//	public @ResponseBody String manager() {
//		return "관리자";
//	}
	
	// 관리자 홈
	@GetMapping("/manager")
	public String home(Model model) {
		log.info("ManagerController home");
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		System.out.println(context);
		System.out.println(authentication);
		
		String managerId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Manager manager = managerService.findById(managerId);
		
		model.addAttribute("manager", manager);
		
		return "manager/home";
	}
	
	//회원관리
	@RequestMapping("/manager/user")
	public String mUser (@RequestParam(value = "curPage", defaultValue = "1") long curPage,
						 @RequestParam(value = "searchType", defaultValue = "") String searchType,
						 @RequestParam(value = "searchValue", defaultValue = "") String searchValue,
						 Model model) {
		Search search = new Search();
		if(!searchType.isEmpty() && !searchValue.isEmpty()) {
			log.info("searchValue : "+searchValue);
			log.info("searchType : "+searchType);
			search.setSearchType(searchType);
			search.setSearchValue(searchValue);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchValue", searchValue);
		}
		List<User> list = managerService.selectUserList(curPage, search);
		
		if(list!= null) {
			//log.info("list is not null");
			if(list.size()>0) {
				model.addAttribute("list", list);
				long totalCnt = managerService.selectUserTotal(search);
				Paging paging = new Paging("/Manager/user", totalCnt, LIST_CNT, PAGE_CNT, curPage,"curPage");
				model.addAttribute("paging",paging);
				//log.info("total: "+totalCnt);
			}
		}
		else {
			//log.info("list == null");
		}
		List<Graph> userList = managerService.userChange();
		
		List<String> monthList = new ArrayList<>();
		List<Long> totalList = new ArrayList<>();
		for(Graph a : userList) {
			String month = a.getRegDate();
			long total = a.getTotalCnt();
			//log.info(month);
			monthList.add(month);
			totalList.add(total);
		}
		
		model.addAttribute("month",monthList.toString());
		model.addAttribute("total",totalList);
		
		
		model.addAttribute("curPage", curPage);
		return "manager/user";
	}
	
	//신고관리
	@RequestMapping("/manager/report")
	public String report (@RequestParam(value = "curPage", defaultValue = "1") long curPage,
						  @RequestParam(value = "userId", defaultValue = "") String userId,
						  @RequestParam(value = "status", defaultValue = "") String status,
						  @RequestParam(value = "type", defaultValue = "") String type,
							Model model, HttpServletRequest request) {
		
		 
		Report report = new Report();
		
		if(!userId.isEmpty()) {
			report.setUserId(userId);
			log.info("userId"+userId);
			model.addAttribute("userId",userId);
		}
		else if(!status.isEmpty()) {
			report.setStatus(status);
			log.info("status"+status);
			model.addAttribute("status",status);
		}
		else if(!type.isEmpty()) {
			report.setReportType(type);
			log.info("type"+type);
			model.addAttribute("type",type);
		}
		
		List<Report> list = managerService.selectReportList(curPage, report);
		if(list != null) {
			if(list.size()>0) {
				long totalCnt = managerService.listTotalCnt(report);
				Paging paging = new Paging("/Manager/report", totalCnt, LIST_CNT, PAGE_CNT, curPage,"curPage");
				model.addAttribute("paging",paging);
			}
		}
		else {
			log.info("list == null");
		}
		model.addAttribute("list", list);
		model.addAttribute("curPage", curPage);
		return "manager/report";
	}
	
	@RequestMapping("/reportUpdate")
	@ResponseBody
	public int reportUpdate (@RequestParam(value="reportNum") long reportNum,
							 @RequestParam(value="status") String status) {
		int cnt = 0;
		Report report = new Report();
		if(reportNum != 0 && !status.isEmpty()) {
			report.setReportNum(reportNum);
			report.setStatus(status);
			
			cnt = managerService.updateReportStatus(report);
		}
		else {
			cnt = -1;
		}
		return cnt;
	}
	//유저업데이트
	@RequestMapping("/userUpdate")
	@ResponseBody
	public int userUpdate (@RequestParam(value="userId") String userId,
			 			   @RequestParam(value="status",  defaultValue = "") String status,
			 			   @RequestParam(value="emailAuth",  defaultValue = "") String email) {
		int cnt=0;
		User user = new User();
		if(!userId.isEmpty()) {
			log.info("userId:"+ userId);
			if(status != "" || email != "") {
				user.setUserId(userId);
				user.setStatus(status);
				user.setEmailAuth(email);
				log.info("status: "+status);
				log.info("email:"+email);
				
				cnt = managerService.updateUserStatus(user);
			}
		}
		
		return cnt;
	}	

	// 로그인
	@GetMapping("/manager/login")
	public String login() {
		return "manager/login";
	}
	
	
}
