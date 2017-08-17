package com.bbg.client.State;

import com.bbg.client.action.Action;
import com.bbg.client.action.RegionAction;
import com.bbg.client.model.Advance;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;
import com.bbg.client.ui.RegionDesc;

public class UpkeepPhase extends GameState {
  public class CityGrowthAction extends RegionAction {
    int cityGrowthAvailable;
    public CityGrowthAction(int cityGrowthAvailable) {
      this.cityGrowthAvailable = cityGrowthAvailable;
      ui.setPhase("City Growth"); 
      ui.setStatus(
          "You have " + 
          cityGrowthAvailable + 
          " to spend on city growth (max per city is " +
          model.getMaximumCityAdvance() +
          ").");
    }
    
    public boolean canExecute(RegionDesc d) {
      return 
        d.getRegion().getCityAdvance()>0 && 
        d.getRegion().getCityAdvance()<model.getMaximumCityAdvance();
    }
    
    public void execute(RegionDesc d) {
      
      if (canExecute(d)) {
        ui.log("Region " + d.getRegion().getName() + " has increase its AV.");
        d.getRegion().setCityAdvance(d.getRegion().getCityAdvance()+1);
        --cityGrowthAvailable;
        d.update();
        ui.updateModel();
        if (cityGrowthAvailable == 0) {
          nextState();
        }
        else {
          ui.setPhase("City Growth"); 
          ui.setStatus(
              "You have " + 
              cityGrowthAvailable + 
              " to spend on city growth (max per city is " +
              model.getMaximumCityAdvance() +
              ").");
          
        }
      }
      else {
        ui.setStatus("This region is not valid for city growth.");
      }
    }
  }
    
  CitySupport citySupport;  
  public UpkeepPhase(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
    citySupport = new CitySupport(engine, model,ui);
  }
  
  public void transition() {
    ui.setPhase( "Upkeep phase" );
    ui.setStatus("");
    
    // Check support for each region:
    ui.log("Check tribe resource support");
    for (int i = 0; i < model.getNumRegion(); ++i){
      Region r = model.getRegion(i);
      if (r.getSupport() < r.getNumTribe()) {
        ui.log("Region " + r.getName() + " loses " + (r.getNumTribe() - r.getSupport()) + " tribes due to lack of support.");
        r.setNumTribe(r.getSupport());
        ui.getMap().getRegionDesc(i).update();
      }
    }
    
    // Decimate gold unless Coinage:
    if (model.hasAdvance(Advance.coinage) == false) {
      ui.log("Decimate all gold");
      model.setGold(0);      
    }
    
    if (model.hasAdvance(Advance.banking) && model.getGold()>3) {
      ui.log("Banking and 3 gold produce 1 gold!");
      model.setGold(model.getGold()+1);
    }
    
    if (model.hasAdvance(Advance.patronage) && model.getNumCity()>4){
      int gold = model.getNumCity() - 4;
      ui.log("Patronage and " + model.getNumCity() + " cities produce " + gold + " gold!");
      model.setGold(gold + model.getGold());
    }
    
    ui.updateModel();
    // TODO :Check for Advance special actions:
    if (model.getCityAdvanceGrowth()>0) {
      setSelectRegionAction(new CityGrowthAction(model.getCityAdvanceGrowth()));      
      setEndOfStateAction(new Action(){
        public void execute() {
          nextState();
        }
      });
    }
    else {
      nextState();
    }
  }
  
  void nextState() {
    ui.log("City Growth is over");
    nextState(citySupport);
  }
}
