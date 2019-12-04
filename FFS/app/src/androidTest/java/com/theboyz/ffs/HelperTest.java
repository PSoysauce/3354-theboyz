package com.theboyz.utils;

import org.junit.Test;

import static.org.Junit.Assert.*;

public class HelperTest {

    Helpers h;
    Double [] tester1;
    double [] tester2;

    @Before // setup the tester
    public void setup(){

        h = new Helpers;

        // setup two doubles to compare
        tester1 = [2.0, 4.0, 7.0, 10.0];
        tester2 = [2.0, 4.0, 7.0, 10.0];

    } // end setup

    @Test
    public void testDoubleToPrimitive() {

        // assert the two are equal
        assertEquals("Checking Double array against double array", h.DoubleToPrimitive(tester1), tester2);

    } // end Double To Primitive


}