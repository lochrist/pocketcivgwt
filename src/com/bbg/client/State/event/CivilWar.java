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
  CIVIL WAR:
  1. Draw the next card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2. All City AVs in the Active Region, and in
  Neighboring Regions, are Reduced by 2. Cities
  Reduced to 0 are Decimated.
  
  2.1. If you have Military, Reduce City AV in
  Neighboring Regions by 3 instead of 2.
  
  2.2. If you have Mythology, Reduce City AV in
  Active Region by 3 instead of 2.
  
  2.3. If you have Civil Service, Cities cannot be
  reduced below 1.
  
  2.4. If you have Architecture, City AV
  reductions are reduced by 1. “Base” City
  Reductions are now 1. Architecture plus
  Military Reductions are 2 and Architecture plus
  Mythology are 2.
  
  3.1. Draw the next card.
  
  3.2. If you have Law, the number in the GREEN
  SQUARE indicates your Collateral Damage.
  
  3.3. If you don’t have Law, the number in the BLUE
  HEX indicates your Collateral Damage.
  
  4. Reduce total Tribes in the Affected Regions by the
  value of your Collateral Damage. You must expend all
  of the Collateral Damage, if possible; however, you
  choose how to Reduce your Tribes if you have more
  Tribes than Collateral Damage in the Affected
  Regions.
  If you have Arts, subtract 2 from Collateral
  Damage.
  If you have Theater, subtract 2 from Collateral
  Damage.
  If you have Meditation, divide your Collateral
  Damage in half, round down.
  After you have finished applying Collateral Damage, if
  you have Medicine, Create 1 Tribe in Affected Region.
*/
public class CivilWar extends EventState {
  public CivilWar(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  class CivilWarDamageAction extends RegionAction{
    int numTribeToLose;
    Vector legalRegion = new Vector();
    public CivilWarDamageAction(int numTribeToLose) {
      this.numTribeToLose = numTribeToLose;
      
      ui.log("Civil War cause " + numTribeToLose + " tribe loss.");
      ui.setStatus("Civil War cause " + numTribeToLose + " tribe loss. Select region to lose tribe.");
      
      legalRegion.add(activeRegion);
      Iterator it = activeRegion.getAdjacentRegions().iterator();
      while (it.hasNext()) {
        Region r = (Region)it.next();
        if (r.getNumTribe()>0) {
          legalRegion.add(r);
        }        
      }

      highlightRegionList(legalRegion.iterator(), true);
    }
    
    public boolean canExecute(RegionDesc d) {
      return legalRegion.contains(d.getRegion());
    }
    
    public void execute(RegionDesc desc){
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        r.setNumTribe(r.getNumTribe() -1);
        if (r.getNumTribe()==0) {
          legalRegion.remove(r);
          desc.setSelected(false);
        }
        
        desc.update();
        ui.updateModel();
        
        --numTribeToLose;
        if (numTribeToLose>0 && !legalRegion.isEmpty()){
          ui.setStatus("Still " + numTribeToLose + " tribe to lose. Select region to lose tribe.");
        }
        else {
          highlightRegionList(legalRegion.iterator(), false);
          applyMedecine();
        }
      }
    }
  }
  
  Region activeRegion;
  public void transition() {
    ui.setPhase("Civil War!");
    ui.setStatus("Civil War");
    
    EventCard ec = StateUtil.drawEvent(model,ui);
          
    activeRegion = model.getRegion(ec.getRegionIndex());
    int activeAvLost = 2;
    int neighborAvLost = 2;
    if (model.hasAdvance(Advance.military)) {
      neighborAvLost = 3;
    }
    if (model.hasAdvance(Advance.mythology)) {
      activeAvLost = 3;
    }
    if (model.hasAdvance(Advance.architecture)) {
      activeAvLost--;
      neighborAvLost--;
    }

    String log = "Civil war cause " + activeAvLost + " av lost in region "
        + activeRegion.getName() + " and " + neighborAvLost
        + " Av lost in neighboring region.";

    applyAvDamage(activeRegion, activeAvLost);

    int totalTribe = activeRegion.getNumTribe();
    for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
      applyAvDamage(activeRegion.getAdjacentRegion(i), neighborAvLost);
      totalTribe += activeRegion.getAdjacentRegion(i).getNumTribe();
    }

    ui.setStatus(log);
    ui.log(log);

    ec = StateUtil.drawEvent(model, ui);

    int tribeLoss = ec.getBlue();
    if (model.hasAdvance(Advance.law)) {
      tribeLoss = ec.getGreen();
    }

    if (model.hasAdvance(Advance.arts)) {
      tribeLoss -= 2;
    }
    if (model.hasAdvance(Advance.theater)) {
      tribeLoss -= 2;
    }
    if (model.hasAdvance(Advance.meditation)) {
      tribeLoss /= 2;
    }

    if (tribeLoss > 0) {
      if (totalTribe > tribeLoss) {
        setSelectRegionAction(new CivilWarDamageAction(tribeLoss));
      } else {
        ui.setStatus("Lose all tribes in region " + activeRegion.getName()
            + " and neighboring regions.");

        activeRegion.setNumTribe(0);
        for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
          Region r = activeRegion.getAdjacentRegion(i);
          r.setNumTribe(0);
        }

        applyMedecine();
      }
    }
  }
  
  void applyAvDamage(Region r,int damage) {
    if ( r.getCityAdvance()>0) {
      r.setCityAdvance(r.getCityAdvance()-damage);
      if (r.getCityAdvance()<1 && model.hasAdvance(Advance.civilService)) {
        r.setCityAdvance(1);
      }
    }
  }
  
  void applyMedecine() {
    boolean hasMedecine = model.hasAdvance(Advance.medecine); 
    if (hasMedecine) {
      ui.log("Medecine helps region " + activeRegion.getName());
      activeRegion.setNumTribe(activeRegion.getNumTribe()+1);
    }    
    ui.getMap().getRegionDesc(activeRegion.getId()).update();
    
    for(int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
      Region r = activeRegion.getAdjacentRegion(i);
      if (hasMedecine) {
        ui.log("Medecine helps region " + r.getName());
        r.setNumTribe(r.getNumTribe()+1);
      }
      ui.getMap().getRegionDesc(r.getId()).update(); 
    }
    
    nextState(nextState);
  }
}
