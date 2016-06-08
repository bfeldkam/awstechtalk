package com.rhc.techtalks.aws.s3;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3Entity;

/**
 * Handle AWS lambda requests
 * @author feldkab
 */
public class AWSLambdaRequestHandler{

	private LambdaLogger logger;

	/**
	 * The method called when the Lambda function is executed
	 * @param event - the SNS event
	 * @param context - useful information about the execution env 
	 */
	public void handleRequest(S3Event event,  Context context) {
		//init logger from context
		logger = context.getLogger();
		
		event.getRecords().forEach(record -> logDetails(record.getS3()));
	}

	/**
	 * log bucket details
	 * @param s3BucketEntity
	 */
	private void logDetails(S3Entity s3Entity){
		String bucketDetails = String.format("There is a new object (%s) in '%s' bucket.", s3Entity.getObject().getKey(), s3Entity.getBucket().getName());
		
		logger.log(bucketDetails);
	}
}
