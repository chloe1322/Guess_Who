package guesswho;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Rule extends JFrame implements ActionListener {
	
		JLabel gameRule;
		JButton backButton;
		JPanel labelPanel = new JPanel();
		JPanel rulePanel = new JPanel();
		
		GridLayout frameLayout;
		
		public Rule() {
			setTitle("Rules");
			setSize(1920,1080);
			
			//whole frame layout
			frameLayout = new GridLayout(2,1);
			setLayout(frameLayout);
			
			// adds in all the rules
			gameRule = new JLabel("<html>Welcome to Guess Who! If you’re unsure how to play, here is a list of the rules!<br/> "
					+ "1) The objective of the game is to guess the computer’s character before they are able to guess yours!<br/>"
					+ "2) When you press the “new game” button, it will take you to a the game page where you will begin the game! <br/>"
					+ "3) After reviewing the characters, using the drop down menu, select your character <br/>"
					+ "4) Now you can ask the computer questions to narrow down your choices! <br/>"
					+ "5) To ask a question, enter a number from the question menu and type in your choice <br/>"
					+ "6) After receiving your answer, you can click on the character images to eliminate them <br/>"
					+ "7) Feel free to repeat numbers from the question menu! <br/>"
					+ "8) The computer will then ask you questions about your character, so answer honestly!  <br/>"
					+ "9) You will take turns with the computer and ask each other questions until you are ready to guess  <br/>"
					+ "10) When you are sure, make your final guess! But be careful because you only get one guess! <br/>");
			
			// adds the buttons and panels
			backButton = new JButton("back"); 
			backButton.addActionListener(this);
				
			labelPanel.add(gameRule);
			rulePanel.add(backButton);
			
			add(labelPanel);
			add(rulePanel);
			setVisible(true);
			
		}
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			
			if (command.equals("back")) {
				GuessWhoHomePage homeFrame = new GuessWhoHomePage();
				setVisible(false);			
			}					
		}
	}
