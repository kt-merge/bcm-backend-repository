package com.example.chicken.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	private static final String BASE_URL = "https://www.ktdarius.shop/";
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

	@Async
	public void sendPasswordChangeEmail(String to, String resetToken) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("[Big Chicken Market] 비밀번호 변경 안내");

		String text = "아래 링크를 통해 비밀번호를 변경하세요:\n"
					  + BASE_URL + "/reset-password?token="
					  + resetToken;

		message.setText(text);
		mailSender.send(message);
	}

}
