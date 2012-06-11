package org.ry0mry.uploadtokindle.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

public class URLValidator extends AbstractValidator {

	public static final URLValidator INSTANCE = new URLValidator();

	public URLValidator() {
		super();
	}

	public URLValidator(String message) {
		super(message);
	}

	@Override
	public String validate(Map<String, Object> parameters, String name) {
		Object value = parameters.get(name);
		if(value!=null) {
			try {
				new URL(String.valueOf(value));
			} catch (MalformedURLException e) {
	            if (message != null) {
	                return message;
	            }
	            return ApplicationMessage.get(getMessageKey(), getLabel(name));
			}
		}
		
		return null;
	}

	@Override
	protected String getMessageKey() {
		return "validator.url.invalid";
	}
}
