package com.bbg.client.State;

import java.util.HashSet;
import java.util.Iterator;

import com.bbg.client.action.Action;
import com.bbg.client.action.RegionAction;
import com.bbg.client.model.Advance;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;
import com.bbg.client.ui.RegionDesc;
import com.google.gwt.user.client.Command;

public class Movement extends GameState {
  class MoveAction extends RegionAction {
    RegionDesc src;    
    public boolean canExecute(RegionDesc d) {
      return d != null;
    }
    
    public void execute(RegionDesc d) {
      if (canExecute(d)) {        
        if (src == null) {
          if (d.getRegion().getNumTribe() > 0)
          {
            src = d;
            Iterator it = legalMove[src.getRegion().getId()].iterator();            
            highlightRegionDescList(it, true);
            ui.setStatus("Select a region where to move.");
                        
          }
          else
          {
            ui.setStatus("Select a region containing tribe.");
          }
        }
        else 
        {              
          if (legalMove[src.getRegion().getId()].contains(d)) {
            ui.log("Move from region: " + src.getRegion().getName() + " to region: " + d.getRegion().getName());
            src.getRegion().setNumTribe(src.getRegion().getNumTribe()-1);
            d.setNumMovingTribe(d.getNumMovingTribe()+1);
            
            ui.setStatus("Select a region from which to move.");
          }
          else
          {
            ui.setStatus("This is not a valid movement.");      
          }
          
          Iterator it = legalMove[src.getRegion().getId()].iterator();
          highlightRegionDescList(it, false);
                      
          src.update();
          d.update();
          ui.updateModel();
          
          src = null;
        }
      }
    }
  }
  
  HashSet[] legalMove;
  public Movement(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
    legalMove = new HashSet[model.getNumRegion()];
    for (int i = 0; i < legalMove.length; ++i) {
      legalMove[i] = new HashSet();
    }
  }
  
  public void transition() {
    // If no city and no tribes: game OVER!
    if (model.getNumTribe() == 0 && model.getNumCity() == 0) {
      nextState(engine.gameOver);
    }
    else{      
      ui.setPhase("Movement");
      ui.setStatus("Select a region from which to move.");
      
      computeLegalMove();
      
      setSelectRegionAction(new MoveAction());
      
      setEndOfStateAction( new Action() {
        public void execute() {
          // Convert all moving tribe into real tribe:
          for (int  i = 0; i < ui.getMap().getNumRegionDesc(); ++i) {
            RegionDesc d = (RegionDesc)ui.getMap().getRegionDesc(i);
            d.setSelected(false);
            
            if (d.getNumMovingTribe()>0) {
              d.getRegion().setNumTribe( d.getNumMovingTribe() + d.getRegion().getNumTribe());
              d.setNumMovingTribe(0);
              d.update();
            }
            
            legalMove[i].clear();
          }
          
          ui.log("Move is over.");
          nextState(engine.eventPhase);
        }
      });
     
    }
  }
  
  void computeLegalMove() {
    // TODO: implant Navigation for a group of tribe. Navigation only on common sea
    // TODO: Equestrian: tribe may not cross frontier
    for (int  i = 0; i < model.getNumRegion(); ++i) {
      Region from = (Region)model.getRegion(i);       
      if (model.hasAdvance(Advance.equestrian)) {
        for (int  d = 0; d < ui.getMap().getNumRegionDesc(); ++d) {
          // Everything is legal
          legalMove[i].add(ui.getMap().getRegionDesc(d));
        }
      }
      else{
        if (from.hasSeaAccess() && 
            (model.hasAdvance(Advance.fishing)||model.hasAdvance(Advance.navigation))) {
          for (int  d = 0; d < ui.getMap().getNumRegionDesc(); ++d) {
            RegionDesc desc = ui.getMap().getRegionDesc(d);
            if (desc.getRegion().hasSeaAccess()) {
              // Add all region with Sea access
              legalMove[i].add(desc);
            }            
          }
        }
        
        if (model.hasAdvance(Advance.roadbuilding)) {
          // Add 2 border region
          for (int  r = 0; r < from.getNumAdjacentRegion(); ++r) {
            Region adj = from.getAdjacentRegion(r);
            int descIndex = adj.getId();
            legalMove[i].add(ui.getMap().getRegionDesc(descIndex));
            
            for (int  r2 = 0; r2 < adj.getNumAdjacentRegion(); ++r2) {
              Region adj2 = from.getAdjacentRegion(r);
              descIndex = adj2.getId();
              legalMove[i].add(ui.getMap().getRegionDesc(descIndex));
            } 
          } 
        }
        else {
          // Add adjacent Region
          for (int  r = 0; r < from.getNumAdjacentRegion(); ++r) {
            Region adj = from.getAdjacentRegion(r);
            int descIndex = adj.getId();
            legalMove[i].add(ui.getMap().getRegionDesc(descIndex));
          } 
        }
      }      
      // Ensure that we aen't a legal move
      legalMove[i].remove(from);      
    }
    
  }
}