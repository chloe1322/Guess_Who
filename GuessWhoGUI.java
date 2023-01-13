package guesswho;

/* 
 * Name: Chloe, Emma, Xin
 * Date: Jan 20, 2021
 * Teacher: Mrs. Andrighetti
 * Description: The GuessWhoGui class for the program that is the game "Guess Who" 
 */

import javax.swing.*;

import guesswho.Character;
import guesswho.ComputerPlayer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.FileWriter;
import java.io.*;

public class GuessWhoGUI extends JFrame implements ActionListener{
	
	private ImageIcon imagesIcons[];
	private JButton imagesButtons[];

	private JComboBox chooseCharacter;
	private String[] characterNames; 
	private JLabel chosenCharacterLabel;
	
	private JLabel header;
	private JLabel menu;
	private JLabel instruction;
	private JTextField userInput;
	
	private JPanel gameBoard;
	private JPanel gamePlay;
	private JPanel chosenCharacter;
	private JPanel actions;
	
	private GridLayout gameBoardLayout;
	private GridLayout gamePlayLayout;
	private BoxLayout chosenCharacterLayout;
	private BoxLayout actionsLayout;
	private GridLayout actionButtonsLayout;
	private GridLayout frameLayout;
	
	
	private ArrayList<ArrayList<String>> listOfTraits;
	private ArrayList <String> genderOptions;
	private ArrayList <String> hairColourOptions;
	private ArrayList <String> sportOptions;
	private ArrayList <String> outfitColourOptions;
	private ArrayList <String> hatTypeOptions;
	
	private int movesCounter;
	private String roundResult;
	
	private ComputerPlayer computer1;
	private Character computerCharacterChosen;
	private ArrayList <Character> listOfCharacters;
	
	public GuessWhoGUI() {
		
		//initializing the game states
		movesCounter = 0;
		roundResult = "";
		
		//computer player
		computerCharacterChosen = new Character();
		listOfCharacters = new ArrayList<Character>();
		listOfTraits = new ArrayList<ArrayList<String>>();
		computer1 = new ComputerPlayer(computerCharacterChosen,listOfCharacters,listOfTraits);

		computer1.readCharacterFile();
		computer1.addQuestionOptions();
		computer1.pickCharacter();
		
		setTitle("Guess Who?");
		setSize(1920,1080);
		
		// setting layout of the frame to be in 1 row 2 columns
		frameLayout = new GridLayout(1,2);
		setLayout(frameLayout);
		
		// game board panel for displaying all the character images / game board
		gameBoard = new JPanel();
		gameBoardLayout = new GridLayout(4,6);
		gameBoard.setLayout(gameBoardLayout);
		
		
		// image icons and buttons used to display the images of each character
		imagesIcons = new ImageIcon[24];
		imagesButtons = new JButton[24];
		
		for (int i=0; i<imagesIcons.length; i++) {
			imagesIcons[i] = new ImageIcon(new ImageIcon(i + ".png").getImage().getScaledInstance(117, 157, Image.SCALE_DEFAULT)); // reads in and resizes all the images files of the characters
			imagesButtons[i] = new JButton(imagesIcons[i]); // stores the ImageIcon into a JButton
			imagesButtons[i].addActionListener(this); // applies ActionListener to all the image buttons
			gameBoard.add(imagesButtons[i]); // adds each button to the game board panel
		}

		// game play panel for actions to be performed during the game
		gamePlay = new JPanel();
		gamePlayLayout = new GridLayout(2,1);
		gamePlay.setLayout(gamePlayLayout);
		
		// chosen character panel for allowing the player to choose the character the computer needs to guess, and displays that picture for the user to view the traits
		chosenCharacter = new JPanel();
		chosenCharacterLayout = new BoxLayout(chosenCharacter,BoxLayout.Y_AXIS);
		chosenCharacter.setLayout(chosenCharacterLayout);
		
		characterNames = new String[25];
	
		
		// stores all the character names in an array for the user to choose
		characterNames[0] = "Please choose your character";
		for (int i=0; i<listOfCharacters.size(); i++) {
			characterNames[i+1] = listOfCharacters.get(i).getCharacterName();
		}
		
		// creates a combo box using the name array 
		chooseCharacter = new JComboBox(characterNames);
		chooseCharacter.setMaximumSize( chooseCharacter.getPreferredSize());
		chooseCharacter.addActionListener(this);
		//using JLabel to display the character's image as the user chooses their name
		chosenCharacterLabel = new JLabel();
		
		// adds the components into the chosen character panel
		chosenCharacter.add(chooseCharacter);
		chosenCharacter.add(chosenCharacterLabel);
		
		// actions panel for the user to interact with the computer and play the game
		actions = new JPanel(); 
		actionsLayout = new BoxLayout(actions, BoxLayout.Y_AXIS);
		actions.setLayout(actionsLayout);
		
		// header, menu, and instructions for user to understand what to do during the game
		header = new JLabel("Question Menu");
		menu = new JLabel("<html>1. Gender<br/>" + 
						  "2. Hair colour<br/>" +
						  "3. Sport<br/>" +
						  "4. Outifit colour<br/>" +
						  "5. Hat type<br/>" +
						  "6. Final guess: name<br/>" +
						  "7. Exit to game home page<html>");
		
		instruction = new JLabel("<html><br/>Please enter your choice: 1 - 7<html>");
		userInput = new JTextField(); // allows user to input in a text field
		userInput.addActionListener(this); 
		
		// adds components into the actions panel
		actions.add(header);
		actions.add(menu);
		actions.add(instruction);
		actions.add(userInput);
		
		gamePlay.add(actions);  // adds actions panel to gamePlay panel
		gamePlay.add(chosenCharacter); // adds chosenCharacter panel to gamePlay panel
		
		add(gameBoard); // adds gameBoard panel to the frame
		add(gamePlay); // adds gamePlay panel to the frame
		
		
		setVisible(true);
	}
	public void actionPerformed(ActionEvent event) {
		Object eventObject = event.getSource();
		
		// displays the image of the character according to user's choice
		if(event.getSource() == chooseCharacter&&chooseCharacter.getSelectedIndex()!=0) {
			
			chosenCharacterLabel.setIcon(imagesIcons[chooseCharacter.getSelectedIndex()-1]);
			
		}
		
		askAboutTrait(); // calls askAboutTrait method
		askAboutSpecificTrait(); // calls askAboutSpecificTrait method
		
		// for the user to click on the character buttons and eliminate characters 
		for (int i=0; i<imagesButtons.length; i++) {
			if (eventObject.equals(imagesButtons[i])) {
				imagesButtons[i].setIcon(null); // sets the ImageIcon on the JButtons to be null so that nothing would be displayed 
			}
		}
		
		// changes to the computer's turn to ask after the user's turn is finished
		computersTurn();
		
		
		// exits the user to main menu/home page after the user chooses 7 from the question menu/enters exit after they win or lose
		if (userInput.getText().equalsIgnoreCase("7")||userInput.getText().equalsIgnoreCase("exit")) {
			writeGameState();
			GuessWhoHomePage guessWhoMainFrame = new GuessWhoHomePage();
			setVisible(false);
		}
		
		
	}
	
	// allows the user to ask about a general trait
	public void askAboutTrait() {
		
		// allows the user to ask about gender
		if (userInput.getText().equals("1")) {
			header.setText("Enter the gender you want to ask about: ");
			menu.setText("<html>male<br/>" +
						 "female<html>");
			instruction.setText("<html><br/>Please enter your choice from the list above<br/>" + 
					"if the spelling is incorrect, the program will not react till spelt correctly<html>");
			userInput.setText("");
			

			
		}
		
		// allows the user to ask about hair colour
		else if (userInput.getText().equals("2")) {
			header.setText("Enter the hair colour you want to ask about: ");
			menu.setText("<html>blonde<br/>" +
						 "navy-blue<br/>" +
						 "brown<br/>" +
						 "black<br/>" +
						 "ginger<html>");
			instruction.setText("<html><br/>Please enter your choice from the list above<br/>" + 
					"if the spelling is incorrect, the program will not react till spelt correctly<html>");
			userInput.setText("");
			

		}
		
		// allows the user to ask about the sport the character plays
		else if (userInput.getText().equals("3")) {
			header.setText("Enter the sport you want to ask about: ");
			menu.setText("<html>basketball<br/>" +
						 "hockey<br/>" +
						 "skiing<br/>" +
						 "soccer<br/>" +
						 "horseback-riding<br/>" +
						 "tennis<br/>" +
						 "swimming<br/>" +
						 "golf<br/>" +
						 "volleyball<html>");
			instruction.setText("<html><br/>Please enter your choice from the list above<br/>" + 
					"if the spelling is incorrect, the program will not react till spelt correctly<html>");
			userInput.setText("");
			
		}
		
		// allows the user to ask about outfit colour
		else if (userInput.getText().equals("4")) {
			header.setText("Enter the outfit colour you want to ask about: ");
			menu.setText("<html>green<br/>" +
						 "blue<br/>" +
						 "yellow<br/>" +
						 "red<br/>" +
						 "white<html>");
			instruction.setText("<html><br/>Please enter your choice from the list above<br/>" + 
					"if the spelling is incorrect, the program will not react till spelt correctly<html>");
			userInput.setText("");
		}
		
		// allows the user to ask about hat type
		else if (userInput.getText().equals("5")) {
			header.setText("Enter the hat type you want to ask about: ");
			menu.setText("<html>nothing<br/>" +
						 "helmet<br/>" +
						 "headband<br/>" +
						 "goggles<br/>" +
						 "cap<br/>" +
						 "beanie<html>");
			instruction.setText("<html><br/>Please enter your choice from the list above<br/>" + 
								"if the spelling is incorrect, the program will not react till spelt correctly<html>");
			userInput.setText("");
		}
		
		// allows the user to take the final guess and guess the name of the character the computer chose
		else if (userInput.getText().equals("6")) {
			
			header.setText("FINAL GUESS!");
			menu.setText("You can only guess the name once, if you get it, you win, if not, you lose");
			instruction.setText("Enter the name of the character you're guessing for, make sure it's spelt correctly: ");
			userInput.setText("");
			
		}
		
		
	}
	
	
	public void askAboutSpecificTrait() {
		addQuestionOptions ();
		
		// gender options
		for(int i = 1; i<genderOptions.size();i++) {
			if (userInput.getText().equalsIgnoreCase(genderOptions.get(i))) {
	
				header.setText("The question you asked: Is this character "+genderOptions.get(i)+"?");
				menu.setText("The computer's answer is: "+computer1.determineYesNo(1, userInput.getText()));
				instruction.setText("<html><br/>Please click on all the characters your want to eliminate," +
									"after you're done, please enter 'done'<html>");
				userInput.setText(""); 
				
				
				movesCounter++; // adds 1 to the moves counter
				
			}
		
		}
		// hair colour options
		for(int i = 1; i<hairColourOptions.size();i++) {
			if (userInput.getText().equalsIgnoreCase(hairColourOptions.get(i))) {
				
				header.setText("The question you asked: Is this character's hair colour "+hairColourOptions.get(i)+"?");
				menu.setText("The computer's answer is: "+computer1.determineYesNo(2, userInput.getText()));
				instruction.setText("<html><br/>Please click on all the characters your want to eliminate," +
									"after you're done, please enter 'done'<html>");
				userInput.setText(""); 
				
				movesCounter++; // adds 1 to the moves counter
			}
		
		}
		
		// sport options 
		for(int i = 1; i<sportOptions.size();i++) {
			if (userInput.getText().equalsIgnoreCase(sportOptions.get(i))) {
				
				header.setText("The question you asked: Is this character's sport "+sportOptions.get(i)+"?");
				menu.setText("The computer's answer is: "+computer1.determineYesNo(3, userInput.getText()));
				instruction.setText("<html><br/>Please click on all the characters your want to eliminate," +
									"after you're done, please enter 'done'<html>");
				userInput.setText(""); 
				
				movesCounter++; // adds 1 to the moves counter
			}
		
		}
		
		// outfit colour options 
		for(int i = 1; i<outfitColourOptions.size();i++) {
			if (userInput.getText().equalsIgnoreCase(outfitColourOptions.get(i))) {
				 
				header.setText("The question you asked: Is this character's outfit colour "+outfitColourOptions.get(i)+"?");
				menu.setText("The computer's answer is: "+computer1.determineYesNo(4, userInput.getText()));
				instruction.setText("<html><br/>Please click on all the characters your want to eliminate," +
									"after you're done, please enter 'done'<html>");
				userInput.setText("");
				
				movesCounter++; // adds 1 to the moves counter
			}
		
		}
		
		// hat type options 
		for(int i = 1; i<hatTypeOptions.size();i++) {
			if (userInput.getText().equalsIgnoreCase(hatTypeOptions.get(i))) {
				
				if (userInput.getText().equalsIgnoreCase("nothing") || userInput.getText().equalsIgnoreCase("goggles")) { // nothing on their head or goggles
					header.setText("The question you asked: Is this character wearing "+hatTypeOptions.get(i)+" on their head?");
				}
				else {
					header.setText("The question you asked: Is this character wearing a "+hatTypeOptions.get(i)+" on their head?");
				}
				menu.setText("The computer's answer is: "+computer1.determineYesNo(5, userInput.getText()));
				instruction.setText("<html><br/>Please click on all the characters your want to eliminate," +
									"after you're done, please enter 'done'<html>");
				userInput.setText(""); 
				
				movesCounter++; // adds 1 to the moves counter
			}
		
		}
		
		// final guess for user to guess the name of the character
		for (int i = 1; i<characterNames.length; i++) {
			if (userInput.getText().equalsIgnoreCase(characterNames[i])) {
				header.setText("The question you asked: Is the character's name "+characterNames[i]+"?");
				
				if (computer1.characterDetermine(userInput.getText())) {
					menu.setText("Congratulations! You guessed the character!");
					roundResult = "won";
				}
				else {
					menu.setText("Sorry, you lost the game, the character was " + computer1.getCharacterChosen().getCharacterName());
					roundResult = "lost";
				}
				
				instruction.setText("<html><br/>The total number of moves you used: " + movesCounter+
									", after you're done reading this message, please enter 'exit' to exit to main menu<html>");
				userInput.setText(""); 
				
			}
		}
		
		
	}
	
	// allows the computer to ask questions and allows the player to answer yes or no/whether or not the computer's guess is correct
	public void computersTurn() {
		
		// allows computer to ask the question and prompts the user to answer computer's question
		if (userInput.getText().equalsIgnoreCase("done")) {

			header.setText("The Computer's Turn");
			
			String question = computer1.randomlyPickQuestion();
			menu.setText("The computer asked: " + question);
			
			
			boolean check = false; // check variable to see if the computer has guessed the character
			
			for(int i = 0; i<characterNames.length; i++) {
				if(question.equalsIgnoreCase("Is your character name : " + characterNames[i])) {
					instruction.setText("<html><br/>Please enter correct or incorrect<html>");
					userInput.setText("");
					check = true; // sets check to true if computer is trying to guess for the character
					break;
				}
			
			}
			
			// continues the game if the computer did not try to guess the character
			if (check == false) {
				instruction.setText("<html><br/>Please enter yes or no<html>");
				userInput.setText("");
			}
			


		}
		
		// directs back to main question menu after the user answers the computer's question
		if (userInput.getText().equalsIgnoreCase("yes")||userInput.getText().equalsIgnoreCase("no")) {
			
			computer1.eliminate(userInput.getText());
			computer1.removeTrait(userInput.getText());
			
			header.setText("Question Menu");
			menu.setText("<html>1. Gender<br/>" + 
							  "2. Hair colour<br/>" +
							  "3. Sport<br/>" +
							  "4. Outifit colour<br/>" +
							  "5. Hat type<br/>" +
							  "6. Final guess: name<br/>" +
							  "7. Exit to game home page<html>");
			
			instruction.setText("<html><br/>Please enter your choice: 1 - 7<html>");
			userInput.setText("");
		}
		
		// if the computer guesses correctly first, the user loses
		if (userInput.getText().equalsIgnoreCase("correct")) {
			menu.setText("Sorry! The computer guessed the character first, you lost the game!");
			roundResult = "lost";
			
			instruction.setText("<html><br/>The total number of moves you used: " + movesCounter+
					", after you're done reading this message, please enter 'exit' to exit to main menu<html>");
			userInput.setText("");
		}
		
		// if the computer guesses the wrong character, the user wins
		if (userInput.getText().equalsIgnoreCase("incorrect")) {
			menu.setText("Congrats! The computer guessed the wrong character, you won the game!");
			roundResult = "won";
			
			instruction.setText("<html><br/>The total number of moves you used: " + movesCounter+
					", after you're done reading this message, please enter 'exit' to exit to main menu<html>");
			userInput.setText("");
		}
		
		
		
	}
	
	
	// reads attributes from a file into the array for the computer to ask questions
	public ArrayList<ArrayList<String>> addQuestionOptions () { 
		try { 
			File myFile = new File ("questionOptions.txt");
			Scanner fileScan = new Scanner (myFile);
			listOfTraits = new ArrayList<ArrayList<String>> ();

			//gender options
			genderOptions = new ArrayList <String> ();
			genderOptions.add("gender"); // index 0 specifies what attribute type it is 
			genderOptions.add("male");
			genderOptions.add("female");
			listOfTraits.add(genderOptions);

			// hair colour options
			hairColourOptions = new ArrayList <String> ();
			for (int i = 0; i <= 5; i++) {
				hairColourOptions.add(fileScan.next());
			}
			listOfTraits.add(hairColourOptions);

			// sport options 
			sportOptions = new ArrayList <String> ();
			for (int i = 0; i <= 9; i++) { 
				sportOptions.add(fileScan.next());
			}
			listOfTraits.add(sportOptions);

			// outfit colour options 
			outfitColourOptions = new ArrayList <String> ();
			for (int i = 0; i <= 5; i++) { 
				outfitColourOptions.add(fileScan.next());
			}
			listOfTraits.add(outfitColourOptions);

			// hat type options 
			hatTypeOptions = new ArrayList <String> ();
			for (int i = 0; i <= 6; i++) { 
				hatTypeOptions.add(fileScan.next());
			}
			listOfTraits.add(hatTypeOptions);
			return (listOfTraits);


		} catch (Exception e) { 
			System.out.println("something went wrong reading the file");
			(e).printStackTrace();
			return (listOfTraits);
		}
	}
	
	public void writeGameState(){
		//write the file
		try {  
			//open the file
			FileWriter writer = new FileWriter("gameHistory.txt", true);  
			writer.write(String.valueOf(movesCounter)); // writes the move counter
			writer.write(" "); // writes a space
			writer.write(roundResult);// writes the result
			writer.write("\n"); // writes on a new line
			writer.close();  
		} 
	    catch (IOException e) {  
			e.printStackTrace();  
	    }
		      
	}
}