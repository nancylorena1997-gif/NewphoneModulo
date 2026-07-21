package com.newphone.newphonemodulo.ui.util;

import com.newphone.newphonemodulo.ui.config.FieldType;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Component;
import java.util.Map;

public final class FormUtils {

    private FormUtils() {
    }

    public static String getText(JComponent component) {
        if (component instanceof JTextArea textArea) {
            return textArea.getText().trim();
        }
        if (component instanceof JTextField textField) {
            return textField.getText().trim();
        }
        return "";
    }

    public static void setText(JComponent component, Object value) {
        String text = value == null ? "" : String.valueOf(value);
        if (component instanceof JTextArea textArea) {
            textArea.setText(text);
        } else if (component instanceof JTextField textField) {
            textField.setText(text);
        }
    }

    public static Integer parseInteger(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Integer.valueOf(value.trim());
    }

    public static Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Double.valueOf(value.trim());
    }

    public static void setEnabled(JComponent component, boolean enabled) {
        component.setEnabled(enabled);
        if (component instanceof JTextArea textArea) {
            textArea.setEditable(enabled);
        }
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isBlank();
    }

    public static void validateRequired(String value, String fieldLabel) {
        if (isEmpty(value)) {
            throw new IllegalArgumentException("El campo '" + fieldLabel + "' es obligatorio.");
        }
    }

    public static void validateType(String value, FieldType type, String fieldLabel) {
        if (isEmpty(value)) {
            return;
        }
        try {
            switch (type) {
                case INTEGER -> Integer.parseInt(value.trim());
                case DOUBLE -> Double.parseDouble(value.trim());
                default -> {
                }
            }
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El campo '" + fieldLabel + "' tiene un formato invalido.");
        }
    }

    public static void clearForm(Map<String, JComponent> components) {
        components.values().forEach(component -> setText(component, ""));
    }

    public static void focusFirst(Component component) {
        if (component != null) {
            component.requestFocusInWindow();
        }
    }
}
