package gui;

import helpers.CurrentView;
import program.Updater;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private Updater updater;
    private JLabel updateTime;

    public MainWindow(JTabbedPane startPanel, JTabbedPane p4Panel, JTabbedPane srExtraPanel) {
        mainFrame = new JFrame("Radio Info");
        this.startPanel = startPanel;
        this.p4Panel = p4Panel;
        this.srExtraPanel = srExtraPanel;
        updateTime = new JLabel("Senast updaterad: "+Calendar.getInstance().getTime().toString());
        mainFrame.add(updateTime,BorderLayout.SOUTH);
        updater = new Updater();
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(this::updateData, 1, 1, TimeUnit.HOURS);
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

    private void setCurrentPanel(JTabbedPane currentPanel) {
        this.currentPanel = currentPanel;
        startPanel.setVisible(false);
        p4Panel.setVisible(false);
        srExtraPanel.setVisible(false);
        mainFrame.add(currentPanel);
    }

    public void updateView() {
        switch (currentView) {
            case MAIN:
                mainFrame.remove(currentPanel);
                setCurrentPanel(startPanel);
                startPanel.setVisible(true);
                mainFrame.pack();
                break;
            case P4:
                mainFrame.remove(currentPanel);
                setCurrentPanel(p4Panel);
                p4Panel.setVisible(true);
                mainFrame.pack();
                break;
            case SREXTRA:
                mainFrame.remove(currentPanel);
                setCurrentPanel(srExtraPanel);
                srExtraPanel.setVisible(true);
                mainFrame.pack();
                break;
        }
    }

    private void updateData(){
        updateTime.setText("Senast updaterad: "+Calendar.getInstance().getTime().toString());
        System.out.println("Updating at: "+ Calendar.getInstance().getTime());
        ArrayList<JTabbedPane> panes = updater.update();
        setStartPanel(panes.get(0));
        setP4Panel(panes.get(1));
        setSrExtraPanel(panes.get(2));
        updateView();
    }
}
