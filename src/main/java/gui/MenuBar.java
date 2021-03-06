/*
* MenuBar - creates a menu bar specific to RadioInfo
*
* Version: 1.0
*
* Created by: Alexander Nyström
*
*/

package gui;

import helpers.CurrentView;
import helpers.UpdateButtonListener;

import javax.swing.*;

/**
 * Created by Alexander Nyström(dv15anm) on 21/12/2016.
 */
public class MenuBar {

    private JMenuBar menuBar;
    private MainWindow mainWindow;

    /**
     * Create a menu bar with 4 menus
     * @param mainWindow the main window which the menu bar belong to
     */
    public MenuBar (MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createStartMenu());
        menuBar.add(createP4Menu());
        menuBar.add(createSRExtraMenu());
    }

    /**
     * Create the file menu containing update and exit
     * @return the finished menu
     */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("Options");
        fileMenu.add(creteUpdate());
        fileMenu.add(createExit());
        return fileMenu;
    }

    /**
     * Creates the update menu item
     * @return the update item
     */
    private JMenuItem creteUpdate() {
        JMenuItem update = new JMenuItem("Update");
        update.addActionListener(new UpdateButtonListener(mainWindow));
        return update;
    }

    /**
     * Creates the exit item that will close the program when clicked
     * @return exit item
     */
    private JMenuItem createExit() {
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        return exit;
    }

    /**
     * Create the menu to switch to the p4 schedule
     * @return finished p4 menu
     */
    private JMenu createP4Menu() {
        JMenu p4Menu = new JMenu("P4");
        JMenuItem show = new JMenuItem("Show schedule");
        show.addActionListener(e -> {
            mainWindow.setCurrentView(CurrentView.P4);
            mainWindow.updateView();
        });
        p4Menu.add(show);
        return p4Menu;
    }

    /**
     * Create menu to switch to the mixed schedule also the start page
     * @return the finished mixed menu
     */
    private JMenu createStartMenu() {
        JMenu start = new JMenu("Blandat");
        JMenuItem show = new JMenuItem("Show schedule");
        show.addActionListener(e -> {
            mainWindow.setCurrentView(CurrentView.MAIN);
            mainWindow.updateView();
        });
        start.add(show);
        return start;
    }

    /**
     * Create the menu to switch to the schedules for SR Extra channels
     * @return the finished menu
     */
    private JMenu createSRExtraMenu() {
        JMenu SRExtra = new JMenu("SR Extra");
        JMenuItem show = new JMenuItem("Show schedule");
        show.addActionListener(e -> {
            mainWindow.setCurrentView(CurrentView.SREXTRA);
            mainWindow.updateView();
        });
        SRExtra.add(show);
        return SRExtra;
    }

    /**
     *
     * @return the menu bar
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
