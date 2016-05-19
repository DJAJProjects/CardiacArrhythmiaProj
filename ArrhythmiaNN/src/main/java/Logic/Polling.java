package Logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

<<<<<<< HEAD
    public List<Result> run(BasicNetwork network, String pollingFilePath){
=======
	public static String pollingFilePath = "pollingData.txt";
	public static String networkFilePath = "network2.txt";
	public ArrayList<OutputArrhythmiaData> arrhythmiaDataList;
>>>>>>> origin/master

	public void run(){
    	arrhythmiaDataList = new ArrayList<OutputArrhythmiaData>();
        DataManagement dm  = new DataManagement();
        BasicNetwork network = dm.loadNeuralNetwork(networkFilePath);
        // Loads prepared data set from given file.
        // VersatileMLDataSet dataSet = dm.getDataSource("data.txt");
        BasicMLDataSet pollingDataSet = dm.getBasicMLDataSet(pollingFilePath);

        // test the neural network
        System.out.println("Neural Network Results:");
<<<<<<< HEAD

        // Prepares list of results
        List<Result> results = new ArrayList<Result>();

        for(MLDataPair pollingRecord: pollingDataSet ) {
            final MLData output = network.compute(pollingRecord.getInput());

            Result res = new Result();
            res.setIdeal(pollingRecord.getIdealArray());
            res.setActual(output.getData());
            results.add(res);

            // -----------TEMPORARY------------
=======
        int j =1;
        for(MLDataPair pair: pollingDataSet ) {
            final MLData output = network.compute(pair.getInput());
>>>>>>> origin/master
            System.out.println("ACTUAL OUTPUT: " + output.getData(0)
                    + " " + output.getData(1)
                    + " " + output.getData(2)
                    + " " + output.getData(3)
                    + " " + output.getData(4)
                    + " " + output.getData(5)
                    + " " + output.getData(6)
                    + " " + output.getData(7)
                    + " " + output.getData(8));
<<<<<<< HEAD
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
=======
            System.out.println(" IDEAL OUTPUT: " + pair.getIdeal().getData(0)
                    + " " + pair.getIdeal().getData(1)
                    + " " + pair.getIdeal().getData(2)
                    + " " + pair.getIdeal().getData(3)
                    + " " + pair.getIdeal().getData(4)
                    + " " + pair.getIdeal().getData(5)
                    + " " + pair.getIdeal().getData(6)
                    + " " + pair.getIdeal().getData(7)
                    + " " + pair.getIdeal().getData(8));
            ArrayList<Double> actualOutputs = new ArrayList<Double>();
            ArrayList<Double> idealOutputs = new ArrayList<Double>();
            for(int i =0 ; i<9;i++){
            	actualOutputs.add(output.getData(i));
            }
            for(int i =0 ; i<9;i++){
            	idealOutputs.add(pair.getIdeal().getData(i));
            }
            arrhythmiaDataList.add(new OutputArrhythmiaData(actualOutputs, idealOutputs, j++));
>>>>>>> origin/master
        }
        return results;
    }
}
