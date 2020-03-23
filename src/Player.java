import java.util.ArrayList;

public class Player {
	private ArrayList<Card> c;
	private String name;
	private int score; // 100 max

	public Player(String name) {
		this.name = name;
		this.score = 0;
		c = new ArrayList();
	}

	public void add(Card card) {
		c.add(card);
	}

	public String getName() {
		return name;
	}

	public void displayCards() {
		for (int i = 0; i < c.size(); i++) {
			System.out.println("" + ( i + 1 ) + " - - - " + c.get(i));
		}
	}

	public int getCardSize() {
		return c.size();
	}

	public ArrayList<Card> getC() {
		return c;
	}

	public void setScore(int newScore) {
		score += newScore;
	}

	public int getScore() {
		return score;
	}

	/**
	 * If you want to make the strategy perfect to play, it's kinda hard. But you got to try.
	 * The goal is to minimize the amount of points you have, but meanwhile you need to preserve some special cards in case
	 * @param c
	 * @return
	 */
	public Card play(Card c) {


		// initialize the variables
		Card card_to_return = new Card();
		int index_of_card;

		// Check wild / WildFour card
		index_of_card = this.c.indexOf(new WildFour());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			checkUno();
			return card_to_return;
		}

		index_of_card = this.c.indexOf(new Wild());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			checkUno();
			return card_to_return;
		}

		// Check Action card

		for (int i = 0; i < this.c.size(); i ++) {
			Card cd = this.c.get(i);
			if (cd instanceof Action && ((Action) cd).getIntColor() == ((Color)c).getIntColor()) {
				this.c.remove(i);
				checkUno();
				return cd;
			}
		}

//		if (c instanceof Color) {
			// Check ordinary card
			int ordinary_card_value = -1;
			for (Card cd : this.c) {
				if (cd instanceof Color && cd.getValue() == c.getValue() &&
						((Color) cd).getIntColor() == ((Color) c).getIntColor()) {
					// Pick up the best card choice to return
					if (cd.getValue() >= ordinary_card_value) {
						card_to_return = cd;
						ordinary_card_value = cd.getValue();
					}
				}
			}
			if (ordinary_card_value != -1) {
				checkUno();
				return card_to_return;
			}
//		}
		return null;
	}

	/**
	 * Check if the card pile only remains 2 cards. (the conditions have already been met.)
	 * @return
	 */
	public void checkUno() {
		if (c.size() == 2) {
			System.out.println("UNO!");
		}
	}

	public boolean done() {
		return c.size() == 0;
	}
}