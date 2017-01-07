package program;

import gui.MainWindow;

import javax.swing.*;

/**
 * Created by Alexander Nyström(dv15anm) on 07/01/2017.
 */
public class Run {

    public static void main(String[] args ){
        MainWindow window = new MainWindow();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setUpGUI();
            }
        });
    }
}
