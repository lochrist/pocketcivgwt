package com.bbg.client.State.event;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.GameState;
import com.bbg.client.model.Event;
import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;

public abstract class EventState extends GameState {
  Event event;
  int id;
  GameState nextState;
  public EventState(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
    this.id = id;
  }
  
  public void setEvent(Event e) {
    event = e;
  }
  
  public void setNextState(GameState gameState) {
    this.nextState = gameState;
  }
}
