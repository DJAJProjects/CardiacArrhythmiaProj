package Logic;

import View.MainFrame;

import java.awt.*;

/**
 * Created by Kuba on 16.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        final SupervisedLearning sl = new SupervisedLearning();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(sl);
            }
        });
    }

}

