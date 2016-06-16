package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.deserializer.RequestDeserializer;
import com.dkovalov.movieland.dto.UserCredentials;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestDeserializerTest {
    private RequestDeserializer deserializer = new RequestDeserializerImpl();

    @Test
    public void testAuthorizationRequest() {
        String requestJSON = "{\"login\": \"test_login\", \"password\": \"test_password\"}";
        UserCredentials credentials = deserializer.authorizationRequest(requestJSON);
        assertEquals(credentials.getLogin(), "test_login");
        assertEquals(credentials.getPassword(), "test_password");
    }
}
