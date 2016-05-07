package Logic;

import org.encog.ConsoleStatusReportable;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.MLRegression;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

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


        /*BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true,279));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true,150));
        network.addLayer(new BasicLayer(new ActivationSigmoid(),true,9));
        network.getStructure().finalizeStructure();

        Backpropagation trainer = new Backpropagation(network, dataSet);
        trainer.setBatchSize(1);
        trainer.iteration();*/


        // Send any output to the console.
        model.setReport(new ConsoleStatusReportable());

        // Normalize the data. Encog will automatically determine the correct normalization
        // type based on the model.
        dataSet.normalize();

        // Hold back some data for a final validation.
        // Shuffle the data into a random ordering.
        // Use a seed of 1001 so that we always use the same holdback and will get more consistent results.
        model.holdBackValidation(0.3, true, 1001);

        // Choose whatever is the default training type for this model.
        model.selectTrainingType(dataSet);

        MLRegression bestMethod = (MLRegression)model.crossvalidate(5, true);

        // Display the training and validation errors.
        System.out.println( "Training error: " + model.calculateError(bestMethod, model.getTrainingDataset()));
        System.out.println( "Validation error: " + model.calculateError(bestMethod, model.getValidationDataset()));

        // Display our normalization parameters.
        NormalizationHelper helper = dataSet.getNormHelper();
        System.out.println(helper.toString());

        // Display the final model.
        System.out.println("Final model: " + bestMethod);


    }
}
