package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander Nyström(dv15anm) on 21/12/2016.
 */
public class MainWindow {
    private JFrame mainFrame;
    private JTabbedPane p4Panel;
    private JTabbedPane srExtraPanel;
    private JTabbedPane startPanel;

    public MainWindow(JTabbedPane startPanel, JTabbedPane p4Panel, JTabbedPane srExtraPanel) {
        mainFrame = new JFrame("Radio Info");
        this.startPanel = startPanel;
        this.p4Panel = p4Panel;
        this.srExtraPanel = srExtraPanel;
    }

    private void addJMenuBar(){
        MenuBar menu = new MenuBar(this);
        mainFrame.setJMenuBar(menu.getMenuBar());
    }

    public void setUpGUI() {
        mainFrame.add(startPanel, BorderLayout.CENTER);
        startPanel.setVisible(true);
        addJMenuBar();
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public JTabbedPane getP4Panel() {
        return p4Panel;
    }

    public JTabbedPane getSrExtraPanel() {
        return srExtraPanel;
    }

    public JTabbedPane getStartPanel() {
        return startPanel;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
}
