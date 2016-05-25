package Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominika Błasiak
 */
public class OutputArrhythmiaData {
	public ArrayList<Double> actualOutput;
	public ArrayList<Double> idealOutput;
	public boolean proper;
	public int id;
	public OutputArrhythmiaData(ArrayList<Double> actualOutput, ArrayList<Double> idealOutput, int id, boolean correct) {
		super();
		this.actualOutput = actualOutput;
		this.idealOutput = idealOutput;
		this.id = id;
		this.proper = correct;
	}	
	public String toString(){
		if(proper)
			return "Zestaw "+id + " (poprawny)";
		else
			return "Zestaw "+id + " (niepoprawny)";
	}
}
