package org.ry0mry.uploadtokindle.service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.ry0mry.uploadtokindle.controller.UploadLinkDto;
import org.ry0mry.uploadtokindle.exception.ErrorConsts;
import org.ry0mry.uploadtokindle.exception.Upload2KindleException;
import org.ry0mry.uploadtokindle.model.UploadDocument;
import org.ry0mry.uploadtokindle.model.UploadSpec;
import org.ry0mry.uploadtokindle.util.FileUtils;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.ResponseTooLargeException;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class UrlFetcherService {
	private static final FetchOptions options = FetchOptions.Builder.withDeadline(10.0); 

	private final URLFetchService fetchService;

	public UrlFetcherService() {
		this(URLFetchServiceFactory.getURLFetchService());
	}

	public UrlFetcherService(URLFetchService fetchService) {
		this.fetchService = fetchService;
	}

	public UploadSpec decideUploadSpec(UploadLinkDto dto) {
    	if(dto.getUrl()==null) {
    		return null;
    	}

    	String fileName = dto.getFileName();
    	if(fileName==null) {
    		fileName = FileUtils.toFileName(dto.getUrl());
    	}
    	if(fileName.isEmpty()) {
    		fileName = "document";
    	}
    	
    	UploadSpec.Ext ext = UploadSpec.getExtFromString(dto.getFileExt()!=null ? dto.getFileExt() : FilenameUtils.getExtension(fileName));

    	return new UploadSpec(fileName, ext, dto.isConvertInPrimitive());
	}
	
	public UploadDocument download(String url, UploadSpec uploadSpec) throws Upload2KindleException {
		HTTPResponse fetchResp = downloadInternal(url, uploadSpec);
		int statusCode = fetchResp.getResponseCode();

		if(statusCode==HttpServletResponse.SC_OK) {
			if(uploadSpec.getExtSpec()==UploadSpec.Ext.AUTO) {
				URL finalUrls = fetchResp.getFinalUrl();
				String finalFileName = finalUrls != null ? FileUtils.toFileName(fetchResp.getFinalUrl().toString()) : uploadSpec.getFileNameSpec();
				String mediaType = FileUtils.getContentType(fetchResp.getHeaders());
				uploadSpec = uploadSpec.applyFetchResponse(finalFileName, mediaType);
			}

			return new UploadDocument(uploadSpec, fetchResp.getContent());
		} else {
			throw new Upload2KindleException(ErrorConsts.ERR_FETCH + statusCode);
		}
	}

	private HTTPResponse downloadInternal(String url, UploadSpec uploadSpec) throws Upload2KindleException {
		try {
			HTTPRequest fetchReq = new HTTPRequest(new URL(url), HTTPMethod.GET, options);
			return this.fetchService.fetch(fetchReq);
		} catch (SocketTimeoutException e) {
			throw new Upload2KindleException(ErrorConsts.ERR_FETCH_TIMEOUT);
		} catch (ResponseTooLargeException e) {
			throw new Upload2KindleException(ErrorConsts.ERR_FETCH_TOOLARGE);
		} catch (IOException e) {
			throw new Upload2KindleException(ErrorConsts.ERR_FETCH);
		}
	}
}
