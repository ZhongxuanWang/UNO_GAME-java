import java.util.ArrayList;

public class Player {
	private ArrayList<Card> c;
	private String name;
	private double score;

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
		for (int i = 0; i < c.size(); i ++) {
			System.out.println("" + ( i + 1 ) + " - - - " + c.get(i));
		}
	}

	public int getCardSize() {
		return c.size();
	}

	public ArrayList<Card> getC() {
		return c;
	}

	public Card play(Card c) {

		int ordinary_card_value = 0;
		Card card_to_return = new Card();

		for (Card cd : this.c) {

			// If it's a WildFour card
			if (cd instanceof Wild || cd instanceof WildFour) {
				return cd;
			}

			// If it's an Action card
			if (cd instanceof Action && c instanceof Action && ((Color) cd).getIntColor() == ((Color) c).getIntColor() &&
					((Action) cd).getIntAction() == ((Action) c).getIntAction()) {
				return cd;
			}

			// If it's an ordinary card
			if ( cd instanceof Color && ! ( cd instanceof Action ) && c instanceof Color && ! ( c instanceof Action ) &&
					cd.getValue() == c.getValue() && ((Color) cd).getIntColor() == ((Color) c).getIntColor() ) {
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

	public boolean done() {
		return c.size() == 0;
	}

	public boolean checkwin() {
		return done() && score >= 500;
	}
}