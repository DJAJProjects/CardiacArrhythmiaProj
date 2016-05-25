package Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominika Błasiak
 */
public class OutputArrhythmiaData {
	public ArrayList<Double> actualOutput;
	public ArrayList<Double> idealOutput;
	public int id;
	public OutputArrhythmiaData(ArrayList<Double> actualOutput, ArrayList<Double> idealOutput, int id) {
		super();
		this.actualOutput = actualOutput;
		this.idealOutput = idealOutput;
		this.id = id;
	}	
	public String toString(){
		return "Zestaw "+id;
	}
}
