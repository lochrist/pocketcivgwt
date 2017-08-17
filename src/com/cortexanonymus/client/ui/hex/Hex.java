package com.cortexanonymus.client.ui.hex;

import com.google.gwt.user.client.ui.Image;

public class Hex {
  static public final int WATER = -2;

  static public final int FRONTIER = -1;

  private Image feature;
 
  private int x;

  private int y;

  private int type;
  
  public Hex(int type, int x, int y) {
    this.x = x;
    this.y = y;
    this.type = type;
  }

  /**
   * @return Returns the coord.
   */
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
  /**
   * @return Returns the type.
   */
  public int getType() {
    return type;
  }

  /**
   * @param type The type to set.
   */
  public void setType(int type) {
    this.type = type;
  }
  
  public Image getFeature() {
    return feature;
  }
  
  public void setFeature(Image img) {
    feature = img;
  }

}