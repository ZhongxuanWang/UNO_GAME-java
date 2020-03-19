import java.util.ArrayList;

public class Player {
	ArrayList<Card> c;
	String name;
	double score;

	public Player(String name) {
		this.name = name;
		this.score = 0;
		c = new ArrayList();
	}

	public void add() {

	}
	public void play() {

	}
	public boolean done() {
		return c.size() == 0;
	}
}