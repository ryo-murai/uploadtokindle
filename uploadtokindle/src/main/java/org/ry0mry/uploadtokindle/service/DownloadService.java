package org.ry0mry.uploadtokindle.service;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.ry0mry.uploadtokindle.model.UploadDocument;
import org.ry0mry.uploadtokindle.model.UploadSpec;
import org.ry0mry.uploadtokindle.util.FileUtils;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class DownloadService {
	private static final FetchOptions options = FetchOptions.Builder.withDeadline(10.0); 

	private final URLFetchService fetchService;

	public DownloadService(URLFetchService fetchService) {
		this.fetchService = fetchService;
	}
	
	public DownloadService() {
		this(URLFetchServiceFactory.getURLFetchService());
	}

	public UploadDocument downloadDocument(String url, UploadSpec uploadSpec) throws IOException {
		HTTPRequest fetchReq = new HTTPRequest(new URL(url), HTTPMethod.GET, options);
		HTTPResponse fetchResp = this.fetchService.fetch(fetchReq);
		int statusCode = fetchResp.getResponseCode();

		if(statusCode==HttpServletResponse.SC_OK) {
			if(uploadSpec.getExtSpec()==UploadSpec.Ext.AUTO) {
				String finalUrl = FileUtils.toFileName(fetchResp.getFinalUrl().toString());
				String mediaType = FileUtils.getContentType(fetchResp.getHeaders());
				uploadSpec = uploadSpec.applyFetchResponse(finalUrl, mediaType);
			}

			return new UploadDocument(uploadSpec, fetchResp.getContent());
		} else {
			throw new IOException(""+statusCode);
		}
	}

}
