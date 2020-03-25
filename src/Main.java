import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		clear_screen();

		Dealer d = new Dealer();

		d.initialize();
		System.out.println("Here's the draw Pile after shuffled.");
		d.shuffle();
		d.displayDrawPile();
		System.out.println("-------- \n" +
				"It would be closed in 0.5s...");

		// IntelliJ
		// Only values (not variables) can be displayed with prefix.
		sleep(500);
		clear_screen();


		int number_of_user = 0;
		while (true) {
			System.out.println("Welcome to UNO!\n" +
					"First thing first, how many players would you like to add?");
			try {
				number_of_user = Integer.parseInt(new Scanner(System.in).nextLine().trim());
				if (number_of_user < 2 || number_of_user > 10) {
					System.out.println("Number of players out ranged [2,10].");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Only numbers are expected");
				continue;
			}
			break;
		}

		for (int i = 1; i <= number_of_user; i++) {
			System.out.println("Player " +i+ " is ready to be set the name!");
			String name = new Scanner(System.in).nextLine();
			d.addPlayer(new Player(name));
		}

		System.out.println("Get u! Names will be assigned to those players! Cards will be dealt!");
		d.deal();

		System.out.println("Game starts in 0.5s! The first player plays first!");
		sleep(500);
		while (true) {
			if (d.play()) {
				System.out.println("\nTHIS IS THE DISCARD PILE AFTER ALL\n");
				d.displayDiscardPile();
				System.out.println("Cards would be reassigned!");
				d.initialize();
				d.shuffle();
				d.displayDrawPile();
				System.out.println("-------- \n" +
						"It would be closed in 0.5s...");
				sleep(500);
				clear_screen();
				d.deal();
			} else {
				break;
			}
		}

		clear_screen();
		System.out.println("Game ends. This game lasts" + ((int)(System.currentTimeMillis() - start) / 60000)
				+ " minutes " + ((System.currentTimeMillis() - start) % 1000.0) + " seconds");

	}

	public static void clear_screen()
	{
		// I decide not to implement this method since watching screen vomiting words is a thing to enjoy!
//		System.out.print("\033[H\033[2J");
//		System.out.flush();
	}

	public static void sleep(long millisecond) {
		try {Thread.sleep(millisecond);} catch (Exception ignored) {}
	}
}