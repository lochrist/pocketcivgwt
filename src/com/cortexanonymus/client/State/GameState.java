package com.cortexanonymus.client.State;

import java.util.Iterator;

import com.cortexanonymus.client.State.AdvancePhase.SelectActiveRegion;
import com.cortexanonymus.client.action.Action;
import com.cortexanonymus.client.action.RegionAction;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.ui.GameUi;
import com.cortexanonymus.client.ui.RegionDesc;

public abstract class GameState {
  protected GameEngine engine;
  protected GameModel model;
  protected GameUi ui;
  protected Action endOfStateAction;
  protected RegionAction selectRegion;
  
  public class NextPhaseAction extends Action {
    GameState ns;
    public NextPhaseAction(GameState ns) {
      this.ns = ns;
    }
    
    public void execute() {
      nextState(ns);
    }
  }
  
  public GameState(GameEngine engine, GameModel model,GameUi ui) {
    this.engine = engine;
    this.model = model;
    this.ui = ui;
  }
  
  public void nextState(GameState ns) {
    // try to clean up action mess:
    ui.setActiveRegion(null);
    
    highlightRegionDescList(ui.getMap().getRegionDescIterator(), false);
    
    removeEndOfStateAction();
    removeSelectRegionAction();
        
    ui.setStatus("");
    ui.updateModel();
    
    ns.transition();
  }
  
  public void setSelectRegionAction(RegionAction selectRegion) {
    // There can be only one select region action at a time.
    removeSelectRegionAction();
    ui.addSelectRegionAction(selectRegion);
    this.selectRegion = selectRegion;
  }
  
  public void setEndOfStateAction(Action endOfStateAction) {
    // There can be only one End of State Action at the time
    removeEndOfStateAction();
    ui.addEndOfStateAction(endOfStateAction);
    this.endOfStateAction = endOfStateAction;
  }
  
  public void removeSelectRegionAction() {
    if (selectRegion != null){
      ui.removeSelectRegionAction(selectRegion);
      endOfStateAction = null;
    }
  }
  
  public void removeEndOfStateAction() {
    if (endOfStateAction != null){
      ui.removeEndOfStateAction(endOfStateAction);
      endOfStateAction = null;
    }
  }
  
  public void highlightRegionList(Iterator it, boolean isSelected) {
    while(it.hasNext()) {
      Region r = (Region)it.next();
      ui.getMap().getRegionDesc(r.getId()).setSelected(isSelected);
    }
  }
  
  public void highlightRegionDescList(Iterator it, boolean isSelected) {
    while(it.hasNext()) {
      RegionDesc desc = (RegionDesc)it.next();
      desc.setSelected(isSelected);      
    }
  }
  
  
  
  public abstract void transition();
}
