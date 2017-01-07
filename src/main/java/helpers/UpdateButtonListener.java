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

    public UpdateButtonListener(MainWindow mainWindow) {
        updater = new Updater();
        this.mainWindow = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {
        SwingWorker aWorker = new SwingWorker() {
            public Object doInBackground() {
                mainWindow.setUpdateTime("Senast updaterad: "+
                        Calendar.getInstance().getTime().toString());
                mainWindow.getMainFrame().setCursor(Cursor.getPredefinedCursor(
                        Cursor.WAIT_CURSOR));
                ArrayList<JTabbedPane> panes = updater.update();
                mainWindow.setStartPanel(panes.get(0));
                mainWindow.setP4Panel(panes.get(1));
                mainWindow.setSrExtraPanel(panes.get(2));
                return null;
            }

            public void done() {
                mainWindow.updateView();
                mainWindow.getMainFrame().setCursor(Cursor.getPredefinedCursor(
                        Cursor.DEFAULT_CURSOR));
            }
        };
        aWorker.run();
}
}
