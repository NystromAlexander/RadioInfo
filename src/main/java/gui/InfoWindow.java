package gui;

import program.Channel;
import program.Tableau;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class InfoWindow {

    private JFrame mainFrame;
    private ArrayList<JScrollPane> panels;
    private ArrayList<String> names;

    /**
     * InfoWindow is a window with tabs for each radio channel that have been
     * read.
     */
    public InfoWindow() {
        panels = new ArrayList<JScrollPane>();
        names = new ArrayList<String>();
        mainFrame = new JFrame("Radio Info");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Create a tab for given channel
     * @param name name of the channel
     * @param channel channel object containing the information needed
     */
    public void createTab(String name, Channel channel) {
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();


        scrollPane.add(panel);
        names.add(name);
        panels.add(scrollPane);
    }

    /**
     * Will create all the tabs and add them to the main window.
     */
    public void buildTabbs() {
        JTabbedPane tabs = new JTabbedPane();

        for (int i = 0; i < panels.size(); i++) {
            tabs.addTab(names.get(i), panels.get(i));
        }

        mainFrame.add(tabs);
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }
}
