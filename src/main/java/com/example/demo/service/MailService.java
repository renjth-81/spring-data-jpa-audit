package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MessageDto;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(MessageDto messageDto){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(messageDto.getMessage());
		message.setTo(messageDto.getToMailId());
		message.setSubject(messageDto.getSubject());
		javaMailSender.send(message);
	}

}
