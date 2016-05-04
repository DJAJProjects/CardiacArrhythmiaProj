package Logic;

import org.encog.ConsoleStatusReportable;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;

/**
 * Created by Kuba on 16.04.2016.
 *
 * Manages supervised learning of feedforward neural network.
 */
public class SupervisedLearning {

    public void initialise(String path){
        DataManagement dm  = new DataManagement();

        // Loads prepared data set from given file.
//        VersatileMLDataSet dataSet = dm.getDataSource("data.txt");
        VersatileMLDataSet dataSet = dm.getDataSource(path);

        // Encog model allows to easily swap between different model types and automatically normalize data.
        EncogModel model = new EncogModel(dataSet);

        // Create feedforward neural network as the model type
        model.selectMethod(dataSet, MLMethodFactory.TYPE_FEEDFORWARD);

        // Send any output to the console.
        model.setReport(new ConsoleStatusReportable());
    }
}
