package Logic;


import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.VersatileMLDataSet;
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
    public VersatileMLDataSet getDatSource(String path){
        File sourceFile = new File(path);

        CSVDataSource dataSource = new CSVDataSource(sourceFile, false, CSVFormat.DECIMAL_POINT);
        VersatileMLDataSet dataSet = new VersatileMLDataSet(dataSource);

        ColumnDefinition outputColumn = null;

        // For each column in data set
        for(int i = 0; i < columnsAmount; i++){

            //Defining linear column
            if( isNominalColumn(i)){
                dataSet.defineSourceColumn("", i, ColumnType.continuous);
            }
            //Defining nominal column
            else{
                dataSet.defineSourceColumn("", i, ColumnType.nominal);
            }

            //Defining output column
            if(i == outputColumnIndex){
                outputColumn = dataSet.defineSourceColumn("arhythmia type", i, ColumnType.continuous);
            }
        }

        // Analyze the data, determine the min/max/mean/sd of every column.
        dataSet.analyze();

        // Map the prediction column to the output of the model, and all
        // other columns to the input.
        dataSet.defineSingleOutputOthersInput(outputColumn);

        return dataSet;
    }

    private  boolean isNominalColumn(int columnNum){
        for(int i = 0; i < nominalColumnsNumbers.length; i++){
            if(columnNum == i) return true;
        }
        return false;
    }
}
