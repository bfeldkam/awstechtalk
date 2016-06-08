package com.rhc.techtalks.aws.s3.test;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3BucketEntity;
import com.amazonaws.services.s3.event.S3EventNotification.S3Entity;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.event.S3EventNotification.S3ObjectEntity;
import com.rhc.techtalks.aws.s3.AWSLambdaRequestHandler;

@RunWith(MockitoJUnitRunner.class)
public class AWSLambdaRequestHandlerTest {

	@Mock
	Context context;
	
	@InjectMocks
	AWSLambdaRequestHandler handler;
	
	@Test
	public void handleRequest(){
		when(context.getLogger()).thenReturn(new TestLambdaLogger());
		
		handler.handleRequest(getMockS3Event(), context);
	}

	/**
	 * create a test S3Event
	 * @return
	 */
	private S3Event getMockS3Event() {
		
		S3BucketEntity bucket = new S3BucketEntity("test bucket", null, null);
		S3ObjectEntity s3Object = new S3ObjectEntity("test object", 1L, null, null);
		S3Entity s3 = new S3Entity(null, bucket, s3Object, null);
		S3EventNotificationRecord record = new S3EventNotificationRecord(null, null, null, null, null, null, null, s3, null);
		
		List<S3EventNotificationRecord> records = Collections.singletonList(record);
		S3Event s3Event = new S3Event(records);
		
		return s3Event;
	}
	
	/**
	 * test logger to print to stanard out
	 * @author feldkab
	 */
	private class TestLambdaLogger implements LambdaLogger{

		@Override
		public void log(String string) {
			System.out.println(string);
		}
	}
}
