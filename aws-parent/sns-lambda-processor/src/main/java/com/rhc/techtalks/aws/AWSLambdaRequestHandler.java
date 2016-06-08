package com.rhc.techtalks.aws;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

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
	public void handleRequest(SNSEvent event,  Context context) {
		//init logger from context
		logger = context.getLogger();
		
		event.getRecords().forEach(record -> hashPassword(record.getSNS().getMessage()));
	}

	/**
	 * Hash inputed string using SHA-256
	 * @param plainTextPw
	 */
	private void hashPassword(String plainTextPw){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] hashedPw = md.digest(plainTextPw.getBytes());

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hashedPw.length; i++) {
				sb.append(Integer.toString((hashedPw[i] & 0xff) + 0x100, 16).substring(1));
			}

			logger.log("Hashed password hex value: "+sb.toString());

		} catch (NoSuchAlgorithmException e) {
			logger.log("Error hashing password\n"+e.getMessage());
		}
	}
}
