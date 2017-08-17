package com.bbg.client.State;

import java.util.HashMap;

import com.bbg.client.data.Events;
import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;

public class GameEngine {
  GameModel model;
  GameUi ui;
  
  SetupPhase setupPhase;  
  PopulationPhase populationPhase;
  GameOver gameOver;
  EventPhase eventPhase;
  PostEventPhase postEventPhase;
  AdvancePhase advancePhase;
  UpkeepPhase upkeepPhase;  
  HashMap eventMap;
  
  public GameEngine(GameModel model, GameUi ui) {
    this.model = model;
    this.ui = ui;
    
    this.ui.setStatus("Welcome to Civilization!");
    
    setupPhase = new SetupPhase(this,model,ui);     
    populationPhase = new PopulationPhase(this,model,ui);
    gameOver = new GameOver(this,model,ui);
    eventPhase = new EventPhase(this,model,ui);
    postEventPhase = new PostEventPhase(this,model,ui);
    advancePhase = new AdvancePhase(this,model,ui);
    upkeepPhase = new UpkeepPhase(this,model,ui);
    
    eventMap = Events.generateEventMap(this,model,ui);
  }
  
  public void start() {
    setupPhase.transition();
  }
  
  public HashMap getEventMap() {
    return eventMap;
  }
  
  public GameState gameOver() {
    return gameOver;
  }
}
