package com.bbg.client.State;

import com.bbg.client.action.Action;
import com.bbg.client.model.Advance;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;

public class CitySupport extends GameState {
  public CitySupport(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
  }
  
  public void transition() {    
    ui.setStatus("");
    // Check for city farm support:
    // TODO: check for cartage
    ui.log("Check city food support");
    for (int i = 0; i < model.getNumRegion(); ++i) {
      Region r = model.getRegion(i);
      if (r.getCityAdvance()>0 && 
          r.hasFarm() == false && 
          !(r.hasSeaAccess() && model.hasAdvance(Advance.fishing))) {
        ui.log("City " + r.getName() + " loses 1 AV due to lack of food.");
        r.setCityAdvance(r.getCityAdvance()-1);
        ui.getMap().getRegionDesc(i).update();
      }
    }
    
    setEndOfStateAction( new Action(){
      public void execute() {
        ui.log("Upkeep is over");
        setEndOfStateAction(new GameState.NextPhaseAction(engine.populationPhase));
      }
    });
  }

}
