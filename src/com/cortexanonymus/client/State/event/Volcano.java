package com.cortexanonymus.client.State.event;

import java.util.Vector;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.StateUtil;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;

/**
  VOLCANO:
  Note: A Volcano is a specialized Mountain; it provides
  Stone as if it was a regular Mountain. A Volcano
  should be drawn on the map like a Mountain, except
  with smoke rising from the top and maybe some cool
  lava flowing from the top peak.
  
  1. Draw the next Event Card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2.1. If the Active Region has no Mountains or
  Volcanoes:
  Create a new Volcano in the Active Region.
  Tribes in the Active Region are reduced to 1.
  
  2.2. If the Active Region has a Mountain, but no
  Volcano:
  Re-draw the Mountain as a Volcano.
  Reduce City AVs by 2 in the Active Region.
  Reduce Tribes to 1 in the Active Region.
  Farms and Wonders are Decimated in the Active
  Region.
  
  2.3. If the Active Region has a Volcano:
  Decimate Cities, Farms, Tribes, Forest and Wonders in
  the Active Region.
  Create a Desert in the Active Region.
  In all Neighboring Regions, Reduce Tribes by 2.
  
  2.3.1 If you have Engineering, Cities cannot be
  Reduced below 1AV. Decimated Cities are
  instead reduced to 1AV.
  
  3. If you have Medicine, Create 1 Tribe in each
  Region that was affected by the Volcano.
*/
public class Volcano extends EventState {
  Region activeRegion;
  public Volcano(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Volcano!");
    ui.setStatus("Volcano");
    
    EventCard ec = StateUtil.drawEvent(model, ui);

    activeRegion = model.getRegion(ec.getRegionIndex());
    if (activeRegion.hasMountain() == false
        && activeRegion.hasVolcano() == false) {
      ui.log("Create Volcano and reduce tribe to 1 " + activeRegion.getName());

      activeRegion.setHasVolcano(true);

      if (activeRegion.getNumTribe() > 0) {
        if (model.hasAdvance(Advance.medecine)) {
          ui.log("Medecine helps against volcano");
          activeRegion.setNumTribe(2);
        } else {
          activeRegion.setNumTribe(1);
        }
      }
    } else if (activeRegion.hasVolcano()) {
      ui.log("Activate volcano and expand desolation in region "
          + activeRegion.getName());

      activeRegion.setHasFarm(false);
      activeRegion.setHasForest(false);
      activeRegion.setHasDesert(true);
      activeRegion.setWonders(new Vector());

      if (activeRegion.getNumTribe() > 0 && model.hasAdvance(Advance.medecine)) {
        activeRegion.setNumTribe(1);
      } else {
        activeRegion.setNumTribe(0);
      }

      int cityAdvance = activeRegion.getCityAdvance();
      if (cityAdvance > 0 && model.hasAdvance(Advance.engineering)) {
        activeRegion.setCityAdvance(1);
      } else {
        activeRegion.setCityAdvance(0);
      }

      for (int i = 0; i < activeRegion.getNumAdjacentRegion(); ++i) {
        Region r = activeRegion.getAdjacentRegion(i);
        int tribe = r.getNumTribe();
        if (tribe > 0) {
          r.setNumTribe(tribe - 2);
          if (model.hasAdvance(Advance.medecine)) {
            r.setNumTribe(r.getNumTribe() + 1);
          }
          ui.getMap().getRegionDesc(r.getId()).update();
          ui.log("Region " + r.getName() + " loses 2 tribes.");
        }
      }
    } else if (activeRegion.hasMountain()) {
      ui.log("Create Volcano, reduce AV by 2 and tribe to 1 in region "
          + activeRegion.getName());

      activeRegion.setHasMountain(false);
      activeRegion.setHasVolcano(true);

      activeRegion.setHasFarm(false);
      activeRegion.setWonders(new Vector());

      if (activeRegion.getNumTribe() > 0) {
        if (model.hasAdvance(Advance.medecine)) {
          activeRegion.setNumTribe(2);
        } else {
          activeRegion.setNumTribe(1);
        }
      }

      int cityAdvance = activeRegion.getCityAdvance();
      if (cityAdvance > 0) {
        activeRegion.setCityAdvance(cityAdvance - 2);
        if (model.hasAdvance(Advance.engineering)
            && activeRegion.getCityAdvance() == 0) {
          activeRegion.setCityAdvance(1);
        }
      }
    }

    ui.getMap().getRegionDesc(activeRegion.getId()).update();
    nextState(nextState);
  }
  
}