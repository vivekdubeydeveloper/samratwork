/**
 * 
 */
package com.s3.aws.exception;

import java.util.HashMap;
import java.util.Map;



/**
 * @auth
 *
 */
public class AmazonUploadDownloadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6125976525557460700L;

	private Map<String, Object> excObj = new HashMap<String, Object>();

	public AmazonUploadDownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Map<String, Object> excObj) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.excObj = excObj;
	}

	public AmazonUploadDownloadException(String message) {
		super(message);
	}

	public Map<String, Object> getExcObj() {
		return excObj;
	}

}
