package com.chetan.channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;
import com.chetan.tasks.EmailNotificationTask;

@Component
public class EmailChannel implements Channel {

	private ExecutorService executorService;
	
	@Autowired
	private ApplicationContext context;
	
	public EmailChannel() {
		executorService = Executors.newSingleThreadExecutor();
	}

	@Override
	public void notify(Message message) {
		EmailNotificationTask task = context.getBean(EmailNotificationTask.class);
		task.setMessage(message);
		executorService.execute(task);
	}

	@Override
	public Boolean handles(ChannelType channelType) {
		return ChannelType.email == channelType;
	}
	
}
