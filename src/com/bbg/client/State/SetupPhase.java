package com.bbg.client.State;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.bbg.client.action.RegionAction;
import com.bbg.client.data.Events;
import com.bbg.client.model.Advance;
import com.bbg.client.model.Deck;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;
import com.bbg.client.ui.RegionDesc;

public class SetupPhase extends GameState {
  class InitialPopulationAction extends RegionAction {
    public boolean canExecute(RegionDesc d) {
      return d != null;
    }
    
    public void execute(RegionDesc d) {
      if (canExecute(d)) {
        ui.log("Add  tribe in region " + d.getRegion().getName());
        
        int tribeNum = d.getRegion().getNumTribe() + 1;
        d.getRegion().setNumTribe(tribeNum);
        
        d.update();
        ui.updateModel();
        
        if( model.getNumTribe() >= 3 ) {
          setEndOfStateAction(new GameState.NextPhaseAction(engine.populationPhase));
        }
      }
    }
  }
  
  public SetupPhase(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
  }
  public void transition() {
    // Init all stuff for start of game:
    ui.setPhase("Game Setup");
    ui.setStatus("");
    
    normalSetup();

    // testSetup();
    
    ui.updateModel();
    
    // Place 3 tribes in one region:
    ui.setStatus("Place 3 tribe in any one region");
    setSelectRegionAction( new InitialPopulationAction());
  }
  
  void normalSetup() {
    // Era 1
    model.setEra(1);
    
    // Shuffle deck and discard 3 first cards    
    model.getEventDeck().shuffle();
    ui.log("Deck is shuffle, 3 events are discarded");
    
    model.getEventDeck().drawDeck();
    model.getEventDeck().drawDeck();
    model.getEventDeck().drawDeck();
  }
  
  void testSetup() {
    for (int i = 0; i < model.getNumRegion(); ++i) {
      Region r = model.getRegion(i);
      // r.setCityAdvance( 4);
      // r.setNumTribe(5);
      ui.getMap().getRegionDesc(i).update();
    }
    
    Vector originalEvents = Events.generateEventList();
    HashMap eventMap = new HashMap();
    for (int i = 0; i< originalEvents.size(); ++i) {
      EventCard c = (EventCard)originalEvents.elementAt(i);
      eventMap.put(String.valueOf(c.getId()), c);      
    }
    
    model.setEra(1);
    
    // Generate our own stacked event deck:
    Vector events = new Vector();
    events.add(eventMap.get("12"));
    events.add(eventMap.get("14"));
    events.add(eventMap.get("3"));
    events.add(eventMap.get("9"));
    events.add(eventMap.get("1"));
    events.add(eventMap.get("9"));
    events.add(eventMap.get("2"));
    events.addAll(originalEvents);       
    
    model.setEventDeck(new Deck(events));
    
    // model.acquireAdvance(Advance.diplomacy);
    
    // model.getTradingPartners().add("Atlantea");
        
    // model.setGold(100);
    
  }
}
