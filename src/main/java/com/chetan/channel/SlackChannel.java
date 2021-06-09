package com.chetan.channel;

import org.springframework.stereotype.Component;

import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;

@Component
public class SlackChannel implements Channel{

	@Override
	public void notify(Message message) {
		System.out.println("Stub method to send Slack Messages !");
	}

	@Override
	public Boolean handles(ChannelType channelType) {
		return ChannelType.slack == channelType;
	}

}
