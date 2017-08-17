package com.bbg.client.State;

import com.bbg.client.GameOverException;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;

public class StateUtil {
  public static EventCard drawEvent(GameModel model,GameUi ui) {
    String log = "Draw event..."; 
    EventCard ec = model.getEventDeck().drawDeck();
    if (ec == null) {
      // deck is empty: make an era check:
      log += "Deck is empty, era check!";
      ui.log(log);
      if (model.getNumCity() < model.getEra()) {
        throw new GameOverException("Era check failed");
      }
      else {        
        ui.log("Shuffle deck and discard 3 events.");
        model.getEventDeck().shuffle();
        model.getEventDeck().drawDeck();
        model.getEventDeck().drawDeck();
        model.getEventDeck().drawDeck();
        
        // Get the card for the new Era
        ec = model.getEventDeck().drawDeck();
        
        // Increment Era:
        model.setEra(model.getEra()+1);
        ui.log("Era is incremented to: " + model.getEra());        
      }
    }
    model.setDrawEvent(ec);
    ui.updateModel();
    return ec;
  }
}
