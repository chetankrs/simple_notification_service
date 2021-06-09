package com.chetan.util;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	private AtomicLong idGenerator = new AtomicLong();
	
	public Long generateNextId() {
		return idGenerator.incrementAndGet();
	}
	
}
