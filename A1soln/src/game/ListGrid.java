package game;

import java.util.ArrayList;
import java.util.List;

import sprites.Sprite;

public class ListGrid<T> extends Grid<T> {

  private List<List<T>> grid;
  private int numRows;
  private int numColumns;

  /**
   * this constructor method initializes a list of grids
   * 
   * @param numRows
   * @param numColumns
   */
  public ListGrid(int numRows, int numColumns) {
    super();
    ArrayList<List<T>> temp = new ArrayList<List<T>>(); // Declare a list within a list
    this.numRows = numRows; // initialize the row numbers
    this.numColumns = numColumns; // initialize the column number
    this.grid = temp; // initialize the grid with list of list implementation

    // loop through the number of rows and create that many number of list under the original list
    for (int rows = 0; rows < numRows; rows++) {
      ArrayList<T> list = new ArrayList<T>();
      temp.add(list);
    }

  }

  /**
   * This method returns the number of rows this grid has
   * 
   * @return the numRows
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * This method returns the number of column this grid has
   * 
   * @return the numColumns
   */
  public int getNumColumns() {
    return numColumns;
  }


  /**
   * This method returns the cell located at a certain row and column
   * 
   * @param row
   * @param column
   * @return T
   */
  public T getCell(int row, int column) {
    List<T> Row = grid.get(row);
    T result = Row.get(column);

    return result;

  }

  /**
   * This method sets the cell located at a certain row and column
   * 
   * @param row
   * @param column
   * @param T
   */
  public void setCell(int row, int column, T item) {
    List<T> Row = grid.get(row);
    if (Row.size() > column) {
      Row.remove(column);
    }
    Row.add(column, item);

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
    result = prime * result + ((grid == null) ? 0 : grid.hashCode());
    result = prime * result + numColumns;
    result = prime * result + numRows;
    return result;
  }

}
