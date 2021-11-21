package com.amazonaws.services.lambda.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 Custom Lambda Serde is the interface for customized lambda input (de) serializer to pass in the {@code LambdaInvokerFactory}.
 builder. If customized serde is provided, input will be serialized using this serde.
 */
public interface CustomLambdaSerde extends java.io.Serializable {
    /**
     Method that can be used to serialize any Object as a String.
     */
    String writeValueAsString(Object value) throws JsonProcessingException;

    /**
     Method that can be used to deserialize String to Object.
     */
    <T> T readValue(String src, Class<T> valueType) throws IOException;
}
