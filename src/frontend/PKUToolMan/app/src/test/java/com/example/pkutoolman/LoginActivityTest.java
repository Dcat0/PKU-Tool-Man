package com.example.pkutoolman;

import junit.framework.TestCase;

public class LoginActivityTest extends TestCase {
    public LoginActivity test = new LoginActivity();

    public void test1() {
        try {
            int expectedReturn = 1;
            int actualReturn = LoginActivity.loginCheckTest("1","1");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void test2() {
        try {
            int expectedReturn = 2;
            int actualReturn = LoginActivity.loginCheckTest("","1");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void test3() {
        try {
            int expectedReturn = 4;
            int actualReturn = test.loginCheckTest("s@s","1");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void test4() {
        try {
            int expectedReturn = 4;
            int actualReturn = LoginActivity.loginCheckTest("fyc@pku.edu.cn",MD5.encrypt("1234567"));
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void test5() {
        try {
            int expectedReturn = 5;
            int actualReturn = test.loginCheckTest("s@s","1");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void test6() {
        try {
            int expectedReturn = 6;
            int actualReturn = test.loginCheckTest("s","1");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void test7() {
        try {
            int expectedReturn = 7;
            int actualReturn = test.loginCheckTest("s@s","1");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


}