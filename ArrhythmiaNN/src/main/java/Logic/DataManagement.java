package Logic;


import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.versatile.normalizers.strategies.BasicNormalizationStrategy;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kuba on 19.04.2016.
 */
public class DataManagement {


    private double[][] input;
    private double[][] output;

    /**
     * Nominal columns numbers counting from 1.
     * The rest columns are treated as linear.
     */
    private Integer[] nominalColumnsNumbers = new Integer[]{2,22,23,24,25,26,27};

    private Integer[] consideredOutputs = new Integer[]{1,2,3,4,5,6,9,10,16};

    private int outputColAmount;
    private int inputColAmount = 279;
    private int columnsAmount;
    private int firstOutputIndex = 280;

    public  DataManagement(){
        outputColAmount = consideredOutputs.length;
        columnsAmount = inputColAmount + outputColAmount;
    }

    /**
     * Changes output column in CSV file from integer-output to binary-output
     * and saves it in a new file.
     */
    public File rearrangeFile(String sourcePath, String destPath)
            throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter(destPath, "UTF-8");
        BufferedReader br = new BufferedReader(new FileReader(sourcePath));
        try {

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {

                String[] result = line.split(",");
                int output = Integer.parseInt(result[result.length - 1]);

                if(isProperOutput(output)){
                    String inputLine = line.substring(0, line.lastIndexOf(","));
                    String outputLine = rearrangeOutputCSV(output);
                    String newLine = inputLine +"," + outputLine;
                    sb.append(newLine);
                    sb.append(System.lineSeparator());
                }

                line = br.readLine();
            }
            String everything = sb.toString();
            writer.write(everything);

            br.close();

            return new File(destPath);

        } catch(Exception ex){
            return null;
        }
    }

    /**
     * Loads data from file with given path, processes it and returns as VersatileMLDataSet.
     */
    public BasicMLDataSet getBasicMLDataSet(String path){

        String newFilePath = "newData.txt";

        File file = null;

        try {
            file = rearrangeFile(path, newFilePath);
            if(file == null)return null;
        }catch(Exception ex){
            return null;
        }

        // Creates data source with data in comma separated value file (CSV)
        CSVDataSource dataSource = new CSVDataSource(file, false, CSVFormat.DECIMAL_POINT);

        // Creates VersatileDataSet which makes it possible to read CSV data source and normalize it
        VersatileMLDataSet dataSet = new VersatileMLDataSet(dataSource);


        // For each column in data set
        for(int i = 0; i < columnsAmount; i++){

            //Defining output columns
            if(i >= firstOutputIndex){
                ColumnDefinition outputColumn = dataSet.defineSourceColumn("arhythmia type", i, ColumnType.continuous);
                dataSet.defineOutput(outputColumn);
            }
            //Defining linear column
            else if( isNominalColumn(i)){
                ColumnDefinition inputCol = dataSet.defineSourceColumn("atribute " + Integer.toString(i), i, ColumnType.continuous);
                dataSet.defineInput(inputCol);
            }
            //Defining nominal column
            else{
                ColumnDefinition inputCol = dataSet.defineSourceColumn("atribute " + Integer.toString(i), i, ColumnType.continuous);
                dataSet.defineInput(inputCol);
            }
        }
       // double[][] data2 = dataSet.
        // Analyze the data, determine the min/max/mean/sd of every column.
        dataSet.analyze();

        EncogModel model = new EncogModel(dataSet);

        model.selectMethod(dataSet, MLMethodFactory.TYPE_FEEDFORWARD);

        dataSet.getNormHelper().setNormStrategy( new BasicNormalizationStrategy(0,1,0,1));

        // Normalize the data. Encog will automatically determine the correct normalization
        // type based on the model.
        dataSet.normalize();

        double[][] data = dataSet.getData();

        input = new double[424][inputColAmount];
        output = new double[424][outputColAmount];

        // Rewriting normalised input and output data to a simple 2D arrays
        for(int row = 0; row < 424; row++){
            for(int col = 0; col < inputColAmount; col++){
                input[row][col] = data[row][col];
            }
            for(int col = 0; col < outputColAmount; col++){
                output[row][col] = data[row][col + inputColAmount];
            }
        }

        BasicMLDataSet trainingSet = new BasicMLDataSet(input, output);

        return trainingSet;
    }

    /**
     * Determines whether given column index is connected with
     * column with nominal values basing on predefined data about
     * columns types.
     */
    private  boolean isNominalColumn(int columnNum){
        for(int i = 0; i < nominalColumnsNumbers.length; i++){
            if(columnNum == nominalColumnsNumbers[i]) return true;
        }
        return false;
    }

    /**
     * Determines whether given output value is in the set of
     * outputs considered in this program.
     */
    private boolean isProperOutput(int value){
        for(int i = 0; i < consideredOutputs.length; i++){
            if(value == consideredOutputs[i]) return true;
        }
        return false;
    }

    private String rearrangeOutputCSV(int value){
        String out = "";
        for(int i = 0; i < consideredOutputs.length; i++){
            if(i!=0)out+=",";
            if(consideredOutputs[i] == value) out += "1";
            else out += "0";

        }
        return out;
    }
}
