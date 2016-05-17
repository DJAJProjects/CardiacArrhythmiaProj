package Logic;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;

/**
 * Created by Kuba on 16.04.2016.
 *
 * Manages neutral network polling
 */
public class Polling {

    public void run(BasicNetwork network, String pollingFilePath){

        DataManagement dm  = new DataManagement();

        // Loads prepared data set from given file.
        // VersatileMLDataSet dataSet = dm.getDataSource("data.txt");
        BasicMLDataSet pollingDataSet = dm.getBasicMLDataSet("pollingData.txt");

        // test the neural network
        System.out.println("Neural Network Results:");
        for(MLDataPair pair: pollingDataSet ) {
            final MLData output = network.compute(pair.getInput());
            System.out.println("ACTUAL OUTPUT: " + output.getData(0)
                    + " " + output.getData(1)
                    + " " + output.getData(2)
                    + " " + output.getData(3)
                    + " " + output.getData(4)
                    + " " + output.getData(5)
                    + " " + output.getData(6)
                    + " " + output.getData(7)
                    + " " + output.getData(8));
            System.out.println(" IDEAL OUTPUT: " + pair.getIdeal().getData(0)
                    + " " + pair.getIdeal().getData(1)
                    + " " + pair.getIdeal().getData(2)
                    + " " + pair.getIdeal().getData(3)
                    + " " + pair.getIdeal().getData(4)
                    + " " + pair.getIdeal().getData(5)
                    + " " + pair.getIdeal().getData(6)
                    + " " + pair.getIdeal().getData(7)
                    + " " + pair.getIdeal().getData(8));
        }
    }
}
