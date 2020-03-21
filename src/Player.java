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
		// Try sth new!
		for (int i = 0; i < c.size();) {
			System.out.println("" + ( ++i + 1 ) + " - - - " + c.get(i));
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

		// Check wild / wildfour card
		index_of_card = this.c.indexOf(new WildFour());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			return card_to_return;
		}

		// Check Action card
		index_of_card = this.c.indexOf( new Action(
				((Action) c).getIntAction(),
				((Action) c).getIntColor())
		);
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			return card_to_return;
		}

		// Check ordinary card
		int ordinary_card_value = 0;
		for (Card cd : this.c) {
			if ( cd instanceof Color && cd.getValue() == c.getValue() &&
					((Color) cd).getIntColor() == ((Color) c).getIntColor() ) {
				// Pick up the best card choice to return
				if (cd.getValue() >= ordinary_card_value) {
					card_to_return = cd;
					ordinary_card_value = cd.getValue();
				}
			}
		}




		// For processing ordinary card
		int ordinary_card_value = 0;
		Card card_to_return = new Card();

		if (c instanceof Wild || c instanceof WildFour) {

			// loop through every


			int index_of_Wild_WildFour_card = this.c.indexOf(new WildFour());
			if (index_of_Wild_WildFour_card != -1) {
				card_to_return = this.c.get(index_of_Wild_WildFour_card);
				this.c.remove(index_of_Wild_WildFour_card);
			}
		} else if (c instanceof Action) {
			int index_action_card = this.c.indexOf( new Action(
					((Action) c).getIntAction(),
					((Action) c).getIntColor())
			);
			if (index_action_card != -1) {

			}
		} else if (c instanceof Color) {

		} else {
			return null;
		}
			return cd;

		for (Card cd : this.c) {

			// If it's a WildFour card
			if (cd instanceof Wild || cd instanceof WildFour) {
				return cd;
				// If it's an Action card
			} else if (cd instanceof Action && c instanceof Action && ((Color) cd).getIntColor() ==
					((Color) c).getIntColor() && ((Action) cd).getIntAction() == ((Action) c).getIntAction()) {
				return cd;
				// If it's an ordinary card
			} else if ( cd instanceof Color && c instanceof Color && cd.getValue() == c.getValue() &&
					((Color) cd).getIntColor() == ((Color) c).getIntColor() ) {
				// Pick up the best card choice to return
				if (cd.getValue() >= ordinary_card_value) {
					card_to_return = cd;
					ordinary_card_value = cd.getValue();
				}
			}
		}
		// If the card is being changed
		if (card_to_return.getValue() != -1) return card_to_return;

		return null;
	}

	/**
	 * Check if the card pile only remains 2 cards. (the conditions have already been met.)
	 * @return
	 */
	public boolean checkUno() {
		return c.size() == 2;
	}

	public boolean done() {
		return c.size() == 0;
	}
}