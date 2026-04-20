package com.converter.service;

import com.converter.model.ConversionRecord;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class HistoryService {
    private static final Path HISTORY_FILE = Path.of("converter_history.txt");

    public void save(ConversionRecord record) throws IOException {
        Files.writeString(
            HISTORY_FILE,
            record.toHistoryLine() + System.lineSeparator(),
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }

    public String loadAll() throws IOException {
        if (!Files.exists(HISTORY_FILE)) {
            return "--- History Records ---\n\nNo records found.";
        }

        List<String> lines = Files.readAllLines(HISTORY_FILE, StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            return "--- History Records ---\n\nNo records found.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("--- History Records ---").append(System.lineSeparator()).append(System.lineSeparator());
        for (String line : lines) {
            builder.append(line).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
