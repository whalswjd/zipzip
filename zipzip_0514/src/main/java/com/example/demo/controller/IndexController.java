package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.Agent;
import com.example.demo.dto.Item;
import com.example.demo.dto.ItemFile;
import com.example.demo.dto.Manager;
import com.example.demo.dto.News;
import com.example.demo.dto.Notice;
import com.example.demo.dto.User;
import com.example.demo.service.AgentService;
import com.example.demo.service.ItemService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.NewsService;
import com.example.demo.service.NoticeService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	
	private final NoticeService noticeService;
	private final NewsService newsService;
//	private final UserService userService;
//	private final AgentService agentService;
//	private final ManagerService managerService;
	
	private final ItemService itemService;
	
	@GetMapping("/")
	public String index(Notice notice, Model model) throws Exception {
		//List<Notice> noticeList = noticeService.selectAll(notice);
		List<News> newsList = newsService.getNewsDatas();
		
//		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//		
//		User user = userService.findById(userId);
//		Agent agent = agentService.findById(userId);
//		Manager manager = managerService.findById(userId);
		
		List<Item> list = itemService.recent9item();
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				//log.info("몇개? {}",list.get(i).getItemNum());
				String tamp="";
				switch(list.get(i).getItemPtype()) {
					case "S" : tamp="매매"; break;
					case "Y" : tamp="전세"; break;
					case "M" : tamp="월세"; break;
				}
				
				list.get(i).setItemPtype(tamp);
				//보증금
				long tmp = list.get(i).getItemDeposit();
				if (tmp != 0) {
					String a="";
					if (tmp>=10000) {
						a = String.valueOf(tmp/10000)+"억 ";
						if (tmp % 10000 != 0) {
					        a += String.valueOf(tmp % 10000);
					    }
					}
					else {
						a = String.valueOf(tmp);
					}
					list.get(i).setTransD(a);

				}
				//월세
				tmp = list.get(i).getItemMonthPrice();
				if (tmp != 0) {
					String a="";
					if (tmp>=10000) {
						a = String.valueOf(tmp/10000)+"억 ";
						if (tmp % 10000 != 0) {
					        a += String.valueOf(tmp % 10000);
					    }
					}
					else {
						a = String.valueOf(tmp);
					}
					list.get(i).setTransM(a);
				}
			}
			model.addAttribute("list",list);
		}
		
		//model.addAttribute("notice", noticeList);
		model.addAttribute("news", newsList);
//		model.addAttribute("user", user);
//		model.addAttribute("agent", agent);
//		model.addAttribute("manager", manager);
		
		return "index";
	}
	
	@GetMapping("/401")
	public String authentication() {
		return "error/401";
	}
	
	@GetMapping("/403")
	public String authorization() {
		return "error/403";
	}
	
}
