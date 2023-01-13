package guesswho;

import java.io.*;
import java.util.*;
import guesswho.Character;

/* 
 * Name: Chloe, Emma, Xin
 * Date: Jan 20, 2021
 * Teacher: Mrs. Andrighetti
 * Description: The computer player class for the program that is the game "Guess Who" 
 */

public class ComputerPlayer {
	// attributes
	protected Character characterChosen;
	protected ArrayList<Character> listOfCharacters;
	private ArrayList<ArrayList<String>> listOfTraits;
	private ArrayList<String> genderOptions;
	private ArrayList<String> hairColourOptions;
	private ArrayList<String> sportOptions;
	private ArrayList<String> outfitColourOptions;
	private ArrayList<String> hatTypeOptions;
	private int randomGenNum;
	private int randomSpecificNum;

	// constructor
	public ComputerPlayer(Character characterChosen, ArrayList<Character> listOfCharacters,
			ArrayList<ArrayList<String>> listOfTraits) {
		this.characterChosen = characterChosen;
		this.listOfCharacters = listOfCharacters;
		this.listOfTraits = listOfTraits;
	}

	// accessor method to the characterChosen
	public Character getCharacterChosen() { return characterChosen;}
	
	// other methods
	// reads characters from the file
	public ArrayList<Character> readCharacterFile() {
		try {
			File myFile = new File("characters.txt");
			Scanner fileScan = new Scanner(myFile);
			while (fileScan.hasNextLine()) { // scans each attribute from the file
				String characterName = fileScan.next();
				String gender = fileScan.next();
				String hairColour = fileScan.next();
				String sport = fileScan.next();
				String outfitColour = fileScan.next();
				String hatType = fileScan.next();
				// adds it to the computer character array list
				listOfCharacters.add(new Character(characterName, gender, hairColour, sport, outfitColour, hatType));
			}
			fileScan.close();
			return (listOfCharacters);
		} catch (Exception e) {
			System.out.println("Something went wrong");
			return (listOfCharacters);
		}
	}

	// reads attributes from a file into the array for the computer to ask questions
	public ArrayList<ArrayList<String>> addQuestionOptions() {
		try {
			File myFile = new File("questionOptions.txt");
			Scanner fileScan = new Scanner(myFile);
			listOfTraits = new ArrayList<ArrayList<String>>();

			// gender options
			genderOptions = new ArrayList<String>();
			genderOptions.add("gender"); // index 0 specifies what attribute type it is
			genderOptions.add("male");
			// genderOptions.add("female");
			listOfTraits.add(genderOptions);

			// hair colour options
			hairColourOptions = new ArrayList<String>();
			for (int i = 0; i <= 5; i++) {
				hairColourOptions.add(fileScan.next());
			}
			listOfTraits.add(hairColourOptions);

			// sport options
			sportOptions = new ArrayList<String>();
			for (int i = 0; i <= 9; i++) {
				sportOptions.add(fileScan.next());
			}
			listOfTraits.add(sportOptions);

			// outfit colour options
			outfitColourOptions = new ArrayList<String>();
			for (int i = 0; i <= 5; i++) {
				outfitColourOptions.add(fileScan.next());
			}
			listOfTraits.add(outfitColourOptions);

			// hat type options
			hatTypeOptions = new ArrayList<String>();
			for (int i = 0; i <= 6; i++) {
				hatTypeOptions.add(fileScan.next());
			}
			listOfTraits.add(hatTypeOptions);
			fileScan.close();
			return (listOfTraits);
		} catch (Exception e) {
			System.out.println("something went wrong reading the file");
			(e).printStackTrace();
			return (listOfTraits);
		}
	}

	// method to pick character
	public void pickCharacter() {
		Random randomNumber = new Random();// randomly generate index
		int characterChoice = randomNumber.nextInt(24);// randomly generate a number between 0 and 24
		characterChosen = new Character(listOfCharacters.get(characterChoice).getCharacterName(),
				listOfCharacters.get(characterChoice).getGender(),
				listOfCharacters.get(characterChoice).getHairColour(), listOfCharacters.get(characterChoice).getSport(),
				listOfCharacters.get(characterChoice).getOutfitColour(),
				listOfCharacters.get(characterChoice).getHatType());
	}

	// randomly generates numbers to ask a question
	public String randomlyPickQuestion() {
		Random random = new Random();
		if (listOfCharacters.size() == 1) {// guess the name if there is only one character left
			return("Is your character name : " + listOfCharacters.get(0).getCharacterName());
		} else {
			do { // checks to make sure there are still specific options left for the attribute
				randomGenNum = random.nextInt(listOfTraits.size()) + 0; // for general options: sport, hat type, etc.
			} while (listOfTraits.get(randomGenNum).size() == 1);
			if (listOfCharacters.size() == 24) { 
				randomGenNum = 0; // smart AI - will always ask about the gender first
			}
			while (listOfCharacters.size() != 1) {
				randomSpecificNum = random.nextInt(listOfTraits.get(randomGenNum).size() - 1) + 1; // specific options: soccer, golf, etc.
				// outputs questions depending on the numbers generated
				if (listOfTraits.get(randomGenNum).get(0).equalsIgnoreCase("gender")) { // will ask about gender
					return "Is the character's gender " + listOfTraits.get(randomGenNum).get(randomSpecificNum) + "?";
				} else if (listOfTraits.get(randomGenNum).get(0).equalsIgnoreCase("hair")) { // will ask about hair colour
					return "Is the character's hair colour " + listOfTraits.get(randomGenNum).get(randomSpecificNum) + "?";
				} else if (listOfTraits.get(randomGenNum).get(0).equalsIgnoreCase("sport")) { // will ask about sport type
					return "Is the character's sport " + listOfTraits.get(randomGenNum).get(randomSpecificNum) + "?";
				} else if (listOfTraits.get(randomGenNum).get(0).equalsIgnoreCase("outfit")) { // will ask about outfit colour
					return "Is the character's outfit colour " + listOfTraits.get(randomGenNum).get(randomSpecificNum) + "?";
				} else if (listOfTraits.get(randomGenNum).get(0).equalsIgnoreCase("hat")) { // will ask about hat type
					if (randomSpecificNum == 1 || randomSpecificNum == 4) { // nothing on their head or goggles
						return "Is the character wearing " + listOfTraits.get(randomGenNum).get(randomSpecificNum) + " on their head?";
					} else {
						return "Is the character wearing a " + listOfTraits.get(randomGenNum).get(randomSpecificNum) + " on their head?";
					}
				} else {
					return "something went wrong in randomlyPickquestion method";
				}
				
			}
		}
		return "Something went wrong";
	}

	// eliminates the question/trait from the array if it has been asked
	public void removeTrait(String answer) {
		if (answer.equalsIgnoreCase("yes")) { // smart AI - if the answer is yes will remove all questions about that trait (eg. if hair colour is blue, will not ask about hair colour anymore
			listOfTraits.remove(randomGenNum);
		} else { // smart AI - if the answer is no will only remove that specific question (eg. if hair colour is not blue, will just remove the blue option)
			listOfTraits.get(randomGenNum).remove(randomSpecificNum);
		}
	}

	// give the player's guess a yes or no answer
	public boolean characterDetermine(String guessCharacterName) {
		// if their guess equals the name of the computer's chosen character
		if (guessCharacterName.equalsIgnoreCase(characterChosen.getCharacterName())) {
			return true;
		} else {
			return false;
		}
	}

	public String determineYesNo(int traitNumber, String trait) {
		if (traitNumber == 1) {// find the trait first, gender
			if (characterChosen.getGender().equalsIgnoreCase(trait)) { // if the character chosen has the trait asked about
				return "yes";
			} else {
				return "no";
			}
		} else if (traitNumber == 2) { // hair colour
			if (characterChosen.getHairColour().equalsIgnoreCase(trait)) {
				return "yes";
			} else {
				return "no";
			}
		} else if (traitNumber == 3) { // sport
			if (characterChosen.getSport().equalsIgnoreCase(trait)) {
				return "yes";
			} else {
				return "no";
			}
		} else if (traitNumber == 4) { // outfit colour
			if (characterChosen.getOutfitColour().equalsIgnoreCase(trait)) {
				return "yes";
			} else {
				return "no";
			}
		} else { // hat type
			if (characterChosen.getHatType().equalsIgnoreCase(trait)) {
				return "yes";
			} else {
				return "no";

			}
		}

	}

	// eliminates characters out of the computer's array to narrow down the options
	public void eliminate(String playerAnswer) {
		String trait = listOfTraits.get(randomGenNum).get(randomSpecificNum); // this is the trait the computer asked about (eg. basketball, soccer, etc)
		String traitType = listOfTraits.get(randomGenNum).get(0); // will get the attribute type (eg. hair, gender, etc.)

		if (traitType.equalsIgnoreCase("gender")) { // equals gender
			if (playerAnswer.equalsIgnoreCase("yes")) {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getGender().equalsIgnoreCase(trait)) {
						// not remove this index of element of listOfCharacter
					} else {
						listOfCharacters.remove(i);// remove this index of element of listOfCharacter
						i--; // sets i back down so that when the element is removed it will still check the following element
					}
				}

			}

			else { // doesn't equal gender
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getGender().equalsIgnoreCase(trait)) {
						listOfCharacters.remove(i); // remove this index of element of listOfCharacter
						i--;
					} else {
						// not remove this index of element of listOfCharacter
					}
				}
			}
		} else if (traitType.equalsIgnoreCase("hair")) {
			if (playerAnswer.equalsIgnoreCase("yes")) {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getHairColour().equalsIgnoreCase(trait)) {
						// not remove this index of element of listOfCharacter
					} else {
						listOfCharacters.remove(i);// remove this index of element of listOfCharacter
						i--;
					}
				}
			} else {// answer is no
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getHairColour().equalsIgnoreCase(trait)) {
						listOfCharacters.remove(i); // remove this index of element of listOfCharacter
						i--;
					} else {
						// not remove this index of element of listOfCharacter
					}
				}
			}

		} else if (traitType.equalsIgnoreCase("sport")) {
			if (playerAnswer.equalsIgnoreCase("yes")) {// answer is yes
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getSport().equalsIgnoreCase(trait)) {
						// not remove this index of element of listOfCharacter
					} else {
						listOfCharacters.remove(i);// remove this index of element of listOfCharacter
						i--;
					}
				}
			} else {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getSport().equalsIgnoreCase(trait)) {
						listOfCharacters.remove(i); // remove this index of element of listOfCharacter
						i--;
					} else {
						// not remove this index of element of listOfCharacter
					}
				}
			}
		} else if (traitType.equalsIgnoreCase("outfit")) {
			if (playerAnswer.equalsIgnoreCase("yes")) {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getOutfitColour().equalsIgnoreCase(trait)) {
						// not remove this index of element of listOfCharacter
					} else {
						listOfCharacters.remove(i); // remove this index of element of listOfCharacter
						i--;
					}
				}
			} else if (playerAnswer.equalsIgnoreCase("no")) {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getOutfitColour().equalsIgnoreCase(trait)) {
						listOfCharacters.remove(i); // remove this index of element of listOfCharacter
						i--;
					} else {
						// not remove this index of element of listOfCharacter
					}
				}

			}
		} else if (traitType.equalsIgnoreCase("hat")) {
			if (playerAnswer.equalsIgnoreCase("yes")) {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getHatType().equalsIgnoreCase(trait)) {
						// not remove this index of element of listOfCharacter
					} else {
						listOfCharacters.remove(i);// remove this index of element of listOfCharacter
						i--;
					}
				}
			} else {
				for (int i = 0; i < listOfCharacters.size(); i++) {// use for loop to determine if the trait is in the character list for each character
					if (listOfCharacters.get(i).getHatType().equalsIgnoreCase(trait)) {
						listOfCharacters.remove(i); // remove this index of element of listOfCharacter
						i--;
					} else {
						// not remove this index of element of listOfCharacter
					}
				} // end for loop
			} // end else
		} // end else
	}// method end
}// end class