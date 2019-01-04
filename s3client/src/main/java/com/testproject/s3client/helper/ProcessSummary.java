package com.testproject.s3client.helper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProcessSummary {
	
	private static List<ProcessDetails> processDetailsList = new CopyOnWriteArrayList<>();
	
	public static void addProcessDetails(ProcessDetails processDetails) {
		processDetailsList.add(processDetails);
	}

	public static List<ProcessDetails> getProcessDetailsList() {
		return processDetailsList;
	}

	public static void setProcessDetailsList(List<ProcessDetails> processDetailsList) {
		ProcessSummary.processDetailsList = processDetailsList;
	}
	
	

}
