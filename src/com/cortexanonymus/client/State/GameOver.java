package com.cortexanonymus.client.State;

import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.ui.GameUi;

public class GameOver extends GameState {
  public GameOver(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Game Over");
    ui.setStatus("Game Over");
    
    
  }
}
