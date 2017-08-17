package com.cortexanonymus.client.ui.hex;

import com.google.gwt.user.client.ui.Image;

public class ModularHex extends Hex {
  private Image img;
  
  public ModularHex(int type, int x, int y) {
    super(type, x, y);
  }

  public void setBackground(Image img) {
    this.img = img;
  }

  public Image getBackground() {
    return img;
  }
}
