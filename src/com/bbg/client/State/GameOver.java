package com.bbg.client.State;

import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;

public class GameOver extends GameState {
  public GameOver(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
  }
  
  public void transition() {
    ui.setPhase("Game Over");
    ui.setStatus("Game Over");
    
    
  }
}
