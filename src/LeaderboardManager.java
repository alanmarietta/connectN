// The purpose of this file is to read in from our leaderboard.txt file and also keep track of the leaderboard so we can properly display the best turn counts recorded.
package edu.wm.cs.cs301.connectn;

import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private Map<String, Integer> scores = new HashMap<>();
    private final String leaderboardFilePath = "resources/leaderboard"; // No extension mentioned

    // Constructor loads the leaderboard
    public LeaderboardManager() {
        scores.put("small", null);
        scores.put("medium", null);
        scores.put("large", null);
        loadScores();
    }

    // Method to load scores from the file
    private void loadScores() {
        File file = new File(leaderboardFilePath);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split each line into two parts: the game mode and the score
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String gameMode = parts[0];
                    // Check if the score part is "null"
                    if ("null".equals(parts[1])) {
                        // If score is "null", do not add it to the scores map
                        continue;
                    }
                    int score = Integer.parseInt(parts[1]);
                    // Put the game mode and score into the scores map
                    scores.put(gameMode, score);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the leaderboard file: " + e.getMessage());
        }
    }


    // Method to update the leaderboard
    public void updateScore(String gameMode, int newScore) {
        // Get the current best score for the game mode
        Integer currentBestScore = scores.get(gameMode);
        
        // Check if the new score should replace the current best score
        if (currentBestScore == null || newScore < currentBestScore) {
            scores.put(gameMode, newScore);
            saveScores();
        }
    }

    // Method to save scores back to the file
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(leaderboardFilePath, false))) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the leaderboard: " + e.getMessage());
        }
    }

    // Method to display the leaderboard
    public void displayLeaderboard() {
        // Check if the leaderboard is empty
        if (scores.isEmpty()) {
            System.out.println("No scores to display.");
            return;
        }

        System.out.println("\nCurrent Leaderboard:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            // If there's no score for a game mode, skip printing it
            if (entry.getValue() == null) {
                continue;
            }

            // If there's a score, print the game mode and the score
            System.out.printf("Game Mode: %-6s Best Score: %d turns\n", entry.getKey(), entry.getValue());
        }
    }
}
