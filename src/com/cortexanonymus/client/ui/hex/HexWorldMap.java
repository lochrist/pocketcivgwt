package com.cortexanonymus.client.ui.hex;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.RegionDesc;
import com.cortexanonymus.client.ui.WorldMap;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class HexWorldMap extends AbsolutePanel implements WorldMap {
  protected Vector regions = new Vector();
  
  protected Vector adjacentHex = new Vector();

  protected int numHexX;

  protected int numHexY;

  protected Hex hexGrid[][];

  protected int hexWidth;

  protected int hexHeight;
  
  protected int featureWidth;
  
  protected int featureHeight;
  
  static protected HexWorldMap instance;

  public HexWorldMap(int numHexX, int numHexY, int hexWidth, int hexHeight, int featureWidth, int featureHeight) {
    this.numHexX = numHexX;
    this.numHexY = numHexY;
    this.hexWidth = hexWidth;
    this.hexHeight = hexHeight;
    this.featureWidth = featureWidth;
    this.featureHeight = featureHeight;

    hexGrid = new Hex[numHexX][];
    for (int c = 0; c < numHexX; ++c) {
      hexGrid[c] = new Hex[numHexY];
    }
    HexUtil.set(numHexX, numHexY, hexWidth, hexHeight);
    
    setStyleName("world-map");
  }
  
  static public HexWorldMap get() {
    return instance;
  }

  public RegionDesc addRegion(Region region) {
    RegionDesc d = new HexRegionDesc(region);
    regions.add(d);
    return d;
  }
  
  public int getNumRegionDesc() {
    return regions.size();
  }
  
  public RegionDesc getRegionDesc(int i) {
    return (RegionDesc)regions.elementAt(i);
  }
  
  public Iterator getRegionDescIterator() {
    return regions.iterator();
  }

  public void addFrontier(int x, int y) {
    addHex(createHex(Hex.FRONTIER, x, y));
  }

  public void addWater(int x, int y) {
    addHex(createHex(Hex.WATER, x, y));
  }

  public void addHex(Hex h) {
    hexGrid[h.getX()][h.getY()] = h;
  }

  public void createMap() {
    for (int r = 0; r < regions.size(); ++r) {
      HexRegionDesc desc = (HexRegionDesc)regions.elementAt(r);
      
      HashSet adjacentRegions = new HashSet(4);
      for (int h = 0; h < desc.getNumHex(); ++h) {
        Hex hex = desc.getHex(h);
        Vector adjacentHex = getAdjacentHex(hex.getX(), hex.getY());
        for (int ah = 0; ah < adjacentHex.size(); ++ah) {
          Hex ahex = (Hex)adjacentHex.elementAt(ah);
          if (ahex.getType() != desc.getRegion().getId()) {
            // Has sea Access
            if (ahex.getType() == Hex.WATER ) {
              desc.getRegion().setHasSeaAccess(true);
            }
            else if (ahex.getType() == Hex.FRONTIER) {
              desc.getRegion().setHasFrontierAccess(true);
            }
            else {
              // RegionId : 1 based
              // Region index : 0 based
              // Hex Type: corresponds to RegionId
              adjacentRegions.add(getRegionDesc(ahex.getType()).getRegion());
            }                               
          }
        }
      }      
      
      Iterator it = adjacentRegions.iterator();
      while (it.hasNext()) {
        desc.getRegion().addAdjacentRegion((Region)it.next());
      }
      
      desc.createRegion();
      desc.update();
    }
    
    setPixelSize(hexWidth * numHexX, HexUtil.getHexHPlusS() * numHexY + HexUtil.getHexH());
  }

  public Hex getHex(int x, int y) {
    return hexGrid[x][y];
  }
  
  public Widget getUi() {
    return this;
  }
  
  protected Vector getAdjacentHex(int x, int y) {
    adjacentHex.clear();
    if ((y & 1) == 0) {     
      /*
       * it's an even row
       *     [1,1]   [2,1]
       *    [1,2] [2,2] [3,2] 
       *     [1,3]   [2,3]
       */
      
      if (x - 1 >= 0) {
        if (y - 1 >= 0) {
          adjacentHex.add(hexGrid[x - 1][y - 1]);
        }
        
        adjacentHex.add(hexGrid[x - 1][y]);
        
        if (y + 1 < numHexY) {
          adjacentHex.add(hexGrid[x - 1][y + 1]);
        }
      }
      
      if (y - 1 >= 0 ) {
        adjacentHex.add(hexGrid[x][y-1]);
      }
      
      if (x + 1 < numHexX) {
        adjacentHex.add(hexGrid[x+1][y]);
      }
      
      if (y + 1 < numHexY ) {
        adjacentHex.add(hexGrid[x][y+1]);
      }
      
    }
    else {
      /*
       * it's an odd row
       *     [2,2]   [3,2]
       *    [1,3] [2,3] [3,3] 
       *     [2,4]   [3,4]
       */
      if (y - 1 >= 0 ){
        adjacentHex.add(hexGrid[x][y-1]);
      }
      
      if (x - 1 >= 0 ){
        adjacentHex.add(hexGrid[x-1][y]);
      }
      
      if (y + 1 < numHexY ){
        adjacentHex.add(hexGrid[x][y+1]);
      }
      
      if (x + 1 < numHexX) {
        if (y - 1 >= 0 ){
          adjacentHex.add(hexGrid[x+1][y-1]);
        }
        
        adjacentHex.add(hexGrid[x+1][y]);
        
        if (y + 1 < numHexY ){
          adjacentHex.add(hexGrid[x+1][y+1]);
        }
      }
    }
    return adjacentHex;
  }
  
  protected abstract Hex createHex(int type, int x, int y);
}

