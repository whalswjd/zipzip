package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.Notice;
import com.example.demo.dto.NoticeFile;
import com.example.demo.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	public void insert(Notice notice) throws IOException {
		if (notice.getNoticeFile().get(0).isEmpty()) {
			noticeRepository.insert(notice);
		}
		
		else {
			Notice insertNotice = noticeRepository.insert(notice);
			
			int idx = 0;
			
			for (MultipartFile file : notice.getNoticeFile()) {
				
				long noticeNum = notice.getNoticeNum();
				System.out.println("noticeNum : " + noticeNum);
				
				idx++;
				
				String fileOrgName = file.getOriginalFilename();
				System.out.println("fileOrgName : " + fileOrgName);
				
				String fileName = noticeNum + "_" + idx + ".jpg";
				System.out.println("idx : " + idx);
				System.out.println("fileName : " + fileName);
				
				NoticeFile noticeFile = new NoticeFile();
				noticeFile.setNoticeNum(insertNotice.getNoticeNum());
				noticeFile.setFileIdx(idx);
				noticeFile.setFileOrg(fileOrgName);
				noticeFile.setFileName(fileName);
				
				String savePath = "C:/project/final/zipzip/src/main/resources/static/resources/upload/board/notice/" + fileName;
				file.transferTo(new File(savePath));
				
				noticeRepository.insertFile(noticeFile);
			}
		}
	}
	
	public List<Notice> selectAll() {
		return noticeRepository.selectAll();
	}
	
	public void updateHit(long noticeNum) {
		noticeRepository.updateHit(noticeNum);
	}
	
	public Notice selectOne(long noticeNum) {
		return noticeRepository.selectOne(noticeNum);
	}
	
	public void update(Notice notice) {
		noticeRepository.update(notice);
	}
	
	public void delete(long noticeNum) {
		noticeRepository.delete(noticeNum);
	}
	
	public List<NoticeFile> selectFile(long noticeNum) {
		return noticeRepository.selectFile(noticeNum);
	}
}
