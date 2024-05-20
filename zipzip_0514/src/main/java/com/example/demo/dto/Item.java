package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long itemNum; 				//아이템 번호
	private String userId;				//중개인
	private String itemName;			//아파트명
	private String itemAddress;			//주소
	private String itemAddressDetail;	//상세주소
	private String itemPtype;			//지불 방식
	private String itemStatus;			//판매완료/판매 전
	private String recommnedItem;		//추천 마크
	private long itemCount;				//조회수
	private String itemIntro;
	private String itemIntroDetail;		//소개글
	private long itemDeposit;			//보증금
	private long itemMonthPrice;		//월세
	private long itemMaintainPrice;
	private Double itemArea;				
	private String regDate;
	
	private String itemParking;
	private String itemBuildDate;
	private String itemDirection;
	private String itemKind;
	private String itemElevator;
	private int itemFloor;
	private String itemMoveAvbl;
	private String itemMoveDate;
	private String itemPet;
	
	private String likeItem;
	
	private String iNum;
	
	
	private String transD;		
	private String transM;
	
	private String agentName;
	private String agentIntro;
	private String fileName;
	private String agentRight;
	
	private String jsonObj;
	

	//private List<ItemFile> itemFile;
	private String agentId;				//중개인 아이디		//매물거래타입	
	private String itemItype;			//매물종류타입		//매물등록일
	private String recommendItem;		//추천마크여부

	private List<MultipartFile> itemFile;
	private List<ItemFile> itemFileName;
	
	
}
