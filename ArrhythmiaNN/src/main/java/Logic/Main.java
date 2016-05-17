package Logic;

import View.MainFrame;
import org.encog.neural.networks.BasicNetwork;

import java.awt.*;

/**
 * Created by Kuba on 16.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        final SupervisedLearning sl = new SupervisedLearning();
        BasicNetwork network = sl.run("learningData.txt");
        final Polling pl = new Polling();
        pl.run(network, "pollingData.txt");
        final DataManagement dm = new DataManagement();
        dm.saveNeuralNetwork("network2.txt", network);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(sl);
            }
        });
    }

}

