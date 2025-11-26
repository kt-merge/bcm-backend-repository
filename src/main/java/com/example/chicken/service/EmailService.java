package com.example.chicken.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	private static final String SUBJECT = "[Big Chicken Market] 경매 낙찰 안내";

	public void sendAuctionSuccessEmail(String to, String productName) {
		log.info("Sending auction success email to {}", to);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(SUBJECT);

		String text = "축하합니다! 상품# %s 경매에 낙찰됐습니다!".formatted(productName);
		message.setText(text);
		mailSender.send(message);
	}
}
