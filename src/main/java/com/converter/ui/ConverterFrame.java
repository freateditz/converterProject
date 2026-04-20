package com.converter.ui;

import com.converter.model.ConversionRecord;
import com.converter.service.ConverterService;
import com.converter.service.HistoryService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ConverterFrame extends JFrame {
    private final JTextField inputField = new JTextField();

    private final JRadioButton decimalToBinaryRadio = new JRadioButton("Decimal to Binary", true);
    private final JRadioButton binaryToDecimalRadio = new JRadioButton("Binary to Decimal");

    private final JLabel resultValueLabel = new JLabel("--", SwingConstants.CENTER);
    private final JLabel inputTypeValueLabel = new JLabel("--");
    private final JLabel outputTypeValueLabel = new JLabel("--");
    private final JLabel adviceValueLabel = new JLabel("--");

    private final HistoryService historyService = new HistoryService();

    private String lastMode = "";
    private String lastInput = "";
    private String lastOutput = "";

    public ConverterFrame() {
        setTitle("Number Converter Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1120, 700));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(232, 237, 243));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(24, 74, 109));
        header.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel title = new JLabel("Decimal / Binary Converter", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Dialog", Font.BOLD, 42));
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Fast and accurate number system conversion", SwingConstants.CENTER);
        subtitle.setForeground(new Color(220, 237, 245));
        subtitle.setFont(new Font("Dialog", Font.PLAIN, 20));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(3));
        header.add(subtitle);
        return header;
    }

    private JSplitPane createMainContent() {
        JPanel leftPanel = createInputPanel();
        JPanel rightPanel = createResultPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(8);
        splitPane.setEnabled(false);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        return splitPane;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setBackground(new Color(241, 245, 249));

        JLabel sectionTitle = new JLabel("Conversion Input");
        sectionTitle.setFont(new Font("Dialog", Font.BOLD, 34));
        panel.add(sectionTitle);
        panel.add(Box.createVerticalStrut(16));

        panel.add(createFieldLabel("Conversion Mode:"));
        panel.add(createRadioPanel());
        panel.add(Box.createVerticalStrut(16));

        panel.add(createFieldLabel("Input Number:"));
        styleTextField(inputField);
        panel.add(inputField);
        panel.add(Box.createVerticalStrut(6));

        JLabel hintLabel = new JLabel("Use digits for decimal or only 0/1 for binary.");
        hintLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        hintLabel.setForeground(new Color(87, 101, 116));
        panel.add(hintLabel);
        panel.add(Box.createVerticalStrut(20));

        panel.add(createButtonGrid());
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Dialog", Font.BOLD, 24));
        label.setBorder(new EmptyBorder(0, 0, 6, 0));
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Dialog", Font.PLAIN, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(168, 178, 189), 2),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        field.setBackground(Color.WHITE);
    }

    private JPanel createRadioPanel() {
        JPanel modePanel = new JPanel();
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.X_AXIS));
        modePanel.setBackground(new Color(241, 245, 249));

        decimalToBinaryRadio.setFont(new Font("Dialog", Font.BOLD, 28));
        decimalToBinaryRadio.setBackground(new Color(241, 245, 249));

        binaryToDecimalRadio.setFont(new Font("Dialog", Font.BOLD, 28));
        binaryToDecimalRadio.setBackground(new Color(241, 245, 249));

        ButtonGroup group = new ButtonGroup();
        group.add(decimalToBinaryRadio);
        group.add(binaryToDecimalRadio);

        modePanel.add(decimalToBinaryRadio);
        modePanel.add(Box.createHorizontalStrut(30));
        modePanel.add(binaryToDecimalRadio);
        modePanel.add(Box.createHorizontalGlue());

        return modePanel;
    }

    private JPanel createButtonGrid() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 14, 12));
        buttonPanel.setBackground(new Color(241, 245, 249));

        JButton calculateButton = createActionButton("Convert", new Color(38, 125, 173));
        JButton saveButton = createActionButton("Save", new Color(44, 174, 96));
        JButton historyButton = createActionButton("History", new Color(114, 78, 169));
        JButton clearButton = createActionButton("Clear", new Color(68, 68, 68));

        calculateButton.addActionListener(e -> performConversion());
        saveButton.addActionListener(e -> saveRecord());
        historyButton.addActionListener(e -> showHistoryDialog());
        clearButton.addActionListener(e -> clearAllFields());

        buttonPanel.add(calculateButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.BOLD, 32));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(18, 10, 18, 10)
        ));
        button.setMargin(new Insets(16, 10, 16, 10));
        return button;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
        panel.setBackground(new Color(236, 242, 246));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(248, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(191, 201, 213), 2),
            new EmptyBorder(24, 24, 24, 24)
        ));

        JLabel heading = new JLabel("Your Conversion Result", SwingConstants.CENTER);
        heading.setFont(new Font("Dialog", Font.BOLD, 44));
        heading.setAlignmentX(CENTER_ALIGNMENT);

        resultValueLabel.setFont(new Font("Dialog", Font.BOLD, 110));
        resultValueLabel.setForeground(new Color(35, 121, 197));
        resultValueLabel.setAlignmentX(CENTER_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(16));
        card.add(resultValueLabel);
        card.add(Box.createVerticalStrut(24));
        card.add(createInfoRow("Input Type:", inputTypeValueLabel));
        card.add(Box.createVerticalStrut(18));
        card.add(createInfoRow("Output Type:", outputTypeValueLabel));
        card.add(Box.createVerticalStrut(18));
        card.add(createInfoRow("Advice:", adviceValueLabel));

        panel.add(card);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createInfoRow(String labelText, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(248, 250, 252));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Dialog", Font.BOLD, 32));

        valueLabel.setFont(new Font("Dialog", Font.PLAIN, 32));

        row.add(label, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.CENTER);
        return row;
    }

    private void performConversion() {
        String input = inputField.getText().trim();

        if (input.isEmpty()) {
            showError("Please enter a value to convert.");
            return;
        }

        boolean decimalToBinary = decimalToBinaryRadio.isSelected();
        if (decimalToBinary && !ConverterService.isValidDecimal(input)) {
            showError("Decimal input must contain digits only (optional leading -). ");
            return;
        }

        if (!decimalToBinary && !ConverterService.isValidBinary(input)) {
            showError("Binary input must contain only 0 and 1 (optional leading -). ");
            return;
        }

        try {
            String output;
            if (decimalToBinary) {
                output = ConverterService.decimalToBinary(input);
                inputTypeValueLabel.setText("Decimal");
                outputTypeValueLabel.setText("Binary");
                adviceValueLabel.setText("Tip: binary groups in 4 bits are easy to read.");
            } else {
                output = ConverterService.binaryToDecimal(input);
                inputTypeValueLabel.setText("Binary");
                outputTypeValueLabel.setText("Decimal");
                adviceValueLabel.setText("Tip: keep leading zeros only when format matters.");
            }

            resultValueLabel.setText(output);
            resultValueLabel.setForeground(selectColorByLength(output.length()));

            lastMode = decimalToBinary ? "Decimal to Binary" : "Binary to Decimal";
            lastInput = input;
            lastOutput = output;
        } catch (NumberFormatException ex) {
            showError("Input value is out of supported range.");
        }
    }

    private Color selectColorByLength(int length) {
        if (length <= 8) {
            return new Color(35, 170, 105);
        }
        if (length <= 20) {
            return new Color(217, 145, 29);
        }
        return new Color(188, 67, 58);
    }

    private void saveRecord() {
        if (lastOutput.isEmpty()) {
            showError("Please calculate a conversion first.");
            return;
        }

        ConversionRecord record = new ConversionRecord(lastMode, lastInput, lastOutput);
        try {
            historyService.save(record);
            JOptionPane.showMessageDialog(this, "Record saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showError("Unable to save history: " + ex.getMessage());
        }
    }

    private void showHistoryDialog() {
        String history;
        try {
            history = historyService.loadAll();
        } catch (IOException ex) {
            showError("Unable to load history: " + ex.getMessage());
            return;
        }

        JDialog dialog = new JDialog(this, "Converter History", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(new Color(240, 244, 248));
        dialog.getRootPane().setBorder(new EmptyBorder(14, 14, 14, 14));

        JTextArea textArea = new JTextArea(history);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(980, 420));

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 244, 248));
        buttonPanel.add(okButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void clearAllFields() {
        inputField.setText("");
        decimalToBinaryRadio.setSelected(true);

        resultValueLabel.setText("--");
        resultValueLabel.setForeground(new Color(35, 121, 197));
        inputTypeValueLabel.setText("--");
        outputTypeValueLabel.setText("--");
        adviceValueLabel.setText("--");

        lastMode = "";
        lastInput = "";
        lastOutput = "";
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.WARNING_MESSAGE);
    }
}
