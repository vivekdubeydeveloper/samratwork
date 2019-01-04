package com.testproject.s3client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.testproject.s3client.helper.ProcessDetails;
import com.testproject.s3client.helper.ProcessSummary;
import com.testproject.s3client.util.ApplicationConstant;
import com.testproject.s3client.util.JSONUtil;
import com.testproject.s3client.util.PropertyReader;

public class AmazonLoadTester {

	final static Logger logger = Logger.getLogger(AmazonLoadTester.class);

	public void testAWSLoad() {

		try {

			List<Map<String, String>> payloadList = new ArrayList<>();

			long sTime = System.currentTimeMillis();
			logger.debug("Start Time "+LocalDateTime.now());

			@SuppressWarnings("unchecked")
			Map<String, Object> inputMap = (Map<String, Object>) JSONUtil
					.getJsonObjectFromFile(PropertyReader.getProperty("input_json_file"));


			Iterator<Entry<String, Object>> inputSetItr = inputMap.entrySet().iterator();

			while (inputSetItr.hasNext()) {

				Entry<String, Object> inputEntry = inputSetItr.next();
				String bucketName = inputEntry.getKey();
				@SuppressWarnings("unchecked")
				Map<String, String> valMap = (Map<String, String>) inputEntry.getValue();

				String basePath = valMap.get(ApplicationConstant.BASE_PATH);
				String region = valMap.get(ApplicationConstant.REGION);
				String filesName = valMap.get(ApplicationConstant.FILES_NAME);

				String[] fileames = filesName.split(",");
				if (fileames.length == 0) {
					Map<String, String> payloadInputMap = new HashMap<>();
					payloadInputMap.put(ApplicationConstant.BUCKET_NAME, bucketName);
					payloadInputMap.put(ApplicationConstant.FILE_PATH, basePath);
					payloadInputMap.put(ApplicationConstant.REGION, region);
					payloadList.add(payloadInputMap);
				} else {
					for (String fileName : fileames) {
						Map<String, String> payloadInputMap = new HashMap<>();
						payloadInputMap.put(ApplicationConstant.BUCKET_NAME, bucketName);
						payloadInputMap.put(ApplicationConstant.FILE_PATH, basePath+fileName);
						payloadInputMap.put(ApplicationConstant.REGION, region);
						payloadList.add(payloadInputMap);
					}
				}

			}

			ExecutorService pool = Executors.newFixedThreadPool(payloadList.size());

			for (Map<String,String> payload : payloadList) {
				logger.debug("payload= "+payload);
				Runnable r1 = new WebServiceCaller(JSONUtil.getJson(payload));
				pool.execute(r1);
			}

			pool.shutdown();
			while (!pool.isTerminated()) {
			}

			long eTime = System.currentTimeMillis();
			ProcessDetails processDetails=new ProcessDetails();
			processDetails.setPayload(ApplicationConstant.COMPLETE_SUMMARY);
			processDetails.setToalTimeInProcess(eTime-sTime);
			processDetails.setStatus("");
			ProcessSummary.addProcessDetails(processDetails);
			
			printSummary();
			logger.debug("End Time "+LocalDateTime.now());
		} catch (Exception e) {
			logger.error("Error in: ", e);

		}

	}

	
	private void printSummary() {
		logger.debug("++++++++++++++++Summary Begin+++++++++++++++++++++++++");
		ProcessSummary.getProcessDetailsList().forEach(logger::debug);
		logger.debug("++++++++++++++++Summary End+++++++++++++++++++++++++");
	}
}
