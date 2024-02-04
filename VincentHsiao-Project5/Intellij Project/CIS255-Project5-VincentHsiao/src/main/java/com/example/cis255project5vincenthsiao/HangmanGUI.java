package com.example.cis255project5vincenthsiao;

import java.io.File;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

// 19-31: Exceptions to be thrown by guessValid function when guess isn't valid. Will be handled in the guessHandler function.
class EmptyInputException extends Throwable{
	public EmptyInputException(){
		super("Invalid input: empty input text!");
	}
}

class ImproperInputSizeException extends Throwable{
	public ImproperInputSizeException(){
		super("input can not be more than one character!");
	}
}

class ImproperInputFormException extends Throwable{
	public ImproperInputFormException(){
		super("input must be a alphabetical letter!");
	}
}

public class HangmanGUI extends Application {
	Random rand = new Random();
	private String currentWord; // the randomly selected word
	private TextField guessField; // the user enters their guess here
	private Text currentWordText; // show the current word (with - for unguessed letters)
	private Text outcomeText; // show the outcome of each guess and the game
	private Text wrongGuessesText; // show a list of incorrect guesses
	private Text wrongGuessNumberText; // show how many incorrect guesses (or how many guesses remain)
	private String[] currentWordArr;
	private String[] currentProgress;

	private Button restartButton;
	private HBox restartBox;
	private boolean won = false; // true if all of the letters are revealed
	private int curWrongGuesses; // number of guesses the user took
	private Character[] guesses; // letters guessed
	private final static int MAX_WRONG_GUESSES = 7;
	private static final Color TITLE_AND_OUTCOME_COLOR = Color.rgb(221, 160, 221);
	private static final Color INFO_COLOR = Color.rgb(224, 255, 255);
	private static final Color WORD_COLOR = Color.rgb(224, 255, 255);



	private void endGame(){
		restartBox.setVisible(true);
		guessField.setEditable(false);
	} // shows restart button at the end

	public void setupGame(){

		// re-initialize wrong guesses and reset guess count
		guesses = new Character[MAX_WRONG_GUESSES];
		curWrongGuesses = 0;
		won = false;

		guessField.setEditable(true);
		restartBox.setVisible(false);

		// choose word and set up arrays
		currentWord = chooseWord();
		System.out.println(currentWord);
		currentWordArr = currentWord.split("");
		currentProgress = new String[currentWordArr.length];
		Arrays.fill(currentProgress, "-");

		currentWordText.setText(String.join("",currentProgress));
		wrongGuessesText.setText("Guessed letters: []");
		wrongGuessNumberText.setText(MAX_WRONG_GUESSES+ "/" + MAX_WRONG_GUESSES + " guesses left.");
		outcomeText.setText("");
	} // prepares a new game

	// main game GUI
	public void gameLoop(Stage primaryStage){
		VBox mainVBox = new VBox();
		mainVBox.setStyle("-fx-background-color: royalblue");
		mainVBox.setAlignment(Pos.CENTER);
		mainVBox.setSpacing(10);


		Text welcomeText = new Text("Welcome to Hangman!");
		welcomeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
		welcomeText.setFill(TITLE_AND_OUTCOME_COLOR);
		Text introText1 = new Text("Guess a letter.");
		Text introText2 = new Text("You can make " + MAX_WRONG_GUESSES + " wrong guesses!");
		introText1.setFont(Font.font("Helvetica", 24));
		introText1.setFill(INFO_COLOR);
		introText2.setFont(Font.font("Helvetica", 24));
		introText2.setFill(INFO_COLOR);
		VBox introBox = new VBox(welcomeText, introText1, introText2);
		introBox.setAlignment(Pos.CENTER);
		introBox.setSpacing(10);
		mainVBox.getChildren().add(introBox);

		// create before game is started
		outcomeText = new Text( "");
		guessField = new TextField();
		wrongGuessNumberText = new Text("");

		wrongGuessesText = new Text("");
		currentWordText = new Text("");
		currentWordText.setFont(Font.font("Helvetica", FontWeight.BOLD, 48));
		currentWordText.setFill(WORD_COLOR);
		HBox currentBox = new HBox(currentWordText);
		currentBox.setAlignment(Pos.CENTER);
		currentBox.setSpacing(10);
		mainVBox.getChildren().add(currentBox);

		Text guessIntroText = new Text("Enter your guess: ");
		guessIntroText.setFont(Font.font("Helvetica", 26));
		guessIntroText.setFill(INFO_COLOR);
		guessField.setOnAction(this::handleGuessField);
		HBox guessBox = new HBox(guessIntroText, guessField);
		guessBox.setAlignment(Pos.CENTER);
		guessBox.setSpacing(10);
		mainVBox.getChildren().add(guessBox);

		outcomeText.setFont(Font.font("Helvetica", 28));
		outcomeText.setFill(TITLE_AND_OUTCOME_COLOR);
		HBox outcomeBox = new HBox(outcomeText);
		outcomeBox.setAlignment(Pos.CENTER);
		outcomeBox.setSpacing(10);
		mainVBox.getChildren().add(outcomeBox);

		wrongGuessesText.setFont(Font.font("Helvetica", 24));
		wrongGuessesText.setFill(INFO_COLOR);
		HBox wrongGuessesBox = new HBox(wrongGuessesText);
		wrongGuessesBox.setAlignment(Pos.CENTER);
		wrongGuessesBox.setSpacing(10);
		mainVBox.getChildren().add(wrongGuessesBox);

		wrongGuessNumberText.setFont(Font.font("Helvetica", 24));
		wrongGuessNumberText.setFill(INFO_COLOR);
		HBox wrongGuessNumberBox = new HBox(wrongGuessNumberText);
		wrongGuessNumberBox.setAlignment(Pos.CENTER);
		mainVBox.getChildren().add(wrongGuessNumberBox);

		restartButton = new Button("Play Again!");
		restartButton.setFont(Font.font("Helvetica", 20));
		restartBox = new HBox(restartButton);
		restartBox.setAlignment(Pos.CENTER);
		restartBox.setSpacing(5);
		mainVBox.getChildren().add(restartBox);

		// hidden restart button
		restartBox.setVisible(false);
		restartButton.setOnAction(this::restart);

		// set up game
		setupGame();

		Scene scene = new Scene(mainVBox, 550, 500);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void start(Stage primaryStage) {
		gameLoop(primaryStage);
	}


	// updates display (will convert array to string)
	private void updateDisplay(String[] newDisplayContent, Text display, String del){
		display.setText(String.join(del,newDisplayContent));
	}
	private void updateDisplay(String newDisplayContent, Text display){
		display.setText(newDisplayContent);
	}

	// checks if guess is valid, returns true if yes, throws exception otherwise (will be caught)
	private boolean guessValid(String guess) throws EmptyInputException, ImproperInputFormException, ImproperInputSizeException{
		// Checks if a guess string is a letter (checks length of string=1, is a valid letter)
		char guessLetter;
		if(guess.length() == 1){
			guessLetter = guess.charAt(0);
			if(Character.isLetter(guessLetter)){
				return true;
			} else {
				throw new ImproperInputFormException();
			}
		} else if (guess.length() == 0){
			throw new EmptyInputException();
		} else {
			throw new ImproperInputSizeException();
		}
	}

	// this works by initializing a initial array of indices as -1, and will fill up as letter matches are found in the word. if the first item of the array is -1, means that there are no letter matches.
	// the # of letter matches in a word is equal to the number of items of the array that are not -1
	private Integer[] getLetterInstances(String word, char character){
		Integer[] letterInstances = new Integer[word.length()];
		Arrays.fill(letterInstances, -1);
		int ctr = 0;
		for (int i = 0; i < word.length(); i++){
			if(word.charAt(i) == character){
				letterInstances[ctr] = i;
				ctr++;
			}
		}
		return letterInstances;
	}

	private void printArray(Object[] arr){
		System.out.print("[");
		for (int i = 0; i < arr.length; i++){
			System.out.print(arr[i].toString() + ",");
		}
		System.out.println("]");
	}

	// did the user already guess the letter?
	private boolean alreadyContains(Character[] arr, Character obj){
		for (Character ch : arr){
			if(ch!=null && ch.equals(obj)){
				return true;
			}
		}

		return false;
	}

	private String outputCharacterArrAsString(Character[] arr, String del){
		String retVal = "";
		for (Character c: arr){
			if(c == null){
				break;
			}
			retVal += (c.toString() + del);
		}
		return retVal;
	}
	private void updateWord(Integer[] positions, String[] word, char letter){
		for (Integer i: positions){
			// a -1 element marks the end of the positions list
			if(i == -1){
				break;
			}
			word[i] = Character.valueOf(letter).toString();
		}
		updateDisplay(word,currentWordText,"");
		if(String.join("",currentProgress).equals(currentWord)){
			won=true;
		}
	}

	private void handleGuessField(ActionEvent event){

		String guess = guessField.getText().toLowerCase();
		char guessLetter = '-';
		Integer[] letterInstances;
		// checks if guess is valid, if not throws exception
		try {
			if (guessValid(guess)) {
				guessLetter = guess.charAt(0);
				if (alreadyContains(guesses, Character.valueOf(guessLetter))) {
					updateDisplay("'" + guessLetter + "' is already guessed!", outcomeText);
				} else {
					guesses[curWrongGuesses] = Character.valueOf(guessLetter);
					curWrongGuesses++;
					letterInstances = getLetterInstances(currentWord, guessLetter);
					printArray(letterInstances);
					if (letterInstances[0].equals(-1)) {
						updateDisplay("'" + guessLetter + "' is not in the word!", outcomeText);

					} else {
						updateWord(letterInstances, currentProgress, guessLetter);
						updateDisplay("'" + guessLetter + "' is in the word!", outcomeText);
					}

				}
				updateDisplay("Guessed letters: [" + outputCharacterArrAsString(guesses, ",") + "]", wrongGuessesText);
				guessField.setText("");
			// the else statement is unnessecary
			} else {
				System.out.println("Invalid guess!");
			}
			updateDisplay((MAX_WRONG_GUESSES - curWrongGuesses) + "/" + MAX_WRONG_GUESSES + " guesses remain.", wrongGuessNumberText);
		} catch (ImproperInputFormException | ImproperInputSizeException | EmptyInputException e){
			Alert exceptionAlert = new Alert(Alert.AlertType.ERROR, e.toString());
			exceptionAlert.show();
			updateDisplay(e.getMessage(),outcomeText);
		}

		// ends game if user won or ran out of guesses
		if(won) {
			updateDisplay("Congratulations! You won!\nClick below to play again", outcomeText);
			endGame();
		} else if (curWrongGuesses == MAX_WRONG_GUESSES){
			updateDisplay(currentWord,currentWordText);
			updateDisplay("Sorry, you lost.\nTry again with the button below.", outcomeText);
			endGame();
		}
	}

	private String chooseWord(){
		String returnWord = "";
		int ctr = 0;
		int wordNo = 0;
		int words = 0;

		try {
			FileInputStream fis = new FileInputStream(new File("words.txt").getAbsolutePath());
			wordNo = rand.nextInt(34990);
			Scanner rdr = new Scanner(fis);
			while (rdr.hasNextLine()){
				ctr++;
				if(ctr == wordNo){
					returnWord = rdr.nextLine();
					break;
				}
				else { rdr.nextLine(); }
			}
			if(returnWord.equals("")){
				returnWord = "empty";
			}



		} catch (FileNotFoundException fnfe) {

			Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Words.txt file is missing!");
			errorAlert.showAndWait();
			guessField.setEditable(false);
			returnWord = "";
		}

		return returnWord;
	}

	// restarts game (from button press)
	private void restart(ActionEvent actionEvent) {
		setupGame();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
