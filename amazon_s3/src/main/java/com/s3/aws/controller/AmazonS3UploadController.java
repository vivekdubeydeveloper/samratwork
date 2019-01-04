/**
 * 
 */
package com.s3.aws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Regions;
import com.s3.aws.model.Amazon;
import com.s3.aws.service.AmazonBaseService;

/**
 * @author
 */
@RestController
public class AmazonS3UploadController {
	@Autowired
	AmazonBaseService amazonBaseService;

	@PostMapping("/uploadfile")
	public @ResponseBody Object uploadFile(@RequestBody Amazon amazon) throws Exception {
		return amazonBaseService.uploadFile(amazon.getBucketName(), amazon.getKey(),amazon.getFilePath(), Regions.valueOf(amazon.getRegion()));
	}
	
	
	@PostMapping("/getListOfFiles")
	public @ResponseBody Object getListOfFiles(@RequestBody Amazon amazon) throws Exception {
		return amazonBaseService.getListFiles(amazon.getBucketName(), Regions.valueOf(amazon.getRegion()));
	}

}
