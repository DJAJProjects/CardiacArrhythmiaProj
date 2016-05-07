package Logic;


import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;

import java.io.File;

/**
 * Created by Kuba on 19.04.2016.
 */
public class DataManagement {


    /**
     * Nominal columns numbers counting from 1.
     * The rest columns are treated as linear.
     */
    private Integer[] nominalColumnsNumbers = new Integer[]{2,22,23,24,25,26,27};

    private int columnsAmount = 280;

    private int outputColumnIndex = 279;

    public  DataManagement(){

    }
    /**
     * Loads data from file with given path, processes it and returns as VersatileMLDataSet.
     */
    public VersatileMLDataSet getDataSource(String path){

        // File with data
        File sourceFile = new File(path);

        // Creates data source with data in comma separated value file (CSV)
        CSVDataSource dataSource = new CSVDataSource(sourceFile, false, CSVFormat.DECIMAL_POINT);
        VersatileMLDataSet dataSet = new VersatileMLDataSet(dataSource);

        ColumnDefinition outputColumn = null;

        // For each column in data set
        for(int i = 0; i < columnsAmount; i++){

            //Defining output column
            if(i == outputColumnIndex){
                outputColumn = dataSet.defineSourceColumn("arhythmia type", i, ColumnType.continuous);
            }
            //Defining linear column
            else if( isNominalColumn(i)){
                dataSet.defineSourceColumn("atribute " + Integer.toString(i), i, ColumnType.continuous);
            }
            //Defining nominal column
            else{
                dataSet.defineSourceColumn("atribute " + Integer.toString(i), i, ColumnType.continuous);
            }
        }

        // Analyze the data, determine the min/max/mean/sd of every column.
        dataSet.analyze();

        // Map the prediction column to the output of the model, and all
        // other columns to the input.
        dataSet.defineSingleOutputOthersInput(outputColumn);

        return dataSet;
    }


    /**
     * Determines whether given column index is connected with
     * column with nominal values basing on predefined data about
     * columns types.
     */
    private  boolean isNominalColumn(int columnNum){
        for(int i = 0; i < nominalColumnsNumbers.length; i++){
            if(columnNum == i) return true;
        }
        return false;
    }
}
