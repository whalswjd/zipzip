package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.InterestItem;
import com.example.demo.dto.Item;
import com.example.demo.dto.Search;
import com.example.demo.dto.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
   
	private final Logger log = LoggerFactory.getLogger(getClass());   
	private final UserService userService;
	private final ItemService itemService;
	private final BCryptPasswordEncoder userPasswordEncoder;
   
	// 회원 테스트
//	@GetMapping("/user")
//	public @ResponseBody String user() {
//		return "회원";
//	}
   
	// 회원가입 페이지
	@GetMapping("/user/join")
   	public String join(User user) {
		log.info("UserController join");
		
		return "user/join";
	}
   
	// 회원가입
	@PostMapping("/user/insert")
	public String insert(@Valid User user, Errors errors, Model model) throws Exception {
      
		// POST 요청시 넘어온 user 입력값에서 Validation에 걸리는 경우
		if (errors.hasErrors()) {
			// 회원가입 실패시 입력 데이터 유지
			model.addAttribute("user", user);         
         
			// 회원가입 실패시 메세지값을 모델에 매핑해서 전달
			Map<String, String> validateResult = userService.validateHandler(errors);
         
			// map.keyset() -> 모든 key값을 가져온다
			// 가져온 키를 반복문을 통해 키와 메세지 매핑
			for (String key : validateResult.keySet()) {
            
				// model.addAttribute("valid_userId", "아이디는 필수 입력사항 입니다");
				model.addAttribute(key, validateResult.get(key));
			}
         
			return "user/join";
		}
      
		userService.insert(user);
      
		log.info("insert success : " + user);
      
		return "redirect:/user/login";
      
	}
   
	// 로그인 페이지
	@GetMapping("/user/login")
	public String login() {
		log.info("UserController login");
		
		return "user/login";
	}
   
	// 아이디 중복 검사
	@ResponseBody
	@PostMapping("/idCheck")
	public Map<Object, Object> idCheck(@RequestBody String userId) throws Exception {
      
		Map<Object, Object> map = new HashMap<Object, Object>();
		int result = 0;
      
		result = userService.idCheck(userId);
      
		map.put("check", result);
      
		return map;
      
	}
	
	@GetMapping("/findIdPwd")
	public String findIdPwd() {
		return "user/findIdPwd";
	}
   
	@GetMapping("/user/info")
	public String info(Model model) {
      
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findById(userId);
      
		model.addAttribute("user", user);
      
		return "user/info";
	}
	
	//회원정보 수정 화면
	@GetMapping("/user/modifyUser")
	public String modifyUser(Model model) throws Exception {
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.userSelect(userId);
		
		model.addAttribute("user",user); 
		
		return "/user/modifyUser";
	}
	
	//회원정보 수정 ajax 통신
	@PostMapping("/modifyUserProc")
    @ResponseBody
    public ResponseEntity<User> modifyUserProc(@RequestBody User user ) {
		
        // 수신된 유저 정보를 수정
		User user2 = new User();
		
		user.setUserId(user.getUserId());
		user.setUserPwd(userPasswordEncoder.encode(user.getUserPwd()));
		user.setUserName(user.getUserName());
		user.setUserPhone(user.getUserPhone());
		user.setUserEmail(user.getUserEmail());
		
		System.out.println("------------------------");
		System.out.println("userId : " + user.getUserId());
		System.out.println("userName : " + user.getUserName());
		System.out.println("userPwd : " + user.getUserPwd());
		System.out.println("userPhone : " + user.getUserPhone());
		System.out.println("userEmail : " + user.getUserEmail());
		System.out.println("------------------------");
 			
		
        int count = userService.userUpdate(user);

		System.out.println("------------------------");
		System.out.println("count : " + count);
		System.out.println("------------------------");
				
		if(count > 0) 
        {
            return new ResponseEntity<>
            (user2, HttpStatus.OK);
        } 
        
		else 
        {
            return new ResponseEntity<>
            (HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
	
	//찜목록 리스트 화면
	@GetMapping("/user/interestItem")
	public String interestItem(Model model) throws Exception {
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.userSelect(userId);
		
		List<InterestItem> interestItem = userService.interestItemSelect(userId);
		
		model.addAttribute("user",user); 
		model.addAttribute("interestItem",interestItem);
			
		return "/user/interestItem";
	}
	
	//찜목록 상세페이지
   @RequestMapping("/user/interestItemDetail")
   public String interestItemDetail (@RequestParam(value = "itemNum")long itemNum, Model model) {
         Item item = new Item();
         Search search = new Search();
         
         String userId = SecurityContextHolder.getContext().getAuthentication().getName();
         User user = userService.userSelect(userId);
           
         search.setItemNum(itemNum);
         search.setUserId(userId);    
            
      if(itemNum != 0)
      {
         item = itemService.selectInterestItemDetail(search);
         
         //보증금(매매가)
         long tmp = item.getItemDeposit();
         
         if( item.getItemPtype().equals("S")|| item.getItemPtype().equals("Y"))
         {
            if (tmp != 0) {
               String a="";
               if (tmp>=10000) 
               {
                  a = String.valueOf(tmp/10000)+"억 ";
                  if (tmp % 10000 != 0) 
                  {
                       a += String.valueOf(tmp % 10000)+"만원";
                  }
               }
               
               else { a=String.valueOf(tmp)+"만원"; }
               
               
               item.setTransD(a);
            }
         }
         else
         {
          //월세
            tmp = item.getItemMonthPrice();
            
            if (tmp != 0) 
            {
               String a="";
               if (tmp>=10000) 
               {
                  a = String.valueOf(tmp/10000)+"억";
                  if (tmp % 10000 != 0) 
                  {
                       a += String.valueOf(tmp % 10000)+"만원";
                  }
               }
             
               else { a=String.valueOf(tmp)+"만원"; } 
                
               item.setTransM(a);
            }
         }
           
      }
      
      String tamp="";
      switch(item.getItemPtype()) {
         case "S" : tamp="매매 "; break;
         case "Y" : tamp="전세 "; break;
         case "M" : tamp="월세 "; break;
      }
      item.setItemPtype(tamp);
      item.setINum(String.format("%010d", itemNum));
      
      model.addAttribute("itemNum",itemNum);
      model.addAttribute("userId",userId);
      model.addAttribute("item",item);
      model.addAttribute("user",user); 
      
      return "/user/interestItemDetail";
   }

	
	// 찜목록 추가(ajax 통신)
	@PostMapping("/user/interestAddProc")
	@ResponseBody
	public ResponseEntity<String> interestAddProc(@RequestParam("itemNum") String itemNum ) {
		  
    	InterestItem interestItem = new InterestItem();
    	
    	String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	long itemNum2 =  Long.parseLong(itemNum);
    	
    	interestItem.setUserId(userId);
    	interestItem.setItemNum(itemNum2);
    	
    	int cnt = userService.interestItemFindSelect(interestItem);
    	
    	if(cnt == 0)
    	{
    		int count = userService.interestItemInsert(interestItem);
        	
            if (count > 0) 
            {
                return ResponseEntity.ok("200");
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.OK).body("400");
            }
    	}
    	else
    	{
    		return ResponseEntity.ok("500");
    	}	
    	
    }
	
    // 찜목록 삭제(ajax 통신)
    @PostMapping("/user/interestDeleteProc")
    @ResponseBody
    public ResponseEntity<String> interestDeleteProc(@RequestParam("itemNum") String itemNum ) {
  
    	InterestItem interestItem = new InterestItem();
    	
    	String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	long itemNum2 =  Long.parseLong(itemNum);
    	
    	interestItem.setUserId(userId);
    	interestItem.setItemNum(itemNum2);
    	
    	int cnt = userService.interestItemFindSelect(interestItem);
    	
    	if(cnt >0)
    	{
    		int count = userService.interestItemDelete(interestItem);
            
            if (count > 0) 
            {
                return ResponseEntity.ok("200");
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.OK).body("404");
            }
    	}
    	else
    	{
    		return ResponseEntity.ok("500");
    	}

    }

}