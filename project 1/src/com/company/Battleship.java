// Project 1 - Battleship.java
// Vincent Hsiao, CIS 255

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class Battleship {

    /*
    * Private class variables:
    *
    * shots: number of shots before player loses
    * gameBoard: board with the position of all the ships (not visible to player)
    * playerBoard: board with hits and misses from player
    * boardSize: Size of the 2d array board (1st value represents the height or first length, 2nd value represents the width or second length)
    * ships: the size of the ships to be placed onto gameBoard
    * shipsArr: Arraylist version of ships array. Used in gameloop method to keep track of ship health ships
    * directions: used by placeShips() to find vacant spots for ships
    * referenceBoard: numbered version of playerBoard used to find specified spaces
    */

    private static int shots;
    private static char[][] gameBoard;
    private static char[][] playerBoard;
    private static int[] boardSize = {8,8};
    private static int[] ships = {5, 4, 3, 3};
    private static List<Integer> shipsArr = new ArrayList<Integer>();
    private static int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    private static int[][] referenceBoard;

    // print a board
    public static void outputGameBoard(char[][] gameBoard){
        for (int i = 0; i < boardSize[0]; i++){
            for (int k = 0; k < boardSize[1]; k++){
                System.out.print(gameBoard[i][k] + " ");
            }
            System.out.println();
        }
    }

    // print reference board
    public static void outputReferenceBoard(){
        System.out.println("Space numbers corresponding to gameboard");
        for (int i = 0; i < boardSize[0]; i++){
            for (int k = 0; k < boardSize[1]; k++){
                System.out.print(referenceBoard[i][k] + " ");
            }
            System.out.println();
        }
    }

    // find valid location to place ship of size N
    public static int[][] placeShip(int shipSize){
        Random rand = new Random();


        int[][] curShipPos = new int[shipSize][2];

        // valid positions for the current ship found?
        boolean positionFound = false;
        while (positionFound != true){

            //generate random start position
            int startX = rand.nextInt(boardSize[0]);
            int startY = rand.nextInt(boardSize[1]);

            // check if random spot is empty
            if(gameBoard[startX][startY] == 'm') {
                // loop through all possible directions
                int positions;
                for (int i = 0; i < directions.length; i++) {
                    // the position array of the ship in this direction ([x, y])
                    curShipPos = new int[shipSize][2];
                    curShipPos[0][0] = startX;
                    curShipPos[0][1] = startY;
                    int curX = startX;
                    int curY = startY;
                    // guaranteed positions
                    positions = 0;

                    // System.out.println("Cur Pos: " + curX + "," + curY);

                    // Checking if ship can be place in a certain direction (spaces are empty)
                    for (int k = 1; k < shipSize; k++){
                        curX = curX + directions[i][0];
                        curY = curY + directions[i][1];
                        // current position out of bounds
                        if(curX < 0 || curX >= boardSize[0] || curY < 0 || curY >= boardSize[1]){
                            //System.out.println("Out of bounds at "+curX + " " + curY);
                            break;
                        }
                        // current spot taken
                        else if (gameBoard[curX][curY] != 'm'){
                            //System.out.println("Something is already at "+curX + " " + curY);
                            break;
                        }
                        else{
                            // set kth position in position array to x, y
                            curShipPos[k][0] = curX;
                            curShipPos[k][1] = curY;
                            positions = positions + 1;
                            // System.out.println(curX + " " + curY);
                        }
                    }
                    // check if all (shipSize) positions in current direction are clear to put a ship down
                    if(positions == shipSize-1){
                        positionFound = true;
                        break;
                    }
                }

            }


        }

        // returns current acceptable ship position
        return curShipPos;
    }

    // place ships from ships array on gameboard
    public static void placeShips(){
        // loop through all the ships required, find positions, and place them onto the gameboard
        for (int i = 0; i < ships.length; i++){
            int[][] shipPos = placeShip(ships[i]); // <- placeShip() finds valid position on board for ship of size n
            for (int k = 0; k < shipPos.length; k++){
                // "place" ships down on gameboard after finding valid spot
                gameBoard[shipPos[k][0]][shipPos[k][1]] = Character.forDigit(i, 10);
            }
        }
    }

    // creates a filled gameboard 2d array of size len1 x len2 filled with filler char
    public static char[][] fillBoard(int len1, int len2, char filler){
        char[][] board = new char[len1][len2];
        for (int i = 0; i < len1; i++){
            for (int k = 0; k < len2; k++){
                board[i][k] = filler;
            }
        }
        return board;
    }

    // set up gameboard, place ships, set up player-facing board and reference board
    public static void setUpGameBoard(int shotsNo){
        shots = shotsNo;
        gameBoard = new char[boardSize[0]][boardSize[1]];
        // set up empty game board
        gameBoard = fillBoard(boardSize[0], boardSize[1], 'm');
        // place ships
        placeShips();
        // set up player-facing board
        playerBoard = fillBoard(boardSize[0], boardSize[1], '-');

        // output current game board (debug only)
        // outputGameBoard(playerBoard);
       //  outputGameBoard(gameBoard);

        // set up reference board
        referenceBoard = new int[boardSize[0]][boardSize[1]];
        int counter = 0;
        for (int i = 0; i < boardSize[0]; i++){
            for (int k = 0; k < boardSize[1]; k++){
                referenceBoard[i][k] = counter;
                counter+=1;
            }
        }

        // outputReferenceBoard();


    }

    // takes numbered guess and determines whether hit or miss
    public static int guess(int spot){
        int spotY = spot % boardSize[1];
        int spotX = Math.floorDiv(spot, boardSize[1]);
        char curBoardSpot = gameBoard[spotX][spotY];
        if(playerBoard[spotX][spotY] != '-'){
            return -3;
        }
        if(curBoardSpot == 'm'){
            playerBoard[spotX][spotY] = 'M';
            return -1;
        } else {
            playerBoard[spotX][spotY] = 'H';
            int value = Character.getNumericValue(curBoardSpot);
            return value;
        }
    }

    // checks if the board is empty by ensuring all ships are sunk (value in shipsArr is zero)
    public static boolean checkIfBoardEmpty(){
        for (int i = 0; i < shipsArr.size(); i++){
            if(shipsArr.get(i) != 0){
                return false;
            }
        }
        return true;
    }

    // main game
    public static void gameLoop(Scanner scnr){
        int guesses = 0;
        // curspot: player's guess from input
        int curSpot;
        // initializee ships arraylist
        for (int i = 0; i < ships.length; i++){
            shipsArr.add(ships[i]);
        }

        // main game loop
        for (int guess = 0; guess < shots; ++guess){

            // shot preamble output

            outputReferenceBoard();
            outputGameBoard(playerBoard);
            while (true) {
                System.out.print(shots - guess + " shots left! Enter a spot on the gameboard to shoot! > ");
                curSpot = scnr.nextInt();

                if (curSpot > boardSize[0] * boardSize[1] - 1 || curSpot < 0) { // invalid shot (number out of bounds)
                    System.out.println("Invalid shot! Make sure to pick from the numbers on the chart");
                }

                else{
                    break;
                }
            }
            int result = guess(curSpot); // -1 = miss, -3 = repeated shot, ship # = hit

            if(result == -1){ // miss
                System.out.println("Looks like our shot has missed this time.\nTry again!\n");
            }
            else if (result == -3){ // repeated shot
                System.out.println("Looks like you wasted a shot by aiming for a target we already tried to hit.\nMake sure to reference the player game board above.\nBetter luck next time!\n");
            }
            else{ //hit
                System.out.println("Intelligence informs us that we have HIT one of our enemy's ships\nGood job!\n");

                shipsArr.set(result, shipsArr.get(result)-1);
                if(shipsArr.get(result) == Integer.valueOf(0)){
                    System.out.println("We've sank enemy's '" + result + "' ship of size " + ships[result] + "! Excellent!\n");
                }
                if(checkIfBoardEmpty() == true){ // checks if all ships have been sunk. if true, the player has won
                    break;
                }
            }
            System.out.println("====================\n");
        }

        // post-game messages
        if(checkIfBoardEmpty() == false){
            System.out.println("Mission failed.\nThe opposition's naval fleet is still intact and have escaped to different locations. \nHere were the positions of their ships.\n");
            outputGameBoard(gameBoard);
            System.out.println("\nBetter luck next time!");
        }
        else {
            System.out.println("Mission success!\nThe opposition's fleet has fallen.\n You win!");
        }

    }

    // play the game (for repeating games)
    public static void playGame(Scanner scnr){
        System.out.println("Welcome to Battleship, player!");
        setUpGameBoard(25); // <- argument: shots - the number of shots the player gets (including hits)
        System.out.println("Directions: input the number of the space you want to shoot. H will appear if you have hit a ship, M will appear if you missed. \nYou only have " + shots + " shots to hit all of the ships, so choose your spots wisely!");

        System.out.println("\nLooks like the computer has decided! That was quick.\n\nShips used in this game will be: ");

        for (int i = 0; i < ships.length; i++){
            System.out.println("Ship " + i + " with size " + ships[i]);
        } // outputs ship information

        System.out.println("You're ready to play!\n\nGood Luck!\n======================\n");


        gameLoop(scnr);
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Java Battleship\n");

        // "true" main game loop - runs until player doesn't want to play battleship anymore
        while (true){
            playGame(scnr);
            System.out.println("Would you like to play again? (y for yes, everything else for no) >");
            String response = scnr.next();
            if(!response.equals("y")){
                System.out.println("Thank you for playing battleship");
                break;
            }
        }

    }
}
