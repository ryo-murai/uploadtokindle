package org.ry0mry.uploadtokindle.controller;

import java.util.Properties;
import java.util.logging.Logger;

import org.ry0mry.uploadtokindle.service.MailToKindleService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

public class UploadController extends Controller {
	private static final Logger logger = Logger.getLogger(UploadController.class.getName());
	
	private final MailToKindleService toKindleService;

    public UploadController(MailToKindleService toKindleService) {
		this.toKindleService = toKindleService;
	}
    
    public UploadController() {
    	this(new MailToKindleService());
    }

	@Override
    public Navigation run() throws Exception {
    	if(this.isPost()) {
    		FileItem fileItem = requestScope("file");
    		String convert = requestScope("convert");
    		boolean convertOption = convert != null;

    		Properties props = new Properties();
    		props.load(UploadController.class.getResourceAsStream("/secret.properties"));
    		
    		String sender = props.getProperty("mail.sender");
    		String recipient = props.getProperty("mail.recipient");
			logger.info("to mail " + fileItem.getFileName() + " from " + sender + " to " + recipient);
    		
    		this.toKindleService.mailDocument(
    				sender, 
    				recipient, 
    				fileItem.getFileName(), 
    				fileItem.getData(), 
    				convertOption);
    		
    		// TODO: show result
    		return redirect("/");
    	}

    	return forward("/index.htm");
    }
}
