package Logic;

/**
 * Created by Kuba on 18.05.2016.
 */
public class Result {

    public Result(int size){
        actual = new double[size];
        ideal = new double[size];
    }

    public Result() {
    }

    public double[] getActual(){return actual;}
    public double[] getIdeal(){return ideal;}
    public void setActual(int index, double value){actual[index] = value;}
    public void setIdeal(int index, double value){ideal[index] = value;}
    public void setActual(double[] tab){actual = tab;}
    public void setIdeal(double[] tab){ideal = tab;}

    private double[] actual;
    private double[] ideal;
}
