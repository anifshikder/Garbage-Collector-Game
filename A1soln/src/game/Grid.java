package game;

import sprites.Sprite;

public abstract class Grid<T> {

  public abstract T getCell(int row, int column);

  public abstract void setCell(int row, int column, T item);

  public abstract int getNumRows();

  public abstract int getNumColumns();

  public abstract int hashCode();


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String result = "";
    int rows = getNumRows();
    int columns = getNumColumns();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        result += getCell(i, j);
      }
      result += "\n";
    }
    return result;
  }

  /**
   * This method check if the given object is equal to this. This is done by checking of both the
   * object has the same number of rows, columns and elements
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    boolean result = ((toString() == ((Grid<T>) obj).toString()));

    return result;
  }

}
