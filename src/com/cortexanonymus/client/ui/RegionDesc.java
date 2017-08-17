package com.cortexanonymus.client.ui;

import java.util.Vector;

import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.hex.HexUtil;
import com.cortexanonymus.client.util.Resource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public abstract class RegionDesc {    
  public static final String ID = "id";
  
  public static final String CITY = "city";

  public static final String CITY_ADVANCE = "cityadvance";

  public static final String TRIBE = "tribe";
  
  public static final String MOVING_TRIBE = "movingtribe";

  public static final String TRIBE_NUM = "tribenum";

  public static final String FOREST = "forest";

  public static final String VOLCANO = "volcano";

  public static final String MOUNTAIN = "mountain";

  public static final String FARM = "farm";
  
  public static final String CRACK = "crack";
  
  public static final String DESERT = "desert";
  
  protected  Region region;
   
  protected Image city;
  protected Image tribe;
  protected Image movingTribe;
  protected Image id;
  protected Label idNumber;
  protected Label cityAdvance;
  protected Label tribeNum;
  protected Label movingTribeNum;
  protected int numMovingTribe;

  public RegionDesc(Region region) {
    this.region = region;
  }
  
  /**
   * @return Returns the region.
   */
  public Region getRegion() {
    return region;
  }
  
  public abstract void setSelected(boolean isSelected);
  public abstract boolean isSelected();
 
  public void createRegion() {
    // Create City Hex:
    id = createImage(createFeature(ID));
    idNumber = createLabel(String.valueOf(region.getName()));
    
    city = createImage(createFeature(CITY));
    city.setVisible(false);
    
    cityAdvance = createLabel("0");
    cityAdvance.setVisible(false);
    
    tribe = createImage(createFeature(TRIBE));
    tribeNum = createLabel("0");
    
    movingTribe = createImage(createFeature(MOVING_TRIBE));
    movingTribeNum = createLabel("0");
    
    // All other feature hex will be created when first updated.
    
  }
    
  public void setNumMovingTribe(int numMovingTribe) {
    this.numMovingTribe = numMovingTribe;
  }
  
  public int getNumMovingTribe() {
    return numMovingTribe; 
  }
  
  protected Image createImage(String url) {
    Image img = new Image(url);
    return img;
  }
  
  protected Label createLabel(String t) {
    Label l = new Label(t);
    return l;
  }
  
  protected String createFeature(String featureName) {
    return Resource.createImgUrl(featureName + ".gif");
  }
   
  
  public abstract void update();
}