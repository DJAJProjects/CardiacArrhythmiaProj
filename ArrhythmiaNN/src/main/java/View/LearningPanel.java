package View;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import Logic.DataManagement;
import Logic.OutputArrhythmiaData;
import Logic.Polling;
import Logic.SupervisedLearning;

/**
 * 
 * @author Dominika Błasiak
 *
 */
public class LearningPanel extends JPanel {
	private JButton startLearningButton, addHiddenLayerButton, networkFileButton, inputDataFileButton;
	private JLabel labelConfiguration, labelHiddenLayer;
	private JLabel labelNetwork, labelInputData, labelAddHiddenLayer, labelIteration;
	private SpringLayout springLayout;
	private JProgressBar progressBar;
	private FileDialog fileDialog;
	private File file;
	private JFrame mainFrame;
	private JTextField iterationText, neuronText;
	private JList<Integer> hiddenLayerList;
	private DefaultListModel<Integer> dflmHiddenLayerList;
	private JScrollPane scrollHiddenLayerList;
	private final SupervisedLearning learning;
	private final LearningPanel learningPanel = this;

	private void SetButton(JComponent button, JComponent topCoomponent) {
		springLayout.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.SOUTH, topCoomponent);
		springLayout.putConstraint(SpringLayout.SOUTH, button, 50, SpringLayout.SOUTH, topCoomponent);
		springLayout.putConstraint(SpringLayout.WEST, button, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, button, 200, SpringLayout.WEST, this);
		this.add(button);
	}

	private void SetTopLabel() {
		springLayout.putConstraint(SpringLayout.NORTH, labelConfiguration, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, labelConfiguration, 50, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, labelConfiguration, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, labelConfiguration, 200, SpringLayout.WEST, this);
	}

	private void SetPathLabel(JComponent component, JButton button, int offset) {
		springLayout.putConstraint(SpringLayout.NORTH, component, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.SOUTH, component, 0, SpringLayout.SOUTH, button);
		springLayout.putConstraint(SpringLayout.WEST, component, 40 + offset, SpringLayout.EAST, button);
		springLayout.putConstraint(SpringLayout.EAST, component, -20, SpringLayout.EAST, this);
		this.add(component);
	}

	public void InitilizeHiddenLayerList() {
		hiddenLayerList = new JList<Integer>();
		scrollHiddenLayerList = new JScrollPane(hiddenLayerList);
		dflmHiddenLayerList = new DefaultListModel<Integer>();
		springLayout.putConstraint(SpringLayout.NORTH, scrollHiddenLayerList, 30, SpringLayout.SOUTH,
				startLearningButton);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollHiddenLayerList, -100, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollHiddenLayerList, 30, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollHiddenLayerList, 200, SpringLayout.WEST, this);
		this.add(scrollHiddenLayerList);
	}

	public void addNewHiddenLayer() {
		try {
			int count = Integer.parseInt(neuronText.getText());

			if (count <= 0)
				throw new NumberFormatException();
			dflmHiddenLayerList.addElement(count);
			hiddenLayerList.setModel(dflmHiddenLayerList);
			neuronText.setText("");

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Podano niepoprawną liczb� neuron�w");
		}

	}

	public void SetHiddenLayerLabel(JLabel label, JScrollPane panel) {
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.SOUTH, startLearningButton);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, panel);
		this.add(label);
	}

	void SetFieldText(JTextField field, JComponent button) {
		springLayout.putConstraint(SpringLayout.NORTH, field, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.SOUTH, field, 0, SpringLayout.SOUTH, button);
		springLayout.putConstraint(SpringLayout.WEST, field, 40, SpringLayout.EAST, button);
		springLayout.putConstraint(SpringLayout.EAST, field, 150, SpringLayout.EAST, button);
		this.add(field);
	}

	public LearningPanel(final JFrame frame, final SupervisedLearning learning) {
		this.mainFrame = frame;
		this.learning = learning;
		springLayout = new SpringLayout();
		this.setLayout(springLayout);

		labelConfiguration = new JLabel("Dane dotyczące sieci: ");
		SetTopLabel();
		this.add(labelConfiguration);

		inputDataFileButton = new JButton("Plik z danymi wej�ci.");
		SetButton(inputDataFileButton, labelConfiguration);

		labelInputData = new JLabel("Nie wybrano pliku z danymi do odpytywania. Domyślnie zostanie załadowany plik  "
				+ DataManagement.inputFilePath);
		SetPathLabel(labelInputData, inputDataFileButton, 0);

		networkFileButton = new JButton("Plik do zapisu sieci");
		SetButton(networkFileButton, inputDataFileButton);

		labelNetwork = new JLabel("Nie wybrano pliku, gdzie zostanie zapisana sieć. Domyślnie będzie to plik "
				+ DataManagement.networkFilePath);
		SetPathLabel(labelNetwork, networkFileButton, 0);

		addHiddenLayerButton = new JButton("Dodaj ukrytą warstwę");
		SetButton(addHiddenLayerButton, networkFileButton);

		neuronText = new JTextField();
		SetFieldText(neuronText, addHiddenLayerButton);

		labelIteration = new JLabel("Wpisz liczbę iteracji:", JLabel.CENTER);
		SetButton(labelIteration, addHiddenLayerButton);

		iterationText = new JTextField();
		iterationText.setText(String.valueOf(learning.getEpochsCount()));
		SetFieldText(iterationText, labelIteration);

		startLearningButton = new JButton("Rozpocznij odpytywanie");
		SetButton(startLearningButton, labelIteration);

		progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, -80, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 30, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -30, SpringLayout.EAST, this);
		this.add(progressBar);

		labelAddHiddenLayer = new JLabel("Wpisz ilość neuronów do pola i kliknij przycisk dodaj ukrytą warstwę");
		SetPathLabel(labelAddHiddenLayer, addHiddenLayerButton, 150);

		InitilizeHiddenLayerList();
		labelHiddenLayer = new JLabel("Ukryte warstwy: ");
		SetHiddenLayerLabel(labelHiddenLayer, scrollHiddenLayerList);

		inputDataFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePath(labelInputData, DataManagement.inputFilePath);

			}
		});

		networkFileButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ChangePath(labelNetwork, DataManagement.networkFilePath);
			}
		});
		addHiddenLayerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				addNewHiddenLayer();
			}
		});

		startLearningButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					int count = Integer.parseInt(iterationText.getText());

					if (count <= 0)
						throw new NumberFormatException();
					learning.setEpochsCount(count);
					progressBar.setValue(5);
					progressBar.setMaximum(count);
					int tab[] = new int[dflmHiddenLayerList.size()];;
					for(int i =0 ; i<dflmHiddenLayerList.size();i++){
						tab[0] = dflmHiddenLayerList.getElementAt(i).intValue();
					}
					if(tab.length> 0)learning.customizeHiddenLayers(tab);
					startLearningButton.setEnabled(false);
					learning.run(learningPanel);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Podano niepoprawną liczbę iteracji");
				}
			}
		});
	}

	void ChangePath(JLabel label, String path) {
		fileDialog = new FileDialog(mainFrame, "Wybierz zbiór danych do odpytywania", FileDialog.LOAD);
		fileDialog.setVisible(true);
		file = new File(fileDialog.getDirectory() + fileDialog.getFile());
		if (file.exists() && file.getPath().substring(file.getPath().lastIndexOf(".") + 1).equals("txt")) {
			label.setText(path = file.getAbsolutePath());
		} else
			JOptionPane.showMessageDialog(null, "Wybrano niepoprawną ścieżkę");
	}
	private int epoch;
	public void RefreshProgressBar(final int epoch) {
		this.epoch= epoch;
		new Thread(new Runnable() {
			
			public void run() {
				progressBar.setValue(epoch);
				mainFrame.repaint();
				progressBar.repaint();
				
			}
		}).start();		
	}
	public void EnabledButton(){
		startLearningButton.setEnabled(true);
	}

	public void finishLearning(){
		DataManagement dm = new DataManagement();
		dm.saveNeuralNetwork(labelNetwork.getText(), learning.getNetwork());
	}
}
