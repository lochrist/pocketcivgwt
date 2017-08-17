package com.bbg.client.defaultdata;

import java.util.Vector;

import com.bbg.client.model.Region;
import com.bbg.client.ui.RegionDesc;
import com.bbg.client.ui.WorldMap;
import com.bbg.client.ui.hex.Hex;
import com.bbg.client.ui.hex.HexRegionDesc;
import com.bbg.client.ui.hex.ModularWorldMap;

public class DefaultMap extends ModularWorldMap {
  static final int NUM_HEX_X = 7;
  static final int NUM_HEX_Y = 13;
  // static final int HEX_WIDTH = 85;
  // static final int HEX_HEIGHT = 98;
  static final int HEX_WIDTH = 70;
  static final int HEX_HEIGHT = 80;
  static final int FEATURE_WIDTH = 30;
  static final int FEATURE_HEIGHT = 30;
  public DefaultMap(Vector regions) {
    super(NUM_HEX_X,NUM_HEX_Y,HEX_WIDTH,HEX_HEIGHT,FEATURE_WIDTH,FEATURE_HEIGHT);
    instance = this;
    
    int regionIndex = 0;
    
    // Region 1    
    HexRegionDesc desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(3,1);
    desc.addHex(4,1);
    desc.addHex(4,2);
    desc.addHex(5,2);
        
    // region2
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(2,2);
    desc.addHex(3,2);
    desc.addHex(2,3);
    desc.addHex(2,4);    
    
    // region3    
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(3,3);
    desc.addHex(3,4);
    desc.addHex(2,5);
    desc.addHex(2,6);    
    
    // region4
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(4,5);
    desc.addHex(5,5);
    desc.addHex(3,6);
    desc.addHex(4,6);    
    
    //  region5
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(1,5);
    desc.addHex(1,6);
    desc.addHex(1,7);
    desc.addHex(2,8);    
    
    // region6        
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(3,10);
    desc.addHex(1,11);
    desc.addHex(2,11);
    desc.addHex(2,12);    
    
    // region7
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(4,3);
    desc.addHex(4,4);
    desc.addHex(5,4);
    desc.addHex(3,5);    
    
    
    // region8
    desc = (HexRegionDesc)addRegion((Region)regions.elementAt(regionIndex++));
    desc.addHex(2,7);
    desc.addHex(3,8);
    desc.addHex(2,9);
    desc.addHex(2,10);    
    
    // Add Frontier region:    
    addFrontier(2,1);
    addFrontier(3,0);
    addFrontier(4,0);
    
    addFrontier(6,2);
    addFrontier(5,3);
    addFrontier(6,4);
    
    addFrontier(5,0);
    addFrontier(5,1);    
    
    addFrontier(6,5);
    addFrontier(6,6);
    addFrontier(5,6);
    
    addFrontier(3,7);
    
    addFrontier(4,7);
    addFrontier(4,8);
    addFrontier(3,9);
    
    createMap();
  }
}
