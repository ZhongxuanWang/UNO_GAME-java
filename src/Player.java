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
		for (Card cd : this.c) {

			// If it's an ordinary card , Action card , Wild card , WildFour card
			// Even it's hard for me to read the code....

			if ( (cd instanceof Color && ! ( cd instanceof Action ) && c instanceof Color && ! ( c instanceof Action ) &&
					cd.getValue() == c.getValue() && cd.getIntColor() == c.getIntColor()) ) {

			}

			// If it's an Action card,
			if((cd instanceof Action && c instanceof Action && cd.getIntColor() == c.getIntColor() &&
					((Action) cd).getIntAction() == ((Action) c).getIntAction())) {

			}
			if (cd instanceof Wild && c instanceof Wild || cd instanceof WildFour && c instanceof WildFour) {
				return cd;
			}
		}
		return null;
	}

	public boolean done() {
		return c.size() == 0;
	}

	public boolean checkwin() {
		return done() && score >= 500;
	}
}