package org.ry0mry.uploadtokindle.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ry0mry.uploadtokindle.dataaccess.UploadUserDao;
import org.ry0mry.uploadtokindle.exception.Upload2KindleException;
import org.ry0mry.uploadtokindle.model.UploadDocument;
import org.ry0mry.uploadtokindle.model.UploadSpec;
import org.ry0mry.uploadtokindle.model.UploadUser;
import org.ry0mry.uploadtokindle.service.DownloadService;
import org.ry0mry.uploadtokindle.service.MailToKindleService;
import org.ry0mry.uploadtokindle.service.UrlFetcherService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UploadlinkController extends Controller {
	
	private static final Logger logger = Logger.getLogger(UploadlinkController.class.getName());
	
	private final MailToKindleService kindleMailer;
	
	private final UrlFetcherService urlFetcher;
	
	private UploadUserDao uploadUserDao = new UploadUserDao();
	
	private UserService userService = UserServiceFactory.getUserService();
	
    public UploadlinkController() {
    	this(new UrlFetcherService(), new MailToKindleService());
    }

    public UploadlinkController(UrlFetcherService fetchService, MailToKindleService mailKindleService) {
		this.urlFetcher = fetchService;
		this.kindleMailer = mailKindleService;
	}

	@Override
    public Navigation run() throws Exception {
		User user = this.userService.getCurrentUser();
		
		UploadLinkDto dto = new UploadLinkDto();
		BeanUtil.copy(request, dto);

		// validate
		Validators v = new Validators(request);
		if(!v.add("url", v.required(), URLValidator.INSTANCE).validate()) {
			return promptUploadSpec(dto, null);
		}

		UploadUser uploadUser = this.uploadUserDao.getUploadUser(user);
		
		UploadSpec uploadSpec = this.urlFetcher.decideUploadSpec(dto);
		if(uploadSpec.getExtSpec()==UploadSpec.Ext.UNKNOWN) {
			// TODO:
			return forward("");
		}

		try {
			UploadDocument document = this.urlFetcher.download(dto.getUrl(), uploadSpec);
			this.kindleMailer.send(uploadUser, document);
			// TODO: parameterize some info ...
			return forward("success.jsp");
		} catch(Upload2KindleException e){
			logger.log(Level.SEVERE, "", e);
			requestScope("throwable", e);
			return redirect("errors");
		}
    }
	
	private Navigation promptUploadSpec(UploadLinkDto dto, UploadSpec uploadSpec) {
		this.requestScope("url", dto.getUrl());
		if(uploadSpec==null) {
			this.requestScope("fileName", "");
			this.requestScope("fileExt", "");
			this.requestScope("convert", "");
		} else {
			this.requestScope("fileName", uploadSpec.getFileNameSpec());
			this.requestScope("fileExt", uploadSpec.getExtSpec());
			this.requestScope("convert", uploadSpec.isConvertSpec() ? "on" : "");
		}
        return forward("uploadlink.jsp");	
    }
}
