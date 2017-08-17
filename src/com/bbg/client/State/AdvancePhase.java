package com.bbg.client.State;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import com.bbg.client.GameOverException;
import com.bbg.client.action.Action;
import com.bbg.client.action.RegionAction;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;
import com.bbg.client.ui.RegionDesc;
import com.bbg.client.ui.GameUi.GameDialog;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;

public class AdvancePhase extends GameState {
  public class SelectActiveRegion extends RegionAction {    
    public void execute(RegionDesc d) {
      ui.setActiveRegion(d);      
      // Check enable state of region:        
      Iterator it = actions.iterator();
      while(it.hasNext()) {
        RegionAction action = (RegionAction)it.next();
        ui.enableRegionAction(action.canExecute(d), action);
      }
    }
  }
  
  public class CreateFarm extends RegionAction {   
    static final int TRIBE_COST = 2;
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        !r.getRegion().hasFarm() && 
        (r.getRegion().hasForest()) &&
        r.getRegion().getNumTribe() >=TRIBE_COST;
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        ui.log("Create farm using forest in Region " + desc.getRegion().getName() );
        Region r = desc.getRegion();
        r.setHasForest(false);
        r.setHasFarm(true);
        r.setNumTribe(r.getNumTribe()-TRIBE_COST);
        
        desc.update();
        clearAdvanceState();
      }      
    }
  }
  
  public class Agriculture extends RegionAction {
    static final int TRIBE_COST = 2;
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        !r.getRegion().hasFarm() && 
        model.hasAdvance(Advance.agriculture) && !useAgriculture &&
        r.getRegion().getNumTribe() >=TRIBE_COST;
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        ui.log("Create farm using agriculture in Region " + desc.getRegion().getName() );
        useAgriculture = true;
        r.setHasFarm(true);
        r.setNumTribe(r.getNumTribe()-TRIBE_COST);
        
        desc.update();
        clearAdvanceState();
      }      
    }    
  }
  
  public class Horticulture extends RegionAction {
    static final int TRIBE_COST = 3;
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        !r.getRegion().hasForest() &&
        model.hasAdvance(Advance.horticulture) &&
        r.getRegion().getNumTribe() >=TRIBE_COST;
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        ui.log("Create Forest using Horticulture in Region " + desc.getRegion().getName() );        
        r.setHasForest(true);
        r.setNumTribe(r.getNumTribe()-TRIBE_COST);
        
        desc.update();
        clearAdvanceState();
      }      
    }    
  }
  
  public class CreateCity extends RegionAction {
    static final int TRIBE_COST = 4;
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        r.getRegion().getNumTribe() >= TRIBE_COST &&
        r.getRegion().getCityAdvance() == 0;
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        Region r = desc.getRegion();
        ui.log("Create city in Region " + r.getName() );
        r.setNumTribe(r.getNumTribe()-TRIBE_COST);
        r.setCityAdvance(1);
        
        desc.update();
        ui.updateModel();
        clearAdvanceState();
      }      
    }    
  }
  
  public class Expedition extends RegionAction {
    public boolean canExecute(RegionDesc desc) {
      if (desc ==null) {
        return false;
      }
      
      Region r = desc.getRegion();
      return
        (r.hasFrontierAccess() || (model.hasAdvance(Advance.navigation) && r.hasSeaAccess())) && 
        r.getNumTribe()>0;
    }
    
    public void execute(final RegionDesc desc) {      
      throw new GameOverException("Mining");
      /*
      if (canExecute(desc)) {
        final GameUi.GameDialog dlg = ui.createDialog("Select tribe to decimate");
        dlg.addBtn( "Ok", new Command() {
          public void execute() {
            dlg.hideModal();
            commerce(dlg.getReturnCode(),desc);
            clearAdvanceState();
          }
        });
        dlg.addResourceSelector(desc.getRegion().getNumTribe(), 0, desc.getRegion().getNumTribe());
        dlg.showModal();
      }
      */
    }    
    
    void commerce(int tribe, RegionDesc desc) {
      Region r = desc.getRegion();
      r.setNumTribe(r.getNumTribe() - tribe);
      desc.update();
      
      EventCard ec = StateUtil.drawEvent(model,ui);
              
        StringBuffer log = new StringBuffer();
      int expeditionDifficulty = 0;
      if (model.hasAdvance(Advance.navigation) && r.hasSeaAccess()) {
        log.append("Sea expedition");
        if (model.hasAdvance(Advance.astronomy)) {
          log.append(" using astronomy");
          tribe *= 2;
        }
        expeditionDifficulty = ec.getBlue();
      } else {
        log.append("Foreign expedition");
        if (model.hasAdvance(Advance.cavalry)) {
          log.append(" using cavalry");
          tribe *= 2;
        }
        expeditionDifficulty = ec.getGreen();
      }
      if (tribe > expeditionDifficulty) {
        int gold = tribe - expeditionDifficulty;
        model.setGold(model.getGold() + gold);
        ui.updateModel();

        log.append("... success with " + gold + " gold.");
      } else {
        log.append("... failed.");
      }

      ui.updateModel();

      ui.log(log.toString());
      clearAdvanceState();      
    }
  }
  
  public class Mining extends RegionAction {
    GameUi.GameDialog dlg;
    RegionDesc desc;
    static final int TRIBE_COST = 2;
    public Mining() {
      dlg = ui.createDialog("Continue mining?");
      dlg.addBtn( "Yes", new Command() {
        public void execute() {
          mine();
        }
      });
      dlg.addBtn( "No", new Command() {
        public void execute() {
          endMining();
          clearAdvanceState();
        }
      });
    }
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        model.hasAdvance(Advance.mining) &&
        (r.getRegion().hasMountain() || r.getRegion().hasVolcano()) && 
        r.getRegion().getNumTribe()>=TRIBE_COST;
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        this.desc = desc;
        desc.getRegion().setNumTribe(desc.getRegion().getNumTribe()-TRIBE_COST);
        desc.update();
        ui.updateModel();
        mine();
      }
    }
    
    void mine() {
      EventCard ec = StateUtil.drawEvent(model,ui);          
      
      if( ec == null ) {
        endMining();
        nextState(engine.gameOver);
      }            
      else {
        if (ec.getGold()>0) {
          ui.log("Mining gather " + ec.getGold() + " gold.");
         
          model.setGoldMined(model.getGoldMined()+ec.getGold());
          
          ui.updateModel();
          dlg.showModal();
        }
        else {
          ui.setStatus("Mining has failed.");
          ui.log("Mining has failed.");
          
          model.setGoldMined(0);
          endMining();
        }
      }
    }
    
    void endMining() {
      model.setGold(model.getGold() + model.getGoldMined());
      model.setGoldMined(0);
      
      ui.updateModel();
      dlg.hideModal();
    }
  }
  
  public class ResearchAdvance extends RegionAction {
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        advanceRegion.contains(r.getRegion()) == false &&
        r.getRegion().getCityAdvance() > 0 &&
        model.getNumAdvance() > model.getAdvanceAcquired().size();
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        // Evaluate possible Advance:
        Region r = desc.getRegion();
        final Vector advances = new Vector();
        Iterator it = model.getAdvanceAvailable().iterator();
        while (it.hasNext()) {
          Advance advance = (Advance)it.next();
          if ( (advance.getRequirement() == null || 
                advance.getRequirement().isFulfilled(model,r)) &&
                advance.getCost().isFulfilled(model,r)) {
            advances.add(advance);
          }
        }
        
        final GameUi.GameDialog dlg = ui.createDialog("Choose an advance");
        dlg.addAdvanceSelector(advances);
                
        dlg.addBtn( "Ok", new Command() {
          public void execute() {
            dlg.hideModal();
            if (dlg.getReturnCode()>0) {
              // Get selected advance
              Advance a = (Advance)advances.elementAt(dlg.getReturnCode()-1);
              ui.log(a.getName() + " is researched.");
              
              advanceRegion.add(desc.getRegion());
              model.acquireAdvance(a,desc.getRegion());
              ui.updateAdvance();
              
              // Update
              desc.update();
              ui.updateModel();
              clearAdvanceState();
            }
          }
        });
        
        dlg.showModal();
      }      
    }    
  }
  
  public class BuildWonder extends RegionAction {
    public boolean canExecute(RegionDesc r) {
      return 
        r != null &&
        findAvailableWonder(r.getRegion()).size() > 0;
    }
    
    public void execute(final RegionDesc desc) {
      if (canExecute(desc)) {
        // Evaluate possible Advance:
        Region r = desc.getRegion();
        final Vector advances = findAvailableWonder(r);
        
        final GameUi.GameDialog dlg = ui.createDialog("Choose a Wonder to build");
        dlg.addAdvanceSelector(advances);
                
        dlg.addBtn( "Ok", new Command() {
          public void execute() {
            dlg.hideModal();
            if (dlg.getReturnCode()>0) {
              // Get selected advance
              Advance a = (Advance)advances.elementAt(dlg.getReturnCode()-1);
              ui.log(a.getName() + " is built.");
              
              model.acquireWonder(a,desc.getRegion());
              ui.updateWonder();
              
              // Update
              desc.update();
              ui.updateModel();
              clearAdvanceState();
            }
          }
        });
        
        dlg.showModal();
      }      
    }    
    
    Vector findAvailableWonder(Region r) {
      Vector advances = new Vector();
      Iterator it = model.getWonderAvailable().iterator();
      while (it.hasNext()) {
        Advance advance = (Advance)it.next();
        if ( (advance.getRequirement() == null || 
              advance.getRequirement().isFulfilled(model,r)) &&
              advance.getCost().isFulfilled(model,r)) {
        }
      }
      return advances;
    }
   
  }
  
  Vector actions = new Vector();
  HashSet advanceRegion = new HashSet(); 
  boolean useAgriculture = false;
  public AdvancePhase(GameEngine engine, GameModel model,GameUi ui) {
    super(engine,model,ui);
    
    // Create action here and disabled them:
    RegionAction action = new CreateFarm();
    action.setName("Create farm (forest + 2 tribes)");
    actions.add(action);
    
    action = new CreateCity();
    action.setName("Create City (4 tribes)");
    actions.add(action);
    
    action = new Expedition();
    action.setName("Expedition");
    actions.add(action);
    
    action = new Mining();
    action.setName("Mining (3 tribes)");
    actions.add(action);
    
    action = new ResearchAdvance();
    action.setName("Research Advance");
    actions.add(action);
    
    action = new BuildWonder();
    action.setName("Build Wonder");
    actions.add(action);
    
    action = new Agriculture();
    action.setName("Agriculture (create farm, 2 tribes)");
    actions.add(action);
    
    action = new Horticulture();
    action.setName("Horticulture (create forest, 3 tribes)");
    actions.add(action);
    
    Iterator it = actions.iterator();
    while(it.hasNext()) {
      action = (RegionAction)it.next();
      ui.addRegionAction(action);
      ui.enableRegionAction(false, action);
    }
    
  }
  
  public void transition() {
    ui.setPhase( "Advance phase" );
    ui.setStatus("Select a region to advance.");
    
    // Reset event display:
    // model.setDrawEvent(null);
    // model.setCurrentEvent(null);
    
    // Reset advance state
    advanceRegion.clear();
    useAgriculture = false;
        
    setSelectRegionAction(new SelectActiveRegion());
    setEndOfStateAction( new Action(){
      public void execute() {
        ui.log("Advance is over");
        clearAdvanceState();        
        nextState(engine.upkeepPhase);
      }
    });
        
    clearAdvanceState();
  }
    
  public void clearAdvanceState() {
    ui.setActiveRegion(null);    
    
    // Disabled all actions:
    // Check enable state of region:        
    Iterator it = actions.iterator();
    while(it.hasNext()) {
      RegionAction action = (RegionAction)it.next();
      ui.enableRegionAction(false, action);
    }
    ui.updateModel();
  }
}
