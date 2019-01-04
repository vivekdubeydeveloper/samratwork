package com.testproject.s3client;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class AmazonS3Client {
	final static Logger logger = Logger.getLogger(AmazonS3Client.class);
	public static void main(String[] args) {
		logger.debug("++++++++++Process Started++++++++++++++++");
		AmazonLoadTester alt=new AmazonLoadTester();
		alt.testAWSLoad();
		logger.debug("++++++++++Process End++++++++++++++++");
	}
}
