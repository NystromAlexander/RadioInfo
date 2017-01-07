package gui;

import helpers.RowSelectionListener;
import program.Channel;
import program.Tableau;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class InfoWindow {

    private final int STARTTIME_COL = 2;
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
        JScrollPane scrollPane;
        if (tableaus != null) {
             data = new Object[tableaus.size()][3];
//            System.out.println("Has tableau "+channel.getName());
            int put = 0;
            for (int i = 0; i < tableaus.size(); i++) {
//                System.out.println("Adding "+tableaus.get(i).getProgramName()+" "+tableaus.get(i).getStartTime().getTime()+" "+tableaus.get(i).getEndTime().getTime());
                if (tableaus.get(i).getProgramName().compareTo("") != 0) {
                    data[put][0] = tableaus.get(i).getProgramName();
                    data[put][1] = tableaus.get(i).getStartTime().getTime();
                    data[put][2] = tableaus.get(i).getEndTime().getTime();
                } else {
                    data[put][0] = "Ingen beskrivning tillgänglig";
                    data[put][1] = tableaus.get(i).getStartTime().getTime();
                    data[put][2] = tableaus.get(i).getEndTime().getTime();
                }
                put++;
            }
            JTable table = new JTable();
            DefaultTableModel tableModel = new DefaultTableModel(data,columnNames) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            };
            table.setModel(tableModel);
            table.setFillsViewportHeight(true);
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    Date start = (Date)table.getModel().getValueAt(row, STARTTIME_COL);
                    if (start.compareTo(Calendar.getInstance().getTime()) < 0) {
                        setBackground(Color.LIGHT_GRAY);
                    } else {
                        setBackground(table.getBackground());
                        setForeground(table.getForeground());
                    }
                    return this;
                }
            });
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setColumnSelectionAllowed(false);
            table.setRowSelectionAllowed(true);
            table.getSelectionModel().addListSelectionListener(new RowSelectionListener(channel,table));
            scrollPane = new JScrollPane(table);
        } else {
            JLabel noTableau = new JLabel("<html><div " +
                    "\"style=\"margin:30px\"> <h1 style=" +
                    "\"text-align:center\">Ingen tablå tillgänglig</h1>" +
                    "</div></html>");
            scrollPane = new JScrollPane(noTableau);
        }

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
