package com.testproject.s3client.helper;

public class ProcessDetails {

	private long toalTimeInProcess;
	private String payload;
	private String status;
	
	
	public long getToalTimeInProcess() {
		return toalTimeInProcess;
	}
	public void setToalTimeInProcess(long toalTimeInProcess) {
		this.toalTimeInProcess = toalTimeInProcess;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProcessDetails [toalTimeInProcess=" + toalTimeInProcess + ", payload=" + payload + ", status=" + status
				+ "]";
	}
	
	
	
	
}
