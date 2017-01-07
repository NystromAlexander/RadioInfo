package helpers;

import gui.Details;
import program.Channel;
import program.Tableau;

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

    public RowSelectionListener(Channel channels, JTable table) {
        this.channel = channels;
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Date startTid = (Date) table.getValueAt(table.getSelectedRow(),
                    1);
            for (Tableau tableau: channel.getTableau()) {
                if (tableau.getStartTime().getTime().equals(startTid)) {
                    try {
                        new Details(tableau,new URI(channel.getSiteUrl()));
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

    }
}
