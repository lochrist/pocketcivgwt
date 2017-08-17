package com.cortexanonymus.client.State.event;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.GameState;
import com.cortexanonymus.client.model.Event;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.ui.GameUi;

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
