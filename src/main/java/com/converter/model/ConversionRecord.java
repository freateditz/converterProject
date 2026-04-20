package com.converter.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String mode;
    private final String input;
    private final String output;
    private final LocalDateTime timestamp;

    public ConversionRecord(String mode, String input, String output) {
        this.mode = mode;
        this.input = input;
        this.output = output;
        this.timestamp = LocalDateTime.now();
    }

    public String toHistoryLine() {
        return String.format(
            "%s | Mode: %-19s | In: %-24s | Out: %s",
            FORMATTER.format(timestamp),
            mode,
            input,
            output
        );
    }
}
