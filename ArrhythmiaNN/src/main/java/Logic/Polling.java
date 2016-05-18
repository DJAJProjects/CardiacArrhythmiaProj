package Logic;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 16.04.2016.
 *
 * Manages neutral network polling
 */
public class Polling {

    public List<Result> run(BasicNetwork network, String pollingFilePath){

        DataManagement dm  = new DataManagement();

        // Loads prepared data set from given file.
        // VersatileMLDataSet dataSet = dm.getDataSource("data.txt");
        BasicMLDataSet pollingDataSet = dm.getBasicMLDataSet("pollingData.txt");

        // test the neural network
        System.out.println("Neural Network Results:");

        // Prepares list of results
        List<Result> results = new ArrayList<Result>();

        for(MLDataPair pollingRecord: pollingDataSet ) {
            final MLData output = network.compute(pollingRecord.getInput());

            Result res = new Result();
            res.setIdeal(pollingRecord.getIdealArray());
            res.setActual(output.getData());
            results.add(res);

            // -----------TEMPORARY------------
            System.out.println("ACTUAL OUTPUT: " + output.getData(0)
                    + " " + output.getData(1)
                    + " " + output.getData(2)
                    + " " + output.getData(3)
                    + " " + output.getData(4)
                    + " " + output.getData(5)
                    + " " + output.getData(6)
                    + " " + output.getData(7)
                    + " " + output.getData(8));
            System.out.println(" IDEAL OUTPUT: " + pollingRecord.getIdeal().getData(0)
                    + " " + pollingRecord.getIdeal().getData(1)
                    + " " + pollingRecord.getIdeal().getData(2)
                    + " " + pollingRecord.getIdeal().getData(3)
                    + " " + pollingRecord.getIdeal().getData(4)
                    + " " + pollingRecord.getIdeal().getData(5)
                    + " " + pollingRecord.getIdeal().getData(6)
                    + " " + pollingRecord.getIdeal().getData(7)
                    + " " + pollingRecord.getIdeal().getData(8));
            //-----------------------------------
        }
        return results;
    }
}
