package com.campaign.demo.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static int DECIMALS = 2;
    private static BigDecimal HUNDRED = new BigDecimal("100");

    public static BigDecimal getDifference(BigDecimal amountOne, BigDecimal amountTwo) {
        return rounded(amountOne.subtract(amountTwo));
    }

    public static BigDecimal getPercentageCalculation(BigDecimal amount, BigDecimal percentage) {
        BigDecimal result = amount.multiply(percentage);
        result = result.divide(HUNDRED, ROUNDING_MODE);
        return getDifference(amount, result);
    }

    private static BigDecimal rounded(BigDecimal aNumber) {
        return aNumber.setScale(DECIMALS, ROUNDING_MODE);
    }
}
