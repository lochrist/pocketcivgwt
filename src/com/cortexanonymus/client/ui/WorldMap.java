package com.cortexanonymus.client.ui;

import java.util.Iterator;

import com.cortexanonymus.client.model.Region;
import com.google.gwt.user.client.ui.Widget;

public interface WorldMap {
    
  public RegionDesc addRegion(Region region);
  
  public int getNumRegionDesc();  
  public RegionDesc getRegionDesc(int i);
  public Iterator getRegionDescIterator();
  
  public RegionDesc selectRegion(int x, int y);
  
  public void createMap();  
  public Widget getUi();

}
