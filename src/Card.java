
public class Card {
	private int value;

	public Card() {
		value = -1;
	}

	public Card(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		if (value == -1) {
			return "It's a function card.";
		}
		return "Card Number: " + value;
	}

	public static CardTypes getCardType(Card c) {
		if (c instanceof Wild) {
			return CardTypes.WILD;
		} else if (c instanceof WildFour) {
			return CardTypes.WILDFOUR;
		} else if (c instanceof Action) {
			return CardTypes.ACTION;
		} else if (c instanceof Color) {
			return CardTypes.REGULAR_CARD;
		} else {
			return CardTypes.NOTYPE;
		}
	}
}