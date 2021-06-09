package com.chetan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chetan.enumerator.ChannelType;
import com.chetan.handler.MessageHandler;
import com.chetan.model.Message;
import com.chetan.model.NotificationResponseDTO;
import com.chetan.validator.EmailValidator;

@RestController
@RequestMapping("/notify")
public class NotificationController {

	@Autowired
	private MessageHandler messageHandler;
	
	@Autowired
	private EmailValidator emailValidator;
	
	@PostMapping("/{channelType}")
	public NotificationResponseDTO sendNotification(@PathVariable ChannelType channelType, @RequestBody Message message) {
		validateIfEmail(channelType, message);
		return messageHandler.handle(channelType, message);
	}

	private void validateIfEmail(ChannelType channelType, Message message) {
		if(channelType.equals(ChannelType.email)) {
			if(!emailValidator.isValid(message.getFrom())) {
				throw new IllegalArgumentException();
			}
			
			if(!emailValidator.isValid(message.getTo())) {
				throw new IllegalArgumentException();
			}
		}
	}
	
}
