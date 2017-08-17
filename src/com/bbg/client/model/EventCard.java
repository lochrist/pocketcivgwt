package com.bbg.client.model;

import com.bbg.client.util.Resource;

public class EventCard {
  static int NUM_EVENT = 9;

  private int red;

  private int green;

  private int blue;

  private int gold;

  private boolean hasAlliance;

  private int id;
  
  private String img;

  private Event[] event = new Event[NUM_EVENT]; // 1 based index

  public EventCard(int id, int red, int green, int blue, int gold,
      boolean hasAlliance) {
    this.id = id;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.gold = gold;
    this.hasAlliance = hasAlliance;
    this.img = Resource.createImgUrl("event" + id + ".png");
  }

  /**
   * @return Returns the blue.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * @param blue
   *          The blue to set.
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  /**
   * @return Returns the event.
   */
  public Event[] getEvent() {
    return event;
  }

  /**
   * @param event
   *          The event to set.
   */
  public void setEvent(Event[] event) {
    this.event = event;
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
   * @return Returns the green.
   */
  public int getGreen() {
    return green;
  }

  /**
   * @param green
   *          The green to set.
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * @return Returns the hasAlliance.
   */
  public boolean isHasAlliance() {
    return hasAlliance;
  }

  /**
   * @param hasAlliance
   *          The hasAlliance to set.
   */
  public void setHasAlliance(boolean hasAlliance) {
    this.hasAlliance = hasAlliance;
  }

  /**
   * @return Returns the id.
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *          The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return Returns the red.
   */
  public int getRed() {
    return red;
  }

  public int getRegionIndex() {
    return red-1;
  }
  
  /**
   * @param red
   *          The red to set.
   */
  public void setRed(int red) {
    this.red = red;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("Id: " + id);
    buf.append("Red: " + red);
    buf.append("Geen: " + green);
    buf.append("Blue: " + blue);
    buf.append("Gold: " + gold);
    if (hasAlliance) {
      buf.append("Alliance");
    }
    for (int i = 1; i < event.length; ++i) {
      buf.append((i + 1) + event[i].toString());
    }

    return buf.toString();
  }

  /**
   * @return Returns the img.
   */
  public String getImg() {
    return img;
  }
}
