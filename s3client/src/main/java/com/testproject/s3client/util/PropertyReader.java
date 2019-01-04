package com.testproject.s3client.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	private static Properties prop=new Properties();
	
	public static String getProperty(String key) throws IOException {
		if(prop.isEmpty()) {
			prop.load(PropertyReader.class.getResourceAsStream("/com/testproject/s3client/resource/client.properties"));
		}
		return prop.getProperty(key);
	}
}
