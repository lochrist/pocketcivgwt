package com.bbg.client.ui.hex;

import java.util.Vector;

import com.bbg.client.model.Region;
import com.bbg.client.ui.RegionDesc;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class HexRegionDesc extends RegionDesc {    
  private Vector hexes = new Vector();
  boolean isSelected;
  
  public HexRegionDesc(Region region) {
    super(region);
  }

  public int getNumHex() {
    return hexes.size();
  }

  public Hex getHex(int i) {
    return (Hex) hexes.elementAt(i);
  }

  public void addHex(int x, int y) {
    Hex h = HexWorldMap.get().createHex(region.getId(), x, y);
    hexes.add(h);
    HexWorldMap.get().addHex(h); 
  }

  /**
   * @return Returns the region.
   */
  public Region getRegion() {
    return region;
  }
    
  public void createRegion() {
    // Create City Hex:
    super.createRegion();
        
    id.setPixelSize(HexUtil.getHexWidth(),HexUtil.getHexHeight());
    city.setPixelSize(HexUtil.getHexWidth(),HexUtil.getHexHeight());
    tribe.setPixelSize(HexUtil.getHexWidth(),HexUtil.getHexHeight());
    movingTribe.setPixelSize(HexUtil.getHexWidth(),HexUtil.getHexHeight());
        
    // All other feature hex will be created when first updated.
    
  }
  
  public void update() {
    int hexIndex = 0;
    Region r = getRegion();
    updateCity(getHex(hexIndex++));
          
    hexIndex = updateFeature(hexIndex,FARM,r.hasFarm());
    hexIndex = updateFeature(hexIndex,MOUNTAIN,r.hasMountain());
    hexIndex = updateFeature(hexIndex,VOLCANO,r.hasVolcano());
    hexIndex = updateFeature(hexIndex,FOREST,r.hasForest());
    hexIndex = updateFeature(hexIndex,CRACK,r.hasCrack());
    hexIndex = updateFeature(hexIndex,DESERT,r.hasDesert());
    
    for (;hexIndex < getNumHex();++hexIndex) {
      Hex h = getHex(hexIndex);
      if (h.getFeature() != null) {
        h.getFeature().setVisible(false);
      }        
    }
  }
  
  public void setSelected(boolean isSelected) {
    if (this.isSelected!= isSelected) {
      this.isSelected = isSelected;
      for (int i = 0; i < getNumHex(); ++i) {
        ModularHex h = (ModularHex)getHex(i);
        if (isSelected) {
          h.getBackground().addStyleName("selected-region");
        }
        else {
          h.getBackground().removeStyleName("selected-region");
        }
      }
    }    
  }
  
  public boolean isSelected() {
    return isSelected;
  }
  
  protected Image createImage(String url) {
    Image img = super.createImage(url);
    HexWorldMap.get().add(img);
    img.setPixelSize(
        HexWorldMap.get().featureWidth,
        HexWorldMap.get().featureHeight);
    return img;
  }
  
  protected Label createLabel(String t) {
    Label l = super.createLabel(t);
    HexWorldMap.get().add(l);
    return l;
  }

  void updateCity(Hex h) {
    Coord c = HexUtil.hexToPixel(h.getX(), h.getY());
    
    HexWorldMap.get().setWidgetPosition(
        id, 
        c.x, 
        c.y );
    
    HexWorldMap.get().setWidgetPosition(
        idNumber, 
        c.x + HexUtil.getHexWidth() / 2 - 3, 
        c.y + HexUtil.getHexH() - 10 );
    
    cityAdvance.setText(String.valueOf(region.getCityAdvance()));
    cityAdvance.setVisible(region.getCityAdvance()>0);
    HexWorldMap.get().setWidgetPosition(
        cityAdvance, 
        c.x + 10, 
        c.y + HexUtil.getHexHPlusS() - 20);
    
    city.setVisible(region.getCityAdvance()>0);
    HexWorldMap.get().setWidgetPosition(city, c.x, c.y);
    
    tribeNum.setText(String.valueOf(region.getNumTribe()));
    tribeNum.setVisible(region.getNumTribe()>0);
    HexWorldMap.get().setWidgetPosition(
        tribeNum, 
        c.x + HexUtil.getHexWidth() - 15, 
        c.y + HexUtil.getHexHPlusS() - 12);
    HexWorldMap.get().setWidgetPosition(tribe, c.x, c.y);
    tribe.setVisible(region.getNumTribe()>0);
    
    movingTribeNum.setText(String.valueOf(getNumMovingTribe()));
    movingTribeNum.setVisible(getNumMovingTribe()>0);
    HexWorldMap.get().setWidgetPosition(
        movingTribeNum, 
        c.x + HexUtil.getHexWidth() - 33, 
        c.y + HexUtil.getHexHPlusS() - 12);
    HexWorldMap.get().setWidgetPosition(movingTribe, c.x, c.y);
    movingTribe.setVisible(getNumMovingTribe()>0);
  }
  
  int updateFeature(int hexIndex, String featureName, boolean featureExist) {   
    if (hexIndex >= getNumHex()) {
      return hexIndex;
    }
    
    String url = createFeature(featureName);
    Hex h = getHex(hexIndex);
    
    if (featureExist) {
      if (h.getFeature() == null) {
        // create Image:
        h.setFeature(createImage(url));
        HexWorldMap.get().add(h.getFeature());
      }
      else if (h.getFeature().getUrl().equals(url)) {
        // nothing to do
      }
      else {
        // Reload image
        h.getFeature().setUrl(url);
        
      }
      Coord c = HexUtil.hexToPixel(h.getX(),h.getY());
      HexWorldMap.get().setWidgetPosition(
          h.getFeature(),
          c.x + (HexWorldMap.get().hexWidth / 2) - (HexWorldMap.get().featureWidth /2),
          c.y + (HexWorldMap.get().hexHeight / 2) - (HexWorldMap.get().featureHeight /2));
      h.getFeature().setVisible(true);
      ++hexIndex;
    }
    else if (h.getFeature()!=null){
      h.getFeature().setVisible(false);
    }
    return hexIndex;
  }  
}
