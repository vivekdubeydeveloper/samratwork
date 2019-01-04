package com.s3.aws.controller;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import com.s3.aws.exception.AmazonUploadDownloadException;
import com.s3.aws.util.AspApiConstants;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({ AmazonUploadDownloadException.class, SocketTimeoutException.class, ResourceAccessException.class,
			Exception.class })
	public ResponseEntity<Map<String, Object>> exceptionHandler(HttpServletRequest request, Exception ex) {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> exObject;
		if (ex instanceof AmazonUploadDownloadException) {
			entity = new ResponseEntity<>(getExcObjDesc(((AmazonUploadDownloadException) ex).getMessage()), HttpStatus.OK);
		} else if (ex instanceof SocketTimeoutException || ex instanceof ResourceAccessException
				|| ex instanceof Exception) {
			exObject = new HashMap<>();
			exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.API_CONNECTION_ERROR);
			exObject.put(AspApiConstants.ERROR_DESC, " unable to process your request please try again later.");
			exObject.put(AspApiConstants.ERROR_GRP, AspApiConstants.API_CONNECTION_ERROR);
			entity = new ResponseEntity<>(exObject, HttpStatus.GATEWAY_TIMEOUT);
		}
		return entity;
	}

	public Map<String, Object> getExcObjDesc(String message) {
		Map<String, Object> exObject = new LinkedHashMap<>();
		exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ERRORCODE);
		exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
		if (message != null)
			exObject.put(AspApiConstants.ERROR_DESC, message);
		else
			exObject.put(AspApiConstants.ERROR_DESC, " Connection Error: Unable to connect to destination ");
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.API_CONNECTION_ERROR);
		return exObject;
	}
}
