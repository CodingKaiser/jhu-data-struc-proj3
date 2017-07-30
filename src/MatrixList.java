/**
 * Created by falko on 24-07-17.
 */
class MatrixList {

  Node[] rows;
  int i;
  int j;
  Node curr;
  Node itemAbove;
  int maxDimens;

  private class Node {

    Fraction datum;
    Node prev;
    Node next;
    Node down;

    public Node(int x) {
      datum = new Fraction(x);
      prev = null;
      next = null;
      down = null;
    }
  }

  MatrixList(int dimens) {
    rows = new Node[dimens];
    maxDimens = dimens;
    i = 0;
    j = 0;
    curr = null;
  }

  void add(int x, int toI, int toJ) {
    if (curr == null && i == 0 && j == 0) {
      Node newItem = new Node(x);
      rows[0] = newItem;
      curr = newItem;
    } else if (toI == i + 1 && toJ == 0) {
      itemAbove = rows[i];
      Node newItem = new Node(x);
      curr = newItem;
      itemAbove.down = newItem;
      rows[toI] = newItem;
      i++;
      j = 0;
    } else {
      Node newItem = new Node(x);
      curr.next = newItem;
      newItem.prev = curr;
      curr = newItem;
      j++;
      if (i != 0) {
        itemAbove = itemAbove.next;
        itemAbove.down = newItem;
      }
    }
  }

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

  int calculateDeterminant() {
    int rowSwappedCorrection = 1;
    if (rows[0].datum.equalsZero()) {
      int rIndex = 0;
      while (rIndex < rows.length && rows[rIndex].datum.equalsZero()) {
        rIndex++;
      }
      if (rIndex == 0 || rIndex >= rows.length) {
        return 0;
      } else {
        swapFirstRowWithRowAt(rIndex);
        rowSwappedCorrection = -1;
        System.out.println("Switched rows!");
        printContents();
      }
    }
    Node currDiag = rows[0];
    Node currSubRowElementStart;
    while (currDiag != null) {
      if (currDiag.datum.equalsZero()) {
        return 0;
      }
      System.out.println("Before row adjustment:");
      printContents();
      Node currDiagRowElement = currDiag;
      currSubRowElementStart = currDiag.down;
      while (currSubRowElementStart != null) {
        Fraction multFactor = currSubRowElementStart.datum.div(currDiagRowElement.datum);
        if (!multFactor.equalsZero()) {
          Node currSubRowElement = currSubRowElementStart;
          while (currSubRowElement != null) {
            currSubRowElement.datum = currSubRowElement.datum.sub(multFactor.mult(currDiagRowElement.datum));
            currSubRowElement = currSubRowElement.next;
            currDiagRowElement = currDiagRowElement.next;
          }
        }
        currSubRowElementStart = currSubRowElementStart.down;
        currDiagRowElement = currDiag;
      }
      System.out.println("Finished another diagonal");
      printContents();
      currDiag = currDiag.down;
      if (currDiag != null) {
        currDiag = currDiag.next;
      }
    }
    Fraction result;
    Node currEchelon = rows[0];
    result = currEchelon.datum;
    while (currEchelon != null) {
      currEchelon = currEchelon.down;
      if (currEchelon != null) {
        currEchelon = currEchelon.next;
        result = result.mult(currEchelon.datum);
      }
    }
    return rowSwappedCorrection * result.toInt();
  }

  private void swapFirstRowWithRowAt(int rowToSwap) {
    Node zerothTemp = rows[0];
    Node temp = rows[rowToSwap];
    if (rowToSwap == 1) {
      while (temp != null) {
        zerothTemp.down = temp.down;
        temp.down = zerothTemp;
        temp = temp.next;
        zerothTemp = zerothTemp.next;
      }
    } else {
      Node aboveTemp = rows[rowToSwap - 1];
      while (temp != null) {
        aboveTemp.down = zerothTemp;
        Node belowZeroth = zerothTemp.down;
        zerothTemp.down = temp.down;
        temp.down = belowZeroth;
        temp = temp.next;
        zerothTemp = zerothTemp.next;
        aboveTemp = aboveTemp.next;
      }
    }
    Node anchor = rows[rowToSwap];
    rows[rowToSwap] = rows[0];
    rows[0] = anchor;
  }
}