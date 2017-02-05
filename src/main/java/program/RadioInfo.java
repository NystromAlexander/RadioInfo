/*
* RadioInfo - class for running the program
*
* Version: 1.0
*
* Created by: Alexander Nyström
*
*/

package program;

import gui.MainWindow;

import javax.swing.*;

/**
 * Created by Alexander Nyström(dv15anm) on 07/01/2017.
 */
public class RadioInfo {

    public static void main(String[] args ){
        MainWindow window = new MainWindow();
        SwingUtilities.invokeLater(() -> window.setUpGUI());
    }
}
