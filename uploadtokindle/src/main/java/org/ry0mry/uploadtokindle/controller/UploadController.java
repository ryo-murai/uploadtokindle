package org.ry0mry.uploadtokindle.controller;

import org.ry0mry.uploadtokindle.service.MailToKindleService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

public class UploadController extends Controller {
	
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

    		this.toKindleService.mailDocument("murai.ry@gmail.com", "murai.ry@kindle.com", fileItem.getData(), convertOption);
    		
    		return forward("/");
    	}
        return null;
    }
}
