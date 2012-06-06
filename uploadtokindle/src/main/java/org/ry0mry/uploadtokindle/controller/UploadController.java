package org.ry0mry.uploadtokindle.controller;

import java.util.logging.Logger;

import org.ry0mry.uploadtokindle.dataaccess.UploadUserDao;
import org.ry0mry.uploadtokindle.model.UploadUser;
import org.ry0mry.uploadtokindle.service.MailToKindleService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UploadController extends Controller {
	private static final Logger logger = Logger.getLogger(UploadController.class.getName());
	
	private final MailToKindleService toKindleService;
	
	private final UserService userService = UserServiceFactory.getUserService();
	
	// TODO: make mockable
	private final UploadUserDao userDao = new UploadUserDao();

    public UploadController(MailToKindleService toKindleService) {
		this.toKindleService = toKindleService;
	}
    
    public UploadController() {
    	this(new MailToKindleService());
    }

	@Override
    public Navigation run() throws Exception {
		if(!this.userService.isUserLoggedIn()) {
			return redirect(this.userService.createLoginURL(this.request.getRequestURI()));
		}

		if(this.isPost()) {
    		FileItem fileItem = requestScope("file");
    		String convert = requestScope("convert");
    		boolean convertOption = convert != null;

    		User user = this.userService.getCurrentUser();
    		UploadUser uploadUser = this.userDao.getUploadUser(user);
			logger.fine("to mail " + fileItem.getFileName() + " from " + user.getEmail() + " to " + uploadUser.getUploadDestAddress());
    		
    		this.toKindleService.mailDocument(
    				user.getEmail(), 
    				uploadUser.getUploadDestAddress(), 
    				fileItem.getFileName(), 
    				fileItem.getData(), 
    				convertOption);
    		
    		// TODO: show result
    		return redirect("/");
    	}

    	return forward("/");
    }
}
