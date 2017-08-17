package com.cortexanonymus.client.model;

public class Cost {
  private int tribe;

  private int gold;

  private boolean woodNeeded;

  private boolean stoneNeeded;

  private boolean foodNeeded;

  /**
   * @return Returns the foodNeeded.
   */
  public boolean isFoodNeeded() {
    return foodNeeded;
  }

  /**
   * @param foodNeeded
   *          The foodNeeded to set.
   */
  public void setFoodNeeded(boolean foodNeeded) {
    this.foodNeeded = foodNeeded;
  }

  /**
   * @return Returns the gold.
   */
  public int getGold() {
    return gold;
  }

  /**
   * @param gold
   *          The gold to set.
   */
  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * @return Returns the stoneNeeded.
   */
  public boolean isStoneNeeded() {
    return stoneNeeded;
  }

  /**
   * @param stoneNeeded
   *          The stoneNeeded to set.
   */
  public void setStoneNeeded(boolean stoneNeeded) {
    this.stoneNeeded = stoneNeeded;
  }

  /**
   * @return Returns the tribe.
   */
  public int getTribe() {
    return tribe;
  }

  /**
   * @param tribe
   *          The tribe to set.
   */
  public void setTribe(int tribe) {
    this.tribe = tribe;
  }

  /**
   * @return Returns the woodNeeded.
   */
  public boolean isWoodNeeded() {
    return woodNeeded;
  }

  /**
   * @param woodNeeded
   *          The woodNeeded to set.
   */
  public void setWoodNeeded(boolean woodNeeded) {
    this.woodNeeded = woodNeeded;
  }

  public boolean isFulfilled(GameModel model,Region r) {
    return 
      tribe <= r.getNumTribe() &&
      gold <= model.getGold() &&
      (woodNeeded == false || r.hasForest()) &&
      (stoneNeeded == false || (r.hasMountain() ||r.hasVolcano())) &&
      (foodNeeded == false || r.hasFarm());
  }
  
  public String toString() {
    StringBuffer buf = new StringBuffer();
    if (tribe != 0) {
      buf.append(tribe + " Tribe " );
    }
    if (gold != 0) {
      buf.append(gold + " Gold "  );
    }
    if (woodNeeded) {
      buf.append("Wood ");
    }
    if (foodNeeded) {
      buf.append("Food ");
    }
    if (stoneNeeded) {
      buf.append("Stone ");
    }

    return buf.toString();
  }
}