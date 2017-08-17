package com.bbg.client.State.event;

import java.util.Iterator;
import java.util.Vector;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.GameState;
import com.bbg.client.State.StateUtil;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;

/**
 * 
 * ATTACK (VISITATION or BANDITS):
  1. If you have Ministry, draw the next Event
  card and Reduce the size of the Attack Force by
  the value in the Green Circle. Increase your
  amount of Gold by 1 for each Attacking Force
  Reduced in this manner.
  
  2. Starting with the Active Region, Reduce Tribes and
  Attacking Force.
    
  2.1. Reduce 1 Tribe to Reduce 1 Attacking Force.
  Unless….
  
  2.2. If you have Military or Metal Working,
  Reduce 1 Tribe to Reduce up to 2 strength of
  Attacking Force.
  
  2.3. If you have Military and Metal Working,
  Reduce 1 Tribe to Reduce up to 3 strength of
  Attacking Force.
  
  3. If Attacking Force remains after Decimating all
  Tribes…
  
  3.1. Reduce 5 Attacking Force to Reduce 1 City AV.
  Unless…
  
  3.2. If you have Architecture, Reduce 8
  Attacking Force to Reduce 1 City AV..
  3.3. If there is not enough Attacking Force to Reduce a
  City AV, then the Attacking Force is Decimated.
  
  3.4. For every AV reduced by the Attacking Force,
  Decimate 2 Gold from your common stock.
  
  4. If Attacking Force remains after Decimating the
  City, then Attacking Force moves to the Neighboring
  Region with the least amount of Tribes. Attacking
  Force may move through Regions with no Tribes or
  Cities to find a new target to pillage.
  
  4.1. If there is a tie between Regions with equal (or
  zero) Tribes, then Attacking Force moves into the
  Region with the City with the highest AV. Otherwise,
  you choose.
  
  4.2. If you have Equestrian, Reduce strength of
  Attacking Force by 2 when entering a new
  Region with Tribes.
 *
 */

public abstract class Attack extends EventState {
  public Attack(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  int tribeBuffer = 1;
  int avBuffer = 5;
  int attackForce = 0; 
  Region activeRegion;
  void attack(int af){
    attackForce = af;
    if (model.hasAdvance(Advance.ministry)) {
      EventCard ec = StateUtil.drawEvent(model,ui);             
      ui.log("Ministry helps reduce Attack");
      attackForce -= ec.getGreen();
      model.setGold(model.getGold() + ec.getGreen());
    }
            
    if (model.hasAdvance(Advance.military)
        && model.hasAdvance(Advance.metalWorking)) {
      tribeBuffer = 3;
    } else if (model.hasAdvance(Advance.military)
        || model.hasAdvance(Advance.metalWorking)) {
      tribeBuffer = 2;
    }

    if (model.hasAdvance(Advance.architecture)) {
      avBuffer = 8;
    }

    applyDamage(activeRegion);

    Vector adjacentRegions = new Vector();
    adjacentRegions.addAll(activeRegion.getAdjacentRegions());
    Region r = getMinTribeRegion(adjacentRegions);
    while (attackForce > 0 && r != null) {            
      applyDamage(r);
      adjacentRegions.remove(r);
      r = getMinTribeRegion(adjacentRegions);
    }
    setEndOfStateAction(new GameState.NextPhaseAction(nextState));
  }
  
  void applyDamage(Region r) {
    int tribeLoss = 0;
    if (model.hasAdvance(Advance.equestrian)) {
      attackForce -= 2;
      ui.log("Equestrian helps against attack");
    }
    
    while (attackForce>0 && r.getNumTribe()>0) {
      attackForce -= tribeBuffer;
      r.setNumTribe(r.getNumTribe()-1);
      tribeLoss++;
    }
    ui.log("Region " + r.getName() + " loses " + tribeLoss + " tribes in attack.");
        
    int avLoss = 0;
    while (attackForce>0 && r.getCityAdvance()>0) {
      // if there is not enough attacking force to reduce city AV, 
      // then attacking force is decimated.
      if (attackForce > avBuffer) {
        avLoss++;
        model.setGold(model.getGold()-2);   
        r.setCityAdvance(r.getCityAdvance()-1);
      }
      attackForce -= avBuffer;
    }
    ui.log("Region " + r.getName() + " loses " + avLoss + " AV in attack.");
    
    
    ui.getMap().getRegionDesc(r.getId()).update();
  }
  
  Region getMinTribeRegion(Vector adjacentRegions) {
    Region minRegion = null;
    Iterator it = adjacentRegions.iterator();
    while (it.hasNext()) {
      Region r = (Region)it.next();
      if (minRegion == null || r.getNumTribe()<minRegion.getNumTribe()) {
        minRegion = r;
      }
    }
    return minRegion;
  }
}

