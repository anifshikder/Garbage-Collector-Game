package sprites;

import game.Constants;

public class DustBall extends Dirt {

  /**
   * This method initializes a dustball
   * 
   * @param symbol
   * @param row
   * @param column
   * @param value
   */
  public DustBall(int row, int column, int value) {
    super(Constants.DUST_BALL, row, column, value);
  }

  /**
   * Given a certain row and a column, this method moves a dustball
   * 
   * @param row
   * @param column
   */
  public void moveTo(int row, int column) {
    updateCoordinates(row, column);
  }
}
