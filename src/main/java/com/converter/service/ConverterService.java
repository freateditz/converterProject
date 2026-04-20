package com.converter.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public final class ConverterService {
    private static final int MAX_FRACTION_BITS = 48;

    private ConverterService() {
    }

    public static String decimalToBinary(String decimal) {
        String sanitized = decimal.trim();
        boolean negative = sanitized.startsWith("-");
        String digits = negative ? sanitized.substring(1) : sanitized;

        String[] parts = digits.split("\\.", -1);
        String integerPartText = parts.length > 0 && !parts[0].isEmpty() ? parts[0] : "0";
        String fractionPartText = parts.length == 2 ? parts[1] : "";

        BigInteger denominator = BigInteger.ONE;
        BigInteger numerator;
        if (fractionPartText.isEmpty()) {
            numerator = new BigInteger(integerPartText);
        } else {
            denominator = BigInteger.TEN.pow(fractionPartText.length());
            String combined = integerPartText + fractionPartText;
            numerator = new BigInteger(combined.isEmpty() ? "0" : combined);
        }

        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);

        BigInteger integerPart = numerator.divide(denominator);
        BigInteger remainder = numerator.mod(denominator);

        String integerBinary = integerPart.toString(2);
        if (remainder.equals(BigInteger.ZERO)) {
            if (negative && !integerBinary.equals("0")) {
                return "-" + integerBinary;
            }
            return integerBinary;
        }

        StringBuilder fractionBinary = new StringBuilder();
        Map<BigInteger, Integer> seenRemainders = new HashMap<>();
        int repeatStartIndex = -1;

        while (!remainder.equals(BigInteger.ZERO) && fractionBinary.length() < MAX_FRACTION_BITS) {
            Integer seenIndex = seenRemainders.get(remainder);
            if (seenIndex != null) {
                repeatStartIndex = seenIndex;
                break;
            }

            seenRemainders.put(remainder, fractionBinary.length());
            remainder = remainder.multiply(BigInteger.TWO);

            if (remainder.compareTo(denominator) >= 0) {
                fractionBinary.append('1');
                remainder = remainder.subtract(denominator);
            } else {
                fractionBinary.append('0');
            }
        }

        StringBuilder result = new StringBuilder(integerBinary).append('.');
        if (repeatStartIndex >= 0) {
            result.append(fractionBinary, 0, repeatStartIndex)
                  .append('(')
                  .append(fractionBinary.substring(repeatStartIndex))
                  .append(')');
        } else if (remainder.equals(BigInteger.ZERO)) {
            result.append(fractionBinary);
        } else {
            result.append(fractionBinary).append("...");
        }

        boolean isZero = integerPart.equals(BigInteger.ZERO) && fractionBinary.chars().allMatch(ch -> ch == '0');
        if (negative && !isZero) {
            return "-" + result;
        }
        return result.toString();
    }

    public static String binaryToDecimal(String binary) {
        String sanitized = binary.trim();
        boolean negative = sanitized.startsWith("-");
        String digits = negative ? sanitized.substring(1) : sanitized;

        String[] parts = digits.split("\\.", -1);
        String integerPartText = parts.length > 0 && !parts[0].isEmpty() ? parts[0] : "0";
        String fractionPartText = parts.length == 2 ? parts[1] : "";

        BigInteger integerPart = new BigInteger(integerPartText, 2);
        BigDecimal value = new BigDecimal(integerPart);

        if (!fractionPartText.isEmpty()) {
            BigInteger fractionNumerator = new BigInteger(fractionPartText, 2);
            BigInteger fractionDenominator = BigInteger.TWO.pow(fractionPartText.length());
            BigDecimal fraction = new BigDecimal(fractionNumerator).divide(new BigDecimal(fractionDenominator));
            value = value.add(fraction);
        }

        if (negative) {
            value = value.negate();
        }

        return value.stripTrailingZeros().toPlainString();
    }

    public static boolean isValidDecimal(String value) {
        return value != null && value.trim().matches("-?(?:\\d+(?:\\.\\d+)?|\\.\\d+)");
    }

    public static boolean isValidBinary(String value) {
        return value != null && value.trim().matches("-?(?:[01]+(?:\\.[01]+)?|\\.[01]+)");
    }
}
