package helpers;

import gui.MainWindow;
import program.Updater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alexander Nyström(dv15anm) on 02/01/2017.
 */
public class UpdateButtonListener implements ActionListener {

    private Updater updater;
    private MainWindow mainWindow;

    /**
     * Creates a button listener that will update the schedules when pressed
     * @param mainWindow the main gui window
     */
    public UpdateButtonListener(MainWindow mainWindow) {
        updater = new Updater();
        this.mainWindow = mainWindow;
    }

    /**
     * When a update button is pressed it will get new schedules and then
     * update the graphics with the new schedules
     * @param e the event that triggered the method
     */
    public void actionPerformed(ActionEvent e) {
        SwingWorker aWorker = new SwingWorker() {
            ArrayList<JTabbedPane> panes;
            public Object doInBackground() {
                //Set a new time stamp for last updated
                mainWindow.setUpdateTime("Senast updaterad: "+
                        Calendar.getInstance().getTime().toString());
                mainWindow.getMainFrame().setCursor(Cursor.getPredefinedCursor(
                        Cursor.WAIT_CURSOR));
                panes = updater.update();

                return null;
            }

            public void done() {
                mainWindow.setStartPanel(panes.get(0));
                mainWindow.setP4Panel(panes.get(1));
                mainWindow.setSrExtraPanel(panes.get(2));
                mainWindow.updateView();
                mainWindow.getMainFrame().setCursor(Cursor.getPredefinedCursor(
                        Cursor.DEFAULT_CURSOR));
            }
        };
        aWorker.run();
}
}
