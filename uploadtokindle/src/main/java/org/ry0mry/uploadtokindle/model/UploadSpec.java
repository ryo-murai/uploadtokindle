package org.ry0mry.uploadtokindle.model;

import org.apache.commons.io.FilenameUtils;

public class UploadSpec {
	public enum Ext {
		UNKNOWN,
		AUTO,
		PDF,
		MOBI,
		EPUB,
		DOC,
		DOCX,
		JPEG,
		JPG,
		PNG,
		BMP,
		HTM,
		HTML
	}

	private final String fileNameSpec;
	private final UploadSpec.Ext extSpec;
	private final boolean convertSpec;

	public UploadSpec(String fileNameSpec, Ext extSpec, boolean convertSpec) {
		this.fileNameSpec = fileNameSpec(fileNameSpec, extSpec);
		this.extSpec = extSpec;
		this.convertSpec = convertSpec;
	}

	private String fileNameSpec(String fileNameSpec, Ext extSpec) {
		if (extSpec == null
		 || fileNameSpec == null
		 || extSpec == Ext.AUTO 
		 || extSpec == Ext.UNKNOWN) {
			return fileNameSpec;
		}
		
		String extString = FilenameUtils.getExtension(fileNameSpec);
		if(extString.equalsIgnoreCase(extSpec.name())) {
			return fileNameSpec;
		} else {
			return FilenameUtils.getBaseName(fileNameSpec) + "." + extSpec.name().toLowerCase();
		}
	}
	
	public String getFileNameSpec() {
		return fileNameSpec;
	}

	public UploadSpec.Ext getExtSpec() {
		return extSpec;
	}

	public boolean isConvertSpec() {
		return convertSpec;
	}

	public UploadSpec applyFetchResponse(String finalUrl, String mediaType) {
		if(this.extSpec!=Ext.AUTO) {
			return this;
		}
		
		// TODO: need refactoring
		Ext newExt = getExtFromString(FilenameUtils.getExtension(finalUrl));
		if(newExt==Ext.UNKNOWN) {
			newExt = fromContentType(mediaType);
		}
		
		return new UploadSpec(finalUrl, newExt, this.convertSpec);
	}

	public static Ext getExtFromString(String extString) {
		try {
			return Ext.valueOf(extString.toUpperCase());
		} catch (IllegalArgumentException e) {
			return Ext.UNKNOWN;
		}
	}

	private static Ext fromContentType(String contentType) {
		if("application/msword".equals(contentType)) {
			return Ext.DOCX;
		} else if("application/pdf".equals(contentType)) {
			return Ext.PDF;
		} else if("image/jpeg".equals(contentType)) {
			return Ext.JPEG;
		} else if("image/png".equals(contentType)) {
			return Ext.PNG;
		} else if("image/x-ms-bmp".equals(contentType)) {
			return Ext.BMP;
		} else if("text/html".equals(contentType)) {
			return Ext.HTML;
		} else {
			return Ext.UNKNOWN;
		}
	}
}
