import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		clear_screen();

		Dealer d = new Dealer();

		d.initializer();
		System.out.println("Here's the draw Pile after shuffled.");
		d.shuffle();
		d.displayDrawPile();
		System.out.println("-------- \n" +
				"It would close in 3s...");
		try {Thread.sleep(3000);} catch (Exception ignored) {}
		clear_screen();


		int number_of_user = 0;
		while (true) {
			System.out.println("Welcome to UNO!\n" +
					"First thing first, how many players would you like to add?");
			try {
				number_of_user = Integer.parseInt(new Scanner(System.in).nextLine());
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
			System.out.println("Player " +i+ " ! Give me your name to continue!");
			String name = new Scanner(System.in).nextLine();
			d.addPlayer(new Player(name));
		}

		System.out.println("Getu! Game will start! Each of you will get 7 cards!");
		d.deal();

		



	}

	public static void clear_screen() 
	{  
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}