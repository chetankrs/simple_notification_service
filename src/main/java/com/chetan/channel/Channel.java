package com.chetan.channel;

import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;

public interface Channel {

	public void notify(Message message);
	public Boolean handles(ChannelType channelType);
	
}
