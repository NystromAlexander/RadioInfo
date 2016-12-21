package gui;

import javax.swing.*;

/**
 * Created by Alexander Nyström(dv15anm) on 21/12/2016.
 */
public class MenuBar {

    private JMenuBar menuBar;
    private MainWindow mainWindow;

    public MenuBar (MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        menuBar = new JMenuBar();
        menuBar.add(createFileMenue());
        menuBar.add(createStartMenu());
        menuBar.add(createP4Menu());
        menuBar.add(createSRExtraMenu());
    }

    private JMenu createFileMenue() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(creteUpdate());
        fileMenu.add(createExit());
        return fileMenu;
    }

    private JMenuItem creteUpdate() {
        JMenuItem update = new JMenuItem("Update");

        return update;
    }

    private JMenuItem createExit() {
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        return exit;
    }

    private JMenu createP4Menu() {
        JMenu p4Menu = new JMenu("P4");
        JMenuItem show = new JMenuItem("Visa tablå");
        show.addActionListener(e -> {
            mainWindow.getStartPanel().setVisible(false);
            mainWindow.getSrExtraPanel().setVisible(false);
            mainWindow.getMainFrame().add(mainWindow.getP4Panel());
            mainWindow.getP4Panel().setVisible(true);
            mainWindow.getMainFrame().pack();
        });
        p4Menu.add(show);
        return p4Menu;
    }

    private JMenu createStartMenu() {
        JMenu start = new JMenu("Startsida");
        JMenuItem show = new JMenuItem("Visa tablå");
        show.addActionListener(e -> {
            mainWindow.getStartPanel().setVisible(false);
            mainWindow.getSrExtraPanel().setVisible(false);
            mainWindow.getMainFrame().add(mainWindow.getStartPanel());
            mainWindow.getStartPanel().setVisible(true);
            mainWindow.getMainFrame().pack();
        });
        start.add(show);
        return start;
    }

    private JMenu createSRExtraMenu() {
        JMenu SRExtra = new JMenu("SR Extra");
        JMenuItem show = new JMenuItem("Visa tablå");
        show.addActionListener(e -> {
            mainWindow.getStartPanel().setVisible(false);
            mainWindow.getP4Panel().setVisible(false);
            mainWindow.getMainFrame().add(mainWindow.getSrExtraPanel());
            mainWindow.getSrExtraPanel().setVisible(true);
            mainWindow.getMainFrame().pack();
        });
        SRExtra.add(show);
        return SRExtra;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
