package com.newphone.newphonemodulo.ui;

import com.newphone.newphonemodulo.database.DatabaseConnection;
import com.newphone.newphonemodulo.ui.config.ModuleDescriptor;
import com.newphone.newphonemodulo.ui.config.ModuleRegistry;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainFrame extends JFrame {

    private final MdiManager mdiManager;

    public MainFrame() {
        super("NewPhone - Sistema de Gestion");
        JDesktopPane desktopPane = new JDesktopPane();
        mdiManager = new MdiManager(desktopPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(buildMenuBar());
        add(buildContentPanel(desktopPane), BorderLayout.CENTER);
        setMinimumSize(new Dimension(1100, 700));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private JPanel buildContentPanel(JDesktopPane desktopPane) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(desktopPane, BorderLayout.CENTER);
        contentPanel.add(buildStatusBar(), BorderLayout.SOUTH);
        return contentPanel;
    }

    private JLabel buildStatusBar() {
        JLabel statusLabel = new JLabel(" Base de datos: " + DatabaseConnection.getDatabasePath());
        statusLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return statusLabel;
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(buildArchivoMenu());
        menuBar.add(buildUsuariosMenu());
        menuBar.add(buildCatalogoMenu());
        menuBar.add(buildComprasMenu());
        menuBar.add(buildPedidosMenu());
        menuBar.add(buildAtencionMenu());
        menuBar.add(buildReportesMenu());
        menuBar.add(buildVentanaMenu());
        menuBar.add(buildAyudaMenu());

        return menuBar;
    }

    private JMenu buildArchivoMenu() {
        JMenu menu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.addActionListener(event -> dispose());
        menu.add(exitItem);
        return menu;
    }

    private JMenu buildUsuariosMenu() {
        JMenu menu = new JMenu("Usuarios");
        menu.add(createMenuItem("cuenta"));
        menu.add(createMenuItem("cliente"));
        menu.add(createMenuItem("administrador"));
        return menu;
    }

    private JMenu buildCatalogoMenu() {
        JMenu menu = new JMenu("Catalogo");
        menu.add(createMenuItem("categoria"));
        menu.add(createMenuItem("producto"));
        return menu;
    }

    private JMenu buildComprasMenu() {
        JMenu menu = new JMenu("Compras");
        menu.add(createMenuItem("carrito"));
        menu.add(createMenuItem("detalle_carrito"));
        menu.add(createMenuItem("producto_favorito"));
        return menu;
    }

    private JMenu buildPedidosMenu() {
        JMenu menu = new JMenu("Pedidos");
        menu.add(createMenuItem("pedido"));
        menu.add(createMenuItem("detalle_pedido"));
        menu.add(createMenuItem("envio"));
        menu.add(createMenuItem("factura"));
        menu.add(createMenuItem("pago"));
        return menu;
    }

    private JMenu buildAtencionMenu() {
        JMenu menu = new JMenu("Atencion");
        menu.add(createMenuItem("resena"));
        menu.add(createMenuItem("atencion_cliente"));
        return menu;
    }

    private JMenu buildReportesMenu() {
        JMenu menu = new JMenu("Reportes");
        menu.add(createMenuItem("reporte_venta"));
        return menu;
    }

    private JMenu buildVentanaMenu() {
        JMenu menu = new JMenu("Ventana");
        JMenuItem cascadeItem = new JMenuItem("Cascada");
        JMenuItem tileItem = new JMenuItem("Mosaico");
        JMenuItem closeAllItem = new JMenuItem("Cerrar todas");

        cascadeItem.addActionListener(event -> mdiManager.cascadeFrames());
        tileItem.addActionListener(event -> mdiManager.tileFrames());
        closeAllItem.addActionListener(event -> mdiManager.closeAllFrames());

        menu.add(cascadeItem);
        menu.add(tileItem);
        menu.add(closeAllItem);
        return menu;
    }

    private JMenu buildAyudaMenu() {
        JMenu menu = new JMenu("Ayuda");
        JMenuItem aboutItem = new JMenuItem("Acerca de");
        aboutItem.addActionListener(event -> JOptionPane.showMessageDialog(
                this,
                """
                        NewPhone Modulo
                        Aplicacion de escritorio con Java Swing, JDBC y SQLite.
                        Incluye operaciones CRUD para todas las entidades del modelo relacional.
                        """,
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE));
        menu.add(aboutItem);
        return menu;
    }

    private JMenuItem createMenuItem(String moduleKey) {
        ModuleDescriptor<?> descriptor = ModuleRegistry.getModule(moduleKey);
        JMenuItem menuItem = new JMenuItem(descriptor.getTitle());
        menuItem.addActionListener(event -> mdiManager.openModule(descriptor));
        return menuItem;
    }

    public static void showMainFrame() {
        SwingUtilities.invokeLater(() -> {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo lookAndFeelInfo : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(lookAndFeelInfo.getName())) {
                        javax.swing.UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                        break;
                    }
                }
            } catch (Exception ignored) {
            }

            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
