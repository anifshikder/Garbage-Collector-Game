package ui;

import java.awt.HeadlessException;
import java.util.Scanner;

import javax.swing.JFrame;

import game.Constants;
import game.VacuumGame;

public class TextUi implements Ui {

  private VacuumGame game;

  /**
   * @param game
   * @throws HeadlessException
   */
  public TextUi(VacuumGame game) throws HeadlessException {
    super();
    this.game = game;
  }

  /**
   * This method takes in a user input and plays the game until its over at which point a winner is
   * displayed. For each move, the game scenario is printed out on to the screen
   */
  @Override
  public void launchGame() {
    // while the game aint over
    while (!game.gameOver()) {
      // take user input
      Scanner in = new Scanner(System.in);
      // take only the first character
      char character = in.next().charAt(0);
      // move the player
      game.move(character);
      // print the game
      printgame();
    }
    // display the winner
    displayWinner();
  }

  /**
   * This method displays the winner of the game. If it is a tie "Its a tie" is displayed, if P1
   * wins then "Bazinga! P1" is displayed and if P2 wins then "Bazinga! P2" is displayed
   */
  @Override
  public void displayWinner() {
    char winner = game.getWinner();

    if (winner == Constants.TIE) {
      System.out.println("Its a tie");
    } else if (winner == Constants.P1) {
      System.out.println("Bazinga! P1");
    } else if (winner == Constants.P2) {
      System.out.println("Bazinga! P2");
    }
  }

  /**
   * This method prints out the game grid as a string
   */
  private void printgame() {
    String result = "";
    int numRows = game.getNumRows();
    int numCols = game.getNumColumns();
    // loop through each row and column and concatenate accordingly
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        result += game.getSprite(i, j).toString();
      }
      // add new line to hold form
      result += "\n";
    }
    // print result
    System.out.print(result);
  }
}
