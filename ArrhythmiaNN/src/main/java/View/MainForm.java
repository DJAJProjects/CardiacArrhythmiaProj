package View;

import javax.swing.*;

/**
 * Created by Kuba on 16.04.2016.
 */
public class MainForm {

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel mainJPanel;
}
