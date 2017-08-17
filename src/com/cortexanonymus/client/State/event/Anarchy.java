package com.cortexanonymus.client.State.event;

import java.util.Vector;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.GameState;
import com.cortexanonymus.client.State.StateUtil;
import com.cortexanonymus.client.State.AdvancePhase.SelectActiveRegion;
import com.cortexanonymus.client.action.Action;
import com.cortexanonymus.client.action.RegionAction;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;
import com.cortexanonymus.client.ui.RegionDesc;

/**
 * 
 * ANARCHY:
  1. In any Region where the amount of Tribes is greater
  than the City AV, Reduce the City AV by 1 and
  Reduce Tribes by 3. Continue to Reduce Tribes and
  City AV’s this way until the City AV is 1, or until
  amount of Tribes is less than the City AV.
  
  1.1. If you have Literacy, Reduce Tribes by 5
  instead of 3.
  
  1.2. If you have Law, do not Reduce City AV.
  Reduce Tribes by the City AV in each Region.
  
  1.3. If you have Organized Religion, only a
  maximum of 4 Regions are Affected. You select
  the Regions. You may select Regions without
  Cities. Regions with no Cities do not feel the
  effects of Anarchy.
  
  2. If you have Slave Labor, Draw the next card.
  Reduce Tribes throughout your Empire an
  additional amount as shown in the RED
  CIRCLE.
 *
 */

public class Anarchy extends EventState {  
  class SlaveRevoltAction extends RegionAction {
    int tribeToLose;
    
    public SlaveRevoltAction(int tribeToLose) {
      this.tribeToLose = tribeToLose;      
      ui.setStatus("Slave revolt cause " + tribeToLose + " tribes decimation. Select region to bear loss.");      
    }
    
    public boolean canExecute(RegionDesc desc) {
      return desc.getRegion().getNumTribe()>0;
    }
    
    public void execute(RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        --tribeToLose;
        r.setNumTribe(r.getNumTribe()-1);
        desc.update();
        ui.updateModel();
                
        if (tribeToLose>0 && model.getNumTribe()>0) {
          ui.setStatus(tribeToLose + " tribes decimation remaining. Select region to bear loss.");
        }
        else {
          nextState(nextState);
        }
      }
    }
  }
  
  class SelectAnarchyRegion extends RegionAction {
    int numAnarchyRegion;
    Vector regions = new Vector();
    public SelectAnarchyRegion(int numAnarchyRegion) {
      this.numAnarchyRegion = numAnarchyRegion;
      for (int i = 0; i < model.getNumRegion();++i) {
        regions.add(model.getRegion(i));
      }
      highlightRegionList(regions.iterator(), true);      
      ui.setStatus("Anarchy in " + numAnarchyRegion + 
          " regions Select a region to suffer from Anarchy!");
    }
    
    public boolean canExecute(RegionDesc desc) {
      return regions.contains(desc.getRegion());
    }
        
    public void execute(RegionDesc desc) {      
      anarchy(desc.getRegion());
      regions.remove(desc.getRegion());
      desc.setSelected(false);
      
      --numAnarchyRegion;
      if (numAnarchyRegion>0 && !regions.isEmpty()) {
        ui.setStatus("Anarchy in " + numAnarchyRegion + 
        " regions Select a region to suffer from Anarchy!");
      }
      else {
        highlightRegionList(regions.iterator(), false);
        checkSlaveRevolt();
      }
    }
  }
  
  public Anarchy(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase( "Anarchy Event" );
    ui.setStatus("Anarchy");
    if (model.hasAdvance(Advance.organizedReligion))  {
      setSelectRegionAction(new SelectAnarchyRegion(4));
    }
    else {
      for (int i = 0; i < model.getNumRegion();++i) {        
        anarchy(model.getRegion(i));
      }
      checkSlaveRevolt();
    }    
  }
  
  void anarchy(Region r) {
    int tribeDecimation = 3;
    int cityDecimation = 1;
    if (model.hasAdvance(Advance.literacy)) {
      tribeDecimation = 5;
      ui.log("Literacy helps reduce Anarchy");
    }
    if (model.hasAdvance(Advance.law)) {
      ui.log("Law helps reduce Anarchy");
      cityDecimation = 0;
      tribeDecimation = r.getCityAdvance(); 
    }
    while (isAnarchy(r)) {
      ui.log("Revolt in region " + r.getName() + " decimate " + tribeDecimation + " tribes and " + cityDecimation + " City AV.");
      r.setNumTribe(r.getNumTribe() - tribeDecimation);
      r.setCityAdvance(r.getCityAdvance() - cityDecimation);
      
      ui.getMap().getRegionDesc(r.getId()).update();
      ui.updateModel();
    }
  }
  
  void checkSlaveRevolt() {
    if (model.hasAdvance(Advance.slaveLabor)) {
      ui.log("Slave Revolt!");
      EventCard ec = StateUtil.drawEvent(model,ui);      
      int tribeToLose = ec.getRed();
      setSelectRegionAction(new SlaveRevoltAction(tribeToLose));
    }
    else {
      setEndOfStateAction(new GameState.NextPhaseAction(nextState));
    }
  }
  
  boolean isAnarchy(Region r) {
    return r.getCityAdvance()>1 && r.getNumTribe()>r.getCityAdvance();
  }
}
