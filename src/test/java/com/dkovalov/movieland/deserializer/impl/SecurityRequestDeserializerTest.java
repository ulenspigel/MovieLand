package com.dkovalov.movieland.deserializer.impl;

import com.dkovalov.movieland.dto.UserCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
public class SecurityRequestDeserializerTest {
    @Autowired
    private SecurityRequestDeserializerImpl deserializer;

    @Test
    public void testAuthorizationRequest() {
        String requestJSON = "{\"login\": \"test_login\", \"password\": \"test_password\"}";
        UserCredentials credentials = deserializer.authorizationRequest(requestJSON);
        assertEquals(credentials.getLogin(), "test_login");
        assertEquals(credentials.getPassword(), "test_password");
    }
}
