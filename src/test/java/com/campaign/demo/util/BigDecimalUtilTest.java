package com.campaign.demo.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BigDecimalUtilTest {

    @Test
    public void testGetDifference() {
        BigDecimal expected = BigDecimalUtil.getDifference(new BigDecimal(100.99), new BigDecimal(20));
        assertEquals(expected.toString(), "80.99");
    }

    @Test
    public void testGetPercentageCalculation() {
        BigDecimal expected = BigDecimalUtil.getPercentageCalculation(new BigDecimal(200), new BigDecimal(20));
        assertEquals(expected.toString(), "160.00");
    }

    @Test
    public void testGetPercentage() {
        BigDecimal expected = BigDecimalUtil.getPercentage(new BigDecimal(200), new BigDecimal(20));
        assertEquals(expected.toString(), "40.00");
    }
}
