package com.cortexanonymus.client.model;

import java.util.Vector;

import com.cortexanonymus.client.util.Array;

public class Advance {
  public static String masonry = "Masonry";
  public static String engineering = "Engineering";
  public static String architecture = "Architecture";
  public static String agriculture = "Agriculture";
  public static String horticulture = "Horticulture";
  public static String irrigation = "Irrigation";
  public static String equestrian = "Equestrian";
  public static String cavalry = "Cavalry";
  public static String government = "Government";
  public static String military = "Military";
  public static String diplomacy = "Diplomacy";
  public static String democracy = "Democracy";
  public static String civilService = "Civil Service";
  public static String music = "Music";
  public static String literacy = "Literacy";
  public static String medecine = "Medecine";
  public static String arts = "Arts";
  public static String theater = "Theater";
  public static String patronage = "Patronage";
  public static String mythology = "Mythology";
  public static String meditation = "Meditation";
  public static String philosophy = "Philosophy";
  public static String organizedReligion = "Organized Religion";
  public static String ministry = "Ministry";
  public static String law = "Law";
  public static String fishing = "Fishing";
  public static String navigation = "Navigation";
  public static String astronomy = "Astronomy";
  public static String shipping = "Shipping";
  public static String sailsAndRigging = "Sails and Rigging";
  public static String coinage = "Coinage";
  public static String banking = "Banking";
  public static String slaveLabor = "Slave Labor";
  public static String mining = "Mining";
  public static String surveying = "Surveying";
  public static String metalWorking = "Metal Working";
  public static String cartage = "Cartage";
  public static String roadbuilding = "Roadbuilding";
  
  private String name;

  private String reduceEffect;

  private String increaseEffect;

  private String desc;

  private Cost cost;

  private Requirement requirement;

  private int victory;

  /**
   * @return Returns the victory.
   */
  public int getVictory() {
    return victory;
  }

  /**
   * @param victory The victory to set.
   */
  public void setVictory(int victory) {
    this.victory = victory;
  }

  /**
   * @return Returns the cost.
   */
  public Cost getCost() {
    return cost;
  }

  /**
   * @param cost The cost to set.
   */
  public void setCost(Cost cost) {
    this.cost = cost;
  }

  /**
   * @return Returns the desc.
   */
  public String getDesc() {
    return desc;
  }

  /**
   * @param desc The desc to set.
   */
  public void setDesc(String desc) {
    this.desc = desc;
  }

  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns the requirements.
   */
  public Requirement getRequirement() {
    return requirement;
  }

  /**
   * @param requirements The requirements to set.
   */
  public void setRequirement(Requirement requirement) {
    this.requirement = requirement;
  }

  /**
   * @return Returns the increaseEffect.
   */
  public String getIncreaseEffect() {
    return increaseEffect;
  }

  /**
   * @param increaseEffect The increaseEffect to set.
   */
  public void setIncreaseEffect(String increaseEffect) {
    this.increaseEffect = increaseEffect;
  }

  /**
   * @return Returns the reduceEffect.
   */
  public String getReduceEffect() {
    return reduceEffect;
  }

  /**
   * @param reduceEffect The reduceEffect to set.
   */
  public void setReduceEffect(String reduceEffect) {
    this.reduceEffect = reduceEffect;
  }
  
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Name: " + name);
    buf.append("VP: " + victory);
    buf.append("Cost: " + cost);

    /*
    if (requirements.size() > 0) {
      buf.append("Resources needed: " + Array.toString(requirements));
    }
    */
    if (reduceEffect != "") {
      buf.append("Reduce effect of: " + reduceEffect);
    }
    if (increaseEffect != "") {
      buf.append("Increase effect of: " + increaseEffect);
    }

    return buf.toString();
  }

}
