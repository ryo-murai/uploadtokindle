package org.ry0mry.uploadtokindle.service;

import java.io.IOException;

import org.ry0mry.uploadtokindle.exception.ErrorConsts;
import org.ry0mry.uploadtokindle.exception.Upload2KindleException;
import org.ry0mry.uploadtokindle.model.UploadDocument;
import org.ry0mry.uploadtokindle.model.UploadUser;

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

	public void send(UploadUser uploadUser, UploadDocument document) throws Upload2KindleException {
		String sender = uploadUser.getUser().getEmail();
		String to = uploadUser.getCompleteAddress();
		String subject = document.getUploadSpec().isConvertSpec() ? "convert" : "";
		String body = "";
		MailService.Message message = 
				new MailService.Message(sender, to, subject, body);
		message.setAttachments(new MailService.Attachment(document.getUploadSpec().getFileNameSpec(), document.getContent()));
		
		try {
			this.mailService.send(message);
		} catch (IOException e) {
			throw new Upload2KindleException(e, ErrorConsts.ERR_SENDMAIL);
		}
	}
}
