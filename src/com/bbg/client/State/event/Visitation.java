package com.bbg.client.State.event;

import com.bbg.client.State.GameEngine;
import com.bbg.client.State.StateUtil;
import com.bbg.client.action.Action;
import com.bbg.client.action.RegionAction;
import com.bbg.client.model.Advance;
import com.bbg.client.model.EventCard;
import com.bbg.client.model.GameModel;
import com.bbg.client.model.Region;
import com.bbg.client.ui.GameUi;
import com.bbg.client.ui.RegionDesc;
import com.google.gwt.user.client.Command;

/**
 * VISITATION:
  1. If you are a Trading Partner with the visiting
  Empire, go immediately to TRADE (see below).
  
  2.1. If you have Diplomacy, you can attempt to
  make this Empire a Trading Partner.
  
  2.2. Decimate any amount of Tribes and Gold
  to create a Diplomatic Offer. And 1 point to
  your offer for each 1 Gold or 1 Tribe you
  Decimate.
  
  2.3. Draw the next Event card. If your
  Diplomatic Offer is greater than the value of the
  RED CIRCLE, then you have created a Trading
  Partner with the visiting Empire. Make a note
  of this on your pad of paper. Trading Partners
  are permanent for the rest of the game.
  
  2.4. If you have created a trading Partner, go
  immediately to TRADE (see below).
  
  3.1. If you are not a Trading Partner with this Empire,
  place this Event card off to the side and draw the next
  Event card and see if this card has a HANDSHAKE
  icon in the upper right corner.
  
  3.2. If you have Philosophy, you may draw a
  second Event card in an attempt to draw a
  HANDSHAKE symbol.
  
  4. If you reveal a HANDSHAKE icon, then the visiting
  Empire is TRADING with you (see TRADE above).
  However, they are not becoming permanent Trading
  Partners; they are just being nice this round.
  
  5. If you are not Trading, then the Empire that is
  visiting you has come to pillage your Empire!! Draw
  the next Event Card. The RED SQUARE indicates the
  Active Region. If the Active Region does not neighbor
  the Sea or Frontier, then there is no Attack, and the
  Event is over.
  Otherwise, draw the next Event card. Based on the
  symbols on the right side of the Event box of the
  original Visitation Event, add up the values in the same
  symbols on the newly drawn card (See the “Q and A”
  section later on for an example). This becomes the
  strength of the Attacking Force. See ATTACK above.
  
  TRADE:
  1. Draw the next Event card. Increase the amount of
  Gold you currently have by the value in the RED
  CIRCLE.
  
  2. If you have Shipping, and the card that you
  have just drawn to indicate your Gold income
  also has a HANDSHAKE, draw another Event
  card. Additionally increase the amount of Gold
  in your common stock by the value in the
  GREEN SQUARE.
*/
public class Visitation extends Attack {
  int tradeOffering;
  
  public class TribeOfferingAction extends RegionAction {
    public TribeOfferingAction() {
      ui.setStatus("Decimate tribe to make a trade offering. Offering is " + tradeOffering);
      ui.log("Decimate tribe to make a trade offering. Offering is " + tradeOffering);
    }
    
    public boolean canExecute(RegionDesc d) {
      return d.getRegion().getNumTribe()>0;
    }
    
    public void execute(RegionDesc d) {
      if (canExecute(d)) {
        Region r = d.getRegion();
        r.setNumTribe(r.getNumTribe()-1);
        
        d.update();
        ui.updateModel();
        
        tradeOffering++;
        
        ui.setStatus("Decimate tribe to make a trade offering. Offering is " + tradeOffering);
        ui.log("Decimate tribe to make a trade offering. Offering is " + tradeOffering);
      }
    }
  }
  
  public Visitation(int id,GameEngine engine, GameModel model,GameUi ui) {
    super(id,engine,model,ui);
  }   
  
  public void transition() { 
    if (model.getTradingPartners().contains(event.getDesc())) {
      trade();
    }
    else {
      // Check for trading partners:
      if (model.hasAdvance(Advance.diplomacy)) {
        checkTradingPartners();
      }
      else {
        checkVisitation();
      }
    }
  }
  
  void checkTradingPartners() {
    tradeOffering = 0;
    // Make gold offering
    final GameUi.GameDialog dlg = ui.createDialog("Select gold to offer for a trade partnership");
    dlg.addBtn( "Ok", new Command() {
      public void execute() {
        dlg.hideModal();
        tradeOffering += dlg.getReturnCode();
        model.setGold(model.getGold() - tradeOffering);
        makeGoldOffering();
      }
    });
    dlg.addResourceSelector(model.getGold(), 0, model.getGold());
    dlg.showModal();
  }
  
  void makeGoldOffering() {
    setSelectRegionAction(new TribeOfferingAction());
    setEndOfStateAction(new Action() {
      public void execute() {
        removeEndOfStateAction();
        removeSelectRegionAction();
        makeTradeOffer();
      }
    });
  }
  
  void makeTradeOffer() {
    ui.setStatus("Final trade offer is " + tradeOffering);
    EventCard ec = StateUtil.drawEvent(model, ui);
    if (tradeOffering > ec.getRed()) {
      ui.setStatus("Trade offer is success!");
      ui.log("Trade offer is success!");
      model.getTradingPartners().add(event.getDesc());
      trade();
    } else {
      ui.setStatus("Trade offer has failed.");
      ui.log("Trade offer has failed.");
      checkVisitation();
    }
        
  }
  
  void checkVisitation() {
    EventCard ec = StateUtil.drawEvent(model, ui);
    if (ec.isHasAlliance()) {
      trade();
    } else {
      if (model.hasAdvance(Advance.philosophy)) {
        ec = StateUtil.drawEvent(model, ui);
        if (ec.isHasAlliance()) {
          trade();
        }
      } else {
        // attack
        ec = StateUtil.drawEvent(model, ui);
        activeRegion = model.getRegion(ec.getRegionIndex());
        int visitForce = 0;
        visitForce += event.getRed() * ec.getRed();
        visitForce += event.getGreen() * ec.getGreen();
        visitForce += event.getBlue() * ec.getBlue();
        attack(visitForce);
      }
    }
  }
  
  void trade() {
    String toLog = "The mighty " + event.getDesc() + " Empire has agreed to trade with you."; 
    ui.setStatus(toLog);
    ui.log(toLog);
    EventCard ec = StateUtil.drawEvent(model, ui);
    int goldGain = ec.getRed();
    if (model.hasAdvance(Advance.shipping) && ec.isHasAlliance()) {
      if (ec == null) {
        nextState(engine.gameOver());
      } else {
        goldGain += ec.getRed() + ec.getGreen();
      }
    }
    model.setGold(model.getGold() + goldGain);
    ui.log("Your empire gain " + goldGain + " gold in trade.");
    nextState(nextState);
  }
}
