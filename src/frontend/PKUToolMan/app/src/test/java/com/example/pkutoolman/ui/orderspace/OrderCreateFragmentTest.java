package com.example.pkutoolman.ui.orderspace;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderCreateFragmentTest {
    OrderCreateFragment f = new OrderCreateFragment();
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void test1() throws JSONException {
        boolean ret = f.create_check("购物","1","2","1","1","1","123");
        assertEquals(false, ret);
    }
}