package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Window extends JFrame {

    private final Panel panel;

    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UHK FIM PGRF : Lukáš Silný");

        panel = new Panel();
        panel.mod = 0;
        add(panel, BorderLayout.CENTER);
        setVisible(true);
        pack();

        setLocationRelativeTo(null);

        // lepší až na konci, aby to neukradla nějaká komponenta v případně složitějším UI
        panel.setFocusable(true);
        panel.grabFocus(); // důležité pro pozdější ovládání z klávesnice
    }

    public Panel getPanel() {
        return panel;
    }



}
