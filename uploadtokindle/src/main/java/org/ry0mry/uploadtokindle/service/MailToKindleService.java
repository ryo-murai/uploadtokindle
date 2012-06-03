package org.ry0mry.uploadtokindle.service;

import java.io.IOException;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

public class MailToKindleService {

	private final MailService mailService;

	public MailToKindleService() {
		this(MailServiceFactory.getMailService());
	}

	public MailToKindleService(MailService mailService) {
		this.mailService = mailService;
	}

	public void mailDocument(String fromAddress, String toAddress, String fileName, byte[] document, boolean converterOption) throws IOException {
		MailService.Message message = 
				new MailService.Message(fromAddress, toAddress, converterOption ? "convert" : "", "");
		message.setAttachments(new MailService.Attachment(fileName, document));
		
		this.mailService.send(message);
	}
}
