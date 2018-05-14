package com.example.demo.service;

import static org.junit.Assert.assertEquals;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dto.MessageDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MailServiceTests {

	@Autowired
	private MailService mailService;

	@Rule
	public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

	@Test
	public void shouldSendSingleMail() throws MessagingException {
		MessageDto messageDto = new MessageDto();
		messageDto.setMessage("this is a test mail!!");
		messageDto.setToMailId("renjithkn@stgit.com");
		messageDto.setSubject("test mail");
		mailService.send(messageDto);

		MimeMessage[] receivedMessages = smtpServerRule.getMessages();
		assertEquals(1, receivedMessages.length);

		MimeMessage current = receivedMessages[0];

		assertEquals(messageDto.getSubject(), current.getSubject());
		assertEquals(messageDto.getToMailId(), current.getAllRecipients()[0].toString());
	}

}
