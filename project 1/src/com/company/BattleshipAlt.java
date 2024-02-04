package com.company;

import java.util.Scanner;
import java.util.Random;

public class BattleshipAlt {

    private static final int GRID_SIZE = 20; // Size of the game grid
    private static final int MAXIMUM_SHIP_SIZE = 5; // Maximum size of a ship
    private static final int MINIMUM_SHIP_SIZE = 2; // Minimum size of a ship
    private static final int MAXIMUM_MISS_COUNT = 10; // Maximum number of allowed misses before the game ends


    public static void main(String[] args) {

        // Create an object to handle user input
        Scanner input = new Scanner(System.in);
        // Initialize a character array to represent the game grid
        char[] grid = new char[GRID_SIZE];

        System.out.println("\n\n\t\t\t Welcome to the Battleship Game!\n");

        // Loop to play the game
        do {
            startGame(input, grid); // Call the start function
        } while (playAgain(input));

        System.out.println("\n\t\tThank you for playing Battleship Game!");
        input.close();
    }

    // Method to start the Game
    private static void startGame(Scanner input, char[] grid) {
        // Initialize the game board
        initializeBoard(grid);

        // Variables to track game progress
        int shipCount = 0; // Number of placed ships
        int hits = 0;     // Number of successful hits
        int misses = 0;  // Number of missed attempts

        // Place three random ships on the board
        while (shipCount < 3) {
            int shipSize = randomShipSize();
            int startPosition = randomStartPosition(grid, shipSize);

            if (verifyShipPosition(grid, startPosition, shipSize)) {
                setShipOnGrid(grid, startPosition, shipSize);
                shipCount++;
            }
        }

        // Continue the game until the player sinks all ships or reaches maximum misses
        while (hits < shipCount * 3 && misses < MAXIMUM_MISS_COUNT) {
            int guess = inputUserGuess(input);

            if (isValidGuess(guess)) {
                if (grid[guess] == 'S') {
                    System.out.println("Hit!");
                    grid[guess] = 'X';
                    hits++;
                } else if (grid[guess] == 'X') {
                    System.out.println("You've already guessed this position. Try again.");
                } else {
                    System.out.println("Miss.");
                    grid[guess] = 'O';
                    misses++;
                }

                // Display current hits and misses
                displayHitsAndMisses(hits, misses);
            } else {
                System.out.println("Guess out of bounds! Enter a number between 0-" + (GRID_SIZE-1));
            }
        }
        // Show the endgame message
        endGameMessage(hits, shipCount * 3);
    }

    // Method to initialize the game board
    private static void initializeBoard(char[] grid) {
        // Loop to initialize the game board with empty spaces
        for (int i = 0; i < GRID_SIZE; i++) {
            grid[i] = ' ';
        }
    }


    // Method to generate a random ship size
    private static int randomShipSize() {
        // Generate a random number
        Random random = new Random();
        // Generate a random ship size within the specified range
        return random.nextInt(MAXIMUM_SHIP_SIZE - MINIMUM_SHIP_SIZE + 1) + MINIMUM_SHIP_SIZE;
    }

    // Method to generate a random starting position for a ship
    private static int randomStartPosition(char[] grid, int shipSize) {
        // Generate a random number
        Random random = new Random();
        // Ensuring the entire ship fit within the remaining grid space
        return random.nextInt(GRID_SIZE - shipSize + 1);
    }


    // Method to verify the ship position
    private static boolean verifyShipPosition(char[] grid, int startPosition, int shipSize) {
        for (int j = startPosition; j < startPosition + shipSize; j++) {
            if (grid[j] != ' ') {
                // If any position is not empty, the ship cannot be placed
                return false;
            }
        }
        return true;
    }

    // Method to set a ship on the game grid starting from the specified position
    private static void setShipOnGrid(char[] grid, int startPosition, int shipSize) {
        for (int j = startPosition; j < startPosition + shipSize; j++) {
            grid[j] = 'S'; // Mark the grid position as part of a ship
        }
    }

    // Method to get the user input
    private static int inputUserGuess(Scanner input) {
        System.out.print("Enter your guess (0-" + (GRID_SIZE - 1) + "): ");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            input.next(); // Clear the invalid input
        }
        return input.nextInt();
    }

    // Method to check if the provided guess is within the valid range of grid positions
    private static boolean isValidGuess(int guess) {
        // Check if the guess is greater than or equal to 0 and less than GRID_SIZE
        return guess >= 0 && guess < GRID_SIZE;
    }

    // Method to display hits and misses
    private static void displayHitsAndMisses(int hits, int misses) {
        System.out.println("Hits: " + hits + " Misses: " + misses);
    }

    // Method to display the end game message
    private static void endGameMessage(int hits, int maxHits) {
        if (hits == maxHits) {
            System.out.println("Congratulations! You've sunk all the ships. You win!");
        } else {
            System.out.println("Game over. You've exceeded the maximum number of misses. You lose.");
        }
    }

    // Method to play the game again
    private static boolean playAgain(Scanner input) {
        System.out.print("Do you want to play again? (yes/no): ");
        String playAgain = input.next().toLowerCase();
        return playAgain.equals("yes");
    }
}
