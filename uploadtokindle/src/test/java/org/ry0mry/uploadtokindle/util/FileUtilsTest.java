package org.ry0mry.uploadtokindle.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.google.appengine.api.urlfetch.HTTPHeader;

import static org.hamcrest.CoreMatchers.is;

public class FileUtilsTest {

	@Test
	public void testToFileName() {
		assertThat(FileUtils.toFileName(""), is(""));
		assertThat(FileUtils.toFileName("http://example.com/foo/bar/file.ext"), is("file.ext"));
		assertThat(FileUtils.toFileName("http://example.com/foo/bar/file/"), is("file"));
		assertThat(FileUtils.toFileName("http://example.com/foo/bar/file.ext?param=1&param=2"), is("file.ext"));
	}

	@Test
	public void testFileExtString() {
		assertThat(FilenameUtils.getExtension(""), is(""));
		assertThat(FilenameUtils.getExtension("file.ext"), is("ext"));
		assertThat(FilenameUtils.getExtension("file.mid.ext"), is("ext"));
		assertThat(FilenameUtils.getExtension("file.longlonglongext"), is("longlonglongext"));
		assertThat(FilenameUtils.getExtension("nodot"), is(""));
	}

	@Test
	public void testGetContentType() {
		// case 1: multiple header
		// example from  http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.16
		List<HTTPHeader> headers = new ArrayList<HTTPHeader>(5);
		headers.add(new HTTPHeader("Date", "Wed, 15 Nov 1995 06:25:24 GMT"));
		headers.add(new HTTPHeader("Last-Modified", "Wed, 15 Nov 1995 04:58:08 GMT"));
		headers.add(new HTTPHeader("Content-Range", "bytes 21010-47021/47022"));
		headers.add(new HTTPHeader("Content-Length", "26012"));
		headers.add(new HTTPHeader("Content-Type", "image/gif"));

		String actual = FileUtils.getContentType(headers);
		assertThat(actual, is("image/gif"));


		// case 2: case of header name
		// case 3: no content-type
		// case 4: text/html; charset=ISO-8859-4
		// case 5:  TEXT/HTML ; charset=ISO-8859-4
	}
}
