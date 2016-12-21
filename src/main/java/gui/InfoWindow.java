package gui;

import program.Channel;
import program.Tableau;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class InfoWindow {

    private JPanel mainFrame;
    private ArrayList<JScrollPane> panels;
    private ArrayList<String> names;
    private Channel channel;
    /**
     * InfoWindow is a window with tabs for each radio channel that have been
     * read.
     */
    public InfoWindow() {
        panels = new ArrayList<>();
        names = new ArrayList<>();
        mainFrame = new JPanel();
//        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Create a tab for given channel
     * @param channel channel object containing the information needed
     */
    public void createTab(Channel channel) {
        this.channel = channel;
        List<Tableau> tableaus = channel.getTableau();
        String[] columnNames = {"Program", "Start Tid", "Slut Tid"};
        Object[][] data;
        if (tableaus != null) {
             data = new Object[tableaus.size()][3];
//            System.out.println("Has tableau "+channel.getName());
            for (int i = 0; i < tableaus.size(); i++) {
//                System.out.println("Adding "+tableaus.get(i).getProgramName()+" "+tableaus.get(i).getStartTime().getTime()+" "+tableaus.get(i).getEndTime().getTime());
                data[i][0] = tableaus.get(i).getProgramName();
                data[i][1] = tableaus.get(i).getStartTime().getTime();
                data[i][2] = tableaus.get(i).getEndTime().getTime();
            }
        } else {
            data = new Object[1][3];
        }
        JTable table = new JTable(data,columnNames);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        names.add(channel.getName());
        panels.add(scrollPane);
    }

    /**
     * Will create all the tabs and add them to the main window.
     */
    public JTabbedPane buildTabbs() {
        JTabbedPane tabs = new JTabbedPane();

        //TODO make it load the icon for the channel and add it to the tab
//        ImageIcon resized = null;
//        System.out.println(channel.getImgUrl());
//        if (channel.getImgUrl().compareTo("") != 0) {
//            ImageIcon logo = new ImageIcon(channel.getImgUrl());
//            JFrame test = new JFrame();
//            JLabel label = new JLabel();
//            label.setIcon(logo);
//            test.add(label);
//            test.pack();
//            test.setVisible(true);
//            Image img = logo.getImage();
//            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
//            Graphics g = bi.createGraphics();
//            g.drawImage(img,0,0,20,20,null);
//            resized = new ImageIcon(bi);
//        }
//
//        System.out.println(channel.getName()+" "+channel.getImgUrl());
        for (int i = 0; i < panels.size(); i++) {
            tabs.addTab(names.get(i), panels.get(i));
        }

//        JFrame temp = new JFrame("test");
//        mainFrame.add(tabs, BorderLayout.CENTER);
//        temp.add(mainFrame);
//        temp.pack();
//        temp.setVisible(true);
        return tabs;
    }

//    public void setVisible(boolean visible) {
//        mainFrame.pack();
//        mainFrame.setVisible(visible);
//    }
}
