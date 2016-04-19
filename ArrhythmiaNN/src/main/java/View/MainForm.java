package View;

import Logic.DataManagement;
import Logic.Polling;
import Logic.SupervisedLearning;

import javax.swing.*;

/**
 * Created by Kuba on 16.04.2016.
 */
public class MainForm {

    public static DataManagement dataManagement;
    public static Polling polling;
    public static SupervisedLearning supervisedLearning;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel mainJPanel;
}
