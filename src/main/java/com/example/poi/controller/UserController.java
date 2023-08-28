package com.example.poi.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.poi.entity.MessageData;
import com.example.poi.entity.User;
import com.example.poi.service.UserService;
import com.example.poi.util.ResponseStructure;

@RestController
@RequestMapping("/user")
@CrossOrigin //(allowCredentials = "true", originPatterns = "http://127.0.0.1:5500")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<List<User>>> extractUsersFromExcel(@RequestParam MultipartFile file) throws IOException{
		return service.extractUsersFromExcel(file);
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<ResponseStructure<List<User>>> getUsers(){
		return service.getUsers();
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user){
		return service.saveUser(user);
	}
	
	@PostMapping("/write")
	public ResponseEntity<String> uploadUserToExcel(@RequestParam String filePath) throws IOException{
		return service.uploadUsersToExcel(filePath);
	}
	
	@PostMapping("/read/csv")
	public ResponseEntity<String> readCSV(@RequestParam MultipartFile csvFile) throws IOException{
		return service.readCSV(csvFile);
	}
	
	@PostMapping("/write/csv")
	public ResponseEntity<String> writeUsersToCSV(@RequestParam String filePath) throws IOException{
		return service.writeUsersToCSV(filePath);
	}
	
	@PostMapping("/mail")
	public ResponseEntity<String> sendMailToStudents(@RequestBody MessageData messageData){
		return service.sendMailToStudents(messageData);
	}
	

	@PostMapping("/mime-mail")
	public ResponseEntity<String> sendMimeMail(@RequestBody MessageData messageData) throws MessagingException{
		System.out.println("called");
		return service.sendMimeMail(messageData);
	}
	
	@GetMapping("/async-call")
	public ResponseEntity<String> testAsyncBehaviour(){
		return service.testAsyncBehaviour();
	}
}
