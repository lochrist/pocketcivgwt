package com.cortexanonymus.client.State.event;

import java.util.Iterator;
import java.util.Vector;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.StateUtil;
import com.cortexanonymus.client.action.RegionAction;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;
import com.cortexanonymus.client.ui.RegionDesc;

/**
  SANDSTORM:
  1. Draw the next Event Card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2.1.1. If the Active Region contains a Desert:
  Select two Neighboring Regions. Decimate Farms and
  Forests in these Regions.
  Create Deserts in the selected Regions.
  
  2.1.2. If you have Irrigation, do not Decimate Farms.
  
  2.2.1. If the Active Region does not contain a Desert:
  Decimate Farms and Forests in the Active Region.
  Create a Desert in the Active Region.
  
  2.2.2. If you have Irrigation, do not Decimate
  Farms.
*/
public class Sandstorm extends EventState {
  public Sandstorm(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  class CreateDesert extends RegionAction{
    int regionToDesert;    
    Vector legalRegion;
    static final int NUM_REGION_TO_DESERTIFY = 2;
    public CreateDesert(Vector legalRegion) {
      this.legalRegion = legalRegion;
      this.regionToDesert = Math.min(NUM_REGION_TO_DESERTIFY, legalRegion.size());
      
      ui.log("Sandstorm creates desert in " + regionToDesert + " regions.");
      ui.setStatus("Sandstorm creates desert in " + regionToDesert + " regions. Select a region.");           
    }
    
    public boolean canExecute(RegionDesc d) {
      return legalRegion.contains(d.getRegion());
    }
    
    public void execute(RegionDesc desc){
      if (canExecute(desc)) {
        Region r = desc.getRegion();        
        desertify(r);
        legalRegion.remove(r);
        desc.setSelected(false);        
        
        --regionToDesert;
        if (regionToDesert>0 && !legalRegion.isEmpty()){
          ui.setStatus("Still " + regionToDesert + " region to desertify. Select region.");
        }
        else {  
          nextState(nextState);
        }
      }
    }
  }
  
  Region activeRegion;
  public void transition() {
    ui.setPhase("Sandstorm");
    EventCard ec = StateUtil.drawEvent(model, ui);
    activeRegion = model.getRegion(ec.getRegionIndex());
    if (activeRegion.hasDesert()) {
      Vector legalRegion = new Vector();
      Iterator it = activeRegion.getAdjacentRegions().iterator();
      while (it.hasNext()) {
        Region r = (Region)it.next();
        if (!r.hasDesert()) {
          legalRegion.add(r);
        }
      }
      
      if (legalRegion.size()>0){        
        setSelectRegionAction(new CreateDesert(legalRegion));
      }
      else {
        nextState(nextState);
      }      
    } else {
      desertify(activeRegion);
      nextState(nextState);
    }
    
  }
  
  public void desertify(Region r) {
    ui.setPhase("Create desert in region " + r.getName());
    r.setHasDesert(true);
    if (!model.hasAdvance(Advance.irrigation)) {
      r.setHasFarm(false);
    }
    r.setHasForest(false);
    
    ui.getMap().getRegionDesc(r.getId()).update();
    ui.updateModel();
  }
}