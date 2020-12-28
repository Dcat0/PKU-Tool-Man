package com.example.pkutoolman.ui.myorder;

import androidx.fragment.app.Fragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyorderFragmentTest {

    MyorderFragment fragment = new MyorderFragment();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test1() {
        boolean ret = fragment.getNewMessage(28,1);
        assertEquals(false, ret);
    }

    @Test
    public void test2() {
        boolean ret = fragment.getNewMessage(27,1);
        assertEquals(true, ret);
    }
}