package View;

import Logic.Polling;
import Logic.SupervisedLearning;

import javax.swing.*;
import java.awt.*;
/**
 * 
 * @author Dominika Błasiak
 *
 */
public class MainFrame extends JFrame{
    public final JFrame frame = this;
    private SpringLayout springLayout;
    private  JTabbedPane tabbedPane;
    private JLabel learningLabel, pollingLabel;
    private final Polling polling;
	private final SupervisedLearning learning;
	
    void SetPositionTabbelPane(){
    	springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, getContentPane());
    }
    void AddLearningPanel(){
    	tabbedPane.addTab("",new ImageIcon("icon\\ico_2"), new LearningPanel(frame, learning));
    	learningLabel = new JLabel("Nauczanie", JLabel.CENTER);
        learningLabel.setPreferredSize(new Dimension(200, 30));
        tabbedPane.setTabComponentAt(1, learningLabel); 
    	
    }
    void AddPollingPanel(){
    	 tabbedPane.addTab("",  new ImageIcon("icon\\ico_1"),new PollingPanel(frame, polling));
         pollingLabel = new JLabel("Odpytywanie", JLabel.CENTER);
         pollingLabel.setPreferredSize(new Dimension(200, 30));
         tabbedPane.setTabComponentAt(0, pollingLabel); 
    }
    public MainFrame(final SupervisedLearning sl, final Polling polling){
        setName("Typ arytmii");
        this.polling= polling;
        this.learning = sl;
        setTitle("Rozpoznawanie typów arytmii serca");
        setMinimumSize(new Dimension(600, 300));
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);        
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        SetPositionTabbelPane();
        AddPollingPanel();
        AddLearningPanel();
        getContentPane().add(tabbedPane);
                       
///
//        btnStart.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent e) {
//                sl.run(path);
//			}
//		});
        pack();
        setVisible(true);
    }
}
