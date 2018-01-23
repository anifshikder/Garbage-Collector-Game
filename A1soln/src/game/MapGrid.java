package game;

import java.util.HashMap;
import java.util.Map;

public class MapGrid<T> extends Grid<T> {

  private Map<Integer, Map<Integer, T>> grid;
  private int numRows;
  private int numColumns;

  /**
   * This constructor creates and initializes a grid.
   * 
   * @param numRows
   * @param numColumns
   */
  public MapGrid(int numRows, int numColumns) {
    super();
    Map<Integer, Map<Integer, T>> temp = new HashMap<Integer, Map<Integer, T>>(); // Declare a map
                                                                                  // within a map
    this.numRows = numRows; // initialize the row numbers
    this.numColumns = numColumns; // initialize the column number
    this.grid = temp; // initialize the grid with map of map implementation

    // loop through the number of rows and create that many number of maps under the original map
    for (int rows = 0; rows <= numRows; rows++) {
      Map<Integer, T> map = new HashMap<Integer, T>();
      temp.put(rows, map);
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

    Map<Integer, T> Row = grid.get(row); // get the row map
    T result = Row.get(column); // get the item from the row map

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

    Map<Integer, T> Row = grid.get(row); // get the row map
    Row.put(column, item); // set the item inside the row map
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
