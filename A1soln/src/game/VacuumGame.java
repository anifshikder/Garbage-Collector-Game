package game;

import sprites.CleanHallway;
import sprites.Dirt;
import sprites.Dumpster;
import sprites.Dust;
import sprites.DustBall;
import sprites.Sprite;
import sprites.Vacuum;
import sprites.Wall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that represents the basic functionality of the vacuum game. This class is responsible for
 * performing the following operations: 1. At creation, it initializes the instance variables used
 * to store the current state of the game. 2. When a move is specified, it checks if it is a legal
 * move and makes the move if it is legal. 3. It reports information about the current state of the
 * game when asked.
 * 
 * @param Enters <T>
 */
public class VacuumGame<T> {

  private Random random; // a random number generator to move the DustBalls
  private Grid<Sprite> grid; // the grid
  private Vacuum vacuum1; // the first player
  private Vacuum vacuum2; // the second player
  private List<Dust> dusts; // the dusts
  private List<DustBall> dustBalls; // the dust balls

  /**
   * Creates a new <code>VacuumGame</code> that corresponds to the given input text file. Assumes
   * that the input file has one or more lines of equal lengths, and that each character in it
   * (other than newline) is a character that represents one of the <code>Sprite</code>s in this
   * game. Uses gridType to implement the grid.
   * 
   * @param layoutFileName path to the input grid file
   * @param gridType the type of grid implementation to use
   */
  public VacuumGame(String layoutFileName, Constants.GridType gridType) throws IOException {
    dusts = new ArrayList<>();
    dustBalls = new ArrayList<>();
    random = new Random();

    // open the file, read the contents, and determine dimensions of the grid
    int[] dimensions = getDimensions(layoutFileName);
    int numRows = dimensions[0];
    int numColumns = dimensions[1];

    if (gridType.equals(Constants.GridType.LIST_GRID)) {
      grid = new ListGrid<>(numRows, numColumns);
    } else {
      grid = new MapGrid<>(numRows, numColumns);
    }

    // open the file again, read the contents, and store them in grid

    Scanner sc = new Scanner(new File(layoutFileName));
    for (int row = 0; row < numRows; row++) {
      String nextLine = sc.nextLine();

      /********
       * Initialize the grid
       ********/
      for (int column = 0; column < nextLine.length(); column++) {
        char character = nextLine.charAt(column);
        chrToSpr(character, row, column);
      }
    }
    sc.close();
  }

  /*********
   * Lots of methods.
   * @param <T>
   ************/
  /**
   * This method looks at a character and matches it to a certain type of sprite Afterwards it
   * creates the corresponding objects and sets the cell to given rows and columns.
   * 
   * @param character
   * @param row
   * @param column
   */
  private void chrToSpr(char character, int row, int column) {

    // if the character is a clean hallway
    if (character == Constants.CLEAN) {
      CleanHallway result = new CleanHallway(row, column);
      grid.setCell(row, column, result);
    }
    // if the character is a wall
    else if (character == Constants.WALL) {
      Wall result = new Wall(row, column);
      grid.setCell(row, column, result);
    }
    // if the character is a player one
    // initialize vacuum 1
    else if (character == Constants.P1) {
      Vacuum result = new Vacuum(Constants.P1, row, column, Constants.CAPACITY);
      this.vacuum1 = result;
      grid.setCell(row, column, result);
    }
    // if the character is a player two
    // initialize vacuum 2
    else if (character == Constants.P2) {
      Vacuum result = new Vacuum(Constants.P2, row, column, Constants.CAPACITY);
      this.vacuum2 = result;
      grid.setCell(row, column, result);
    }
    // if the character is a dust
    // initialize dusts
    else if (character == Constants.DUST) {
      Dust result = new Dust(row, column, Constants.DUST_SCORE);
      grid.setCell(row, column, result);
      dusts.add(result);
    }
    // if the character is a dustball
    // initialize dustBalls
    else if (character == Constants.DUST_BALL) {
      DustBall result = new DustBall(row, column, Constants.DUST_BALL_SCORE);
      grid.setCell(row, column, result);
      dustBalls.add(result);
    }
    // if the character is a dumpster
    else if (character == Constants.DUMPSTER) {
      Dumpster result = new Dumpster(row, column);
      grid.setCell(row, column, result);
    }
  }

  /**
   * This returns the number of rows the game has
   * 
   * @return NumRows
   */
  public int getNumRows() {
    int NumRows = grid.getNumRows();
    return NumRows;
  }

  /**
   * This returns the number of column the game has
   * 
   * @return NumColumns
   */
  public int getNumColumns() {
    int NumColumns = grid.getNumColumns();
    return NumColumns;
  }

  /**
   * This method returns the sprite located at a certain row and column
   * 
   * @return result
   */
  public Sprite getSprite(int row, int col) {
    Sprite result = grid.getCell(row, col);
    return result;
  }

  /**
   * This method returns whatever grid object (list or map implementation).
   * 
   * @return the grid
   */
  public Grid<Sprite> getGrid() {
    return grid;
  }

  /**
   * This method returns the vacuum 1
   * 
   * @return the vacuum1
   */
  public Vacuum getVacuumOne() {
    return vacuum1;
  }

  /**
   * This method returns the vacuum 2
   * 
   * @return the vacuum2
   */
  public Vacuum getVacuumTwo() {
    return vacuum2;
  }

  /**
   * This method checks the size of dusts and dustballs when the size for both of them are zero.
   * then the game ends.
   * 
   * @return result
   */
  public boolean gameOver() {
    // start with false
    boolean result = false;
    // if the condition is met, become true
    if ((dusts.size() == 0) && (dustBalls.size() == 0)) {
      result = true;
    }
    return result;
  }

  /**
   * Given a move character, this game makes the corresponding player move to the corresponding cell.
   * 
   * @param nextMove
   */
  public void move(char nextMove) {

    int newcol1 = vacuum1.getColumn();
    int newrow1 = vacuum1.getRow();
    int newcol2 = vacuum2.getColumn();
    int newrow2 = vacuum2.getRow();
    
    // If the move is to move right for player one
    if (nextMove == Constants.P1_RIGHT) {
      newcol1 = newcol1 + 1;
      if (validForVacuum(newrow1, newcol1)) {
        moveHelperOne(newrow1, newcol1, vacuum1);
      }
    
    // If the move is to move left for player one
    } else if (nextMove == Constants.P1_LEFT) {
      newcol1 = newcol1 - 1;
      if (validForVacuum(newrow1, newcol1)) {
        moveHelperOne(newrow1, newcol1, vacuum1);
      }
    
    // If the move is to move up for player one
    } else if (nextMove == Constants.P1_UP) {
      newrow1 = newrow1 - 1;
      // If the requested move is valid, use the helper
      // method of completion
      if (validForVacuum(newrow1, newcol1)) {
        moveHelperOne(newrow1, newcol1, vacuum1);
      }
      
    // If the move is to move down for player one
    } else if (nextMove == Constants.P1_DOWN) {
      newrow1 = newrow1 + 1;
      // If the requested move is valid, use the helper
      // method of completion
      if (validForVacuum(newrow1, newcol1)) {
        moveHelperOne(newrow1, newcol1, vacuum1);
      }
      
    // If the move is to move right for player two
    } else if (nextMove == Constants.P2_RIGHT) {
      newcol2 = newcol2 + 1;
      // If the requested move is valid, use the helper
      // method of completion
      if (validForVacuum(newrow2, newcol2)) {
        moveHelperOne(newrow2, newcol2, vacuum2);
      }
      
    // If the move is to move left for player two
    } else if (nextMove == Constants.P2_LEFT) {
      newcol2 = newcol2 - 1;
      // If the requested move is valid, use the helper
      // method of completion
      if (validForVacuum(newrow2, newcol2)) {
        moveHelperOne(newrow2, newcol2, vacuum2);
      }
      
    // If the move is to move up for player two
    } else if (nextMove == Constants.P2_UP) {
      newrow2 = newrow2 - 1;
      // If the requested move is valid, use the helper
      // method of completion
      if (validForVacuum(newrow2, newcol2)) {
        moveHelperOne(newrow2, newcol2, vacuum2);
      }
      
    // If the move is to move down for player two
    } else if (nextMove == Constants.P2_DOWN) {
      newrow2 = newrow2 + 1;
      // If the requested move is valid, use the helper
      // method of completion
      if (validForVacuum(newrow2, newcol2)) {
        moveHelperOne(newrow2, newcol2, vacuum2);
      }
    }
  }
  
  /**
   * Given the row to move, the column to move and the vacuum object, this method carries out the
   * process of moving for a vacuum object from a certain cell to a certain cell
   * 
   * @param newrow
   * @param newcol
   * @param vacuum
   */
  private void moveHelperOne(int newrow, int newcol, Vacuum vacuum) {

    // Get the object residing in the cell where the vacuum wants to move
    // Then get that vacuum's symbol
    Sprite adjacentObject = grid.getCell(newrow, newcol);
    char adjacentObjectSymbol = adjacentObject.getSymbol();
    
    Sprite under = vacuum.getUnder(); // Take what's under
    int intialrow = vacuum.getRow(); // Get the initial row of the vacuum
    int initialcol = vacuum.getColumn(); // Get the initial column of the vacuum
    
    // If the vacuum is a dust
    if (adjacentObjectSymbol == Constants.DUST) {
      int dustscore = Constants.DUST_SCORE; // Get the score for a dust
      vacuum.moveTo(newrow, newcol); // Move to the new spot
      grid.setCell(newrow, newcol, vacuum); // set the cell to make the move happen in grid
      grid.setCell(intialrow, initialcol, under); // reveal the previous cell
      vacuum.setUnder(adjacentObject);
      
      // if the clean method checks out and the vacuum is not full
      if (vacuum.clean(dustscore)) {
        CleanHallway clean = new CleanHallway(newrow, newcol); // make a hallway object
        vacuum.setUnder(clean); // set the object under for the vacuum
        removedust(newrow, newcol); // remove a dust from our tracking of the dusts
      }
      //ballsBath(); // Balls Bath !

    } else if (adjacentObjectSymbol == Constants.DUST_BALL) {
      int dustballscore = Constants.DUST_BALL_SCORE; // Get the score for a dust ball
      
      vacuum.moveTo(newrow, newcol); // Move to the new spot
      grid.setCell(newrow, newcol, vacuum); // set the cell to make the move happen in grid
      grid.setCell(intialrow, initialcol, under); // reveal the previous cell
      vacuum.setUnder(adjacentObject);
      // if the clean method checks out and the vacuum is not full
      if (vacuum.clean(dustballscore)) {
        CleanHallway clean = new CleanHallway(newrow, newcol); // make a hallway object
        vacuum.setUnder(clean); // set the object under for the vacuum
        removedustB(newrow, newcol); // remove a dust ball from our tracking of the dustballs
      }
      //ballsBath(); // Balls Bath !

    } else if (adjacentObjectSymbol == Constants.DUMPSTER) {

      vacuum.moveTo(newrow, newcol); // Move to the new spot
      grid.setCell(newrow, newcol, vacuum); // set the cell to make the move happen in grid
      grid.setCell(intialrow, initialcol, under); // reveal the previous cell

      vacuum.setUnder(adjacentObject); // set the object under for the vacuum
      vacuum.empty(); // dump all the dusts
      //ballsBath(); // Balls Bath !

    } else if (adjacentObjectSymbol == Constants.CLEAN) {

      vacuum.moveTo(newrow, newcol); // Move to the new spot
      grid.setCell(newrow, newcol, vacuum); // set the cell to make the move happen in grid
      grid.setCell(intialrow, initialcol, under); // reveal the previous cell

      vacuum.setUnder(adjacentObject); // set the object under for the vacuum
      //ballsBath(); // Balls Bath !
    }
  }

  /**
   * Given the row and a column, this method removes the dust particle situated in that dimension.
   * 
   * @param row
   * @param col
   */
  private void removedust(int row, int col) {

    // iterate through our listing of the dust objects
    // iff our dust objects matches with the dust object we are trying to remove
    // remove it
    for (int i = 0; i < dusts.size(); i++) {
      Dust theone = dusts.get(i);
      int theonerow = theone.getRow();
      int theonecol = theone.getColumn();
      if ((row == theonerow) && (col == theonecol)) {
        dusts.remove(i);
      }
    }
  }

  /**
   * Given the row and a column, this method removes the dust ball particle situated in that
   * dimension.
   * 
   * @param row
   * @param col
   */
  private void removedustB(int row, int col) {

    // iterate through our listing of the dust objects
    // iff our dust objects matches with the dust object we are trying to remove
    // remove it
    for (int i = 0; i < dustBalls.size(); i++) {

      DustBall theone = dustBalls.get(i);
      int theonerow = theone.getRow();
      int theonecol = theone.getColumn();
      if ((row == theonerow) && (col == theonecol)) {
        dustBalls.remove(i);
      }
    }
  }

  /**
   * check if the requested move is a valid one.
   * 
   * @param newrow
   * @param newcol
   * @return result
   */
  private boolean validForVacuum(int newrow, int newcol) {
    boolean result = true;
    Sprite objs = grid.getCell(newrow, newcol);
    char a = objs.getSymbol();
    if ((a == Constants.WALL) || (a == Constants.P1) || (a == Constants.P2)) {
      result = false;
    }
    return result;
  }

  /**
   * This method first generates a random value within the bound of 4. Then given the we have one or
   * more dustballs, it to moves each dust ball randomly (with the help of a helper method). The
   * only exception where a dust ball doesnt move is if it is under a vacuum
   */
  private void ballsBath() {
    // Declare a random int
    int whichside = random.nextInt(4);
    // get every dustball from the list
    if (dustBalls.size() > 0) {
      for (int i = 0; i < dustBalls.size(); i++) {
        DustBall dustball = dustBalls.get(i);
        // if not under a vacuum
        if (((vacuum1.getColumn() != dustball.getColumn())
            && (vacuum1.getRow() != dustball.getRow()))
            || (((vacuum2.getColumn() != dustball.getColumn())
                && (vacuum2.getRow() != dustball.getRow())))) {
          // move the dustballs randomly
          wheretomove(whichside, dustball);
        }
      }
    }
  }

  /**
   * Given a certain move token and a dustball, this method moves the dustball to either right,
   * left, up, or down under a vacuum
   * 
   * @param whichside
   * @param dustball
   */
  private void wheretomove(int whichside, DustBall dustball) {
    // Get the dustball's initial dimensions
    int row = dustball.getRow();
    int column = dustball.getColumn();
    // If 0, the row shifts up by one
    if (whichside == 0) {
      row += 1;
      // Helper method for moving each dustball
      eachBallMove(dustball, row, column);
      // If 1, the row shifts down by one
    } else if (whichside == 1) {
      row = row - 1;
      // Helper method for moving each dustball
      eachBallMove(dustball, row, column);
      // If 2, the column shifts up by one
    } else if (whichside == 2) {
      column += 1;
      // Helper method for moving each dustball
      eachBallMove(dustball, row, column);
      // If 3, the column shifts down by one
    } else if (whichside == 3) {
      column = column - 1;
      // Helper method for moving each dustball
      eachBallMove(dustball, row, column);
    }
  }

  /**
   * Given a certain dust ball, row and a column, this method moves the dust ball to that certain
   * place. under a vacuum
   * 
   * @param dustball
   * @param row
   * @param column
   */
  private void eachBallMove(DustBall dustball, int row, int column) {

    // Get the sprite object located at the desired location of travel
    Sprite nextobj = grid.getCell(row, column);
    // Find that sprites symbol
    char a = nextobj.getSymbol();
    // Check if the following move is valied for a dustball

    if (validForDustBalls(row, column)) {
      if (a == Constants.DUST) {
        // Get the initial dimensions of the dust ball
        int dustobjectrow = dustball.getRow();
        int dustobjectcolumn = dustball.getColumn();
        // create a dust object at that place
        Dust dustobject = new Dust(dustobjectrow, dustobjectcolumn, Constants.DUST_SCORE);

        // move the dust ball
        // reveal the dust
        dustball.moveTo(row, column);
        grid.setCell(dustobjectrow, dustobjectcolumn, dustobject);
        grid.setCell(row, column, dustball);

      } else if (a == Constants.CLEAN) {
        // Get the initial dimensions of the dust ball
        int dustobjectrow = dustball.getRow();
        int dustobjectcolumn = dustball.getColumn();
        // create a dust object at that place
        Dust dustobject = new Dust(dustobjectrow, dustobjectcolumn, Constants.DUST_SCORE);
        // start tracking the dust particle
        dusts.add(dustobject);

        // move the dust ball
        // reveal the dust
        dustball.moveTo(row, column);
        grid.setCell(dustobjectrow, dustobjectcolumn, dustobject);
        grid.setCell(row, column, dustball);
      }
    }
  }

  /**
   * Given a certain row and a column, this method check is the move is a valid one for a dust ball
   * (i.e. the next object could only be a dust of clean hall)
   * 
   * @param row
   * @param column
   */
  private boolean validForDustBalls(int newrow, int newcol) {
    boolean result = false;
    Sprite objs = grid.getCell(newrow, newcol);
    char a = objs.getSymbol();
    if ((a == Constants.CLEAN) || (a == Constants.DUST)) {
      result = true;
    }
    return result;
  }

  /**
   * This method checks the score for both vacuums, should both of them have the same socre, it is a
   * tie. Or else, this method returns a winners
   */
  public char getWinner() {
    // check scores for both vacuum
    int vacuum1score = vacuum1.getScore();
    int vacuum2score = vacuum2.getScore();
    // the game is a tie by default
    char result = Constants.TIE;
    // unless a clear winner is established
    if (vacuum1score > vacuum2score) {
      result = Constants.P1;
    } else if (vacuum2score > vacuum1score) {
      result = Constants.P2;
    }
    return result;
  }

  /**
   * Returns the dimensions of the grid in the file named layoutFileName.
   * 
   * @param layoutFileName path of the input grid file
   * @return an array [numRows, numCols], where numRows is the number of rows and numCols is the
   *         number of columns in the grid that corresponds to the given input grid file
   * @throws IOException if cannot open file layoutFileName
   */
  private int[] getDimensions(String layoutFileName) throws IOException {

    Scanner sc = new Scanner(new File(layoutFileName));

    // find the number of columns
    String nextLine = sc.nextLine();
    int numCols = nextLine.length();

    // find the number of rows
    int numRows = 1;
    while (sc.hasNext()) {
      numRows++;
      nextLine = sc.nextLine();
    }

    sc.close();
    return new int[] {numRows, numCols};
  }
}