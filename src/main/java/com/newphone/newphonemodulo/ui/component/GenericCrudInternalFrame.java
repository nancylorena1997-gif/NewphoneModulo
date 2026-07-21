package com.newphone.newphonemodulo.ui.component;

import com.newphone.newphonemodulo.ui.config.FieldConfig;
import com.newphone.newphonemodulo.ui.config.FieldType;
import com.newphone.newphonemodulo.ui.config.ModuleDescriptor;
import com.newphone.newphonemodulo.ui.util.FormUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenericCrudInternalFrame<T> extends JInternalFrame {

    private final ModuleDescriptor<T> descriptor;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final Map<String, JComponent> formComponents = new LinkedHashMap<>();
    private boolean editing;

    public GenericCrudInternalFrame(ModuleDescriptor<T> descriptor) {
        super(descriptor.getTitle(), true, true, true, true);
        this.descriptor = descriptor;
        this.tableModel = new DefaultTableModel(descriptor.getTableHeaders(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(tableModel);
        initializeComponents();
    }

    private void initializeComponents() {
        setSize(900, 550);
        setMinimumSize(new Dimension(700, 450));
        setLayout(new BorderLayout(8, 8));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });

        loadTableData();
    }

    private JPanel buildFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulario"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        int rowIndex = 0;
        for (FieldConfig field : descriptor.getFields()) {
            constraints.gridx = 0;
            constraints.gridy = rowIndex;
            constraints.weightx = 0.0;
            formPanel.add(new JLabel(field.getLabel() + ":"), constraints);

            JComponent component = createFieldComponent(field);
            formComponents.put(field.getName(), component);

            constraints.gridx = 1;
            constraints.weightx = 1.0;
            formPanel.add(component, constraints);
            rowIndex++;
        }
        return formPanel;
    }

    private JComponent createFieldComponent(FieldConfig field) {
        if (field.getType() == FieldType.TEXT_AREA) {
            JTextArea textArea = new JTextArea(3, 20);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            return new JScrollPane(textArea);
        }
        return new JTextField(20);
    }

    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JButton newButton = new JButton("Nuevo");
        JButton saveButton = new JButton("Guardar");
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");
        JButton refreshButton = new JButton("Consultar");
        JButton clearButton = new JButton("Limpiar");

        newButton.addActionListener(event -> prepareNewRecord());
        saveButton.addActionListener(event -> saveRecord());
        updateButton.addActionListener(event -> updateRecord());
        deleteButton.addActionListener(event -> deleteRecord());
        refreshButton.addActionListener(event -> loadTableData());
        clearButton.addActionListener(event -> clearForm());

        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);
        return buttonPanel;
    }

    private void prepareNewRecord() {
        editing = false;
        clearForm();
        configurePrimaryKeyFields(false);
    }

    private void configurePrimaryKeyFields(boolean isEditing) {
        for (FieldConfig field : descriptor.getFields()) {
            JComponent component = formComponents.get(field.getName());
            if (field.isPrimaryKey()) {
                FormUtils.setEnabled(component, !field.isAutoGenerated() || isEditing);
            }
        }
    }

    private void saveRecord() {
        try {
            validateForm();
            T entity = descriptor.getEntitySupplier().get();
            descriptor.getFormBinder().readFromForm(entity, unwrapComponents());
            descriptor.getDao().insert(entity);
            showInfo("Registro guardado correctamente.");
            loadTableData();
            clearForm();
        } catch (IllegalArgumentException exception) {
            showWarning(exception.getMessage());
        } catch (SQLException exception) {
            showError("No se pudo guardar el registro.", exception);
        }
    }

    private void updateRecord() {
        try {
            validateForm();
            T entity = descriptor.getEntitySupplier().get();
            descriptor.getFormBinder().readFromForm(entity, unwrapComponents());
            Object id = descriptor.getIdExtractor().apply(entity);
            if (id == null) {
                showWarning("Seleccione un registro para actualizar.");
                return;
            }
            descriptor.getDao().update(entity);
            showInfo("Registro actualizado correctamente.");
            loadTableData();
        } catch (IllegalArgumentException exception) {
            showWarning(exception.getMessage());
        } catch (SQLException exception) {
            showError("No se pudo actualizar el registro.", exception);
        }
    }

    private void deleteRecord() {
        try {
            T entity = descriptor.getEntitySupplier().get();
            descriptor.getFormBinder().readFromForm(entity, unwrapComponents());
            Object id = descriptor.getIdExtractor().apply(entity);
            if (id == null) {
                showWarning("Seleccione un registro para eliminar.");
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Desea eliminar el registro seleccionado?",
                    "Confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            descriptor.getDao().delete(id);
            showInfo("Registro eliminado correctamente.");
            loadTableData();
            clearForm();
        } catch (IllegalArgumentException exception) {
            showWarning(exception.getMessage());
        } catch (SQLException exception) {
            showError("No se pudo eliminar el registro.", exception);
        }
    }

    private void loadTableData() {
        try {
            tableModel.setRowCount(0);
            List<T> records = descriptor.getDao().findAll();
            for (T record : records) {
                tableModel.addRow(descriptor.getRowMapper().apply(record));
            }
        } catch (SQLException exception) {
            showError("No se pudo consultar la informacion.", exception);
        }
    }

    private void loadSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        try {
            Object idValue = tableModel.getValueAt(selectedRow, 0);
            Object id = idValue instanceof Number number ? number.intValue() : idValue;
            T entity = descriptor.getDao().findById(id);
            if (entity != null) {
                editing = true;
                configurePrimaryKeyFields(true);
                descriptor.getFormBinder().writeToForm(entity, unwrapComponents());
            }
        } catch (SQLException exception) {
            showError("No se pudo cargar el registro seleccionado.", exception);
        }
    }

    private void clearForm() {
        FormUtils.clearForm(unwrapComponents());
        table.clearSelection();
        editing = false;
        configurePrimaryKeyFields(false);
    }

    private void validateForm() {
        for (FieldConfig field : descriptor.getFields()) {
            JComponent component = formComponents.get(field.getName());
            String value = extractValue(component);
            if (field.isPrimaryKey() && !field.isAutoGenerated() && FormUtils.isEmpty(value)) {
                FormUtils.validateRequired(value, field.getLabel());
            }
            if (!field.isPrimaryKey() || !field.isAutoGenerated()) {
                if (field.getType() != FieldType.TEXT_AREA && !field.getName().contains("respuesta")
                        && !field.getName().contains("archivo") && !field.getName().contains("analisis")) {
                    if (FormUtils.isEmpty(value) && isRequiredField(field)) {
                        FormUtils.validateRequired(value, field.getLabel());
                    }
                }
            }
            FormUtils.validateType(value, field.getType(), field.getLabel());
        }
    }

    private boolean isRequiredField(FieldConfig field) {
        return field.getType() != FieldType.TEXT && field.getType() != FieldType.TEXT_AREA
                || field.getName().equals("nombre") || field.getName().equals("email")
                || field.getName().equals("contrasena") || field.getName().equals("mensaje")
                || field.getName().equals("comentario") || field.getName().equals("descripcion");
    }

    private String extractValue(JComponent component) {
        if (component instanceof JScrollPane scrollPane
                && scrollPane.getViewport().getView() instanceof JTextArea textArea) {
            return textArea.getText().trim();
        }
        return FormUtils.getText(component);
    }

    private Map<String, JComponent> unwrapComponents() {
        Map<String, JComponent> components = new HashMap<>();
        formComponents.forEach((name, component) -> {
            if (component instanceof JScrollPane scrollPane
                    && scrollPane.getViewport().getView() instanceof JComponent innerComponent) {
                components.put(name, innerComponent);
            } else {
                components.put(name, component);
            }
        });
        return components;
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, descriptor.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, descriptor.getTitle(), JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String message, Exception exception) {
        JOptionPane.showMessageDialog(
                this,
                message + System.lineSeparator() + exception.getMessage(),
                descriptor.getTitle(),
                JOptionPane.ERROR_MESSAGE);
    }

    public String getModuleKey() {
        return descriptor.getKey();
    }
}
