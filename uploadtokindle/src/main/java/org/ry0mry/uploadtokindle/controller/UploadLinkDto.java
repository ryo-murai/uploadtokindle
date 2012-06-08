package org.ry0mry.uploadtokindle.controller;

import org.apache.commons.io.FilenameUtils;
import org.ry0mry.uploadtokindle.model.UploadSpec;
import org.ry0mry.uploadtokindle.util.FileUtils;

public class UploadLinkDto {
	private String url;
	private String fileNameSpec;
	private String extSpec;
	private Boolean convertSpec;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileNameSpec() {
		return fileNameSpec;
	}
	public void setFileNameSpec(String fileNameSpec) {
		this.fileNameSpec = fileNameSpec;
	}
	public String getExtSpec() {
		return extSpec;
	}
	public void setExtSpec(String extSpec) {
		this.extSpec = extSpec;
	}
	public Boolean isConvertSpec() {
		return convertSpec;
	}
	public void setConvertSpec(Boolean convertSpec) {
		this.convertSpec = convertSpec;
	}
	
	public UploadSpec getUploadSpec() {
    	if(url==null) {
    		return null;
    	}

    	boolean convert = convertSpec!=null && convertSpec.booleanValue();

    	String fileName = this.fileNameSpec;
    	if(fileName==null) {
    		fileName = FileUtils.toFileName(url);
    	}
    	if(fileName.isEmpty()) {
    		fileName = "document";
    	}
    	
    	UploadSpec.Ext ext = UploadSpec.getExtFromString(extSpec!=null ? extSpec : FilenameUtils.getExtension(fileName));

    	return new UploadSpec(fileName, ext, convert);
	}
}
