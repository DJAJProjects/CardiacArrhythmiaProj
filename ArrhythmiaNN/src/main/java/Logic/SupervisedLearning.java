package Logic;

import org.encog.ConsoleStatusReportable;
import org.encog.engine.network.activation.*;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.Propagation;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

/**
 * Created by Kuba on 16.04.2016.
 *
 * Manages supervised learning of feedforward neural network.
 */
public class SupervisedLearning {

    public SupervisedLearning(){
        // Default layers settings
        network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationLOG(),true,279));
        network.addLayer(new BasicLayer(new ActivationLOG(), true,400));
        network.addLayer(new BasicLayer(new ActivationLOG(),false,9));
        epochsCount = 3000;
    }

    private BasicNetwork network;

    private int epochsCount;

    public void setEpochsCount(int value){epochsCount = value;}

    public void customizeHiddenLayers(int[] layers){
        network.addLayer(new BasicLayer(new ActivationLOG(),true,279));

        for(int  i =0; i < layers.length; i++){
            network.addLayer(new BasicLayer(new ActivationLOG(),true,layers[i]));
        }

        network.addLayer(new BasicLayer(new ActivationLOG(),false,9));
    }

    public BasicNetwork run(String path){

        DataManagement dm  = new DataManagement();

        // Loads prepared data set from given file.
        // VersatileMLDataSet dataSet = dm.getDataSource("data.txt");
        BasicMLDataSet dataSet = dm.getBasicMLDataSet(path);

        network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationLOG(),true,279));
        network.addLayer(new BasicLayer(new ActivationLOG(),true,400));
        network.addLayer(new BasicLayer(new ActivationLOG(),false,9));
        

        network.getStructure().finalizeStructure();
        network.reset();
        
        // train the neural network
        final Propagation train = new Backpropagation(network, dataSet);

        int epoch = 0;

        do {
            train.iteration();
            if(epoch%10 == 0)
                System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while((epoch < epochsCount));

        train.finishTraining();

        return network;

    }
}
