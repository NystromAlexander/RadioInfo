/*
* Details - class for showing details for a radio program
*
* Version: 1.0
*
* Created by: Alexander Nyström
*
*/

package gui;

import program.ScheduleEntry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Alexander Nyström(dv15anm) on 07/01/2017.
 */
public class Details {

    private JFrame window;
    private ScheduleEntry scheduleEntry;
    private URI channelURI;
    private URI liveURI;

    /**
     * Create a Details window
     * @param scheduleEntry scheduleEntry for a channel
     * @param channelURI URI to the channels website
     */
    public Details(ScheduleEntry scheduleEntry, URI channelURI, URI liveURI) {
        this.channelURI = channelURI;
        this.liveURI = liveURI;
        window = new JFrame("Ytterligare information");
        this.scheduleEntry = scheduleEntry;
        setupWindow();
        window.setVisible(true);
        window.pack();
    }

    /**
     * Set up the window, get picture from the scheduleEntry and make a label
     * with the given description and make a button looking like a link to open
     * the website in a browser, also one for opening the radio in the browser.
     */
    private void setupWindow() {
        JPanel panel = new JPanel(new BorderLayout());
        Image image = null;
        JLabel picture = new JLabel();
        JLabel imageText = new JLabel();
        //Get image associated with the show
        try {
            if (scheduleEntry.getImgUrl().compareTo("") != 0) {
                image = ImageIO.read(new URL(scheduleEntry.getImgUrl()));
            }
        } catch (IOException e) {
            /*Since it does not matter for the program if it was unable to
                retrieve the image this clause is empty*/
        }

        if (image != null) {
            Icon i = new ImageIcon(image);
            picture.setIcon(i);
            panel.add(picture,BorderLayout.NORTH);
        }

        JPanel innerPane = new JPanel(new BorderLayout());
        //If there is a description for the show make a label with it
        if (scheduleEntry.getDescription().compareTo("") != 0) {
            imageText.setText("<html><div " +
                    "\"style=\"margin:30px\"> "+ scheduleEntry.getDescription()+
                    "</div></html>");
        } else { //say there were no descripion for the show
            imageText.setText("<html><div " +
                    "\"style=\"margin:30px\">Ingen beskrivning tillgänglig." +
                    "</div></html>");
        }

        /*Create a button looking like a link that tries to open the live radio
           in a browser*/
        JButton listen = new JButton("<HTML><FONT color=\"#000099\"><U>" +
                "Listen in a web browser</U></FONT></HTML>");
        listen.addActionListener(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(liveURI);
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(
                            null,"Could not open "+
                                    liveURI);
                }
            }
        });
        listen.setBorderPainted(false);
        listen.setOpaque(false);
        listen.setBackground(Color.WHITE);

        //Create a button looking like a link and that tries to open the website
        JButton webSite = new JButton("<HTML><FONT color=\"#000099\"><U>" +
                "Open the website.</U></FONT></HTML>");
        webSite.addActionListener(e -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(channelURI);
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(
                        null,"Could not open "+channelURI);
                }
            }
         });

        webSite.setBorderPainted(false);
        webSite.setOpaque(false);
        webSite.setBackground(Color.WHITE);
        innerPane.add(imageText, BorderLayout.NORTH);
        innerPane.add(webSite, BorderLayout.SOUTH);
        innerPane.add(listen, BorderLayout.CENTER);
        panel.add(innerPane, BorderLayout.CENTER);
        window.add(panel);
    }


}
