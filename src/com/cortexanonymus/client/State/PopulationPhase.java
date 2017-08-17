package com.cortexanonymus.client.State;

import com.cortexanonymus.client.GameOverException;
import com.cortexanonymus.client.action.RegionAction;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;
import com.cortexanonymus.client.ui.RegionDesc;

public class PopulationPhase extends GameState {
  class MiniumPopulationAction extends RegionAction {
    public boolean canExecute(RegionDesc d) {
      return d != null && d.getRegion().getCityAdvance() > 0;
    }
    
    public void execute(RegionDesc d) {
      if (canExecute(d)) {    
        ui.log( "Add a tribe to region " + d.getRegion().getName() );
        int tribeNum = d.getRegion().getNumTribe() + 1;
        d.getRegion().setNumTribe(tribeNum);
        
        d.update();
        ui.updateModel();
        
        if( model.getNumTribe() >= 3 ) {
          checkEndGame();
        }
      }
    }
  }
  
  Movement movement;
  public PopulationPhase(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
    this.movement = new Movement(engine,model,ui);
  }
  public void transition() {
    // Increment game turn
    model.setTurn(model.getTurn()+1);
    
    ui.setPhase( "Population Growth and Movement" );
    ui.log("Turn " + model.getTurn());
    
    // Create one tribe in each region that currently has one tribe:    
    ui.log( "Create one tribe in each region with a tribe." );
    for (int i = 0; i < model.getNumRegion(); ++i) {
      Region r = model.getRegion(i);
      if (r.getNumTribe() > 0) {
        ui.log( "Add tribe to region " + r.getName() );
        r.setNumTribe(r.getNumTribe() + 1);
        ui.getMap().getRegionDesc(i).update();
      }
    }
    
    if (model.getNumTribe() < 3 && model.getNumCity()>0) {
      ui.setStatus("You have less than 3 tribes. Place 1 tribe in any one region with a city:");
      setSelectRegionAction(new MiniumPopulationAction());
    }
    else {
      checkEndGame();
    }
  }
  
  void checkEndGame() {
    ui.log("Check for endgame...");
    if (model.getNumCity()==0 && model.getNumTribe()==0) {
      throw new GameOverException("No tribes or no cities.");
    }
    else {
      setEndOfStateAction(new GameState.NextPhaseAction(movement));
    }
  }
}