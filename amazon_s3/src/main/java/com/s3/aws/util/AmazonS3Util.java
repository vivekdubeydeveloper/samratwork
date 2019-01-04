package com.s3.aws.util;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.s3.aws.exception.AmazonUploadDownloadException;
import com.s3.aws.model.Amazon;

@Configuration

public class AmazonS3Util {

	public static AmazonS3 getAmazonS3Client(String secretkey, String clientId, Regions regions) throws Exception {
		AmazonS3 s3client = null;
		try {
			AWSCredentials credentials = new BasicAWSCredentials(clientId, secretkey);
			s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(regions).build();

		} catch (Exception e) {
			throw new AmazonUploadDownloadException(e.getMessage());
		}
		return s3client;
	}

	@SuppressWarnings("deprecation")
	public static Object multipleFileUpload(String bucketName, String filePath, AmazonS3 s3client) throws Exception {
		File file = new File(filePath);
		List<File> filesList = new ArrayList<File>();
		if (file.exists()) {
			List<Amazon> list = new ArrayList<>();
			if (file.isDirectory()) {
				for (File path : file.listFiles()) {
					filesList.add(path);
					setAmazon(bucketName, filePath, path, s3client.getRegion(), list);
				}
			} else {
				filesList.add(file);
				setAmazon(bucketName, filePath, file, s3client.getRegion(), list);
			}
			TransferManager xfer_mgr = new TransferManager(s3client);
			MultipleFileUpload xfer = null;
			try {
				if (filesList.size() > 1)
					xfer = xfer_mgr.uploadFileList(bucketName, "", new File(filePath), filesList);
				else
					xfer = xfer_mgr.uploadFileList(bucketName, "", new File(file.getParent()), filesList);
				XferMgrProgress.showTransferProgress(xfer);
				XferMgrProgress.waitForCompletion(xfer);
			} catch (Exception e) {
				throw new AmazonUploadDownloadException(e.getMessage());
			}
			xfer_mgr.shutdownNow();
			return list;

		} else
			throw new AmazonUploadDownloadException("folder doesn't exist please provide a valid  path");

	}

	public static void setAmazon(String bucketName, String filePath, File file, Region region, List<Amazon> list) {
		Amazon amazon = new Amazon();
		amazon.setBucketName(bucketName);
		amazon.setFilePath(filePath);
		amazon.setFileSize(file.length());
		amazon.setFileName(file.getName());
		amazon.setKey(file.getName());
		amazon.setFileType(getFileExtension(file));
		amazon.setRegion(region.name());
		list.add(amazon);
	}

	public static Object downloadFile(String bucketName, String key, String filePath, AmazonS3 s3client)
			throws Exception {
		File file = new File(filePath + "/" + key);
		if (file.exists()) {
			TransferManager xfer_mgr = new TransferManager(s3client);
			try {
				Download xfer = xfer_mgr.download(bucketName, key, file);
				XferMgrProgress.showTransferProgress(xfer);
				XferMgrProgress.waitForCompletion(xfer);
			} catch (AmazonServiceException e) {
				System.exit(1);
			}

			xfer_mgr.shutdownNow();
			file = new File(filePath + "/" + key);
			Amazon amazon = new Amazon();
			amazon.setBucketName(bucketName);
			amazon.setFilePath(filePath);
			amazon.setFileSize(file.length());
			amazon.setFileName(file.getName());
			amazon.setKey(file.getName());
			amazon.setFileType(getFileExtension(file));
			amazon.setRegion(s3client.getRegion().name());
			amazon.setFile(Files.readAllBytes(file.toPath()));
			return amazon;
		} else
			throw new AmazonUploadDownloadException("folder doesn't exist please provide a valid  path");

	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
}
