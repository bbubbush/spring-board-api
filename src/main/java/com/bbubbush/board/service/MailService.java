package com.bbubbush.board.service;

import com.bbubbush.board.dto.common.ReqSendMail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

  private final JavaMailSender mailSender;

  public void sendMail(ReqSendMail reqSendMail) {
    final MimeMessage mimeMessage = mailSender.createMimeMessage();
    final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    try {
      mimeMessageHelper.setFrom(reqSendMail.getFrom());
      mimeMessageHelper.setTo(reqSendMail.getTo());
      mimeMessageHelper.setSubject(reqSendMail.getSubject());
      mimeMessageHelper.setText(reqSendMail.getText());
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    mailSender.send(mimeMessage);
  }

}
