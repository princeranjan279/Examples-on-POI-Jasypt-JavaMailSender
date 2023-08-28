package com.example.poi.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.poi.entity.MessageData;
import com.example.poi.entity.User;
import com.example.poi.util.ResponseStructure;

public interface UserService {
	
	public ResponseEntity<ResponseStructure<User>> saveUser(User user);
	
	public ResponseEntity<ResponseStructure<List<User>>> extractUsersFromExcel(MultipartFile file) throws IOException;

	public ResponseEntity<ResponseStructure<List<User>>> getUsers();

	public ResponseEntity<String> uploadUsersToExcel(String filePath) throws IOException;
	
	public ResponseEntity<String> readCSV(MultipartFile file) throws IOException;
	
	public ResponseEntity<String> writeUsersToCSV(String filePath) throws IOException;
	
	public ResponseEntity<String> sendMailToStudents(MessageData messageData);
	
	public ResponseEntity<String> sendMimeMail(MessageData data) throws MessagingException;
	
	public ResponseEntity<String> testAsyncBehaviour();
}
