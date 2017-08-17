package com.bbg.client.State.event;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.StateUtil;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;

/**
  EPIDEMIC:
  1. Draw the next Event Card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2. Draw the next Event Card. Based on the symbols on
  the right side of the Event box of the original Epidemic
  Event, add up the values in the same symbols on the
  newly drawn card (See the “Q and A” section later on
  for an example). Starting with the Active Region, and
  working your way through Neighboring Regions,
  Decimate Tribes equal to the amount of Population
  Loss. Tribes in Regions must be fully Decimated
  before moving on to a new Region.
  If a series of Regions without Tribes block a Region
  with Tribes, those Tribes are safe. You may be able to
  cleverly choose your Regions that are affected by
  Epidemic to Reduce the population loss.
  
  2.2. If you have Roadbuilding or Equestrian,
  Epidemics continue through Regions with no Tribes.
  You cannot attempt to spare some Regions from the
  Epidemic by cleverly emptying out Regions of Tribes
  with the Epidemic, as the Epidemic will simply “jump
  over” the empty Region.
  You must Reduce as many Tribes as possible until you
  have reached the Population Loss value, you have 2
  Tribes remaining in your Empire.
  
  3. If you have Medicine, the amount of
  Population Loss is the value of the BLUE HEX
  divided by 2. You may round down fractions.
  
  4. If you have Roadbuilding, and you completely
  Decimate all Tribes in a Region with a City,
  Reduce the City AV by 2.
*/
public class Epidemic extends EventState {
  public Epidemic(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  Region activeRegion;
  public void transition() {
    ui.setPhase("Epidemic!");
    ui.setStatus("Active region and all neighboring region are affected by epidemic.");
    
    EventCard ec = StateUtil.drawEvent(model, ui);

    activeRegion = model.getRegion(ec.getRegionIndex());
    EventCard epidemicCard = StateUtil.drawEvent(model, ui);

    int epidemic = 0;
    if (model.hasAdvance(Advance.medecine)) {
      epidemic = ec.getBlue() / 2;
    } else {
      epidemic += event.getRed() * epidemicCard.getRed();
      epidemic += event.getGreen() * epidemicCard.getGreen();
      epidemic += event.getBlue() * epidemicCard.getBlue();
    }

    int tribe = activeRegion.getNumTribe();
    activeRegion.setNumTribe(tribe - epidemic);
    ui.log("Region " + activeRegion.getName() + " loses tribes.");
    epidemic -= tribe;
    ui.getMap().getRegionDesc(activeRegion.getId()).update();

    // TODO  currently we ignore epidemic propagation and Roadbulding and Equestrian
    int i = 0;
    while (epidemic > 0 && i < activeRegion.getNumAdjacentRegion()) {
      Region r = activeRegion.getAdjacentRegion(i);
      ui.log("Region " + r.getName() + " loses tribes.");
      tribe = r.getNumTribe();
      r.setNumTribe(tribe - epidemic);
      epidemic -= tribe;
      ++i;
      ui.getMap().getRegionDesc(r.getId()).update();
    }
    nextState(nextState);
  }
}
