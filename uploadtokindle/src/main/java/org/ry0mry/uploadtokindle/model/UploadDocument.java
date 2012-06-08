package org.ry0mry.uploadtokindle.model;

public class UploadDocument {
	private final UploadSpec uploadSpec;
	private final byte[] content;

	public UploadDocument(UploadSpec uploadSpec, byte[] content) {
		this.uploadSpec = uploadSpec;
		this.content = content;
	}

	public UploadSpec getUploadSpec() {
		return uploadSpec;
	}

	public byte[] getContent() {
		return content;
	}
}
