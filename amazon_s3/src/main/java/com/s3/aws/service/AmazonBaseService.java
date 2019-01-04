package com.s3.aws.service;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;

public interface AmazonBaseService {
	
	Bucket createBucket(String bucketName,Regions regions) throws Exception;

	boolean isBucketExist(String bucketName,Regions regions) throws Exception;

	void deleteBucket(String bucketName,Regions regions) throws Exception;

	List<Bucket> getListBuckets(Regions regions) throws Exception;

	Object uploadFile(String bucketName, String key, String  filePath,Regions regions) throws Exception;
	
	ObjectListing getListFiles(String bucketName,Regions regions) throws Exception;
	
	Object downloadFile(String bucketName, String objectKey,Regions regions,String filePath)throws Exception;
	
	void deleteObject(String bucketName, String objectKey,Regions regions)throws Exception;
	
	DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq,Regions regions)throws Exception;
	
	
}
