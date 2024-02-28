/* ConnectNGame.java:
 * - Stores information about the current instance of the game
 * - Controls the single game loop
 * - Handles game logic; gives each player a turn until the game is won, ends in a tie, or the user quits
 * - Initializes game board
 */
package edu.wm.cs.cs301.connectn;
import java.util.Scanner;

public class ConnectNGame {
    private int rows;
    private int columns;
    private GameBoard board;
    private LeaderboardManager leaderboardManager;

    
    // Constructor
    public ConnectNGame(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new GameBoard(rows, columns);
        this.leaderboardManager = new LeaderboardManager();
    }

    
    // Method to start the game
    public boolean start() {
    	boolean humanTurn = true;
    	boolean gameWon = false;
    	
    	// Display Gameboard
    	this.board.displayBoard();
    	
    	// Variable to keep track of turn count
    	int turn = 1;
    	
    	// Turn handling
        while (!gameWon) {
            if (humanTurn) {
                System.out.println("\nHuman's turn\nTurn #: "+ turn);
                
            	// Human player turn logic
            	HumanPlayer human = new HumanPlayer(this.columns);
            	String userInput = human.takeTurn();
            	// Validates whether input is usable
            	if (userInput == null) { // Quitting logic
            		System.out.println("You have chosen to quit. Goodbye!");
        			LeaderboardManager leaderboardManager = new LeaderboardManager(); // Display leaderboard again after quitting
        			leaderboardManager.displayLeaderboard();
            		return false;
            	}
            	try {
            	    int selectedColumn = Integer.parseInt(userInput);
            	    // If we reach this point, userInput is successfully parsed as an integer
            	    if (selectedColumn >= 0 && selectedColumn <= this.columns && this.board.isColumnFull(selectedColumn - 1, this.rows)) {
            	        System.out.println("Valid input. You have placed a piece in column " + selectedColumn);
            	        turn++; // Increment 
            	        this.board.placePiece(selectedColumn - 1, 'X');
            	        this.board.displayBoard();
            	        humanTurn = !humanTurn;
            	    } else {
            	        System.out.println("Column is full or input is an integer but not in the valid range. Please try again.");
            	    }
            	    
            	} catch (NumberFormatException e) {
            	    System.out.println("Invalid input. Please enter a number or type 'quit' to exit.");
            	}

            }
                gameWon = board.checkWin('X');
                if (gameWon) {
                	int finalturnCount = turn - 1; // Decrement by one to get the final turn count
                    System.out.println("\nHuman wins!\nYou won in " + finalturnCount + " turns!");
                    
                    String gameMode = "size";
                    if (this.columns == 5) {
                        gameMode = "small";
                    } else if (this.columns == 7) {
                        gameMode = "medium";
                    } else if (this.columns == 9) {
                        gameMode = "large";
                    }

                    // Update the leaderboard with the final turn count
                    this.leaderboardManager.updateScore(gameMode, finalturnCount);

                    break;
                    
                    
            } else if (humanTurn == false) {
                System.out.println("\nComputer's turn");
             
                ComputerPlayer computer = new ComputerPlayer(this.columns, this.rows, this.board, 'O');
                computer.takeTurn();
                gameWon = board.checkWin('O');
                this.board.displayBoard();
                humanTurn = !humanTurn;
            	} if (gameWon) {
                    System.out.println("\nComputer wins!");
                    break;
                }

            // Switch turns if the game hasn't been won yet
            if (this.board.isBoardFull()) {
            	System.out.println("\nBoard is full, tie game.");
            	break;
            }
        }
        return promptPlayAgain();
    }
    // Method to take each turn. Returns true if successfully placed, returns false if the column is full.
    public boolean playTurn(int selectedColumn, char piece) {
    	int ColumnIndex = selectedColumn - 1;
    	// Check and see if column is full
    	if (this.board.isColumnFull(selectedColumn, rows)) {
    		System.out.println("The selected column is full. Please choose a different column.");
    		return false;
    	} else {
    		// Place piece if the column is not full
    		this.board.placePiece(ColumnIndex, piece);
    		return true;	
    	}	
    }   	
    
    // Method to prompt user to play again
    public boolean promptPlayAgain() {
    	
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Would you like to play again? (Y/N)");
            String response = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(response)) {
                return true; // User wants to play again
            } else if ("N".equals(response)) {
            	System.out.println("\nGoodbye!");
                return false; // User does not want to play again
            } else {
                System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no.");
            }
        }
    }
    	
}