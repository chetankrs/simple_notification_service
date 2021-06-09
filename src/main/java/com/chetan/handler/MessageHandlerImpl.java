package com.chetan.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chetan.channel.Channel;
import com.chetan.channel.ChannelFactory;
import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;
import com.chetan.model.NotificationResponseDTO;
import com.chetan.util.IdGenerator;

@Service
public class MessageHandlerImpl implements MessageHandler {

	@Autowired
	private ChannelFactory channelFactory;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	public NotificationResponseDTO handle(ChannelType channelType, Message message) {
		Channel channel = channelFactory.get(channelType);
		message.setId(idGenerator.generateNextId());
		channel.notify(message);
		return new NotificationResponseDTO(true, message.getId());
	}

}
