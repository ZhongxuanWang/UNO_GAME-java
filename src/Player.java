import java.util.ArrayList;

public class Player {
	private ArrayList<Card> c;
	private String name;
	private int score; // 100 max

	// Customized variables
	private int times_of_winning = 0;

	public Player(String name) {
		this.name = name;
		this.score = 0;
		c = new ArrayList<>();
	}

	public void add(Card card) {
		c.add(card);
	}

	public String getName() {
		return name;
	}

	public void displayCards() {
		System.out.println("\nPlayer " + name + "'s card START- - - - - - - - - - - - - - -");
		for (int i = 0; i < c.size(); i++) {
			System.out.println("" + ( i + 1 ) + " - - - " + c.get(i));
		}
		System.out.println("Player " + name + "'s card END  - - - - - - - - - - - - - - -");
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

	public void winplus() {times_of_winning += 1;}

	public int getTimes_of_winning() {return times_of_winning;}

	/**
	 *
	 * Version 1.1
	 *
	 * If you want to make the strategy perfect to play, it's kinda hard. But you got to try.
	 * The goal is to minimize the amount of points you have, but meanwhile you need to preserve some special cards in case
	 * @param c first card in discarded pile
	 * @return card played
	 */
	public Card play(Card c) {

		int color_allowed = -1;
		if (c instanceof Wild) {
			color_allowed = ((Wild) c).color_chosen;
		} else if (c instanceof WildFour) {
			color_allowed = ((WildFour) c).color_chosen;
		} else if (c instanceof Action) {
			color_allowed = ((Action) c).getIntColor();
		} else if (c instanceof Color) {
			color_allowed = ((Color) c).getIntColor();
		}

		// initialize the variables
		Card card_to_return;
		int index_of_card;

		// Check wild / WildFour card
		index_of_card = getCardIndex(new WildFour());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			((WildFour) card_to_return).color_chosen = chooseColor();
			checkUno();
			return card_to_return;
		}

		index_of_card = getCardIndex(new Wild());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			((Wild) card_to_return).color_chosen = chooseColor();
			checkUno();
			return card_to_return;
		}

		// Check Action card
		for (int i = 0; i < this.c.size(); i ++) {
			Card cd = this.c.get(i);
			if (cd instanceof Action && (color_allowed == 0 || ((Color) cd).getIntColor() == color_allowed)) {
				this.c.remove(i);
				checkUno();
				return cd;
			}
		}

		// Check ordinary card
		int ordinary_card_value = -1;
		int index_of_placement = -1;
		for (int i = 0; i < this.c.size(); i ++) {
			Card cd = this.c.get(i);
			if (cd instanceof Color && cd.getValue() == c.getValue() ||
					(color_allowed == 0 || ((Color) cd).getIntColor() == color_allowed)) {
				// Pick up the best card choice to return
				if (cd.getValue() >= ordinary_card_value) {
					index_of_placement = i;
					ordinary_card_value = cd.getValue();
				}
			}
		}
		if (ordinary_card_value != -1) {
			Card cd = this.c.get(index_of_placement);
			this.c.remove(index_of_placement);
			checkUno();
			return cd;
		}
		return null;
	}

	/**
	 * Check if the card pile only remains 2 cards. (the conditions have already been met.)
	 */
	public void checkUno() {
		if (c.size() == 1) {
			System.out.println("Player: " + name + " shouted out: UNO!");
		}
	}

	public boolean done() {
		return c.size() == 0;
	}

	public int getCardIndex(Card card) {
		for (int i = 0; i < c.size(); i ++) {
			if (c.get(i).getClass() == card.getClass()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Choose the best color for Wild and WildFour card.
	 * @return integral representation of color.
	 */
	public int chooseColor() {
		int max_score = Integer.MIN_VALUE;
		int color_desired = 1;
		for (int i = 1; i <= 4; i ++) {
			int this_score = calcScore(i);
			if (this_score > max_score) {
				max_score = this_score;
				color_desired = i;
			}
		}
		return color_desired;
	}

	/**
	 * Calculate the score of the individual player.
	 * @return integral representation of color.
	 */
	public int calcScore() {
		int score = 0;
		if (this.c.size() == 0) return 0;
		for (Card cd : this.c) {
			if (cd instanceof Wild || cd instanceof WildFour) {
				score += 50;
			} else if (cd instanceof Action) {
				score += 20;
			} else if (cd instanceof Color) {
				score += cd.getValue();
			} else {
				score += 0;
			}
		}
		return score;
	}
	/**
	 * Calculate the score based on the color desired.
	 * @return integral representation of color.
	 */
	public int calcScore(int color_wanted) {
		int score = 0;
		if (this.c.size() == 0) return 0;
		for (Card cd : this.c) {
			if (cd instanceof Action && ((Color) cd).getIntColor() == color_wanted) {
				score += 20;
			} else if (cd instanceof Color && ((Color) cd).getIntColor() == color_wanted) {
				score += cd.getValue();
			}
		}
		return score;
	}
}