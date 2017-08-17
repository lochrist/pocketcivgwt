package com.bbg.client.State;

import com.bbg.client.model.GameModel;
import com.bbg.client.ui.GameUi;

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
