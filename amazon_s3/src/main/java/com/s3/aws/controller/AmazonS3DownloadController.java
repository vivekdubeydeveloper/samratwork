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
public class AmazonS3DownloadController {
	@Autowired
	AmazonBaseService amazonBaseService;

	@PostMapping("/downloadFile")
	public @ResponseBody Object downloadFile(@RequestBody Amazon amazon) throws Exception {
		return amazonBaseService.downloadFile(amazon.getBucketName(), amazon.getKey(), Regions.valueOf(amazon.getRegion()),
				amazon.getFilePath());
	}

}
