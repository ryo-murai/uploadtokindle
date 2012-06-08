package org.ry0mry.uploadtokindle.controller;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

import org.mockito.cglib.core.CollectionUtils;
import org.ry0mry.uploadtokindle.model.UploadDocument;
import org.ry0mry.uploadtokindle.model.UploadSpec;
import org.ry0mry.uploadtokindle.service.DownloadService;
import org.ry0mry.uploadtokindle.service.MailToKindleService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.ResponseTooLargeException;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class UploadlinkController extends Controller {
	
	private static final Logger logger = Logger.getLogger(UploadlinkController.class.getName());
	
	private static final FetchOptions options = FetchOptions.Builder.withDeadline(10.0); 
	
	private final URLFetchService fetchService;

	private final MailToKindleService mailKindleService;

	// TODO: mockable
	private DownloadService downloadService = new DownloadService();
	
    public UploadlinkController() {
    	this(URLFetchServiceFactory.getURLFetchService(), new MailToKindleService());
    }

    public UploadlinkController(URLFetchService fetchService, MailToKindleService mailKindleService) {
		this.fetchService = fetchService;
		this.mailKindleService = mailKindleService;
	}

	@Override
    public Navigation run() throws Exception {
		UploadLinkDto dto = new UploadLinkDto();
		BeanUtil.copy(this.request, dto);
		UploadSpec uploadSpec = dto.getUploadSpec();

    	if(uploadSpec==null || uploadSpec.getExtSpec()==UploadSpec.Ext.UNKNOWN) {
    		return promptUploadSpec(dto, uploadSpec);
    	}

		UploadDocument document;
		try {
			document = this.downloadService.downloadDocument(dto.getUrl(), uploadSpec);
		} catch(SocketTimeoutException e) {
			// TODO:
		} catch(ResponseTooLargeException e) {
			// TODO:
		} catch(IOException e) {
			// TODO:
		}
		
		try {
			//this.mailKindleService.send(uploadUser, document);
		} catch(Exception e) {
			// TODO:
		}    	
    	
		// TODO: foward result page
    	return forward("uploadlink.jsp");
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
