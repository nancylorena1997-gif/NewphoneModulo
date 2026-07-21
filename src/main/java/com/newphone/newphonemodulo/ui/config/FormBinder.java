package com.newphone.newphonemodulo.ui.config;

import javax.swing.JComponent;
import java.util.Map;

public interface FormBinder<T> {

    void writeToForm(T entity, Map<String, JComponent> components);

    void readFromForm(T entity, Map<String, JComponent> components);
}
