package com.example.poi;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.poi.serviceimpl.UserServiceImpl;

@SpringBootApplication
@EnableAsync
public class ApachePoiExample8Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApachePoiExample8Application.class, args);
	}
	

}
