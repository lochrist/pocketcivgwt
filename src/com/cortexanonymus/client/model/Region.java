package com.cortexanonymus.client.model;

import java.util.ArrayList;
import java.util.Vector;

public class Region {
  private int cityAdvance = 0;

  private boolean hasMountain = false;

  private boolean hasVolcano = false;

  private boolean hasForest = false;

  private boolean hasFarm = false;

  private boolean hasDesert = false;

  private boolean hasCrack = false;

  private int numTribe = 0;

  private boolean hasSeaAccess = false;

  private boolean hasFrontierAccess = false;

  private Vector adjacentRegions = new Vector();

  private Vector wonders = new Vector();
  
  private int id;  

  public Region(int id) {
    this.id = id;    
  }

  /**
   * @return Returns the adjacentRegion.
   */
  public Region getAdjacentRegion(int i ) {
    return (Region)adjacentRegions.elementAt(i);
  }

  public int getNumAdjacentRegion() {
    return adjacentRegions.size();
  }
  
  public Vector getAdjacentRegions() {
    return adjacentRegions;
  }
  
  public int getNumWonder() {
    return wonders.size();
  }
  
  public Advance getWonder(int i) {
    return (Advance)wonders.elementAt(i);
  }
  public Vector getWonders() {
    return wonders;
  }
  
  public void setWonders(Vector wonders) {
    this.wonders = wonders;
  }
  
  /**
   * @param adjacentRegion
   *          The adjacentRegion to set.
   */
  public void addAdjacentRegion(Region adjacentRegion) {
    this.adjacentRegions.add(adjacentRegion);
  }

  /**
   * @return Returns the city.
   */
  public int getCityAdvance() {
    return cityAdvance;
  }

  /**
   * @param city
   *          The city to set.
   */
  public void setCityAdvance(int cityPower) {
    if (cityPower < 0 )
    {
      cityPower = 0;
    }
    this.cityAdvance = cityPower;
  }

  /**
   * @return Returns the crack.
   */
  public boolean hasCrack() {
    return hasCrack;
  }

  /**
   * @param crack
   *          The crack to set.
   */
  public void setHasCrack(boolean hasCrack) {
    this.hasCrack = hasCrack;
  }

  /**
   * @return Returns the desert.
   */
  public boolean hasDesert() {
    return hasDesert;
  }

  /**
   * @param desert
   *          The desert to set.
   */
  public void setHasDesert(boolean hasDesert) {
    this.hasDesert = hasDesert;
  }

  /**
   * @return Returns the farm.
   */
  public boolean hasFarm() {
    return hasFarm;
  }

  /**
   * @param farm
   *          The farm to set.
   */
  public void setHasFarm(boolean hasFarm) {
    this.hasFarm = hasFarm;
  }

  /**
   * @return Returns the forest.
   */
  public boolean hasForest() {
    return hasForest;
  }

  /**
   * @param forest
   *          The forest to set.
   */
  public void setHasForest(boolean hasForest) {
    this.hasForest = hasForest;
  }

  /**
   * @return Returns the hasFrontierAccess.
   */
  public boolean hasFrontierAccess() {
    return hasFrontierAccess;
  }

  /**
   * @param hasFrontierAccess
   *          The hasFrontierAccess to set.
   */
  public void setHasFrontierAccess(boolean hasFrontierAccess) {
    this.hasFrontierAccess = hasFrontierAccess;
  }

  /**
   * @return Returns the hasSeaAccess.
   */
  public boolean hasSeaAccess() {
    return hasSeaAccess;
  }

  /**
   * @param hasSeaAccess
   *          The hasSeaAccess to set.
   */
  public void setHasSeaAccess(boolean hasSeaAccess) {
    this.hasSeaAccess = hasSeaAccess;
  }

  /**
   * @return Returns the id.
   */
  public String getName() {
    return String.valueOf(id+1);
  }
  
  public int getId() {
    return id;
  }
    
  /**
   * @return Returns the mountain.
   */
  public boolean hasMountain() {
    return hasMountain;
  }

  /**
   * @param mountain
   *          The mountain to set.
   */
  public void setHasMountain(boolean hasMountain) {
    this.hasMountain = hasMountain;
  }

  /**
   * @return Returns the tribe.
   */
  public int getNumTribe() {
    return numTribe;
  }

  /**
   * @param tribe
   *          The tribe to set.
   */
  public void setNumTribe(int numTribe) {
    if (numTribe < 0 )
    {
      numTribe = 0;
    }
    this.numTribe = numTribe;
  }

  /**
   * @return Returns the volcano.
   */
  public boolean hasVolcano() {
    return hasVolcano;
  }

  /**
   * @param volcano
   *          The volcano to set.
   */
  public void setHasVolcano(boolean hasVolcano) {
    this.hasVolcano = hasVolcano;
  }
  
  public int getSupport() {
    int support = getCityAdvance();
    if (hasMountain()){
      ++support;
    }
    if (hasForest()){
      ++support;
    }
    if (hasVolcano()){
      ++support;
    }
    if (hasFarm()){
      ++support;
    }
    
    return support;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Id:" + id);
    buf.append("Tribe:" + numTribe);
    buf.append("City:" + cityAdvance);

    if (hasForest) {
      buf.append("Forest");
    }
    if (hasDesert) {
      buf.append("Desert");
    }
    if (hasMountain) {
      buf.append("Mountain");
    }
    if (hasVolcano) {
      buf.append("Volcano");
    }
    if (hasFarm) {
      buf.append("Farm");
    }
    if (hasCrack) {
      buf.append("Crack");
    }
    if (hasSeaAccess) {
      buf.append("Coastal");
    }
    if (hasFrontierAccess) {
      buf.append("Foreign");
    }
    for (int i = 0; i < adjacentRegions.size(); ++i) {
      Region reg = (Region) adjacentRegions.elementAt(i);
      buf.append("Adjacent to: " + reg.getName());
    }

    return buf.toString();
  }
}
