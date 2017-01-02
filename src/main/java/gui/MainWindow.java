package gui;

import helpers.CurrentView;

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
    private CurrentView currentView;
    private JTabbedPane currentPanel;

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
        currentView = CurrentView.MAIN;
        currentPanel = startPanel;
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

    public void setP4Panel(JTabbedPane p4Panel) {
        this.p4Panel = p4Panel;
    }

    public void setSrExtraPanel(JTabbedPane srExtraPanel) {
        this.srExtraPanel = srExtraPanel;
    }

    public void setStartPanel(JTabbedPane startPanel) {
        this.startPanel = startPanel;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public CurrentView getCurrentView() {
        return currentView;
    }

    public void setCurrentView(CurrentView currentView) {
        this.currentView = currentView;
    }

    public JTabbedPane getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(JTabbedPane currentPanel) {
        this.currentPanel = currentPanel;
        startPanel.setVisible(false);
        p4Panel.setVisible(false);
        srExtraPanel.setVisible(false);
        mainFrame.add(currentPanel);
    }
}
