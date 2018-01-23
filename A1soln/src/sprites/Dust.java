package sprites;

import game.Constants;

public class Dust extends Dirt {

  /**
   * This method initializes a dust
   * 
   * @param symbol
   * @param row
   * @param column
   * @param value
   */
  public Dust(int row, int column, int value) {
    super(Constants.DUST, row, column, value);
  }


}
