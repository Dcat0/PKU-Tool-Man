package com.example.pkutoolman;

import junit.framework.TestCase;

public class ChatActivityTest extends TestCase {
    ChatActivity test = new ChatActivity();

    public void testNotInSql() {
        try {
            boolean expectedReturn = false;
            boolean actualReturn = test.checkMessageInSql(1, 1, 2,
                    "2020-12-29 10:00:00", "adda");
            assertEquals("success", expectedReturn, actualReturn);
        }catch (Exception e) {
            System.out.println(1);
            System.out.println(e.toString());
        }
    }
}