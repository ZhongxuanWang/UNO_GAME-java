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
		drawPile = new ArrayList();
		players = new ArrayList();
	}

	public void initializer() 
	{
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

	public void play() {
		Scanner scr = new Scanner(System.in);
		outer_loop:
		while (true) {
			for (Player player : players) {
				Main.clear_screen();
				player.displayCards();
				System.out.println("Now, player " + player.getName() + ", press number between 1 and " +
						player.getCardSize() + " and press 'Enter' to continue! Input 'q' to quit the game!");
				int card_selection_num = -1;

//				Codes below are removed as they were part of the Human vs. Computer part.

//				while (true) {
//					try {
//						String input = scr.nextLine().trim();
//						if (input.equals("q")) break outer_loop;
//						card_selection_num = Integer.parseInt(input);
//						if (card_selection_num > player.getCardSize() || card_selection_num < 1)
//							throw new NumberFormatException();
//					} catch (NumberFormatException e) {
//						System.out.println("Only numbers are expected and they must be in the range.");
//						continue;
//					}
//					break;
//				}
				if (!analyze(player.getC().get(card_selection_num - 1)))
//					scr.close();
					break outer_loop;
				System.out.println("Player " + player.getName() + " finished!");
				Main.sleep(2000);
			}
		}
		return;
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
	 * analyze the situation
	 * @param card
	 * @return boolean. 'true' to continue. 'false' to stop.
	 */
	public boolean analyze(Card card) {
		for (Player p : players) {
			if (p.checkwin()) return false;

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

	public void score() {
		
	}

	// Customized methods (apart from instructions)

	public void addPlayer(Player p) {
		players.add(p);
	}
}