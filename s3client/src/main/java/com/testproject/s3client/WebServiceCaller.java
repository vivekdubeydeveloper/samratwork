package com.testproject.s3client;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.testproject.s3client.helper.ProcessDetails;
import com.testproject.s3client.helper.ProcessSummary;
import com.testproject.s3client.util.ApplicationConstant;
import com.testproject.s3client.util.PropertyReader;

public class WebServiceCaller implements Runnable {
	
	final static Logger logger = Logger.getLogger(WebServiceCaller.class);
	
	private String input;
	
	public WebServiceCaller(String input) {
		this.input = input;
	}

	public void run() {

		long sTime=System.currentTimeMillis();
		ProcessDetails processDetails=new ProcessDetails();
		try {
			
			
			String url = PropertyReader.getProperty("app.url");

			Client client = Client.create();
			WebResource webResource = client.resource(url);
			logger.debug("url ="+url+" input ="+input);
		    
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
			String output = response.getEntity(String.class);
			logger.debug("result== "+output);
			
			long eTime=System.currentTimeMillis();
			
			
			processDetails.setPayload(input);
			processDetails.setToalTimeInProcess(eTime-sTime);
			processDetails.setStatus(ApplicationConstant.SUCCESS);
			ProcessSummary.addProcessDetails(processDetails);

		} catch (IOException e) {
			long eTime=System.currentTimeMillis();
			processDetails.setPayload(input);
			processDetails.setToalTimeInProcess(eTime-sTime);
			processDetails.setStatus(ApplicationConstant.FAIL);
			ProcessSummary.addProcessDetails(processDetails);
			logger.error("Error in: "+input);
			logger.error("Error in: ",e);
		}

	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	
	

}
