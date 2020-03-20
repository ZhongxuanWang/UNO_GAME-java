public class Card {
	private int value;

	public Card() {value = -1;}
	public Card(int value) {this.value = value;}

	public int getValue() {return value;}
	
	public String toString() {
		if (value == -1) {return "It's a function card.";}
		return "Card Number: " + value;
	}
}