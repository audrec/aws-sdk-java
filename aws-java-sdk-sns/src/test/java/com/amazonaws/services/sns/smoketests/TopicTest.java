package com.amazonaws.services.sns.smoketests;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test for https://github.com/aws/aws-sdk-java/issues/2347
 */
public class TopicTest {

    /**
     * Test duplicated policy is not added repeatedly
     */
    @Test
    public void testSubscribeQueue() {
        AmazonSNS client = Mockito.mock(AmazonSNS.class);
        AmazonSQS clientSqs = Mockito.mock(AmazonSQS.class);
        SubscribeResult subscribeResult = Mockito.mock(SubscribeResult.class);
        GetQueueAttributesResult queueAttributesResult = Mockito.mock(GetQueueAttributesResult.class);
        List<String> sqsAttrNames = Arrays.asList(QueueAttributeName.QueueArn.toString(),
                QueueAttributeName.Policy.toString());
        Map<String, String> attrs = new HashMap<>();
        attrs.put("Policy", "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Sid\":\"topic-subscription-arn\",\"Effect\":\"Allow\",\"Principal\":{\"AWS\":\"*\"},\"Action\":[\"sqs:SendMessage\"],\"Resource\":[null],\"Condition\":{\"ArnLike\":{\"aws:SourceArn\":[\"arn\"]}}}]}}");
        Mockito.when(clientSqs.getQueueAttributes("url", sqsAttrNames)).thenReturn(queueAttributesResult);
        Mockito.when(client.subscribe("arn", "sqs", null)).thenReturn(subscribeResult);
        ArgumentCaptor<SetQueueAttributesRequest> argumentCaptor = ArgumentCaptor.forClass(SetQueueAttributesRequest.class);

        Topics.subscribeQueue(client, clientSqs, "arn", "url", true);

        Mockito.verify(clientSqs).setQueueAttributes(argumentCaptor.capture());
        SetQueueAttributesRequest attributesRequest = argumentCaptor.getValue();
        assertEquals(1, attributesRequest.getAttributes().size());
    }
}
