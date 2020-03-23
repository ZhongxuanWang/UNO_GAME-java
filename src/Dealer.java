import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Dealer {

	// There are 108 cards in the deck.
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	private ArrayList<Player> players;

	public Dealer() 
	{
	}

	public void initialize()
	{
		drawPile = new ArrayList();
		players = new ArrayList();

		// Add all number cards

		// Add 0 	
		for (int j = 1; j < 5; j ++) {
			drawPile.add(new Color(0,j));
		}
		// Add other numbers
		for (int k = 0; k < 2; k ++) {
			for (int i = 1; i < 10; i ++) {
				for (int j = 1; j < 5; j ++) {
					drawPile.add(new Color(i,j));
				}
			}
		}

		// Add all functioning cards
		for (int k = 0; k < 2; k ++) {
			for (int i = 1; i < 5; i ++) {
				for (int j = 1; j < 4; j ++) {
					drawPile.add(new Action(j,i));
				}
			}
		}
		// Add two special cards
		for (int i = 0; i < 4; i ++) {
			drawPile.add(new Wild());
			drawPile.add(new WildFour());
		}
		
	}
	
	public void shuffle()
	{
		// Shuffle 15 times
		for (int j = 0; j < 15; j ++) {
			Random rd = new Random();
			for (int i = 0; i < drawPile.size(); i ++) {
				int in = rd.nextInt(i+1);

				Card c = drawPile.get(i);
				drawPile.set(i, drawPile.get(in));
				drawPile.set(in, c);
			}
		}
		
	}

	public boolean play() {
		Scanner scr = new Scanner(System.in);
		outer_loop:
		while (true) {
			for (Player player : players) {
				Main.clear_screen();
				player.displayCards();
				if (discardPile.size() == 0) {
					player.play(new Wild());
				}
				if (drawPile.size() == 0) {
					break outer_loop;
				}
				player.play(discardPile.get(0));
				if (player.done()) {
					System.out.println("Wow! Player " + player.getName() + " has been running out of cards! Other " +
							"players card will be calculated to this lucky player!");
					player.setScore(score());
					if (player.getScore() > 500) {
						System.out.println("Wow! Also this player has won the game! Game will stop by now!");
						break outer_loop;
					}
					return true;
				}
				System.out.println("Player " + player.getName() + " finished!");
				Main.sleep(500);
			}
		}
		return false;
	}

	public void deal() {
		discardPile = new ArrayList();
		for (Player player : players) {
			for (int j = 0; j < 7; j++) {
				player.add(drawPile.get(0));
				drawPile.remove(0);
			}
		}
	}

	/**
	 * analyze the situation based on the cards played by one of the players
	 * @param card
	 * @return boolean. 'true' to continue. 'false' to stop.
	 */
	public boolean analyze(Card card) {

		if (card instanceof WildFour) {

		} else if (card instanceof Wild) {

		} else if (card instanceof Action) {
			switch (((Action) card).getIntAction()) {
				case 1:

			}

		} else if (card instanceof Color) {

		} else {
			System.out.println("Error while processing the card! Card: " + card + " cannot be processed!");
			return false;
		}

		for (Player p : players) {


		}
		return true;
	}
	
	public void displayDrawPile() {
		for (Card card : drawPile) {
			System.out.println(card);
		}
	}

	public void displayDiscardPile() {
		for (Card card : discardPile) {
			System.out.println(card);
		}
	}

	public int score() {
		int score = 0;
		for (Player p : players) {
			if (p.getC().size() == 0) continue;
			for (Card cd : p.getC()) {
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
		}
		return score;

//
//		// Daniel sort...
//		for (int i = 0; i < players.size(); i ++) {
//			for (int j = i + 1; j < players.size(); j ++) {
//				if (players.get(i).getScore() < players.get(j).getScore()) {
//					// Swap
//					Player p = players.get(j);
//					players.set(j,players.get(i));
//					players.set(i,p);
//				}
//			}
//		}
// 		for (int i = 0; i < players.size(); i ++) {
// 			Player p = players.get(i);
//			System.out.println("Rank - " + i + " - - Name: " + p.getName() + " gets score " + p.getScore() + "/100");
//		}
	}

	// Customized methods (apart from instructions)

	public void addPlayer(Player p) {
		players.add(p);
	}
}