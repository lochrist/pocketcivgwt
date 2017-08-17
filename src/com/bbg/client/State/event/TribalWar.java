package com.bbg.client.State.event;

import java.util.Iterator;
import java.util.Vector;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.StateUtil;
import com.bbg.client.action.RegionAction;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;
import com.bbg.client.ui.RegionDesc;
/**
  TRIBAL WAR:
  If you have Government, disregard Tribal War,
  otherwise follow the steps below.
  1. Draw the next card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2.1.1. If Active Region has Tribes, select two
  Neighboring Regions with Tribes (if possible).
  
  2.1.2. If you have Music, select one Neighboring
  Region with Tribes (if possible).
  
  2.2. Reduce Neighboring Tribes by the amount of
  Tribes in the Active Region.
  
  2.3.1. Reduce Tribes in Active Region by 2.
  
  2.3.2. If you have Music, Reduce Tribes in the
  Active Region by 1.
  
  3. If the Active Region has no Tribes, disregard Tribal
  War.
*/
public class TribalWar extends EventState {
  public TribalWar(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  class SelectAttackedRegion extends RegionAction {
    int numRegionAttacked;
    int activeRegionLoss;
    Vector legalRegion = new Vector();
    Region activeRegion;
    public SelectAttackedRegion(Region activeRegion) {
      this.activeRegion = activeRegion;
      if (model.hasAdvance(Advance.music)) {
        numRegionAttacked = 1;
        activeRegionLoss = 1;
      }
      else {
        numRegionAttacked = 2;
        activeRegionLoss = 2;
      }
            
      Iterator it = activeRegion.getAdjacentRegions().iterator();
      while (it.hasNext()) {
        Region r = (Region)it.next();
        if (r.getNumTribe()>0) {
          legalRegion.add(r);
        }
      }
            
      if (legalRegion.size()<numRegionAttacked) {
        numRegionAttacked = legalRegion.size();
      }
      
      if (numRegionAttacked>0) {
        highlightRegionList(legalRegion.iterator(), true);
        ui.setStatus("Select " + numRegionAttacked + " neighboring region that will suffer Tribal War."); 
      }
      else {
        endAction();
      }
    }
    
    public boolean canExecute(RegionDesc desc) {
      return 
        desc != null &&
        legalRegion.contains(desc.getRegion());
    }
    
    public void execute(RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        ui.log("Region " + r.getName() + " is attacked and lose " + activeRegion.getNumTribe() + " tribes.");
        
        r.setNumTribe(r.getNumTribe()-activeRegion.getNumTribe());
        desc.setSelected(false);
        legalRegion.remove(r);
        
        desc.update();
        ui.updateModel();
        
        --numRegionAttacked;
        if (numRegionAttacked > 0 && !legalRegion.isEmpty()) {
          ui.setStatus("Select " + numRegionAttacked + " neighboring region that will suffer Tribal War.");
        }
        else {          
          highlightRegionList(legalRegion.iterator(), false);
          endAction();
        }
      }
    }
    
    void endAction() {
      ui.log("Active region " + 
          activeRegion.getName() + 
          " suffers " + 
          activeRegionLoss + 
          " tribes loss." );
      activeRegion.setNumTribe(activeRegion.getNumTribe()-activeRegionLoss);
      ui.getMap().getRegionDesc(activeRegion.getId()).update();
  
      nextState(nextState);
    }
  }
  
  public void transition() {
    ui.setPhase("Tribal War!");
    ui.setStatus("Tribal War");
    
    if (model.hasAdvance(Advance.government)) {
      ui.log("Government protects from Tribal War.");
      nextState(nextState);
    }
    else {
      EventCard ec = StateUtil.drawEvent(model, ui);
      Region activeRegion = model.getRegion(ec.getRegionIndex());
      ui.log("Active region is " + activeRegion.getName());
      if (activeRegion.getNumTribe() == 0) {
        ui.log("No tribal war since acive region has no tribe.");
        nextState(nextState);
      } else {
        setSelectRegionAction(new SelectAttackedRegion(activeRegion));
      }
    }   
  }
}

