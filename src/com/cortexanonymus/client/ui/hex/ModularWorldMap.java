package com.cortexanonymus.client.ui.hex;

import com.cortexanonymus.client.ui.RegionDesc;
import com.cortexanonymus.client.util.Resource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class ModularWorldMap extends HexWorldMap {  
  public ModularWorldMap(int numHexX, int numHexY, int hexWidth, int hexHeight, int featureWidth, int featureHeight) {
    super(numHexX, numHexY, hexWidth, hexHeight,featureWidth,featureHeight);
    
  }

  public void addHex(Hex h) {
    Coord c = HexUtil.hexToPixel(h.getX(), h.getY());
    ModularHex mh = (ModularHex)h; 
    add(mh.getBackground(), c.x, c.y);
    mh.getBackground().setPixelSize(hexWidth,hexHeight);
    
    // TODO: help debugging
    // Label l = new Label("[" + h.getX() + "," + h.getY() + "]");
    // add(l,c.x + 10, c.y + 10);
    
    
    super.addHex(h);
  }

  protected Hex createHex(int type, int x, int y) {
    ModularHex h = new ModularHex(type, x, y);
    switch (type) {
    case Hex.FRONTIER:
      h.setBackground(new Image(Resource.createImgUrl("frontier.gif")));
      break;
    case Hex.WATER:
      h.setBackground(new Image(Resource.createImgUrl("water.gif")));
      break;
    default:
      h.setBackground(new Image(Resource.createImgUrl("hex" + type + ".gif")));
    }

    return h;
  }
     
  public RegionDesc selectRegion(int x, int y) {
    Coord c = HexUtil.pixelToHex(x,y);
    Hex h = getHex(c.x, c.y);
    for (int r = 0; r < getNumRegionDesc(); ++r) {
      HexRegionDesc rd = (HexRegionDesc)getRegionDesc(r);
      for (int i = 0; i < rd.getNumHex(); ++i) {
        if (rd.getHex(i) == h) {
          return rd;
        }
      }
    }
    return null;
  }

  public void createMap() {

    // Fill the rest with water:
    for (int y = 0; y < numHexY; ++y) {
      for (int x = 0; x < numHexX; ++x) {
        if (hexGrid[x][y] == null) {
          // Fill with water: 
          addWater(x, y);
        }
      }
    }

    super.createMap();
  }
}
