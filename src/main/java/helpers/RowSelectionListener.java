package helpers;

import gui.Details;
import program.Channel;
import program.Tableau;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexander Nyström(dv15anm) on 07/01/2017.
 */
public class RowSelectionListener implements ListSelectionListener {

    private Channel channels;
    private JTable table;

    public RowSelectionListener(Channel channels, JTable table) {
        this.channels = channels;
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Date startTid = (Date) table.getValueAt(table.getSelectedRow(),1);
            for (Tableau tableau: channels.getTableau()) {
                if (tableau.getStartTime().getTime().equals(startTid)) {
                    try {
                        new Details(tableau,new URI(channels.getSiteUrl()));
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

    }
}
