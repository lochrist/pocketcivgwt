package com.cortexanonymus.client.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import com.cortexanonymus.client.data.Advances;
import com.cortexanonymus.client.data.Events;


public class GameModel {
  Deck eventDeck;
  Vector regions;
  Vector advanceAvailable;  
  HashMap advanceAcquired = new HashMap();
  HashSet tradingPartners = new HashSet();
  
  Vector wonderAvailable;  
  int gold = 0;
  int era = 1;
  int vp = 0;
  int goldMined = 0;
  int surveying = 0;
  int turn = 0;
  
  EventCard currentEvent;
  EventCard drawEvent;
  
  /**
   * @return Returns the era.
   */
  public int getEra() {
    return era;
  }

  /**
   * @param era The era to set.
   */
  public void setEra(int era) {
    this.era = era;
  }

  public GameModel(Vector regions) {
    this.regions = regions;
    this.eventDeck = new Deck( Events.generateEventList() );
    advanceAvailable = Advances.generateAdvances();    
    wonderAvailable = Advances.generateWonders();
    gold = 0;
        
    for (int  i = 0; i < regions.size();++i) {
      Region r = (Region)regions.elementAt(i);      
    }
  }

  /**
   * @return Returns the gold.
   */
  public int getGold() {
    return gold;
  }

  /**
   * @param gold The gold to set.
   */
  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * @return Returns the advanceAcquired.
   */
  public HashMap getAdvanceAcquired() {
    return advanceAcquired;
  }
  
  /**
   * @return Returns the wonderAvailable.
   */
  public Vector getWonderAvailable() {
    return wonderAvailable;
  }

  /**
   * @param wonderAvailable The wonderAvailable to set.
   */
  public void setWonderAvailable(Vector wonderAvailable) {
    this.wonderAvailable = wonderAvailable;
  }

  /**
   * @return Returns the advanceAvailable.
   */
  public Vector getAdvanceAvailable() {
    return advanceAvailable;
  }

  /**
   * @return Returns the eventDeck.
   */
  public Deck getEventDeck() {
    return eventDeck;
  }
  
  public void setEventDeck(Deck deck) {
    eventDeck = deck;
  }
  
  public EventCard getCurrentEvent() {
    return currentEvent;
  }
  
  public EventCard getDrawEvent() {
    return drawEvent;
  }
  
  public void setCurrentEvent(EventCard ec ) {
    currentEvent = ec;
  }
  public void setDrawEvent(EventCard ec ) {
    drawEvent = ec;
  }

  /**
   * @return Returns the regions.
   */
  public Region getRegion(int i) {
    return (Region)regions.elementAt(i);
  }
  
  public int getVp() {
    return vp;
  }
  
  public int getNumRegion() {
    return regions.size();
  }
  
  public int getNumTribe() {
    int numTribe = 0;
    for (int i = 0; i < getNumRegion(); ++i) {
      numTribe += getRegion(i).getNumTribe();
    }
    return numTribe;
  }
  
  public int getNumAdvance() {
    int cityAdvance = 0;
    for (int i = 0; i < getNumRegion(); ++i) {
      cityAdvance += getRegion(i).getCityAdvance();
    }
    return cityAdvance;
  }
  
  public int getNumCity() {
    int numCity = 0;
    for (int i = 0; i < getNumRegion(); ++i) {
      if (getRegion(i).getCityAdvance()>0) {
        ++numCity;
      }      
    }
    return numCity;
  }
  
  public int getMaximumCityAdvance() {    
    int maxCityAdvance = 2;
    if (hasAdvance(Advance.architecture)) {
      maxCityAdvance = 4;
    }
    if (hasAdvance(Advance.engineering) || hasAdvance(Advance.metalWorking)) {
      maxCityAdvance = 3;
    }    
    return maxCityAdvance;
  }
  
  public int getCityAdvanceGrowth() {
    int cityGrowth = 0;
    if (hasAdvance(Advance.masonry)) {
      ++cityGrowth;
    }
    if (hasAdvance(Advance.civilService)) {
      ++cityGrowth;
    }
    if (hasAdvance(Advance.slaveLabor)) {
      ++cityGrowth;
    }
    return cityGrowth;
  }
  
  public boolean hasAdvance(String advanceName) {    
    return advanceAcquired.containsKey(advanceName);
  }
  
  /**
   * @return Returns the goldMined.
   */
  public int getGoldMined() {
    return goldMined;
  }

  /**
   * @param goldMined The goldMined to set.
   */
  public void setGoldMined(int goldMined) {
    this.goldMined = goldMined;
  }

  /**
   * @return Returns the surveying.
   */
  public int getSurveying() {
    Iterator it = eventDeck.getDiscardIterator();
    int surveying = 0;
    while (it.hasNext()) {
      EventCard c = (EventCard)it.next();
      surveying += c.getGold();
    }
    return surveying;
  }
  
  public void acquireAdvance(Advance advance,Region r) {
    // Pay for advance:
    if (advance.getCost().getGold()>0) {
      gold -= advance.getCost().getGold();
    }
    if (advance.getCost().getTribe()>0) {
      r.setNumTribe(r.getNumTribe()-advance.getCost().getTribe());
    }
    advanceAvailable.remove(advance);
    advanceAcquired.put(advance.getName(),advance);
    
    vp += advance.getVictory();
  }
  
  public void acquireAdvance(String advanceName) {
    // Pay for advance:    
    Advance a = null;
    for (int i = 0; i < advanceAvailable.size();++i) {
      a = (Advance)advanceAvailable.elementAt(i);
      if (a.getName().equals(advanceName)) {
        break; 
      }
    }
    advanceAvailable.remove(a);
    advanceAcquired.put(a.getName(),a);
  }
  
  public void acquireWonder(Advance wonder,Region r) {
    // Pay for advance:
    if (wonder.getCost().getGold()>0) {
      gold -= wonder.getCost().getGold();
    }
    if (wonder.getCost().getTribe()>0) {
      r.setNumTribe(r.getNumTribe()-wonder.getCost().getTribe());
    }
    
    Vector wonders = r.getWonders();
    wonders.add(wonder);
    r.setWonders(wonders);
    
    vp += wonder.getVictory();
  }
  
  public HashSet getTradingPartners() {
    return tradingPartners;
  }

  /**
   * @return the turn
   */
  public int getTurn() {
    return turn;
  }

  /**
   * @param turn the turn to set
   */
  public void setTurn(int turn) {
    this.turn = turn;
  }
}
