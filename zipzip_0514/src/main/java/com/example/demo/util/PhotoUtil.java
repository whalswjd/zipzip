package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PhotoUtil {
   
//   @Value("${upload.path}")
//   private String uploadPath;
   
   public String ckUpload(MultipartHttpServletRequest request) {
      
      MultipartFile uploadFile = request.getFile("upload");
      
      String fileName = getFileName(uploadFile);
      String realPath = getPath(request);
      //String realPath1 = request.getServletContext().getRealPath("/upload/");
      String savePath = realPath + fileName;
      //String savePath1 = realPath1 + fileName;
      String uploadPath = request.getContextPath() + "/upload/" + fileName;   //url
      String uploadPath1 = "../resources/upload/board/" + fileName;
      
      log.info("[ckUpload] uploadPath1 Url -- " + uploadPath1);
      log.info("[ckUpload] uploadPath Url -- " + uploadPath);
      log.info("[ckUpload] realPath Url -- " + realPath);
      log.info("[ckUpload] savePath Url -- " + savePath);
      
      uploadFile(savePath, uploadFile);
      //uploadFile(savePath1, uploadFile);
      
      return uploadPath1;
   }
   
   private void uploadFile(String savePath, MultipartFile uploadFile) {
      File file = new File(savePath);
      try {
         uploadFile.transferTo(file);
      }catch(IOException e) {
         throw new RuntimeException("Failed to upload the file", e);
      }
   }
   
   // 원본파일이름 UUID값으로 변경 메소드
   private String getFileName(MultipartFile uploadFile) {
      
      String orgFileName = uploadFile.getOriginalFilename();
      String ext = orgFileName.substring(orgFileName.lastIndexOf("."));
      
      String fileName = UUID.randomUUID() + "@" + orgFileName;
      
      return fileName;
   }
   
   // 실제 파일 저장 경로 없을 시 생성 후 -> 경로 반환
   private String getPath(MultipartHttpServletRequest request) {
      
      // 지정된 가상 경로("/upload/")에 대응하는 실제 파일 시스템 경로
      String realPath1 = request.getServletContext().getRealPath("/upload/");   //서버에 저장 (1)
      String realPath = "C:/project/webapps/zipzip/src/main/resources/static/resources/upload/board/";   //프로젝트내로 변경 시도
      
      // 실제 파일 시스템 경로를 사용하여 Path 객체를 생성
      Path directoryPath1 = Paths.get(realPath1); //서버 path객체(1)
      Path directoryPath = Paths.get(realPath);
      
      log.info("[getPath] realPath1 -- " + realPath1); //(1)
      log.info("[getPath] realPath -- " + realPath);
      log.info("[getPath] directoryPath1 -- " + directoryPath1); //(1)
      log.info("[getPath] directoryPath -- " + directoryPath);
//      log.info("경로" + uploadPath); 
      
      // 디렉토리가 존재하지 않을 경우 디렉토리 생성
      if(!Files.exists(directoryPath) || !Files.exists(directoryPath1)) {
         try {
            Files.createDirectories(directoryPath);
            //Files.createDirectories(directoryPath1);
         }catch(IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
         }
      }
      
      return realPath;
   }
   
   
   
}