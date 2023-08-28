package com.example.poi.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageData {
	
	private String[] to;
	private String subject;
	private String text;
	private String senderName;
	private String senderAddress;

}

