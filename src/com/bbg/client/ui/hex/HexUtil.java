package com.bbg.client.ui.hex;


public class HexUtil {
  static private int hexWidth;

  static private int hexHeight;

  static private int hexR;

  static private int hexS;

  static private int hexH;

  static private double hexM;

  static private int hexHPlusS;

  static private int hexRTimes2;

  static private Coord hexToPixel = new Coord();

  static private Coord pixelToHex = new Coord();

  static public void set(int numHexX, int numHexY, int hw, int hh) {
    hexWidth = hw;
    hexHeight = hh;
    hexR = hexWidth / 2;
    hexS = (int) (hexR / Math.cos(Math.toRadians(30)));
    hexH = (int) (hexS * Math.sin(Math.toRadians(30)));
    hexM = (double) hexH / (double) hexR;
    hexHPlusS = hexH + hexS;
    hexRTimes2 = hexR * 2;
  }

  static public Coord hexToPixel(int x, int y) {
    hexToPixel.x = x * hexRTimes2 + (y & 1) * hexR;
    hexToPixel.y = y * hexHPlusS;
    return hexToPixel;
  }

  static public Coord pixelToHex(int x, int y) {
    int sectionX = x / hexRTimes2;
    int sectionY = y / hexHPlusS;
    int sectionPixelX = x % hexRTimes2;
    int sectionPixelY = y % hexHPlusS;

    if ((sectionY & 1) == 0) {
      // Section A:
      if (sectionPixelY < (hexH - sectionPixelX * hexM)) {
        // Left edge
        pixelToHex.y = sectionY - 1;
        pixelToHex.x = sectionX - 1;
      } else if (sectionPixelY < (-hexH + sectionPixelX * hexM)) {
        // Right edge
        pixelToHex.y = sectionY - 1;
        pixelToHex.x = sectionX;
      } else {
        // Middle
        pixelToHex.y = sectionY;
        pixelToHex.x = sectionX;
      }
    } else {
      // Section B     
      if (sectionPixelX >= hexR) {
        // Right size
        if (sectionPixelY < (2 * hexH - sectionPixelX * hexM)) {
          // Up triangle
          pixelToHex.x = sectionX;
          pixelToHex.y = sectionY - 1;
        } else {
          // Down triangle
          pixelToHex.x = sectionX;
          pixelToHex.y = sectionY;
        }
      } else if (sectionPixelX < hexR) {
        // Left side
        if (sectionPixelY < (sectionPixelX * hexM)) {
          // Up triangle
          pixelToHex.x = sectionX;
          pixelToHex.y = sectionY - 1;
        } else {
          // Down triangle
          pixelToHex.x = sectionX - 1;
          pixelToHex.y = sectionY;
        }
      } else {
        assert false;
      }
    }

    return pixelToHex;
  }

  /**
   * @return Returns the hexH.
   */
  public static int getHexH() {
    return hexH;
  }

  /**
   * @return Returns the hexHeight.
   */
  public static int getHexHeight() {
    return hexHeight;
  }

  /**
   * @return Returns the hexM.
   */
  public static double getHexM() {
    return hexM;
  }

  /**
   * @return Returns the hexR.
   */
  public static int getHexR() {
    return hexR;
  }

  /**
   * @return Returns the hexS.
   */
  public static int getHexS() {
    return hexS;
  }

  /**
   * @return Returns the hexWidth.
   */
  public static int getHexWidth() {
    return hexWidth;
  }

  /**
   * @return Returns the hexHPlusS.
   */
  public static int getHexHPlusS() {
    return hexHPlusS;
  }

}
