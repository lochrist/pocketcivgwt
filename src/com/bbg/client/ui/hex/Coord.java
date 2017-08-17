package com.bbg.client.ui.hex;

public class Coord {
  public int x = 0;

  public int y = 0;

  public Coord() {

  }

  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public String toString() {
    return "[" + x + "," + y + "]";
  }
}