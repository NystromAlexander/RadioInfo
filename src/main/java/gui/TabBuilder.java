package gui;

import helpers.RowSelectionListener;
import program.Channel;
import program.Tableau;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class TabBuilder {

    private final int STARTTIME_COL = 2;
    private JPanel mainFrame;
    private ArrayList<JScrollPane> panels;
    private ArrayList<String> names;
    private Channel channel;

    /**
     * TabBuilder creates tabs with tables of show information.
     */
    public TabBuilder() {
        panels = new ArrayList<>();
        names = new ArrayList<>();
        mainFrame = new JPanel();
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
            for (int i = 0; i < tableaus.size(); i++) {
                if (tableaus.get(i).getProgramName().compareTo("")
                        != 0) {
                    data[i][0] = tableaus.get(i).getProgramName();
                    data[i][1] = tableaus.get(i).getStartTime().getTime();
                    data[i][2] = tableaus.get(i).getEndTime().getTime();
                } else {
                    data[i][0] = "Ingen beskrivning tillgänglig";
                    data[i][1] = tableaus.get(i).getStartTime().getTime();
                    data[i][2] = tableaus.get(i).getEndTime().getTime();
                }
            }
            JTable table = createTable(data, columnNames);
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
     * Creates a table with the given object matrix and with the given column
     * names.
     * Also adds a listener for row clicks so that a user can click a row
     * and it will open a window with details.
     * If a shows end time has passed
     * the current time rows color will be set to grey
     * @param data the date to be put into the table
     * @param columnNames name of the columns
     * @return the finished table.
     * @see RowSelectionListener
     */
    private JTable createTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable();
        DefaultTableModel tableModel =
                new DefaultTableModel(data,columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };

        table.setModel(tableModel);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table,
                                        Object value, boolean isSelected,
                                        boolean hasFocus, int row, int column) {

                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);

                        Date start = (Date)table.getModel().getValueAt(row,
                                STARTTIME_COL);
                        if (start.compareTo(Calendar.getInstance().
                                getTime()) < 0) {
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
        table.getSelectionModel().addListSelectionListener(
                new RowSelectionListener(channel,table));

        return table;
    }

    /**
     * Will create all the tabs and add them to the main window.
     */
    public JTabbedPane buildTabbs() {
        JTabbedPane tabs = new JTabbedPane();

        for (int i = 0; i < panels.size(); i++) {
            tabs.addTab(names.get(i), panels.get(i));
        }

        return tabs;
    }

}
