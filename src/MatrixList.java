/**
 * A MatrixList data structure. Stores a DxD matrix,
 * where D = the maximum dimension.
 *
 * 0 --> a --> b --> c --> d --> null
 *       |     |     |     |
 *       v     v     v     v
 * 1 --> e --> f --> g --> h --> null
 *       |     |     |     |
 *       v     v     v     v
 * 2 --> i --> j --> k --> l --> null
 *       |     |     |     |
 *       v     v     v     v
 * 3 --> m --> n --> o --> p --> null
 *       |     |     |     |
 *       v     v     v     v
 *      null  null  null  null
 *
 * Expects values to get added row-by-row, left-to-right.
 * Once the matrix is complete, the determinant can be calculated
 * by calling the calcDeterminant method. This method will convert
 * the matrix to row-echelon form and the calculate the product of the
 * diagonal. The result is the determinant.
 *
 * @Author: Falko Noe
 * @Version: 1.0
 */
class MatrixList {

  private Node[] rows;
  private int i;
  private int j;
  private Node curr;
  private Node itemAbove;
  private int maxDimens;

  /**
   * Class Node which serves as the building block for the
   * linked-implementation of the Matrix.
   */
  private class Node {

    Fraction datum;
    Node next;
    Node down;

    /**
     * Constructor for the Node class. Will return
     * a Node with the input value stored as a Fraction
     * and all its pointers to null.
     * @param x: The integer to store in the Node.
     */
    Node(int x) {
      datum = new Fraction(x);
      next = null;
      down = null;
    }
  }

  /**
   * Constructor for the MatrixList class. Creates
   * the header Nodes (stored within an array).
   * @param dimens: The maximum dimensions in terms of
   *              width and height of the matrix.
   */
  MatrixList(int dimens) {
    rows = new Node[dimens];
    maxDimens = dimens;
    i = 0;
    j = 0;
    curr = null;
  }

  /**
   * Adds a new value to the matrix at the specified row
   * and column index. The new value is stored in the form
   * of a Fraction within Node. Values are added from
   * left to right, row-by-row.
   * @param x: The integer to store in the matrix
   * @param toI: The row number at which the new
   *           value should be inserted.
   * @param toJ: The column number at which the new
   *           value should be inserted.
   */
  void add(int x, int toI, int toJ) {
    if (curr == null && i == 0 && j == 0) {
      /* If there is no element in matrix, add it
       * and assign curr reference to it */
      Node newItem = new Node(x);
      rows[0] = newItem;
      curr = newItem;
    } else if (toI == i + 1 && toJ == 0) {
      /* If we are starting a new row, need to do special
       * handling to reassign itemAbove and curr */
      itemAbove = rows[i]; // used to assign Node.down pointer
      Node newItem = new Node(x);
      curr = newItem;
      itemAbove.down = newItem;
      rows[toI] = newItem; // header points to new item
      i++;
      j = 0;
    } else {
      Node newItem = new Node(x);
      curr.next = newItem;
      curr = newItem;
      j++;
      if (i != 0) {
        itemAbove = itemAbove.next;
        itemAbove.down = newItem;
      }
    }
  }

  /**
   * Prints the matrix to out, delimited by spaces.
   */
  void printContents() {
    for (int i = 0; i < rows.length; i++) {
      Node x = rows[i];
      while (x != null) {
        System.out.print(x.datum);
        System.out.print(" ");
        x = x.next;
      }
      System.out.println();
    }
  }

  /**
   * Driver method for calculating the determinant of the matrix currently
   * in memory. Will call methods that swap the first row if need be,
   * convert the matrix to row echelon via matrix reduction, and
   * calculates the determinant by forming the product of the elements
   * in the diagonal.
   * @return: The int value of the determinant of the matrix
   */
  int calculateDeterminant() {
    int rowSwappedCorrection = 1;
    /* If the first item in the first row is 0, must swap the rows */
    if (rows[0].datum.equalsZero()) {
      int rIndex = 0;
      while (rIndex < rows.length && rows[rIndex].datum.equalsZero()) {
        rIndex++;
      }
      if (rIndex == 0 || rIndex >= rows.length) {
        return 0;
      } else {
        swapFirstRowWithRowAt(rIndex);
        // Adjust negative sign of final answer, since row was swapped
        rowSwappedCorrection = -1;
        System.out.println("Swapped rows: 1 and " + (rIndex + 1));
      }
    }
    /* Convert the MatrixList to row echelon form */
    Node currDiag = rows[0];
    Node currSubRowElementStart;
    while (currDiag != null) {
      // Iterate down the diagonal until we fall off the matrix
      if (currDiag.datum.equalsZero()) {
        // Early out, since the product of the diagonal will also be 0
        return 0;
      }
      Node currDiagRowElement = currDiag;
      currSubRowElementStart = currDiag.down;
      while (currSubRowElementStart != null) {
        // multFactor = quotient of start of row and the reference row
        Fraction multFactor = currSubRowElementStart.datum.div(
                currDiagRowElement.datum);
        if (!multFactor.equalsZero()) {
          Node currSubRowElement = currSubRowElementStart;
          /* Iterate right, subtracting value in row by
           * the product of the corresponding value in the reference
           * row and multFactor */
          while (currSubRowElement != null) {
            currSubRowElement.datum = currSubRowElement.datum.sub(
                    multFactor.mult(currDiagRowElement.datum));
            currSubRowElement = currSubRowElement.next;
            currDiagRowElement = currDiagRowElement.next;
          }
        }
        currSubRowElementStart = currSubRowElementStart.down;
        currDiagRowElement = currDiag;
      }
      // Advance pointer to next diagonal --> down & left
      currDiag = currDiag.down;
      if (currDiag != null) {
        currDiag = currDiag.next;
      }
    }
    return calcDiagonal() * rowSwappedCorrection;
  }

  /**
   * Steps through the diagonal in the MatrixList,
   * forming the product of all the values it encounters.
   * The absolute value of the return value equals
   * the absolute value of the determinant.
   * @return: The product of the diagonal.
   */
  private int calcDiagonal() {
    Fraction result;
    Node currEchelon = rows[0];
    result = currEchelon.datum;
    while (currEchelon != null) {
      // Multiply until we fall off the matrix
      currEchelon = currEchelon.down;
      if (currEchelon != null) {
        currEchelon = currEchelon.next;
        result = result.mult(currEchelon.datum);
      }
    }
    return result.toInt();
  }

  /**
   * Method which swaps the first row with the desired row.
   * This method is called when the first row contains a
   * "0" at the first position and needs to be swapped with
   * a row that does has a non-zero value in its first column.
   * @param rowToSwap: The index of the row we would like to
   *                 swap with the first one.
   */
  private void swapFirstRowWithRowAt(int rowToSwap) {
    Node zerothTemp = rows[0]; // select first row
    Node temp = rows[rowToSwap]; // select row x
    if (rowToSwap == 1) {
      while (temp != null) {
        // swap row down-pointers
        zerothTemp.down = temp.down;
        temp.down = zerothTemp;
        temp = temp.next; // advance to right
        zerothTemp = zerothTemp.next; // advance to right
      }
    } else {
      // must also reassign down pointers from row above
      Node aboveTemp = rows[rowToSwap - 1];
      while (temp != null) {
        // swap down pointers
        aboveTemp.down = zerothTemp;
        Node belowZeroth = zerothTemp.down;
        zerothTemp.down = temp.down;
        temp.down = belowZeroth;
        temp = temp.next;
        zerothTemp = zerothTemp.next; // advance to right
        aboveTemp = aboveTemp.next; // advance to right
      }
    }
    // update pointers in row array
    Node anchor = rows[rowToSwap];
    rows[rowToSwap] = rows[0];
    rows[0] = anchor;
  }
}