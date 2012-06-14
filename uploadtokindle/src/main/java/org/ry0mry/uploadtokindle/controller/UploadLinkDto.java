package org.ry0mry.uploadtokindle.controller;

import org.apache.commons.io.FilenameUtils;
import org.ry0mry.uploadtokindle.model.UploadSpec;
import org.ry0mry.uploadtokindle.util.FileUtils;

public class UploadLinkDto {
	private String url;
	private String fileName;
	private String fileExt;
	private Boolean convert;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public Boolean getConvert() {
		return convert;
	}
	public void setConvert(Boolean convert) {
		this.convert = convert;
	}
	public boolean isConvertInPrimitive() {
		return convert!=null && convert.booleanValue();
	}
	
	public UploadSpec getUploadSpec() {
    	if(url==null) {
    		return null;
    	}

    	String fileName = this.fileName;
    	if(fileName==null) {
    		fileName = FileUtils.toFileName(url);
    	}
    	if(fileName.isEmpty()) {
    		fileName = "document";
    	}
    	
    	UploadSpec.Ext ext = UploadSpec.getExtFromString(fileExt!=null ? fileExt : FilenameUtils.getExtension(fileName));

    	return new UploadSpec(fileName, ext, isConvertInPrimitive());
	}
}
