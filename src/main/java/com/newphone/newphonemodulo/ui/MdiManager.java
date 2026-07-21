package com.newphone.newphonemodulo.ui;

import com.newphone.newphonemodulo.ui.component.GenericCrudInternalFrame;
import com.newphone.newphonemodulo.ui.config.ModuleDescriptor;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.util.HashMap;
import java.util.Map;

public class MdiManager {

    private final JDesktopPane desktopPane;
    private final Map<String, JInternalFrame> openFrames = new HashMap<>();

    public MdiManager(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    public <T> void openModule(ModuleDescriptor<T> descriptor) {
        String key = descriptor.getKey();
        JInternalFrame existingFrame = openFrames.get(key);
        if (existingFrame != null) {
            try {
                existingFrame.setSelected(true);
                existingFrame.toFront();
            } catch (Exception ignored) {
                desktopPane.remove(existingFrame);
                openFrames.remove(key);
                createAndShowFrame(descriptor);
            }
            return;
        }
        createAndShowFrame(descriptor);
    }

    private <T> void createAndShowFrame(ModuleDescriptor<T> descriptor) {
        GenericCrudInternalFrame<T> frame = new GenericCrudInternalFrame<>(descriptor);
        openFrames.put(descriptor.getKey(), frame);
        desktopPane.add(frame);
        frame.setVisible(true);
        frame.toFront();
        try {
            frame.setSelected(true);
        } catch (Exception ignored) {
        }
        frame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent event) {
                openFrames.remove(descriptor.getKey());
            }
        });
    }

    public void cascadeFrames() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        int offset = 0;
        for (JInternalFrame frame : frames) {
            frame.setLocation(20 + offset, 20 + offset);
            frame.toFront();
            offset += 30;
        }
    }

    public void tileFrames() {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        if (frames.length == 0) {
            return;
        }

        int columns = (int) Math.ceil(Math.sqrt(frames.length));
        int rows = (int) Math.ceil((double) frames.length / columns);
        int width = desktopPane.getWidth() / columns;
        int height = desktopPane.getHeight() / rows;

        for (int index = 0; index < frames.length; index++) {
            int row = index / columns;
            int column = index % columns;
            frames[index].setBounds(column * width, row * height, width, height);
            frames[index].toFront();
        }
    }

    public void closeAllFrames() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            frame.dispose();
        }
        openFrames.clear();
    }
}
