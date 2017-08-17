package com.bbg.client.State;

import com.bbg.client.State.event.EventState;
import com.bbg.client.model.Event;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;

public class EventPhase extends GameState {
  public EventPhase(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Event Phase");
    ui.setStatus("Draw event...");
        
    String log = "Draw event..."; 
    EventCard ec = StateUtil.drawEvent(model,ui);    
         
      // Check if we need to resolve event:
    Event event = ec.getEvent()[model.getEra()];
    if (event != null) {
      model.setCurrentEvent(ec);
      EventState eventState = (EventState) engine.getEventMap().get(
          event.getName());
      eventState.setEvent(event);
      eventState.setNextState(engine.advancePhase);
      nextState(eventState);
    } else {
      ui.log("No event this turn");
      // Pass to next phase:
      nextState(engine.postEventPhase);
    }      
  }
}
