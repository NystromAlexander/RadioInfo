package gui;

import program.Tableau;

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
    private Tableau tableau;
    private URI channelURI;

    public Details(Tableau tableau, URI channelURI) {
        this.channelURI = channelURI;
        window = new JFrame("Ytterligare information");
        this.tableau = tableau;
        setupWindow();
        window.setVisible(true);
        window.pack();
    }

    private void setupWindow() {
        JPanel panel = new JPanel(new BorderLayout());
        Image image = null;
        JLabel picture = new JLabel();
        JLabel imageText = new JLabel();
        try {
            if (tableau.getImgUrl().compareTo("") != 0) {
                image = ImageIO.read(new URL(tableau.getImgUrl()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image != null) {
            Icon i = new ImageIcon(image);
            picture.setIcon(i);
            panel.add(picture,BorderLayout.NORTH);
        }
        JPanel innerPane = new JPanel(new BorderLayout());
        imageText.setText("<html><div " +
                "\"style=\"margin:30px\"> "+tableau.getDescription()+
                "</div></html>");
        JButton webSite = new JButton("<HTML><FONT color=\"#000099\"><U>" +
                "Gå till kannalens hemsida.</U></FONT></HTML>");
        webSite.addActionListener(e -> {

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(channelURI);
                } catch (IOException io) { JOptionPane.showMessageDialog(
                        null,"Could not open "+channelURI);
                }
            }
         });
        webSite.setBorderPainted(false);
        webSite.setOpaque(false);
        webSite.setBackground(Color.WHITE);
        innerPane.add(imageText,BorderLayout.NORTH);
        innerPane.add(webSite,BorderLayout.CENTER);
        panel.add(innerPane, BorderLayout.CENTER);
        window.add(panel);
    }


}
