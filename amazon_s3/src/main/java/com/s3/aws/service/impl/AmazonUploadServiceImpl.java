package com.s3.aws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.s3.aws.exception.AmazonUploadDownloadException;
import com.s3.aws.service.AmazonBaseService;
import com.s3.aws.util.AmazonS3Util;

@Service
public class AmazonUploadServiceImpl implements AmazonBaseService {

	@Value("${aws3.secretkey}")
	String secretkey;

	@Value("${aws3.client.id}")
	String clientId;

	@Override
	public Bucket createBucket(String bucketName, Regions regions) throws Exception {
		Bucket bucke = null;
		if (!isBucketExist(bucketName, regions)) {
			bucke = getAmazonS3Client(regions).createBucket(bucketName);
			return bucke;
		} else
			throw new AmazonUploadDownloadException(bucketName + " Bucket already exists ::");
	}

	@Override
	public boolean isBucketExist(String bucketName, Regions regions) throws Exception {
		if (bucketName != null)
			return getAmazonS3Client(regions).doesBucketExist(bucketName);
		else
			return false;
	}

	@Override
	public void deleteBucket(String bucketName, Regions regions) throws Exception {
		if (isBucketExist(bucketName, regions))
			getAmazonS3Client(regions).deleteBucket(bucketName);

	}

	@Override
	public List<Bucket> getListBuckets(Regions regions) throws Exception {
		return AmazonS3Util.getAmazonS3Client(secretkey, clientId, regions).listBuckets();
	}

	@Override
	public Object uploadFile(String bucketName, String key, String filePath, Regions regions) throws Exception {
		if (isBucketExist(bucketName, regions))
			return AmazonS3Util.multipleFileUpload(bucketName, filePath, getAmazonS3Client(regions));
		else
			throw new AmazonUploadDownloadException(" Bucket doesn't exist please provide a valid  bucket Name");
	}

	@Override
	public ObjectListing getListFiles(String bucketName, Regions regions) throws Exception {
		return getAmazonS3Client(regions).listObjects(bucketName);
	}

	@Override
	public Object downloadFile(String bucketName, String objectKey, Regions regions, String filePath) throws Exception {
		return AmazonS3Util.downloadFile(bucketName, objectKey, filePath, getAmazonS3Client(regions));
	}

	@Override
	public void deleteObject(String bucketName, String objectKey, Regions regions) throws Exception {
		getAmazonS3Client(regions).deleteObject(bucketName, objectKey);
	}

	@Override
	public DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq, Regions regions)
			throws AmazonUploadDownloadException {
		return null;
	}

	private AmazonS3 getAmazonS3Client(Regions regions) throws Exception {
		return AmazonS3Util.getAmazonS3Client(secretkey, clientId, regions);
	}

}
