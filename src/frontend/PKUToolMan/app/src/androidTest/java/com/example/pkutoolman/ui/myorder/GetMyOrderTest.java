package com.example.pkutoolman.ui.myorder;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.pkutoolman.baseclass.Order;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GetMyOrderTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getMyOrder() {
        ArrayList<Order> publishList = new ArrayList<>(), receiveList = new ArrayList<>();
        GetMyOrder.getMyOrder(InstrumentationRegistry.getInstrumentation().getTargetContext(), 113, publishList, receiveList);
        assertEquals(31, publishList.get(0).id);
        assertEquals(0, receiveList.size());
    }
}