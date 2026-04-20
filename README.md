# Number Converter Pro (Java Swing)

A desktop Java application to convert:
- Decimal to Binary
- Binary to Decimal

The UI style is inspired by your BMI reference layout:
- Header bar with bold title
- Left panel for input fields and actions
- Right panel for large result and details
- History dialog window with saved records

## Features

- Input field: number value
- Conversion mode radio options
- Validation with popup messages
- Save conversion records to local file
- Show complete conversion history
- Clear/reset all fields

## Project Structure

- `src/main/java/com/converter/Main.java`
- `src/main/java/com/converter/ui/ConverterFrame.java`
- `src/main/java/com/converter/service/ConverterService.java`
- `src/main/java/com/converter/service/HistoryService.java`
- `src/main/java/com/converter/model/ConversionRecord.java`

## Requirements

- Java 17+
- Maven 3.9+

## Run

```bash
mvn clean compile
mvn exec:java
```

## One-Click Launcher (Mac/Linux)

```bash
./run.sh
```

If execute permission is missing:

```bash
chmod +x run.sh
./run.sh
```

Optional compile-only mode:

```bash
./run.sh --compile-only
```

## History Storage

Saved records are written to:
- `converter_history.txt` (in project root)
