package com.newphone.newphonemodulo;

import com.newphone.newphonemodulo.database.DatabaseInitializer;
import com.newphone.newphonemodulo.ui.MainFrame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class NewphoneModulo {

    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
            MainFrame.showMainFrame();
        } catch (Exception exception) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    null,
                    "Error al iniciar la aplicacion: " + exception.getMessage(),
                    "NewPhone",
                    JOptionPane.ERROR_MESSAGE));
        }
    }
}
