package View;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import Logic.OutputArrhythmiaData;
import Logic.Polling;

public class LearningPanel extends JPanel{
	private JButton startLearningButton,addHiddenLayoutButton, networkFileButton, inputDataFileButton, iterationButton;
	private JLabel labelConfiguration, labelHiddenLayout;
	private JLabel labelNetwork, labelInputData;
	private SpringLayout springLayout;
	private JProgressBar progressBar;
	private FileDialog fileDialog;
	private File file;
	private JFrame mainFrame;
	private JTextPane iterationText;
	private JList<Integer> hiddenLayoutList;
	private DefaultListModel<Integer> dflmHiddenLayoutList;
	private JScrollPane scrollHiddenLayoutList;
	
	private void SetButton(JButton button, JComponent topCoomponent) {
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
	
	private void SetPathLabel(JComponent component, JButton button) {
		springLayout.putConstraint(SpringLayout.NORTH, component, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.SOUTH, component, 0, SpringLayout.SOUTH, button);
		springLayout.putConstraint(SpringLayout.WEST, component, 40, SpringLayout.EAST, button);
		springLayout.putConstraint(SpringLayout.EAST, component, -20, SpringLayout.EAST, this);
		this.add(component);
	}
	
	public void InitilizeHiddenLayoutList() {
		hiddenLayoutList = new JList<Integer>();
		scrollHiddenLayoutList = new JScrollPane(hiddenLayoutList);
		dflmHiddenLayoutList = new DefaultListModel<Integer>();
		springLayout.putConstraint(SpringLayout.NORTH, scrollHiddenLayoutList, 30, SpringLayout.SOUTH, startLearningButton);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollHiddenLayoutList, -100, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollHiddenLayoutList, 30, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST,scrollHiddenLayoutList, 200, SpringLayout.WEST, this);
		this.add(scrollHiddenLayoutList);
	}
	
	public void SetHiddenLayoutLabel(JLabel label, JScrollPane panel){
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.SOUTH, startLearningButton);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, panel);
		this.add(label);
	}
	public LearningPanel(final JFrame frame){
		this.mainFrame = frame;
		springLayout = new SpringLayout();
		this.setLayout(springLayout);
		
		labelConfiguration = new JLabel("Dane dotycz¹ce sieci: ");
		SetTopLabel();
		this.add(labelConfiguration);
		
		inputDataFileButton = new JButton("Plik z danymi wejœci.");
		SetButton(inputDataFileButton, labelConfiguration);
		
		labelInputData = new JLabel("Nie wybrano pliku z danymi do odpytywania. Domyœlnie zostanie za³adowany plik  "+ Polling.networkFilePath);
		SetPathLabel(labelInputData, inputDataFileButton);
		
		networkFileButton = new JButton("Plik do zapisu sieci");
		SetButton(networkFileButton, inputDataFileButton);
		
		labelNetwork = new JLabel("Nie wybrano pliku, gdzie zostanie zapisana sieæ. Domyœlnie bêdzie to plik "+ Polling.networkFilePath);
		SetPathLabel(labelNetwork, networkFileButton);
		
		addHiddenLayoutButton = new JButton("Dodaj ukryt¹ warstwê");
		SetButton(addHiddenLayoutButton, networkFileButton);
		
		iterationButton = new JButton("Zmieñ liczbê iteracji");
		SetButton(iterationButton,addHiddenLayoutButton );
		
		iterationText = new JTextPane();
		SetPathLabel(iterationText, iterationButton);
	
		startLearningButton = new JButton("Rozpocznij odpytywanie");
		SetButton(startLearningButton, iterationButton);
		
		progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, -80, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 30, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -30, SpringLayout.EAST, this);
		this.add(progressBar);
		
		InitilizeHiddenLayoutList();
		labelHiddenLayout = new JLabel("Ukryte warstwy: ");
		SetHiddenLayoutLabel(labelHiddenLayout,scrollHiddenLayoutList);
		
		inputDataFileButton.addActionListener(new ActionListener() {
			//TODO odkomentowac
			public void actionPerformed(ActionEvent e) {
				//ChangePath(, path);
				
			}
		});
		
		networkFileButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ChangePath(labelNetwork, Polling.networkFilePath);
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
			JOptionPane.showMessageDialog(null, "Wybrano niepoprawn¹ œcie¿kê");
	}
}
