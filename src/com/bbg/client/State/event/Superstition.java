package com.bbg.client.State.event;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.StateUtil;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;
/**
  SUPERSTITION:
  1. Draw the next card. The number in the GREEN
  SQUARE indicates how many Event cards you must
  draw and discard. As normal, if the deck runs out of
  cards, do an End of Era check, shuffle all cards, draw
  and discard 3, and continue discarding until you have
  discarded the correct amount.
  
  1.1. If you have Astronomy, add 2 to the amount
  shown in the Green Square.
  
  1.2. If you have Meditation, you may, but are
  not required to, stop discarding Event cards if
  the Event deck has one card left.
*/
public class Superstition extends EventState {
  public Superstition(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Superstition!");
    ui.setStatus("Superstition");
    
     
    EventCard ec = StateUtil.drawEvent(model, ui);
    int cardToDiscard = ec.getGreen();
    if (model.hasAdvance(Advance.astronomy)) {
      ui.log("Astronomy increases supersition by 2.");
      cardToDiscard += 2;
    }

    while (ec != null && cardToDiscard-- > 0) {
      ui.log("Discard event " + ec.getId());
      ec = StateUtil.drawEvent(model, ui);

    }
    nextState(nextState);
  }
}