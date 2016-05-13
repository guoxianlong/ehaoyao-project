package com.ehaoyao.yhdjkg.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.util.EncodingUtil;

public class CustomFilePart extends FilePart {
	public CustomFilePart(String filename, File file)
			throws FileNotFoundException {
		super(filename, file);
	}

	protected void sendDispositionHeader(OutputStream out) throws IOException {
		out.write(CONTENT_DISPOSITION_BYTES);
		out.write(QUOTE_BYTES);
		out.write(EncodingUtil.getAsciiBytes(getName()));
		out.write(QUOTE_BYTES);
		String filename = getSource().getFileName();
		if (filename != null) {
			out.write(EncodingUtil.getAsciiBytes(FILE_NAME));
			out.write(QUOTE_BYTES);
			out.write(URLEncoder.encode(filename, "utf-8").getBytes());
			out.write(QUOTE_BYTES);
		}
	}
}
