package guesswho;

/* 
 * Name: Chloe, Emma, Xin
 * Date: Jan 25, 2021
 * Teacher: Mrs. Andrighetti
 * Description: The GameHistory class for the program that is the game "Guess Who" 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.*;

public class GameHistory extends JFrame implements ActionListener{
	
	
	JLabel history[];
	JButton backButton;
	JPanel labelPanel = new JPanel();
	JPanel historyPanel = new JPanel();
	
	GridLayout frameLayout;
	GridLayout labelLayout;
	
	//protected int movesCounter;
	protected String roundResult;
	protected int roundCounter;
	protected ArrayList<String> movesCounter;
	protected ArrayList<String> result;
	
	public GameHistory() {
		setTitle("gameHistory");//set title and size
		setSize(1920,1080);
		
		//whole frame layout
		frameLayout = new GridLayout(2,1);//set layout 
		setLayout(frameLayout);
		movesCounter = new ArrayList<String>();// store the move times and game result
		result =  new ArrayList<String>();
		readGameState();// call the method
		labelLayout = new GridLayout(roundCounter,1);// create the layout 
		labelPanel.setLayout(labelLayout);
		history = new JLabel[roundCounter];
		for(int i=0;i<roundCounter;i++) {// create an arraylist
			history[i] = new JLabel("Game #"+(i+1)+": "+"number of moves - "+movesCounter.get(i)+", result - "+result.get(i));
		}
		backButton = new JButton("back");
		backButton.addActionListener(this);
		
		for(int i=0;i<roundCounter;i++) {
		labelPanel.add(history[i]);
		}// add all the variable into the panel
		
		historyPanel.add(backButton);		
		add(labelPanel);
		add(historyPanel);
		setVisible(true);
		
	}
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("back")) {// action listener for back button
			GuessWhoHomePage homeFrame = new GuessWhoHomePage();
			setVisible(false);			
		}					
	}
	
	public void readGameState() {
		//read the file
		try {
			File myFile = new File("gameHistory.txt");
			Scanner fileScan = new Scanner(myFile);
			while (fileScan.hasNext()) { // scans each attribute from the file
				roundCounter++;
				String movesCounterfile =  fileScan.next();
				String fileResult = fileScan.next();
				movesCounter.add(movesCounterfile);
				result.add(fileResult);
				//System.out.println("Game #"+roundCounter+": "+"number of moves - "+movesCounter+" result "+fileResult);
			}
			fileScan.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong in read gameHistory file");
		}
	}
}// class end
