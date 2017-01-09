/*
* MainWindow - this is the main window for the program
*
* Version: 1.0
*
* Created by: Alexander Nyström
*
*/


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

    private static int START = 0;
    private static int P4IND = 1;
    private static int SRE = 2;
    private JFrame mainFrame;
    private JTabbedPane p4Panel;
    private JTabbedPane srExtraPanel;
    private JTabbedPane startPanel;
    private CurrentView currentView;
    private JTabbedPane currentPanel;
    private Updater updater;
    private JLabel updateTime;
    private ScheduledExecutorService runUpdate;

    /**
     * The main graphical window containing the tableaus for the channels
     */
    public MainWindow() {
        mainFrame = new JFrame("Radio Info");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        updateTime = new JLabel("Senast updaterad: "+Calendar.
                getInstance().getTime().toString());
        mainFrame.add(updateTime,BorderLayout.SOUTH);
        updater = new Updater();
        runUpdate = Executors.newSingleThreadScheduledExecutor();
        setUpGUI();
    }

    /**
     * Sets up the initial gui and starts the scheduled update which will update
     * once every hour.
     */
    public void setUpGUI() {
        mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MenuBar menu = new MenuBar(this);
        mainFrame.setJMenuBar(menu.getMenuBar());
        mainFrame.setVisible(true);
        mainFrame.pack();
        currentView = CurrentView.LOAD;
        runUpdate.scheduleAtFixedRate(this::updateData, 0, 1,
                TimeUnit.HOURS);
    }

    /**
     *
     * @return the panel containing the schedule for P4
     */
    public JTabbedPane getP4Panel() {
        return p4Panel;
    }

    /**
     *
     * @return The panel containing the schedule for SR Extra
     */
    public JTabbedPane getSrExtraPanel() {
        return srExtraPanel;
    }

    /**
     *
     * @return The panel containing the schedule for mixed channels
     */
    public JTabbedPane getStartPanel() {
        return startPanel;
    }

    /**
     * Sets the panel with schedules for P4
     * @param p4Panel panel with schedule for p4
     */
    public void setP4Panel(JTabbedPane p4Panel) {
        this.p4Panel = p4Panel;
    }

    /**
     * Sets the panel with schedule for SR Extra channels
     * @param srExtraPanel panel with schedule for SR Extra channels
     */
    public void setSrExtraPanel(JTabbedPane srExtraPanel) {
        this.srExtraPanel = srExtraPanel;
    }

    /**
     * Set the panel with mixed channels
     * @param startPanel panel with schedule for mixed channels
     */
    public void setStartPanel(JTabbedPane startPanel) {
        this.startPanel = startPanel;
    }

    /**
     * Set the current view for the window
     * @param currentView the view that now is the current one
     */
    public void setCurrentView(CurrentView currentView) {
        this.currentView = currentView;
    }

    /**
     * Change the text of updateTime to the given string so that it displayes
     * the time for the last update
     * @param updateTime string saying when the schedules were last updated
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime.setText(updateTime);
    }

    /**
     *
     * @return the main frame
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Set a new current panel and make all panels not visible.
     * @param currentPanel the new panel
     */
    private void setCurrentPanel(JTabbedPane currentPanel) {
        this.currentPanel = currentPanel;
        startPanel.setVisible(false);
        p4Panel.setVisible(false);
        srExtraPanel.setVisible(false);
        mainFrame.add(currentPanel);
    }

    /**
     * updates the view depending on what currentView is set to
     */
    public void updateView() {
        switch (currentView) {
            case MAIN:
                mainFrame.remove(currentPanel);
                setCurrentPanel(startPanel);
                startPanel.setVisible(true);
                mainFrame.pack();
                mainFrame.revalidate();
                mainFrame.repaint();
                break;
            case P4:
                mainFrame.remove(currentPanel);
                setCurrentPanel(p4Panel);
                p4Panel.setVisible(true);
                mainFrame.pack();
                mainFrame.revalidate();
                mainFrame.repaint();
                break;
            case SREXTRA:
                mainFrame.remove(currentPanel);
                setCurrentPanel(srExtraPanel);
                srExtraPanel.setVisible(true);
                mainFrame.pack();
                mainFrame.revalidate();
                mainFrame.repaint();
                break;
            case LOAD:
                currentView = CurrentView.MAIN;
                setCurrentPanel(startPanel);
                startPanel.setVisible(true);
                mainFrame.pack();
                mainFrame.revalidate();
                mainFrame.repaint();
                mainFrame.setCursor(Cursor.getPredefinedCursor(
                        Cursor.DEFAULT_CURSOR));
                break;
        }
    }

    /**
     * Updates the schedules will be called once every hour.
     */
    private void updateData(){
        //Set the time and date for the update
        updateTime.setText("Senast updaterad: "+Calendar.getInstance().
                getTime().toString());
        /*Create a swing worker to do the hard job while the gui
             can remain active*/
        SwingWorker aWorker = new SwingWorker() {
            ArrayList<JTabbedPane> panes;
            public Object doInBackground() {
                panes = updater.update();
                return null;
            }

            public void done() {
                setStartPanel(panes.get(START));
                setP4Panel(panes.get(P4IND));
                setSrExtraPanel(panes.get(SRE));
                updateView();
            }
        };
        aWorker.run();
    }
}
