package com.cortexanonymus.client.State;

import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.ui.GameUi;

public class PostEventPhase extends GameState {
    public PostEventPhase(GameEngine engine, GameModel model,GameUi ui) {
      super(engine,model,ui);
    }
    
    public void transition() {
      ui.setPhase("End of event phase");
      ui.setStatus("End of event phase");      
      setEndOfStateAction(new GameState.NextPhaseAction(engine.advancePhase));
    }
}
