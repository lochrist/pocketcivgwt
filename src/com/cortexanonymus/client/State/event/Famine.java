package com.cortexanonymus.client.State.event;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.StateUtil;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;

/**
  FAMINE:
  1. Draw the next Event Card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2.1. In the Active Region, Decimate Tribes and Farms.
  Reduce City AV by 2.
  
  2.2. If you have Irrigation, do not Decimate
  Farms. Reduce City AV by 1 instead 2.
*/
public class Famine extends EventState {
  public Famine(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Famine!");
    ui.setStatus("Famine");
    
    EventCard ec = StateUtil.drawEvent(model, ui);
    Region activeRegion = model.getRegion(ec.getRegionIndex());
    ui.log("Active region is " + activeRegion.getName());
    if (model.hasAdvance(Advance.irrigation)) {
      ui.log("Active region " + activeRegion.getName()
          + " will lose tribes and 1AV (helped by Irrigation)");
      activeRegion.setNumTribe(0);
      activeRegion.setCityAdvance(activeRegion.getCityAdvance() - 1);
    } else {
      ui.log("Active region " + activeRegion.getName()
          + " will lose tribes, farm and 2AV.");
      activeRegion.setNumTribe(0);
      activeRegion.setCityAdvance(activeRegion.getCityAdvance() - 2);
      activeRegion.setHasFarm(false);
    }
    ui.getMap().getRegionDesc(activeRegion.getId()).update();
    nextState(nextState);
    
  }
}