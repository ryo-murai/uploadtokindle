package org.ry0mry.uploadtokindle.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Attachment;

public class MailToKindleServiceTest {

	@Test
	public void testMailDocument() throws IOException {
		// setup mocks and inputs
		MailService mockMailService = mock(MailService.class);
		byte[] document = {'a', 'b', 'c'};

		// setup target
		MailToKindleService target = new MailToKindleService(mockMailService);

		// test
		target.mailDocument("from@Address", "to@Address", "fileName.ext", document, false);

		// verify
		ArgumentCaptor<MailService.Message> messageArgCapture = 
				ArgumentCaptor.forClass(MailService.Message.class);
		verify(mockMailService).send(messageArgCapture.capture());
		MailService.Message actual = messageArgCapture.getValue();

		assertThat(actual.getSubject().contains("convert"), is(false));
		assertThat(actual.getSender(), is("from@Address"));
		assertThat(actual.getTo().size(), is(1));
		assertThat(actual.getTo().contains("to@Address"), is(true));
		assertThat(actual.getAttachments().size(), is(1));
		MailService.Attachment actualAtch = (Attachment) actual.getAttachments().toArray()[0];
		assertThat(actualAtch.getFileName(), is("fileName.ext"));
		assertArrayEquals(actualAtch.getData(), document);
	}

	@Test
	public void testMailDocumentWithConvertOption() throws IOException {
		// setup mocks and inputs
		MailService mockMailService = mock(MailService.class);
		byte[] document = {'a', 'n', 'y'};

		// setup target
		MailToKindleService target = new MailToKindleService(mockMailService);

		// test
		target.mailDocument("any", "any", "any", document, true);

		// verify
		ArgumentCaptor<MailService.Message> messageArgCapture = 
				ArgumentCaptor.forClass(MailService.Message.class);
		verify(mockMailService).send(messageArgCapture.capture());

		assertThat(messageArgCapture.getValue().getSubject(), is("convert"));
	}

}
