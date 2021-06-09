package com.chetan.handler;

import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;
import com.chetan.model.NotificationResponseDTO;

public interface MessageHandler {

	NotificationResponseDTO handle(ChannelType channelType, Message message);

}
