package guesswho;
/* 
 * Name: Chloe, Emma, Xin
 * Date: Jan 20, 2021
 * Teacher: Mrs. Andrighetti
 * Description: The character class for the program that is the game "Guess Who" 
 */

public class Character {
	// attributes
	private String characterName;
	private String gender;
	private String hairColour;
	private String sport;
	private String outfitColour;
	private String hatType;
	
	// basic constructor
	public Character () { 
	}
	
	// constructor
	public Character (String characterName, String gender, String hairColour, String sport, String outfitColour, String hatType) {
		this.characterName = characterName;
		this.gender = gender;
		this.hairColour = hairColour;
		this.sport = sport;
		this.outfitColour = outfitColour;
		this.hatType = hatType;
	}
	
	// getter methods
	public String getCharacterName () { return characterName; }
	public String getGender () { return gender; }
	public String getHairColour () { return hairColour; }
	public String getSport () { return sport; }
	public String getOutfitColour () { return outfitColour; }
	public String getHatType () { return hatType; }

}
