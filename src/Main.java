/* Main.java:
 * - Creates an instance of the game
 * - Controls "play-again" loop
 * - Creates a new instance of the game each time the user creates a new game */

// Display best score with names and scores (# of turns) for each variation of the game

package edu.wm.cs.cs301.connectn;
import java.util.Scanner; // To receive user input

public class Main {

	public static void main(String[] args) {
		
		// Display welcome message
		System.out.println("Welcome to Connect N! \nConnect N is a variation of the classic tabletop game, Connect 4.");
		System.out.println("This game is played by dropping pieces into columns of your choice. \nTo win, you have to get 3, 4, or 5 (depending on the board size you play) of your pieces in a diagonal, horizontal, or vertical line.");
		System.out.println("To begin, select which board size you would like to play with: small, medium (traditional board size), or large. \n(づ｡◕‿◕｡)づ");
		
		// User can choose small mode (4 rows, 5 columns), medium mode (6 rows, 7 columns), or large mode (8 rows, 9 columns)
		Scanner scanner = new Scanner(System.in);
		boolean playAgain = true;
		while (playAgain == true) {
			
			// Display leaderboard
			LeaderboardManager leaderboardManager = new LeaderboardManager();
			leaderboardManager.displayLeaderboard();
			
			String userInput;
			System.out.println("\nWhat size board would you like to use? Enter your choice into the console. \nsmall/medium/large");
			
			// Check if the input is one of the expected values
			while (true) {
				userInput = scanner.nextLine().toLowerCase(); 
			    if (userInput.equalsIgnoreCase("small") || userInput.equalsIgnoreCase("medium") || userInput.equalsIgnoreCase("large")) {
			        break; // Exit the loop if the input is valid
			    } else {
			        System.out.println("Invalid input. Please try again.");
			    }	
			}

			// Start game instance with correct board size
			ConnectNGame game;
			switch (userInput) {
				case "small":
	                System.out.println("You've selected the small board. Get 3 in a row to win.");
	                game = new ConnectNGame(4, 5);
	                playAgain = game.start();
	                break;
	                
				case "medium":
	                System.out.println("You've selected the medium board. Get 4 in a row to win.");
	                game = new ConnectNGame(6, 7);
	                playAgain = game.start();
	                break;
	                
				case "large":
	                System.out.println("You've selected the large board. Get 5 in a row to win.");
	                game = new ConnectNGame(8, 9);
	                playAgain = game.start();
	                break;
				}
			
			
		}
	}
}