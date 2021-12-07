package com.amazonaws.auth.policy;

import org.junit.Test;

import static com.amazonaws.auth.policy.Statement.Effect.Allow;
import static org.junit.Assert.assertEquals;

/**
 * Test for https://github.com/aws/aws-sdk-java/issues/2347
 */
public class StatementTest {

    /**
     * Test two statements are equal if they have the same id.
     */
    @Test
    public void testEquals() {
        Statement statement1 = new Statement(Allow)
                .withId("c1")
                .withPrincipals(Principal.AllUsers);
        Statement statement2 = new Statement(Allow)
                .withId("c1")
                .withPrincipals(Principal.AllUsers);
        assertEquals(statement1, statement2);
    }
}
