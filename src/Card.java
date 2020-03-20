public class Card {
	private int value;

	public Card() {value = -1;}
	public Card(int v) {value = v;}

	public int getValue() {return value;}

	public int getIntColor() {return -1;}
	
	public String toString() {
		if (value == -1) {return "It's a function card.";}
		return "Card Number: " + value;
	}
}