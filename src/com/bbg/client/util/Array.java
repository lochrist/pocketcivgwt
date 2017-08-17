package com.bbg.client.util;

import java.util.Vector;

public class Array {
  static public void printArray(Object[] o) {
    System.out.println("Printing Array of " + o.length + " elements.");
    for (int i = 0; i < o.length; ++i) {
      System.out.println("#" + i + " " + o[i].toString());
    }
  }

  static public String toString(Object[] o) {
    StringBuffer sb = new StringBuffer(o.length);
    sb.append("Array of " + o.length + " elements.");
    for (int i = 0; i < o.length; ++i) {
      sb.append("#" + i + " " + o[i].toString());
    }
    return sb.toString();
  }

  static public void printArray(Vector v) {
    System.out.println("Printing Array of " + v.size() + " elements.");
    for (int i = 0; i < v.size(); ++i) {
      System.out.println("#" + i + " " + v.elementAt(i).toString());
    }
  }

  static public String toString(Vector v) {
    StringBuffer sb = new StringBuffer(v.size());
    sb.append("Array of " + v.size() + " elements.");
    for (int i = 0; i < v.size(); ++i) {
      sb.append("#" + i + " " + v.elementAt(i));
    }
    return sb.toString();
  }

  static public void fillVector(Object[] src, Vector toFill) {
    for (int i = 0; i < src.length; ++i) {
      toFill.addElement(src[i]);
    }
  }

  static public void quickSort(Object[] o, Comparator c) {
    quickSort(o, c, 0, o.length - 1);
  }

  static private void quickSort(Object[] o, Comparator c, int lo0, int hi0) {
    int lo = lo0;
    int hi = hi0;
    Object mid;

    if (hi0 > lo0) {

      /*
       * Arbitrarily establishing partition element as the midpoint of the
       * array.
       */
      mid = o[(lo0 + hi0) / 2];

      // loop through the array until indices cross
      while (lo <= hi) {
        /*
         * find the first element that is greater than or equal to the
         * partition element starting from the left Index.
         */
        while ((lo < hi0) && (c.compare(o[lo], mid) < 0))
          ++lo;

        /*
         * find an element that is smaller than or equal to the
         * partition element starting from the right Index.
         */
        while ((hi > lo0) && (c.compare(o[hi], mid) > 0))
          --hi;

        // if the indexes have not crossed, swap
        if (lo <= hi) {
          swap(o, lo, hi);
          ++lo;
          --hi;
        }
      }

      /*
       * If the right index has not reached the left side of array must
       * now sort the left partition.
       */
      if (lo0 < hi)
        quickSort(o, c, lo0, hi);

      /*
       * If the left index has not reached the right side of array must
       * now sort the right partition.
       */
      if (lo < hi0)
        quickSort(o, c, lo, hi0);

    }
  }

  static public void quickSort(Vector v, Comparator c) {
    quickSort(v, c, 0, v.size() - 1);
  }

  static private void quickSort(Vector v, Comparator c, int lo0, int hi0) {
    int lo = lo0;
    int hi = hi0;
    Object mid;

    if (hi0 > lo0) {

      /*
       * Arbitrarily establishing partition element as the midpoint of the
       * array.
       */
      mid = v.elementAt((lo0 + hi0) / 2);

      // loop through the array until indices cross
      while (lo <= hi) {
        /*
         * find the first element that is greater than or equal to the
         * partition element starting from the left Index.
         */
        while ((lo < hi0) && (c.compare(v.elementAt(lo), mid) < 0))
          ++lo;

        /*
         * find an element that is smaller than or equal to the
         * partition element starting from the right Index.
         */
        while ((hi > lo0) && (c.compare(v.elementAt(hi), mid) > 0))
          --hi;

        // if the indexes have not crossed, swap
        if (lo <= hi) {
          swap(v, lo, hi);
          ++lo;
          --hi;
        }
      }

      /*
       * If the right index has not reached the left side of array must
       * now sort the left partition.
       */
      if (lo0 < hi)
        quickSort(v, c, lo0, hi);

      /*
       * If the left index has not reached the right side of array must
       * now sort the right partition.
       */
      if (lo < hi0)
        quickSort(v, c, lo, hi0);

    }
  }

  static public class IntComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      return ((Integer) o1).intValue() - ((Integer) o2).intValue();
    }

    public boolean equals(Object o1, Object o2) {
      return o1.equals(o2);
    }
  }

  static private void swap(Object o[], int i, int j) {
    Object T;
    T = o[i];
    o[i] = o[j];
    o[j] = T;
  }

  static private void swap(Vector v, int i, int j) {
    Object T;
    T = v.elementAt(i);
    v.setElementAt(v.elementAt(j), i);
    v.setElementAt(T, j);
  }

  public static void main(String[] args) {
    Integer ar[] = new Integer[100];
    Vector v = new Vector();
    for (int i = 0; i < ar.length; ++i) {
      int ri = Math.abs(5) % 100;
      ar[i] = new Integer(ri);
      v.addElement(new Integer(ri));
    }

    for (int i = 0; i < ar.length; ++i) {
      System.out.println("#" + i + " a: " + ar[i] + " v: " + v.elementAt(i));
    }

    quickSort(ar, new IntComparator());
    quickSort(v, new IntComparator());

    for (int i = 0; i < ar.length; ++i) {
      System.out.println("#" + i + " a: " + ar[i] + " v: " + v.elementAt(i));
    }
    System.out.println("End");
  }
}
