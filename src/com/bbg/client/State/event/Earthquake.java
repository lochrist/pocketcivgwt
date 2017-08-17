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
  EARTHQUAKE:
  Note: Fault Lines should be drawn in the affected
  Region as a small series of cracks. Fault Lines are not
  considered to be a Resource, therefore, they do not
  provide the additional “space” for Tribes during
  upkeep.
  1. Draw the next Event Card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2.1. If the Active Region has no Fault Line:
  Reduce City AV by 1 in the Active Region.
  Reduce Tribes by 2 in the Active Region.
  Create a Fault Line in the Active Region.
  
  2.2.1. If the Active Region has a Fault Line:
  Reduce City AV by 3 in the Active Region.
  Reduce Tribes by 4 in the Active Region.
  Decimate all Wonders in the Active Region
  Create a Fault Line in two neighboring Regions of your
  choice.
  
  2.2.2. Draw the next Event card. Multiply the value in
  the GREEN SQUARE by 2 to determine the Extra
  Population loss.
  Decimate this amount of Tribes in Neighboring
  Regions.
  If you have Engineering, Cities cannot be
  Reduced below 1AV. Decimated Cities are
  instead reduced to 1AV.
  If you have Medicine, Create 1 Tribe in Affected
  Regions.
*/
public class Earthquake extends EventState {
  class SelectFaultRegion extends RegionAction {
    Vector legalRegion;
    int numRegionToFault;
    public SelectFaultRegion(Vector legalRegion) {
      this.legalRegion = legalRegion;
      numRegionToFault = Math.min(legalRegion.size(),2);
      
      ui.setStatus("Select " + numRegionToFault + " to receive fault line.");
      ui.log("Select " + numRegionToFault + " to receive fault line.");
      
      highlightLegalRegion();
    }
    
    public boolean canExecute(RegionDesc desc) {
      return legalRegion.contains(desc.getRegion()) && desc.getRegion().hasCrack() == false;
    }
    
    public void execute(RegionDesc desc) {
      if (canExecute(desc)) {
        desc.getRegion().setHasCrack(true);
        desc.update();
        legalRegion.remove(desc.getRegion());
        --numRegionToFault;
        highlightLegalRegion();
        
        if (numRegionToFault==0 || legalRegion.isEmpty()) {
          highlightRegionList(legalRegion.iterator(), false);
          earthquakeDamage();
        }
      }
    }
    
    public void highlightLegalRegion() {
      Iterator it = legalRegion.iterator();
      while (it.hasNext()){
        RegionDesc desc = ui.getMap().getRegionDesc(((Region)it.next()).getId());        
        desc.setSelected(canExecute(desc));
      }
    }
  }
  
  class EarthquakeDamageAction extends RegionAction{
    int numTribeToLose;
    public EarthquakeDamageAction(int numTribeToLose) {
      this.numTribeToLose = numTribeToLose;
      
      ui.log("Earthquake cause " + numTribeToLose + " tribe loss.");
      ui.setStatus("Earthquake cause " + numTribeToLose + " tribe loss. Select region to lose tribe");
      
      highlightRegionList(activeRegion.getAdjacentRegions().iterator(), true);
    }
    
    public boolean canExecute(RegionDesc d) {
      return
        activeRegion.getAdjacentRegions().contains(d.getRegion())&&
        d.getRegion().getNumTribe()>0;
    }
    
    public void execute(RegionDesc desc){
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        r.setNumTribe(r.getNumTribe() -1);
        desc.update();
        ui.updateModel();
        
        --numTribeToLose;
        if (numTribeToLose>0){
          ui.setStatus("Still " + numTribeToLose + " tribe to lose. Select region to lose tribe");
          highlightRegionList(activeRegion.getAdjacentRegions().iterator(), true);
        }
        else {          
          checkMedecine();
        }
      }
    }
  }
  
  Region activeRegion;
  public Earthquake(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Earthquake!");
    ui.setStatus("Earthquake");
    
    EventCard ec = StateUtil.drawEvent(model,ui);
          
    activeRegion = model.getRegion(ec.getRegionIndex());
    if (activeRegion.hasCrack()) {

      String log = "Fault line in region "
          + activeRegion.getName()
          + " cause 3AV loss, 4 tribes loss, all wonders destroyed and create other fault line in 2 neighboring region.";
      ui.setStatus(log);
      ui.log(log);

      decimateCity(3);
      activeRegion.setNumTribe(activeRegion.getNumTribe() - 4);

      // Destroy wonders
      activeRegion.setWonders(new Vector());

      ui.getMap().getRegionDesc(activeRegion.getId()).update();
      ui.updateModel();

      // Add fault line in 2 neighboring region :
      Vector adjacentRegion = new Vector();
      for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
        if (activeRegion.getAdjacentRegion(i).hasCrack() == false) {
          adjacentRegion.add(activeRegion.getAdjacentRegion(i));
        }
      }

      if (adjacentRegion.size() > 0) {
        setSelectRegionAction(new SelectFaultRegion(adjacentRegion));
      } else {
        earthquakeDamage();
      }
    } else {
      ui.setStatus("Fault line case 1AV loss, 2 tribes loss and create fault line in region "
              + activeRegion.getName());
      ui.log("Fault line case 1AV loss, 2 tribes loss and create fault line in region "
              + activeRegion.getName());

      decimateCity(1);
      activeRegion.setNumTribe(activeRegion.getNumTribe() - 2);
      activeRegion.setHasCrack(true);

      ui.getMap().getRegionDesc(activeRegion.getId()).update();
      ui.updateModel();

      earthquakeDamage();
    }
    
  }
  
  void decimateCity(int decimation) {
    if (activeRegion.getCityAdvance()>0) {
      activeRegion.setCityAdvance(activeRegion.getCityAdvance()-decimation);
      if (model.hasAdvance(Advance.engineering) && activeRegion.getCityAdvance()<1) {
        ui.log("engineering protects region " + activeRegion.getName());
        activeRegion.setCityAdvance(1);
      }
    }    
  }
  
  void earthquakeDamage() {
    EventCard ec = StateUtil.drawEvent(model,ui);
    
    int damage = ec.getGreen() * 2;
    int population = 0;
    for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
      population += activeRegion.getAdjacentRegion(i).getNumTribe();
    }

    if (population > damage) {
      // Select population to decimate
      setSelectRegionAction(new EarthquakeDamageAction(damage));
    } else {
      // Decimate all population
      String log = "Earthquake cause all population in region "
          + activeRegion.getName() + " and adjacent region to be lost.";
      ui.setStatus(log);
      ui.log(log);

      for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
        activeRegion.getAdjacentRegion(i).setNumTribe(0);
        ui.getMap().getRegionDesc(activeRegion.getAdjacentRegion(i).getId())
            .update();
      }
      ui.updateModel();
      checkMedecine();
    }
  }
  
  void checkMedecine() {
    if (model.hasAdvance(Advance.medecine)) {
      ui.log("Medecine helps " + activeRegion.getName());
      
      activeRegion.setNumTribe(activeRegion.getNumTribe()+1);
      ui.getMap().getRegionDesc(activeRegion.getId()).update();
      for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {        
        Region r = activeRegion.getAdjacentRegion(i); 
        ui.log("Medecine helps " + r.getName());
        r.setNumTribe(r.getNumTribe()+1);
        ui.getMap().getRegionDesc(r.getId()).update();
      }
    }
    nextState(nextState);
  }
}
