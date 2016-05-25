package View;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Logic.DataManagement;
import Logic.OutputArrhythmiaData;
import Logic.Polling;

/**
 * 
 * @author Dominika Błasiak
 *
 */
public class PollingPanel extends JPanel {
	private JButton networkFileButton, pollingFileButton, resultFileButton;
	private JButton startPollingButton;
	private SpringLayout springLayout;
	private JLabel labelChoose, labelNetwork, labelPolling, labelResultFile, labelProper, labelWrong;
	//private String resultPath = "result.txt";
	private FileDialog fileDialog;
	private File file;
	private JList<OutputArrhythmiaData> outputList;
	private JList<String> idealOutputList;
	private JList<Double> actualOutputList;
	private DefaultListModel<OutputArrhythmiaData> dflmOutputList;
	private DefaultListModel<String> dflmIdealOutputList;
	private DefaultListModel<Double> dflmActualOutputList;
	private JScrollPane scrollOutputList, scrollActualOutputList, scrollIdealOutputList;
	private final JFrame mainFrame;
	private final Polling polling;
	private JLabel labelOutput, labelActualOutput, labelIdealOutput;
	
	private void SetButton(JButton button, JComponent topCoomponent) {
		springLayout.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.SOUTH, topCoomponent);
		springLayout.putConstraint(SpringLayout.SOUTH, button, 50, SpringLayout.SOUTH, topCoomponent);
		springLayout.putConstraint(SpringLayout.WEST, button, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, button, 200, SpringLayout.WEST, this);
		this.add(button);
	}

	private void SetTopLabel() {
		springLayout.putConstraint(SpringLayout.NORTH, labelChoose, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, labelChoose, 50, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, labelChoose, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, labelChoose, 200, SpringLayout.WEST, this);
	}

	private void SetPathLabel(JLabel label, JButton button) {
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.SOUTH, button);
		springLayout.putConstraint(SpringLayout.WEST, label, 40, SpringLayout.EAST, button);
		springLayout.putConstraint(SpringLayout.EAST, label, -20, SpringLayout.EAST, this);
		this.add(label);
	}

	public void SetSelectedActualOutput(){
		if(outputList.getSelectedValue()!=null) {
			dflmActualOutputList.clear();
			OutputArrhythmiaData data = outputList.getSelectedValue();
			for (int i = 0; i < data.actualOutput.size(); i++) {
				dflmActualOutputList.addElement(data.actualOutput.get(i));
			}
			actualOutputList.setModel(dflmActualOutputList);
		}
	}
	public void InitilizeActualOutputList() {	
		actualOutputList = new JList<Double>();
		dflmActualOutputList = new DefaultListModel<Double>();
		scrollActualOutputList = new JScrollPane(actualOutputList);
		springLayout.putConstraint(SpringLayout.NORTH, scrollActualOutputList, 30, SpringLayout.SOUTH, startPollingButton);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollActualOutputList, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollActualOutputList, 30, SpringLayout.EAST, scrollOutputList);
		springLayout.putConstraint(SpringLayout.EAST, scrollActualOutputList, 200, SpringLayout.EAST, scrollOutputList);
		this.add(scrollActualOutputList);
	}

	public void SetSelectedIdealOutput(){
		if(outputList.getSelectedValue()!=null) {
			dflmIdealOutputList.clear();
			OutputArrhythmiaData data = outputList.getSelectedValue();
			for (int i = 0; i < data.idealOutput.size(); i++) {
				dflmIdealOutputList.addElement(data.idealOutput.get(i).toString() + ", " + DataManagement.outputNames[i]);
			}
			idealOutputList.setModel(dflmIdealOutputList);
		}
	}
	public void InitilizeIdealOutputList() {
		idealOutputList = new JList<String>();
		dflmIdealOutputList = new DefaultListModel<String>();
		scrollIdealOutputList = new JScrollPane(idealOutputList);
		springLayout.putConstraint(SpringLayout.NORTH, scrollIdealOutputList, 30, SpringLayout.SOUTH, startPollingButton);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollIdealOutputList, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollIdealOutputList, 30, SpringLayout.EAST, scrollActualOutputList);
		springLayout.putConstraint(SpringLayout.EAST, scrollIdealOutputList, -400, SpringLayout.EAST, this);
		this.add(scrollIdealOutputList);
	}

	public void SetOutputDataList() {
		dflmOutputList.clear();
		for (int i = 0; i < polling.arrhythmiaDataList.size(); i++) {
			dflmOutputList.addElement(polling.arrhythmiaDataList.get(i));
		}
		outputList.setModel(dflmOutputList);
	}

	public void SetOutputLabel(JLabel label, JScrollPane output){
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.SOUTH, startPollingButton);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, output);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, output);
		this.add(label);
	}
	public void InitilizeOutputList() {
		outputList = new JList<OutputArrhythmiaData>();
		scrollOutputList = new JScrollPane(outputList);
		dflmOutputList = new DefaultListModel<OutputArrhythmiaData>();
		springLayout.putConstraint(SpringLayout.NORTH, scrollOutputList, 30, SpringLayout.SOUTH, startPollingButton);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollOutputList, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollOutputList, 30, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollOutputList, 200, SpringLayout.WEST, this);
		this.add(scrollOutputList);
	}

	public PollingPanel(JFrame frame, final Polling polling) {
		this.mainFrame = frame;
		this.polling = polling;
		springLayout = new SpringLayout();
		this.setLayout(springLayout);

		labelChoose = new JLabel("Wybierz:");
		SetTopLabel();
		this.add(labelChoose);

		networkFileButton = new JButton("Sieć");
		SetButton(networkFileButton, labelChoose);

		labelNetwork = new JLabel(
				"Nie wybrano pliku z siecią. Domyślnie zostanie załadowany plik " + DataManagement.networkFilePath);
		SetPathLabel(labelNetwork, networkFileButton);

		pollingFileButton = new JButton("Dane do odpytywania");
		SetButton(pollingFileButton, networkFileButton);

		labelPolling = new JLabel("Nie wybrano pliku z danymi do odpytywania. Domyślnie zostanie załadowany plik "
				+ DataManagement.pollingFilePath);
		SetPathLabel(labelPolling, pollingFileButton);

		resultFileButton = new JButton("Plik wynikowy");
		SetButton(resultFileButton, pollingFileButton);

		labelResultFile = new JLabel(
				"Nie wybrano pliku, gdzie zostaną zapisane dane wynikowe. Domyślnie będzie to plik result.txt");
		SetPathLabel(labelResultFile, resultFileButton);

		startPollingButton = new JButton("Rozpocznij odpytywanie");
		SetButton(startPollingButton, resultFileButton);

		InitilizeOutputList();
		InitilizeActualOutputList();
		InitilizeIdealOutputList();
		
		labelOutput = new JLabel("Zestaw danych: ");
		SetOutputLabel(labelOutput,scrollOutputList);
		
		labelActualOutput = new JLabel("Aktualne dane: ");
		SetOutputLabel(labelActualOutput,scrollActualOutputList);
		
		labelIdealOutput = new JLabel("Idealne dane: ");
		SetOutputLabel(labelIdealOutput,scrollIdealOutputList);

		labelProper = new JLabel("Liczba poprawnych: ");
		springLayout.putConstraint(SpringLayout.NORTH, labelProper, 30, SpringLayout.SOUTH, startPollingButton);
		springLayout.putConstraint(SpringLayout.SOUTH, labelProper, 50, SpringLayout.SOUTH, startPollingButton);
		springLayout.putConstraint(SpringLayout.WEST, labelProper, 30, SpringLayout.EAST, scrollIdealOutputList);
//		springLayout.putConstraint(SpringLayout.EAST, labelProper, -400, SpringLayout.EAST, this);
		this.add(labelProper);

		labelWrong = new JLabel("Liczba niepoprawnych: ");
		springLayout.putConstraint(SpringLayout.NORTH, labelWrong, 20, SpringLayout.SOUTH, labelProper);
		springLayout.putConstraint(SpringLayout.SOUTH, labelWrong, 50, SpringLayout.SOUTH, labelProper);
		springLayout.putConstraint(SpringLayout.WEST, labelWrong, 30, SpringLayout.EAST, scrollIdealOutputList);
//		springLayout.putConstraint(SpringLayout.EAST, labelProper, -400, SpringLayout.EAST, this);
		this.add(labelWrong);

		networkFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataManagement.networkFilePath = ChangePath(labelNetwork, DataManagement.networkFilePath);
			}
		});
		pollingFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataManagement.pollingFilePath = ChangePath(labelPolling, DataManagement.pollingFilePath);
			}
		});
		resultFileButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DataManagement.resultFilePath = ChangePath(labelResultFile, DataManagement.resultFilePath);

			}
		});
		startPollingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dflmActualOutputList.clear();
				dflmIdealOutputList.clear();
				polling.run();

				labelProper.setText("Liczba poprawnych: "+polling.proper);
				labelWrong.setText("Liczba niepoprawnych: "+polling.bad);
				SetOutputDataList();
				saveResult();
			}
		});
		outputList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				SetSelectedActualOutput();
				SetSelectedIdealOutput();
				
			}
		});
	}

	String ChangePath(JLabel label, String path) {
		fileDialog = new FileDialog(mainFrame, "Wybierz zbiór danych do odpytywania", FileDialog.LOAD);
		fileDialog.setVisible(true);
		file = new File(fileDialog.getDirectory() + fileDialog.getFile());
		if (file.exists() && file.getPath().substring(file.getPath().lastIndexOf(".") + 1).equals("txt")) {
			label.setText(path = file.getAbsolutePath());
			System.out.println(path);
		} else
			JOptionPane.showMessageDialog(null, "Wybrano niepoprawną ścieżkę");
		return path;
	}

	public void saveResult(){
		PrintWriter zapis = null;
		try {
			zapis = new PrintWriter(DataManagement.resultFilePath);
			for(int i = 0;  i < polling.arrhythmiaDataList.size(); i++) {
				zapis.println("Zestaw: " + i);
				for(int j =0 ; j< polling.arrhythmiaDataList.get(i).actualOutput.size();j++)
					zapis.print(polling.arrhythmiaDataList.get(i).actualOutput.get(j) +" ");
				zapis.println();
				for(int j =0 ; j< polling.arrhythmiaDataList.get(i).idealOutput.size();j++)
					zapis.print(polling.arrhythmiaDataList.get(i).idealOutput.get(j)+" ");
				zapis.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		zapis.close();
	}
}
