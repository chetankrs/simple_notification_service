package com.chetan.tasks;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chetan.model.Message;

import lombok.Setter;

@Component
@Scope("prototype")
@Setter
public class EmailNotificationTask implements Runnable{

	private Message message;
	
	@Value("${mail.smtp.host}")
	private String host;
	
	@Value("${mail.smtp.port}")
	private String port;
	
	@Value("${mail.smtp.ssl.enable}")
	private String isSslEnabled;
	
	@Value("${mail.smtp.auth}")
	private String isAuthenticationEnabled;
	
	@Value("${email}")
	private String email;
	
	@Value("${password}")
	private String password;
	
	public EmailNotificationTask(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		System.out.println("Sending email from : " + message.getFrom() + " to :" + message.getTo() + "Message : " + message);

		try {

			Properties properties = new Properties();
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.ssl.enable",isSslEnabled);
			properties.put("mail.smtp.auth", isAuthenticationEnabled);

			Session session = Session.getInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, password);
				}
			});
			session.setDebug(true);

			MimeMessage mimeMessage = new MimeMessage(session);

			mimeMessage.setFrom(email);
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(message.getFrom()));
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(message.getTo()));
			mimeMessage.setSubject(message.getSubject());
			mimeMessage.setText(message.getMessage());
			
			Transport.send(mimeMessage);
			System.out.println("Mail Sent !!");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}


	}

}























