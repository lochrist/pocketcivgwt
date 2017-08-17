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
  UPRISING:
  1. Draw the next card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2. Reduce City AV in Active Region by 2.
  Decimate Tribes in the Active Region.
  Decimate Farms in Active Region.
  
  2.1. If you have Law, Reduce City AV by 1
  instead of 2.
  
  2.2. If you have Organized Religion, Reduce
  Tribes by 2 instead of Decimating them.
  
  3. If you have Slave Labor, Decimate Farms in
  Regions that have no Cities.
  
  4. If you have Military, Select a Neighboring
  Region with a City. Reduce Neighboring City
  AV by 1. Decimate Tribes in selected
  Neighboring Region, unless you have Organized
  Religion, then reduce Neighboring Tribes by 2.
*/
public class Uprising extends EventState {
  public Uprising(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  class UprisingAction extends RegionAction {
    Vector legalRegion;
    public UprisingAction(Vector legalRegion) {
      this.legalRegion = legalRegion;
      highlightRegionList(legalRegion.iterator(), true);  
    }
    
    public boolean canExecute(RegionDesc desc) {
      return legalRegion.contains(desc.getRegion());
    }

    public void execute(RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        uprising(r, 1);
        nextState(nextState);
      }
    }
  }
  
  
  public void transition() {
    ui.setPhase("Uprising!");
    ui.setStatus("Uprising");
    
    EventCard ec = StateUtil.drawEvent(model, ui);

    Region activeRegion = model.getRegion(ec.getRegionIndex());
    ui.log("Active region is " + activeRegion.getName());
    int avToLose = 2;
    if (model.hasAdvance(Advance.law)) {
      avToLose = 1;
      ui.log("Law helps reduce Uprising");
    }

    if (model.hasAdvance(Advance.slaveLabor)
        && activeRegion.getCityAdvance() == 0) {
      ui.log("Slave Labor decimate farm in region with no city.");
      activeRegion.setHasFarm(false);
    }

    uprising(activeRegion, avToLose);

    if (model.hasAdvance(Advance.military)) {
      ui.log("Military increase Uprising.");
      ui.setStatus("Select a neighboring region that will sustain Uprising.");
      Vector adjRegion = new Vector();
      Iterator it = activeRegion.getAdjacentRegions().iterator();
      while (it.hasNext()) {
        Region r = (Region) it.next();
        if (r.getCityAdvance() > 0) {
          adjRegion.add(r);
        }
      }

      if (adjRegion.size() > 0) {        
        setSelectRegionAction(new UprisingAction(adjRegion));
      } else {
        nextState(nextState);
      }
    }      
    
  }
  
  void uprising(Region r, int numAv) {
    int tribeToLose = r.getNumTribe();
    if (model.hasAdvance(Advance.organizedReligion)) {
      tribeToLose = 2;
      ui.log("Organized Religion helps reduce Uprising");
    } 
    r.setNumTribe(r.getNumTribe()-tribeToLose);
    r.setCityAdvance(r.getCityAdvance()-numAv);
    
    ui.log("Region " + r.getName() + " loses " + tribeToLose + 
           " tribes and " + numAv + " AV.");
    
    ui.getMap().getRegionDesc(r.getId()).update();
    ui.updateModel();
  }
}
