package com.example.pkutoolman;

import junit.framework.TestCase;

public class ForgetActivityTest extends TestCase {

    ForgetActivity test = new ForgetActivity();

    public void testWrongEmail() {
        try {
            boolean expectedReturn = false;
            boolean actualReturn = test.Check_email("s@s");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void testTrueEmail() {
        try {
            boolean expectedReturn = true;
            boolean actualReturn = test.Check_email("1@pku.edu.cn");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void testTruePhone() {
        try {
            boolean expectedReturn = true;
            boolean actualReturn = test.Check_phone("13501703838");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void testFalsePhone() {
        try {
            boolean expectedReturn = false;
            boolean actualReturn = test.Check_phone("1301703838");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void testTruePassword() {
        try {
            boolean expectedReturn = true;
            boolean actualReturn = test.Check_pass("1811113131123");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void testFalsePassword() {
        try {
            boolean expectedReturn = false;
            boolean actualReturn = test.Check_pass("18");
            assertEquals("success", expectedReturn, actualReturn);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}