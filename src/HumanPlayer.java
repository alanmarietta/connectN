/**
 * User turn logic.
 */
package edu.wm.cs.cs301.connectn;

import java.util.Scanner;

public class HumanPlayer implements Player {
	private int columns;
	
	// Constructor
	public HumanPlayer(int columns) {
		this.columns = columns;	
	}

	@Override
	public String takeTurn() {
		Scanner input = new Scanner(System.in);
	    // Display instructions
		System.out.println();
	    System.out.println("Choose a slot (1 - " + columns + ") to place your piece or type QUIT to quit the game");
	    
	   // Read user input
	    String userInput = input.nextLine().toLowerCase();
    	if ("quit".equals(userInput)) {
    		return null;
    	} else {
	    return userInput;
	    }
	    
	}

}
