package com.cortexanonymus.client.State.event;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.StateUtil;
import com.cortexanonymus.client.State.event.Anarchy.SlaveRevoltAction;
import com.cortexanonymus.client.action.RegionAction;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;
import com.cortexanonymus.client.ui.RegionDesc;

/**
  CORRUPTION:
  1. Draw the next Event Card. Your level of Corruption
  is the value of the GREEN SQUARE.
  
  1.1. If you have Government, add 3 to your
  Corruption value.
  
  1.2. If you have Literacy, divide your level of
  Corruption in half, round down.
  
  2. Reduce City AVs throughout your Empire equal to
  the amount of Corruption. You may choose which
  Cities to reduce. Any City Reduced to 0 AV is
  Decimated.
  
  3. Decimate all Gold you currently have.
  
  3.1. If you have Law, do not Decimate your
  Gold.

*/
public class Corruption extends EventState {
  class CorruptionAction extends RegionAction {
    int avToLose;
    public CorruptionAction(int avToLose) {
      this.avToLose = avToLose;
      
      ui.setStatus("Corruption causes " + avToLose + " AV decimation. Select regions to bear loss.");
    }
    
    public boolean canExecute(RegionDesc desc) {
      return desc.getRegion().getCityAdvance()>0;
    }
    
    public void execute(RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        --avToLose;
        r.setCityAdvance(r.getCityAdvance()-1);
        desc.update();
        ui.updateModel();

        if (avToLose>0 && model.getNumAdvance()>0) {
          ui.setStatus(avToLose + " AV decimation remaining. Select region to bear loss.");
        }
        else {
          if (model.hasAdvance(Advance.law)) {
            ui.log("Law protect the Empire's gold.");
          }
          else {
            ui.log("Decimate all gold");
            model.setGold(0);
          }
          nextState(nextState);
        }
      }
    }
  }
  
  public Corruption(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Corruption!");
    ui.setStatus("Corruption");
    
    EventCard ec = StateUtil.drawEvent(model,ui);
    
    int avToLose = ec.getGreen();
    if (model.hasAdvance(Advance.government)) {
      avToLose += 3;
      ui.log("Governement increase corruption by 3.");
    }
    if (model.hasAdvance(Advance.literacy)) {
      ui.log("Lieracy halves corruption.");
      avToLose /= 2;
    }

    setSelectRegionAction(new CorruptionAction(avToLose));
    
  }
}
