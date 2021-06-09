package com.chetan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.chetan.enumerator.ChannelType;
import com.chetan.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleNotificationServiceApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class NotificationIntegrationTests {

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	private static final String BASE_URL = "/notification-service";
	private static final String HOST = "http://localhost:";

	@Autowired
	ObjectMapper objectMapper;

	@LocalServerPort
	private int port;

	@Value("${email}")
	String user;

	@Test
	public void testNotifySuccessEmail() throws Exception {
		String url = prepareUrl(ChannelType.email);
		URI uri = new URI(url);
		HttpEntity<Message> entity = new HttpEntity<Message>(generateMessage(), headers);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		
		String expected = "{\"isAccepted\":true,\"messageId\":1}";
		
		assertEquals(expected, response.getBody());
	}
	
	@Test
	public void testNotifySuccessSlack() throws Exception {
		String url = prepareUrl(ChannelType.slack);
		URI uri = new URI(url);
		HttpEntity<Message> entity = new HttpEntity<Message>(generateMessage(), headers);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		
		String expected = "{\"isAccepted\":true,\"messageId\":2}";
		
		assertEquals(expected, response.getBody());
	}
	
	@Test
	public void testNotifyInvalidEmail() throws Exception {
		String url = prepareUrl(ChannelType.email);
		URI uri = new URI(url);
		HttpEntity<Message> entity = new HttpEntity<Message>(generateInvalidEmailMessage(), headers);
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}


	private String prepareUrl(ChannelType type) {
		StringBuilder url = new StringBuilder().append("/notify/").append(type);
		return prepareURLWithPort(url.toString());
	}

	private String prepareURLWithPort(String uri) {
		return new StringBuilder(HOST).append(port).append(BASE_URL).append(uri).toString();
	}

	private Message generateMessage() {
		Message msg = new Message();
		msg.setFrom(user);
		msg.setTo(user);
		msg.setSubject("Test Subject - Integration Test");
		msg.setMessage("Body of Message");
		return msg;
	}
	
	private Message generateInvalidEmailMessage() {
		Message msg = new Message();
		msg.setFrom("");
		msg.setTo(null);
		msg.setSubject("Test Subject - Integration Test");
		msg.setMessage("Body of Message");
		return msg;
	}

}
