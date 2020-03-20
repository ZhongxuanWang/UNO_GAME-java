import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		clear_screen();

		Dealer d = new Dealer();

		d.initializer();
		// System.out.println("Shuffling the cards...");
		d.shuffle();
		d.displayDrawPile();

		int n = 0;
		while (true) {
			System.out.println("Welcome to UNO!\n" +
					"First thing first, how many players would you like to add?");
			try {
				n = Integer.parseInt(new Scanner(System.in).nextLine());
			} catch (Exception e) {
				System.out.println("Numbers expected");
				continue;
			}
			break;
		}

		for (int i = 1; i <= n; i++) {
			System.out.println("Player " +i+ " ! Give me your name to continue!");
			String name = new Scanner(System.in).nextLine();
			d.addPlayer(new Player(name));
		}

		System.out.println("Getu! Already assigned memory for you!");

		



	}

	public static void clear_screen() 
	{  
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}