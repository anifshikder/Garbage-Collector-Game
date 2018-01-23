package sprites;

import game.Constants;

public class Vacuum extends Sprite {

  private int score;
  private int capacity;
  private int fullness;
  private Sprite under;

  /**
   * @param symbol
   * @param row
   * @param column
   */
  public Vacuum(char symbol, int row, int column, int capacity) {
    super(symbol, row, column);
    this.capacity = capacity;
    this.under = new CleanHallway(row, column);
  }

  /**
   * This method is used to move a dust ball object to a desired row and column
   * 
   * @param row
   * @param column
   */
  public void moveTo(int row, int column) {
    updateCoordinates(row, column);

  }

  /**
   * This method first checks if a vacuum is full or not. Then it proceeds to clean a dirt object
   * given the vacuum is not yet full. If the process of cleaning is successful, then true is
   * returned, or else the result is false
   * 
   * @param score
   * @return result
   */
  public boolean clean(int score) {
    boolean result;
    // check if the vacuum is full
    if (fullness == capacity) {
      result = false;
    } else {
      // increment score
      this.score += score;
      // increment fullness
      this.fullness += Constants.FULLNESS_INC;
      result = true;
    }
    return result;
  }

  /**
   * This method empties out the contents of a vacuum by resetting the capacity value.
   */
  public void empty() {
    this.capacity = Constants.EMPTY;
  }

  /**
   * This method returns the sprite object under the vacuum
   * 
   * @return the under
   */
  public Sprite getUnder() {
    return under;
  }

  /**
   * This method sets a sprite object under the vacuum
   * 
   * @param under the under to set
   */
  public void setUnder(Sprite under) {
    this.under = under;
  }

  /**
   * This method returns the score of a vacuum
   * 
   * @return the score
   */
  public int getScore() {
    return score;
  }
}
