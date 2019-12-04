package com.theboyz.utils;

import com.theboyz.ffs.R;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelpersTest {

    Helpers h;
    String test;
    int testb;

    @Before
    public void setUp(){

        h = new Helpers();
        test = "DAL";
        testb = R.drawable.dal;

    }

    @Test
    public void testGetImageId() {
        assertEquals(testb, h.getImageId(test));
    }

}