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

	public void add(Card c) {
		this.c.add(c);
	}

	public Card play(Card c) {
		for (Card cd : this.c) {
			if (cd instanceof Color && ! ( cd instanceof Action ) && cd.getValue() == c.getValue() &&
					cd.getIntColor() == c.getIntColor()) {
				return c;
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