package guesswho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class GuessWhoHomePage extends JFrame implements ActionListener{
	JButton newGame;
	JButton gameHistory;
	JButton instructions;
	JButton exitGame;	
	JLabel description;
	JButton backButton;
	JLabel imageLabel;
	ImageIcon homePageImage;
	
	JPanel imagePanel;
	JPanel buttonPanel;
	
	BoxLayout frameLayout; //set layout for whole frame
	GridLayout layoutPan2;// set grid layout for panel 1
	
	public GuessWhoHomePage() {
	
		setTitle("Guess Who?");//set title and size
		setSize(1920,1080);
		
		frameLayout = new BoxLayout(getContentPane(),BoxLayout.Y_AXIS);//set up the frame layout 
		setLayout(frameLayout);
		layoutPan2 = new GridLayout(4,1);//button panel layout
		imagePanel = new JPanel();
		imagePanel.setPreferredSize(new Dimension(1920,600));
		buttonPanel = new JPanel();
		
		//set game name label
		
		homePageImage= new ImageIcon("guessWhoHomePage.png");
		imageLabel = new JLabel();
		imageLabel.setIcon(homePageImage);
		imagePanel.add(imageLabel);
		// button for new game
		newGame = new JButton("new game");
		newGame.addActionListener(this);
		newGame.setForeground(Color.red);
		//button for history
		gameHistory = new JButton("game history");
		gameHistory.addActionListener(this);
		gameHistory.setForeground(Color.blue);// add color for the button 
		//button for how to play
		instructions = new JButton("how to play");
		instructions.addActionListener(this);
		// button for exit game
		instructions.setForeground(Color.magenta);
		exitGame = new JButton("exit game");
		exitGame.addActionListener(this);
		
		//add button and label in panel
		imagePanel.add(imageLabel);
		buttonPanel.add(newGame);
		buttonPanel.add(gameHistory);
		buttonPanel.add(instructions);
		buttonPanel.add(exitGame);
		//add panel to frame
		
		add(imagePanel);
		add(buttonPanel);
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("new game")) {
			GuessWhoGUI game  = new GuessWhoGUI();// call the game menu
			setVisible(false);// set visible false

		}
		else if (command.equals("game history")) {
		    GameHistory historyMenu = new GameHistory();// call the history frame
			setVisible(false);
		}
		else if (command.equals("how to play")) {
			Rule menu = new Rule();// call the rule menu
		    setVisible(false);
		}
		else if (command.equals("exit game")) {
			System.exit(0);// exit the game
		}				
	}
}