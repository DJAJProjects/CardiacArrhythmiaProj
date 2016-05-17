package View;

import Logic.SupervisedLearning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame{
    public final JFrame frame = this;
    private JButton btnStart;
    private JMenu menu;
    private JMenuBar menuBar;
    private String path;
    public MainFrame(final SupervisedLearning sl){

        setName("Typ arytmii");
        setMinimumSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);
        
        menuBar = new JMenuBar();
        springLayout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, menuBar, 0, SpringLayout.EAST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, menuBar, 30, SpringLayout.NORTH, getContentPane());
        getContentPane().add(menuBar);
        menu = new JMenu("Wybierz plik");
        menuBar.add(menu);
        
        btnStart = new JButton("Start");
        btnStart.setEnabled(false);
        springLayout.putConstraint(SpringLayout.NORTH, btnStart, 18, SpringLayout.SOUTH, menuBar);
        springLayout.putConstraint(SpringLayout.WEST, btnStart, 10, SpringLayout.WEST, getContentPane());
        getContentPane().add(btnStart);
        
        menu.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
                FileDialog fd =new FileDialog(frame,"Wczytaj",FileDialog.LOAD);
                fd.setVisible(true);
                path  =fd.getDirectory() + fd.getFile();
                btnStart.setEnabled(true);
                
//                String katalog = fd.getDirectory();
//                String plik = fd.getFile();
//                System.out.println("Wybrano plik: " + plik);
//                System.out.println("w katalogu: "+ katalog);
//                System.out.println("Ścieżka: "+ katalog + plik);
            }
        });
        
        btnStart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
                sl.run(path);
			}
		});
        setVisible(true);
    }
}
