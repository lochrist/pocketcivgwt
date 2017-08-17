package com.bbg.client.State.event;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.StateUtil;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;

/**
  BANDITS:
  1. Draw the next card. The number in the RED
  CIRCLE indicates the Active Region.
  
  2. If the Active Region, or a Neighboring Region,
  contains a Desert, you are being Attacked by Bandits.
  Otherwise, disregard the Bandit Event.
  
  3. If you are being attacked, keep this card off to the
  side, and then determine the size of the Attacking
  Force by drawing the Next Card. Based on the symbols
  on the right side of the Event box of the original Bandit
  Event, add up the values in the same symbols on the
  newly drawn card (See the “Q and A” section later on
  for an example). This becomes the strength of the
  Attacking Force. See Attack below.
  
  3.1. If you have Law, reduce the value of the
  Attacking Force by one BLUE HEX.
  
  3.2. If you have Democracy, reduce the value of
  the Attacking Force by one BLUE HEX.
  
  3.3. If you have Equestrian, increase the value
  of the Attacking Force by one BLUE HEX.
  
  3.4. If you have Slave Labor, increase the value
  of the Attacking Force by one BLUE HEX.
  You cannot become Trading Partners with Bandits.
*/
public class Bandits extends Attack {
  public Bandits(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Bandits!");
    ui.setStatus("Bandits");
    
    EventCard ec = StateUtil.drawEvent(model,ui);
    activeRegion = model.getRegion(ec.getRegionIndex());
    boolean hasDesert = activeRegion.hasDesert();
    for (int i = 0; hasDesert == false
        && i < activeRegion.getNumAdjacentRegion(); ++i) {
      Region r = activeRegion.getAdjacentRegion(i);
      hasDesert = r.hasDesert();
    }

    if (hasDesert) {
      EventCard banditsCard = StateUtil.drawEvent(model, ui);
      int banditsForce = 0;
      banditsForce += event.getRed() * banditsCard.getRed();
      banditsForce += event.getGreen() * banditsCard.getGreen();
      banditsForce += event.getBlue() * banditsCard.getBlue();

      if (model.hasAdvance(Advance.law)) {
        banditsForce -= banditsCard.getBlue();
      }
      if (model.hasAdvance(Advance.democracy)) {
        banditsForce -= banditsCard.getBlue();
      }
      if (model.hasAdvance(Advance.equestrian)) {
        banditsForce += banditsCard.getBlue();
      }
      if (model.hasAdvance(Advance.slaveLabor)) {
        banditsForce += banditsCard.getBlue();
      }
      attack(banditsForce);

    } else {
      nextState(nextState);
    }
  }
}

