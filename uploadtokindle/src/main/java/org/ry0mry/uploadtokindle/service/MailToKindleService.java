package org.ry0mry.uploadtokindle.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailToKindleService {
	public void mailDocument(String fromAddress, String toAddress, byte[] document, boolean convertOption) throws MessagingException {
		Session session = Session.getDefaultInstance(new Properties());

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
		
		Multipart multipart = new MimeMultipart();
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(convertOption ? "convert" : "", "utf-8");
		multipart.addBodyPart(bodyPart);

		MimeBodyPart attachement = new MimeBodyPart();
		attachement.setContent(document, "application/zip");
		multipart.addBodyPart(attachement);
		
		message.setContent(multipart);
		
		Transport.send(message);
	}
}
