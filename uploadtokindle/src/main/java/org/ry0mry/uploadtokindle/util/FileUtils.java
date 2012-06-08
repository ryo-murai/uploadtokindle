package org.ry0mry.uploadtokindle.util;

import java.util.List;

import com.google.appengine.api.urlfetch.HTTPHeader;

public class FileUtils {
	public static String toFileName(String url) {
		String noParamUrl = url.replaceFirst("\\?.*", "");
		String [] split = noParamUrl.split("/");
		
		return split.length > 0 ? split[split.length-1] : noParamUrl;
	}

	public static String getContentType(List<HTTPHeader> headers) {
		for(HTTPHeader header : headers) {
			if("Content-Type".equalsIgnoreCase(header.getName())) {
				String mediaType = header.getValue();
				int index = mediaType.indexOf(';');

				String type = index == -1 ? mediaType : mediaType.substring(0, index);

				return type.trim().toLowerCase();
			}
		}

		return null;
	}
}
