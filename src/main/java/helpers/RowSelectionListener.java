package helpers;

import gui.Details;
import program.Channel;
import program.Schedule;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by Alexander Nyström(dv15anm) on 07/01/2017.
 */
public class RowSelectionListener implements ListSelectionListener {

    private Channel channel;
    private JTable table;

    /**
     * Listener for user clicking a row in a table
     * @param channel the channel the table belongs to
     * @param table the table containing the data
     */
    public RowSelectionListener(Channel channel, JTable table) {
        this.channel = channel;
        this.table = table;
    }

    /**
     * If a row is clicked a new window will be opened with more information
     * about the program on that row
     * @param e event that triggered the method
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Date startTid = (Date) table.getValueAt(table.getSelectedRow(),
                    1);
            for (Schedule schedule : channel.getSchedule()) {
                if (schedule.getStartTime().getTime().equals(startTid)) {

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                new Details(schedule,new URI(channel.getSiteUrl()));
                            } catch (URISyntaxException e1) {
                                JOptionPane.showMessageDialog(null,
                                        "There were an internal error");
                            }
                        }
                    });
                }
            }
        }

    }
}
