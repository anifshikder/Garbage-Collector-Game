package sprites;

public abstract class Sprite {

  private char symbol;
  private int row;
  private int column;

  /**
   * This method initializes a sprite object
   * 
   * @param symbol
   * @param row
   * @param column
   */
  public Sprite(char symbol, int row, int column) {
    super();
    this.symbol = symbol;
    this.row = row;
    this.column = column;
  }

  /**
   * This method returns the symbol of a sprite object
   * 
   * @return the symbol
   */
  public char getSymbol() {
    return symbol;
  }

  /**
   * This method gets the row of a sprite object
   * 
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * This method gets a column of a sprite object
   * 
   * @return the column
   */
  public int getColumn() {
    return column;
  }

  /**
   * This method update coordinates of a sprite object be setting its row and column
   */
  protected void updateCoordinates(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + column;
    result = prime * result + row;
    result = prime * result + symbol;
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Sprite other = (Sprite) obj;
    if (column != other.column)
      return false;
    if (row != other.row)
      return false;
    if (symbol != other.symbol)
      return false;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return symbol + "";
  }


}
