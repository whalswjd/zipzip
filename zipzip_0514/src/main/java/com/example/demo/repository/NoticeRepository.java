package com.example.demo.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Notice;
import com.example.demo.dto.NoticeFile;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {
	
	private final SqlSessionTemplate sql;
	
	public Notice insert(Notice notice) {
		sql.insert("Notice.insert", notice);
		return notice;
	}
	
	public void insertFile(NoticeFile noticeFile) {
		sql.insert("Notice.insertFile", noticeFile);
	}
	
	public List<Notice> selectAll() {
		return sql.selectList("Notice.selectAll");
	}
	
	public void updateHit(long noticeNum) {
		sql.update("Notice.updateHit", noticeNum);
	}
	
	public Notice selectOne(long noticeNum) {
		return sql.selectOne("Notice.selectOne", noticeNum);
	}
	
	public void update(Notice notice) {
		sql.update("Notice.update", notice);
	}
	
	public void delete(long noticeNum) {
		sql.delete("Notice.delete", noticeNum);
	}
	
	public List<NoticeFile> selectFile(long noticeNum) {
		return sql.selectList("Notice.selectFile", noticeNum);
	}
}
