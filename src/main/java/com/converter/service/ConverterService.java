package com.converter.service;

import java.math.BigInteger;

public final class ConverterService {
    private ConverterService() {
    }

    public static String decimalToBinary(String decimal) {
        BigInteger value = new BigInteger(decimal.trim());
        return value.toString(2);
    }

    public static String binaryToDecimal(String binary) {
        String sanitized = binary.trim();
        boolean negative = sanitized.startsWith("-");
        String digits = negative ? sanitized.substring(1) : sanitized;

        BigInteger value = new BigInteger(digits, 2);
        if (negative) {
            value = value.negate();
        }
        return value.toString();
    }

    public static boolean isValidDecimal(String value) {
        return value != null && value.trim().matches("-?\\d+");
    }

    public static boolean isValidBinary(String value) {
        return value != null && value.trim().matches("-?[01]+");
    }
}
