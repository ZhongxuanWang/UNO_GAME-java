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
		System.out.println("\nPlayer " + name + "'s card START- - - - - - - - - - - - - - -");
		for (int i = 0; i < c.size(); i++) {
			System.out.println("" + ( i + 1 ) + " - - - " + c.get(i));
		}
		System.out.println("Player " + name + "'s card END  - - - - - - - - - - - - - - -");
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

		int color_allowed = -1;
		if (c instanceof Wild || c instanceof WildFour) {
			color_allowed = 0;
		} else if (c instanceof Action) {
			color_allowed = ((Action) c).getIntColor();
		} else if (c instanceof Color) {
			color_allowed = ((Color) c).getIntColor();
		}

		// initialize the variables
		Card card_to_return = new Card();
		int index_of_card;

		// Check wild / WildFour card
		index_of_card = getCardIndex(new WildFour());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
			checkUno();
			return card_to_return;
		}

		index_of_card = getCardIndex(new Wild());
		if (index_of_card != -1) {
			card_to_return = this.c.get(index_of_card);
			this.c.remove(index_of_card);
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
		// TODO strengthen the strategy by checking the colors of the car4ds in the hand
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
			checkUno();
			Card cd = this.c.get(index_of_placement);
			this.c.remove(index_of_placement);
			return cd;
		}
		return null;
	}

	/**
	 * Check if the card pile only remains 2 cards. (the conditions have already been met.)
	 */
	public void checkUno() {
		if (c.size() == 2) {
			System.out.println("Player " + name + " shouted out: UNO!");
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

	public int chooseColor() {
		// TODO choose more wisely
		return (int)(Math.random() * 4) + 1;
	}

	/**
	 * Calculate the score of the individual player.
	 * @return
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
}