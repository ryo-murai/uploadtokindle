package org.ry0mry.uploadtokindle.controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.ry0mry.uploadtokindle.model.UploadSpec;

public class UploadLinkDtoTest extends UploadLinkDto {

	@Test
	public void testUrlParam() {
		// case 1: no url specified
		UploadLinkDto target = new UploadLinkDto();
		target.setUrl(null);

		assertThat(target.getUploadSpec(), nullValue());

		// case 2: url without fileext specified and rest are not
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file/");
		UploadSpec uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("file"));
		assertThat(uploadSpec.getExtSpec(), is(UploadSpec.Ext.UNKNOWN));

		// case 3: url with fileext specified and rest are not
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file.mobi/");
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("file.mobi"));
		assertThat(uploadSpec.getExtSpec(), is(UploadSpec.Ext.MOBI));

		// case 4: url with parameter
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file?param=value");
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("file"));
	}

	@Test
	public void testFileAndExtParam() {
		// case 1: url and file are specified
		UploadLinkDto target = new UploadLinkDto();
		target.setUrl("http://example.com/file/");
		target.setFileNameSpec("specifiedFileName");
		UploadSpec uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("specifiedFileName"));
		assertThat(uploadSpec.getExtSpec(), is(UploadSpec.Ext.UNKNOWN));

		// case 2: url and file with ext are specified
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file.epub");
		target.setFileNameSpec("specifiedFileName.mobi");
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("specifiedFileName.mobi"));
		assertThat(uploadSpec.getExtSpec(), is(UploadSpec.Ext.MOBI));

		// case 3: url, file, and fileext are specified
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file.epub");
		target.setFileNameSpec("specifiedFileName.mobi");
		target.setExtSpec("PDF");
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("specifiedFileName.pdf"));
		assertThat(uploadSpec.getExtSpec(), is(UploadSpec.Ext.PDF));

		// case 4: url and blank filenamd are specified
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file/");
		target.setFileNameSpec("");
		target.setExtSpec("EPUB");
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.getFileNameSpec(), is("document.epub"));
		assertThat(uploadSpec.getExtSpec(), is(UploadSpec.Ext.EPUB));	}

	@Test
	public void testConvertParam() {
		// case 1: null convert spec specified
		UploadLinkDto target = new UploadLinkDto();
		target.setUrl("http://example.com/file/");
		target.setConvertSpec(null);
		UploadSpec uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.isConvertSpec(), is(false));

		// case 2: true convert spec specified
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file/");
		target.setConvertSpec(Boolean.TRUE);
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.isConvertSpec(), is(true));

		// case 3: false convert spec specified
		target = new UploadLinkDto();
		target.setUrl("http://example.com/file/");
		target.setConvertSpec(Boolean.FALSE);
		uploadSpec = target.getUploadSpec();
		assertThat(uploadSpec, notNullValue());
		assertThat(uploadSpec.isConvertSpec(), is(false));
	}
}
