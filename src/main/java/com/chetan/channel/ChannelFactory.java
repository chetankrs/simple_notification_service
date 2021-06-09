package com.chetan.channel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;

@Component
public class ChannelFactory {
	
    private final List<Channel> channels;

    @Autowired
    public ChannelFactory(List<Channel> channels) {
        this.channels = channels;
    }

    public Channel get(ChannelType channel) {
        return channels
                .parallelStream()
                .filter(service -> service.handles(channel))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No channel found with type : "+ channel));
    }

    public void notifyAll(Message msg) {
        for(Channel c : channels) {
            c.notify(msg);
        }
    }

    public List<Channel> getChannels() {
        return channels;
    }
}