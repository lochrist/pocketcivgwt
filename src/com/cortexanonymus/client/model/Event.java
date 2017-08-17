package com.cortexanonymus.client.model;

import java.util.HashMap;

public class Event {
  public static final int TRIBAL_WAR = 0;
  public static final int EPIDEMIC = 1;
  public static final int FAMINE = 2;
  public static final int UPRISING = 3;
  public static final int VISITATION = 4;
  public static final int EARTHQUAKE = 5;
  public static final int VOLCANO = 6;
  public static final int CORRUPTION = 7;
  public static final int CIVIL_WAR = 8;
  public static final int SUPERSTITION = 9;
  public static final int FLOOD = 10;
  public static final int SANDSTORM = 11;
  public static final int ANARCHY = 12;
  public static final int BANDITS = 13;
  
  private String name;

  private int red = 0;

  private int blue = 0;

  private int green = 0;
  
  private int id;
  
  private String desc;

  public Event(int id,String desc,int red, int green, int blue) {
    this.id = id;
    this.name = Event.eventIdToString(id);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.desc =desc;
  }

  public String toString(int id) {
    StringBuffer buf = new StringBuffer();
    buf.append("Name: " + name);
    if (red > 0) {
      buf.append("Red: " + red);
    }
    if (blue > 0) {
      buf.append("Blue: " + blue);
    }
    if (green > 0) {
      buf.append("Green: " + green);
    }
    return buf.toString();
  }
    
  public String getName() {
    return eventIdToString(id);
  }
  
  public String getDesc() {
    return desc;
  }
  
  public static String eventIdToString(int id) {
    String s = "";
    switch(id){
    case ANARCHY:
      s = "Anarchy";
      break;
    case BANDITS:
      s = "Bandits";
      break;
    case CIVIL_WAR:
      s = "Civil War";
      break;
    case CORRUPTION:
      s = "Corruption";
      break;
    case EARTHQUAKE:
      s = "Earthquake";
      break;
    case EPIDEMIC:
      s = "Epidemic";
      break;
    case FAMINE:
      s = "Famine";
      break;
    case FLOOD:
      s = "Flood";
      break;
    case SANDSTORM:
      s = "Sandstorm";
      break;
    case TRIBAL_WAR:
      s = "Tribal War";
      break;
    case SUPERSTITION:
      s = "Superstition";
      break;
    case UPRISING:
      s = "Uprising";
      break;
    case VISITATION:
      s = "Visitation";
      break;
    case VOLCANO:
      s = "Volcano";
      break;
    }
    return s;
  }

  /**
   * @return the blue
   */
  public int getBlue() {
    return blue;
  }

  /**
   * @return the green
   */
  public int getGreen() {
    return green;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @return the red
   */
  public int getRed() {
    return red;
  }
}
