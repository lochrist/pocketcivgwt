package com.cortexanonymus.client.State.event;

import java.util.Iterator;
import java.util.Vector;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.StateUtil;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;

/**
  FLOOD:
  Note: Flood can become a Tsunami if the Active
  Region borders the Sea
  
  1. Draw the next Event Card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2.1. If the Active Region Neighbors the Sea, then see
  TSUNAMI below.
  
  2.2.1. If the Active Region does not Neighbor the Sea:
  Reduce Tribes by 2 in the Active Region.
  Decimate Farms in the Active Region.
  Reduce City AV by 1 in the Active Region.
  Create a Forest in the Active Region.
  
  2.2.2. If you have Irrigation, do not Decimate Farms.
  Do not Reduce City AV. You still may Create a Forest
  if no Forest is currently in this Region.
  
  TSUNAMI (FLOOD):
  1. Draw the next Event Card. Multiply the value in the
  GREEN SQUARE by 2 to determine Damage.
  
  2. Each Region Neighboring the Sea (that neighbors
  the Active Region) is inflicted with this amount of
  Damage, and must have the following elements
  Reduced appropriately. Damage is inflicted on Tribes
  first, then any remaining Damage after all Tribes are
  Reduced is inflicted on City AV. If a City is
  Decimated, then remaining damage is inflicted on
  Wonders of your choice in the Region, one at a time.
  1 Tribe = 1 Damage.
  1 City AV = 2 Damage.
  1 Wonder = 3 Damage.
  
  2.1. If you have Engineering, 1 Wonder = 10
  Damage.
  
  2.2. If you have Civil Service, 1 Tribe = 3
  Damage, 1 City AV = 5 Damage.
  
  3. If you have Medicine, Create 1 Tribe in each
  region affected by the Tsunami.
*/
public class Flood extends EventState {
  public Flood(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  Region activeRegion;
  public void transition() {
    EventCard ec = StateUtil.drawEvent(model, ui);

    activeRegion = model.getRegion(ec.getRegionIndex());
    if (activeRegion.hasSeaAccess()) {
      tsunami();
    } else {
      ui.setPhase("Flood");
      String log = "";
      if (model.hasAdvance(Advance.irrigation)) {
        activeRegion.setHasForest(true);
        activeRegion.setNumTribe(activeRegion.getNumTribe() - 2);
        log = "Region " + activeRegion.getName()
            + " lose 2 tribes and gain forest.";
      } else {
        log = "Region " + activeRegion.getName()
            + " lose 2 tribes, 1 AV, all farms and gain forest.";
        activeRegion.setHasFarm(false);
        activeRegion.setHasForest(true);
        activeRegion.setNumTribe(activeRegion.getNumTribe() - 2);
        activeRegion.setCityAdvance(activeRegion.getCityAdvance() - 1);
      }

      ui.setStatus(log);
      ui.log(log);

      ui.getMap().getRegionDesc(activeRegion.getId()).update();
      nextState(nextState);
    }
    
  }
  
  void tsunami() {
    ui.setPhase("Tsunami");
    EventCard ec = StateUtil.drawEvent(model, ui);

    int loss = ec.getGreen() * 2;
    applyDamage(activeRegion, loss);
    for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
      Region r = activeRegion.getAdjacentRegion(i);
      if (r.hasSeaAccess()) {
        applyDamage(r, loss);
      }
    }
    ui.updateWonder();

    nextState(nextState);
    
  }
  
  void applyDamage(Region r, int loss) {
    ui.log("Apply Tsunami damage: " + loss);
    int tribeBuffer = 1;
    int cityBuffer = 2;
    int wonderBuffer = 3;
    if (model.hasAdvance(Advance.civilService)) {
      tribeBuffer = 3;
      cityBuffer = 5;      
    }
    if (model.hasAdvance(Advance.engineering)) {
      wonderBuffer = 10;
    }
    
    int tribeLoss = 0;
    while (loss > 0 && r.getNumTribe()>0) {
      loss -= tribeBuffer;
      tribeLoss += 1;
      r.setNumTribe(r.getNumTribe()-1);
    }
    String log = "Region " + r.getName() + " loses " + tribeLoss + " tribes due to Tsunami.";    
    ui.log(log);
    
    
    int avLoss = 0;
    while (loss >0 && r.getCityAdvance()>0) {
      loss -= cityBuffer;
      r.setCityAdvance(r.getCityAdvance()-1);
      avLoss += 1;
    }
    log = "Region " + r.getName() + " loses " + avLoss + " AV due to Tsunami.";    
    ui.log( log );
        
    Vector wonders = r.getWonders();
    while (loss>0 && r.getNumWonder()>0) {
      Advance minAdvance = getMinAdvance(wonders);
      wonders.remove(minAdvance);
      loss -= wonderBuffer;
      ui.log("Region " + r.getName() + " loses " + minAdvance.getName());      
    }    
    r.setWonders(wonders);
    ui.getMap().getRegionDesc(r.getId()).update();
  }
  
  Advance getMinAdvance(Vector wonders) {
    Advance min = null;
    Iterator it = wonders.iterator();
    while (it.hasNext()) {
      Advance a = (Advance)it.next();
      if (min == null || a.getVictory() < min.getVictory()) {
        min = a;
      }
    }
    return min;
  }
}
