package com.chetan.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Message {

	private Long id;
	private String from;
	private String to;
	private String message;
	private String subject;
	
}
