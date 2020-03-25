import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Dealer {

	// There are 108 cards in the deck.
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	private ArrayList<Player> players = new ArrayList();

	// Customized. They are for the communications between 'analyze' and 'play' method for this class. Is that OK? (Y/N)
	int direction = 1;
	int player_index = 0;

	public Dealer() 
	{
	}

	public void initialize()
	{
		drawPile = new ArrayList();
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
		// Shuffle 20 times in total. Time and space complexity are not in the considerations.
		for (int j = 0; j < 10; j ++) {
			Random rd = new Random();
			for (int i = 0; i < drawPile.size(); i ++) {
				int in = rd.nextInt(i + 1);

				Card c = drawPile.get(i);
				drawPile.set(i, drawPile.get(in));
				drawPile.set(in, c);
			}
		}
		for (int j = 0; j < 10; j ++) {
			Random rd = new Random();
			for (int i = 0; i < drawPile.size(); i ++) {
				int in = rd.nextInt(drawPile.size());

				Card c = drawPile.get(i);
				drawPile.set(i, drawPile.get(in));
				drawPile.set(in, c);
			}
		}
		
	}

	public boolean play()
	{
		// Set defaults
		boolean halt = false; // Halt is enabled by action card.

		while (true) {
			Player player = players.get(player_index);

			// If the card asked to halt
			if (halt) {
				System.out.println("Player " + player.getName() + " is halted for this round!");
				halt = false;
				continue;
			}

			Main.clear_screen();
			player.displayCards();

			Card c = null;

			// Deal with complex problems.
			if (discardPile.size() == 0) {
				c = player.play(new Wild());
			} else {
				c = player.play(discardPile.get(0));
			}

			// Results check
			if (player.done()) {
				System.out.println("Wow! Player " + player.getName() + " has been running out of cards! Other " +
						"players card will be calculated to this lucky player!");
				player.setScore(score());
				if (player.getScore() > 500) {
					showResult();
					System.out.println("Wow! Also this player has won the game! Game will stop by now!");
					break;
				}
				return true;
			}

			if (c != null) {
				discardPile.add(0,c);
			} else if (c == null) {
				// player is punished when no card could be played.
				player.add(drawCard());
			} else if (c instanceof Wild || c instanceof WildFour) {

			}

			halt = !analyze(c);

			System.out.println("Player " + player.getName() + " finished! He played " + c);
			player_index = nextPlayerIndex();
			Main.sleep(500);
		}
		return false;
	}

	public void deal()
	{
		discardPile = new ArrayList();
		for (Player player : players) {
			ArrayList<Card> cards = player.getC();
			if (cards.size() != 0) {
				while (cards.size() > 0) {
					cards.remove(0);
				}
			}
			for (int j = 0; j < 7; j++) {
				player.add(drawCard());
			}
		}

	}

	/**
	 * analyze the situation based on the cards played by one of the players and take actions.
	 * @param card
	 * @return boolean. 'true' to continue. 'false' to stop.
	 */
	public boolean analyze(Card card)
	{
		//TODO Wild / WildFour can change the color
		if (card instanceof WildFour) {
			int player_index = nextPlayerIndex();
			players.get(player_index).add(drawCard());
			players.get(player_index).add(drawCard());
			players.get(player_index).add(drawCard());
			players.get(player_index).add(drawCard());
			return false;
		} else if (card instanceof Wild) {
			return true;
		} else if (card instanceof Action) {
			switch (((Action) card).getIntAction()) {
				case 1:
					return false;
				case 2:
					direction = - direction;
					return false;
				case 3:
					int player_index = nextPlayerIndex();
					players.get(player_index).add(drawCard());
					players.get(player_index).add(drawCard());
					return false;
				default:
					return true;
			}
		} else if (card instanceof Color || card == null) {
			// Normal flow
			return true;
		} else {
			System.out.println("Error while processing the card! Card: " + card + " cannot be processed!");
			return false;
		}
	}
	
	public void displayDrawPile()
	{
		for (Card card : drawPile) {
			System.out.println(card);
		}
	}

	public void displayDiscardPile()
	{
		for (Card card : discardPile) {
			System.out.println(card);
		}
	}

	public int score()
	{
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
	}

	// Customized methods (apart from instructions)

	public void addPlayer(Player p)
	{
		players.add(p);
	}

	/**
	 * Check if it's able to draw cards and draw cards from pile
	 * @return Card drew
	 */
	public Card drawCard()
	{
		drawPileRefill();
		Card c = drawPile.get(0);
		drawPile.remove(0);
		return c;
	}

	/**
	 * Precondition: player_index is valid
	 * ATTENTION: This method will not change the value from method player_index.
	 * @return the next player's index.
	 */
	public int nextPlayerIndex()
	{
		// If the index exceeds max
		if (player_index + direction == players.size()) {
			return 0;
		}
		// If the index exceeds min
		if (player_index + direction < 0) {
			return player_index + direction + players.size();
		}
		return player_index + direction;
	}

	/**
	 * Check if DrawPile is empty. If so, draw cards from discarded pile and reshuffle. In this program, it is also
	 * called in extensive times.
	 */
	public void drawPileRefill()
	{
		// 4 is the number of max cards that would be probably drawn from the pile.
		if (drawPile.size() > 4) return;
		// The first card is neglected to not disturb other features.
		for (int i = 1; i < discardPile.size(); i ++) {
			drawPile.add(discardPile.get(i));
			shuffle();
		}
	}

	/**
	 * Displaying the results after a final winner is produced. The sorting algorithm is used.
	 */
	public void showResult() {
		for (int i = 0; i < players.size(); i ++) {
			for (int j = i + 1; j < players.size(); j ++) {
				if (players.get(i).getScore() < players.get(j).getScore()) {
					// Swap
					Player p = players.get(j);
					players.set(j,players.get(i));
					players.set(i,p);
				}
			}
		}
 		for (int i = 0; i < players.size(); i ++) {
 			Player p = players.get(i);
			System.out.println("Rank - " + i + " - - Name: " + p.getName() + " gets score " + p.getScore() + "/500");
		}
	}

}