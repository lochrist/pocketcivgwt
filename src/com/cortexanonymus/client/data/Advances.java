package com.cortexanonymus.client.data;

import java.util.Vector;

import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.Cost;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.model.Region;
import com.cortexanonymus.client.model.Requirement;

public class Advances { 
  static public Vector generateAdvances() {
    Vector v = new Vector();
    
    Advance a = new Advance();
    a.setName(Advance.masonry);
    a.setVictory(3);
    Cost c = new Cost();
    c.setTribe(2);
    c.setStoneNeeded(true);
    a.setCost(c);
    a.setDesc("During Upkeep, you can increase one City AV by 1. The maximum AV of a City is 2, unless otherwise noted.");
    v.add(a);
    
    
    a = new Advance();
    a.setName(Advance.engineering);
    a.setVictory(5);
    c = new Cost();
    c.setTribe(3);
    c.setWoodNeeded(true);
    c.setStoneNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.masonry) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.masonry);
      }
    });
    a.setReduceEffect("Volcano,Earthquake,Tsunami");
    a.setDesc("The maximum AV of a city is 3, unless otherwise noted.");
    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.architecture);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(3);
    c.setGold(4);
    c.setWoodNeeded(true);
    c.setStoneNeeded(true);
    a.setCost(c);    
    a.setRequirement( new Requirement(Advance.engineering) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.engineering);
      }
    });
    a.setReduceEffect("Civil War");
    a.setDesc("The maximum AV of a city is 4, during attacks, reduce 8 attacking force for every 1 city AV.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.agriculture);
    a.setVictory(5);
    c = new Cost();
    c.setTribe(4);
    c.setStoneNeeded(true);
    a.setCost(c);    
    a.setDesc("Farmers can be created in any Region, and you do NOT decimate Forests to create farms. You still must decimate 2 tribes to create a Farm.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.horticulture);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(1);    
    c.setWoodNeeded(true);    
    a.setCost(c);            
    a.setDesc("Decimate 3 tribes in a Region to create a forest.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.irrigation);
    a.setVictory(10);
    c = new Cost();
    c.setTribe(1);
    c.setGold(1);
    c.setWoodNeeded(true);    
    a.setCost(c);    
    a.setRequirement( new Requirement("Agriculture or Horticulture") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.agriculture) || 
        model.hasAdvance(Advance.horticulture);        
      }
    });
    a.setReduceEffect("Flood,Famine,Sandstorm");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.equestrian);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(4);    
    c.setFoodNeeded(true);
    a.setCost(c);    
    a.setRequirement( new Requirement("Agriculture or Horticulture") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.agriculture) || 
        model.hasAdvance(Advance.horticulture);        
      }
    });
    a.setReduceEffect("Attacks");
    a.setIncreaseEffect("Bandits,Epidemic");
    a.setDesc("You may move tribes to any Region.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.cavalry);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(4);
    c.setGold(5);
    c.setWoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.equestrian) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.equestrian);        
      }
    });    
    a.setDesc("A single tribe counts as 2 when going on an Expedition to the Frontier.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.government);
    a.setVictory(6);
    c = new Cost();
    c.setTribe(3);    
    c.setFoodNeeded(true);
    a.setCost(c);
    a.setReduceEffect("Tribal War");
    a.setIncreaseEffect("Corruption");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.military);
    a.setVictory(6);
    c = new Cost();
    c.setTribe(4);    
    c.setGold(3);
    c.setFoodNeeded(true);
    c.setStoneNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.government) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.government);    
      }
    });
    a.setReduceEffect("Attacks");
    a.setIncreaseEffect("Uprising,Civil War");
    a.setDesc("Single Tribes count as 2 during Attacks.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.diplomacy);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(4);    
    c.setGold(1);
    c.setWoodNeeded(true);    
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.government) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.government);    
      }
    });    
    a.setDesc("Can attempt to create Trading partners with Visiting Empires.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.democracy);
    a.setVictory(10);
    c = new Cost();
    c.setGold(6);
    c.setWoodNeeded(true);    
    c.setStoneNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.government) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.government);    
      }
    });    
    a.setReduceEffect("Uprising,Bandits");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.civilService);
    a.setVictory(12);
    c = new Cost();
    c.setGold(5);
    c.setWoodNeeded(true);
    c.setFoodNeeded(true);
    c.setStoneNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.democracy) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.democracy);    
      }
    });
    a.setReduceEffect("Tsunami,Civil War");    
    a.setDesc("During Upkeep, you increase one City AV by 1.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.music);
    a.setVictory(3);
    c = new Cost();
    c.setTribe(1);
    c.setWoodNeeded(true);    
    a.setCost(c);
    a.setReduceEffect("Tribal War");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.literacy);
    a.setVictory(3);
    c = new Cost();
    c.setTribe(2);
    c.setWoodNeeded(true);    
    a.setCost(c);    
    a.setReduceEffect("Anarchy,Corruption");        
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.medecine);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(4);
    c.setGold(2);
    c.setWoodNeeded(true);
    c.setFoodNeeded(true);    
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.literacy) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.literacy);    
      }
    });
    a.setReduceEffect("Tsunami,Epidemic,Earthquake,Volcano,Civil War");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.arts);
    a.setVictory(10);
    c = new Cost();
    c.setTribe(2);    
    c.setWoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement("Literacy or Music") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.literacy) || 
        model.hasAdvance(Advance.music);
      }
    });
    a.setReduceEffect("Civil War");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.theater);
    a.setVictory(10);
    c = new Cost();
    c.setTribe(2);    
    c.setWoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement("Literacy or Music") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.literacy) || 
        model.hasAdvance(Advance.music);
      }
    });
    a.setReduceEffect("Civil War");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.patronage);
    a.setVictory(8);
    c = new Cost();
    c.setGold(6);    
    c.setFoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement("Arts and Theater") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.arts) && model.hasAdvance(Advance.theater);
      }
    });
    a.setDesc("During Upkeep, for every City you have over 4, you collect 1 Gold.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.mythology);
    a.setVictory(2);
    c = new Cost();
    c.setTribe(1);    
    c.setFoodNeeded(true);
    a.setCost(c);
    a.setIncreaseEffect("Civil War");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.meditation);
    a.setVictory(4);
    c = new Cost();
    c.setTribe(2);    
    c.setFoodNeeded(true);
    c.setWoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.mythology) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.mythology);
      }
    });
    a.setReduceEffect("Civil War,Superstition");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.philosophy);
    a.setVictory(12);
    c = new Cost();
    c.setTribe(4);    
    c.setFoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.meditation) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.meditation);
      }
    });
    a.setDesc("If you don't draw a HANDSHAKE symbol during a Visitation, you may draw a next card for a HNDSHAKE symbol.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.organizedReligion);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(4);    
    c.setGold(6);
    c.setWoodNeeded(true);
    c.setStoneNeeded(true);
    a.setCost(c);    
    a.setRequirement( new Requirement(Advance.mythology) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.mythology);
      }
    });    
    a.setReduceEffect("Uprising,Anarchy");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.ministry);
    a.setVictory(10);
    c = new Cost();
    c.setGold(8);
    c.setFoodNeeded(true);
    c.setWoodNeeded(true);
    c.setStoneNeeded(true);
    a.setCost(c);    
    a.setRequirement( new Requirement(Advance.organizedReligion) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.organizedReligion);
      }
    });    
    a.setReduceEffect("Attacks");
    a.setDesc("After determining the size of an Atacking force from Bandist or a Visitation, draw next card, reduce Attacking force by the Hex, gain 1 gold for each Attacking force reduce.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.law);
    a.setVictory(10);
    c = new Cost();
    c.setGold(5);    
    c.setStoneNeeded(true);
    a.setCost(c);    
    a.setRequirement( new Requirement("Governement, Philosophy") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.government) && model.hasAdvance(Advance.philosophy);
      }
    });    
    a.setReduceEffect("Anarchy,Corruption,Uprising,Civil War");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.fishing);
    a.setVictory(6);
    c = new Cost();
    c.setTribe(5);    
    c.setWoodNeeded(true);
    a.setCost(c);    
    a.setDesc("Must be acquired by a city in a regionthat borders the Sea. Cities that boder the sea do not need to check for Farm Support during Upkeep. You may move your tribes between any Regions that border the Sea at the cost of 1 Tribe.");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.navigation);
    a.setVictory(5);
    c = new Cost();
    c.setTribe(2);    
    c.setWoodNeeded(true);
    a.setCost(c);           
    a.setDesc("Tribes that border the Sea may go on Expeditions (to the sea). Treat sea expeditions the same as you would if the tribes were going to a Frontier. You may move your tribes between regions that border the Sea at the cost of 1 Tribes.");    
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.astronomy);
    a.setVictory(3);
    c = new Cost();
    c.setTribe(4);    
    c.setStoneNeeded(true);
    a.setCost(c);       
    a.setIncreaseEffect("Superstition");
    a.setDesc("Once per Era, you may ignore an Event card and it's result and draw a new Event card.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.shipping);
    a.setVictory(8);
    c = new Cost();
    c.setTribe(2);    
    c.setGold(2);
    c.setWoodNeeded(true);
    a.setCost(c);       
    a.setRequirement( new Requirement("Navigation or Fishing") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.navigation) || model.hasAdvance(Advance.fishing);
      }
    });   
    a.setReduceEffect("Trade");
    a.setDesc("Increase the potential amount of gold you can earn from a Trade.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.sailsAndRigging);
    a.setVictory(6);
    c = new Cost();
    c.setTribe(1);    
    c.setGold(2);
    c.setWoodNeeded(true);
    a.setCost(c);       
    a.setRequirement( new Requirement("Navigation , Astronomy") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.navigation) && model.hasAdvance(Advance.astronomy);
      }
    });       
    a.setDesc("Single Tribes going on Expedition to the Sea couns as 2 tribes.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.coinage);
    a.setVictory(3);
    c = new Cost();
    c.setTribe(1);    
    c.setGold(3);
    c.setStoneNeeded(true);
    a.setCost(c);       
    a.setDesc("Do not decimate gold during upkeep. Effectively, you can now store Gold for the entire game.");
    v.add(a);

    a = new Advance();
    a.setName(Advance.banking);
    a.setVictory(5);
    c = new Cost();
    c.setTribe(3);    
    c.setGold(3);
    c.setStoneNeeded(true);
    a.setCost(c);       
    a.setRequirement( new Requirement(Advance.coinage) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.coinage);
      }
    });   
    a.setDesc("During upkeep, if you have over 3 Gold in your stock, add 1 gold.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.slaveLabor);
    a.setVictory(1);
    c = new Cost();    
    c.setFoodNeeded(true);
    a.setCost(c);       
    a.setIncreaseEffect("Anarchy, Uprising,Bandits");   
    a.setDesc("During upkeep, you can increase one City AV by 1. The maximum AV of a city is 2 unless otherwise noted.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.mining);
    a.setVictory(5);
    c = new Cost();    
    c.setStoneNeeded(true);
    a.setCost(c);
    a.setDesc("You may draw for gold during your Upkeep at the cost of 3 tribes in a region with mountain/volcano.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.surveying);
    a.setVictory(5);
    c = new Cost();  
    c.setGold(2);
    c.setTribe(1);
    c.setStoneNeeded(true);
    c.setWoodNeeded(true);
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.mining) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.mining);
      }
    });  
    a.setDesc("You may look through the discard pile of Events before deciding to mine.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.metalWorking);
    a.setVictory(7);
    c = new Cost();    
    c.setTribe(2);
    c.setStoneNeeded(true);    
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.mining) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.mining);
      }
    });  
    a.setReduceEffect("Attacks");
    a.setDesc("The maximum AV of a city is 3, unless otherwise noted.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.cartage);
    a.setVictory(2);
    c = new Cost();    
    c.setTribe(2);
    c.setStoneNeeded(true);    
    a.setCost(c);    
    a.setDesc("Cities upkeep requirement for farms are not confined to local regions.");
    v.add(a);
    
    a = new Advance();
    a.setName(Advance.roadbuilding);
    a.setVictory(6);
    c = new Cost();    
    c.setTribe(1);
    c.setStoneNeeded(true);    
    a.setCost(c);
    a.setRequirement( new Requirement(Advance.cartage) {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.cartage);
      }
    });  
    a.setDesc("Tribes may move across two borders");
    v.add(a);
    
    return v;
  }
  
  static public Vector generateWonders() {
    Vector v = new Vector();
    
    Advance a = new Advance();
    a.setName("Giant Guilded Statue of Me");
    a.setVictory(15);
    Cost c = new Cost();
    c.setTribe(4);
    c.setGold(20);
    a.setCost(c);
    a.setRequirement( new Requirement("Arts") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.arts);
      }
    });    
    v.add(a);
    
    a = new Advance();
    a.setName("City of Atlantis");
    a.setVictory(30);
    c = new Cost();
    c.setTribe(5);
    c.setGold(25);
    a.setCost(c);
    a.setRequirement( new Requirement("Trading partner with Atlantea and a city with sea access.") {
      public boolean isFulfilled(GameModel model,Region r) {
        return r.getCityAdvance()>0 && r.hasSeaAccess();
      }
    });     
    v.add(a);
    
    a = new Advance();
    a.setName("Coliseum of Death");
    a.setVictory(30);
    c = new Cost();
    c.setTribe(4);
    c.setGold(20);
    a.setCost(c);
    a.setRequirement( new Requirement("Theater and a city") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.theater) && r.getCityAdvance() > 0;
      }
    });     
    v.add(a);
    
    a = new Advance();
    a.setName("Hanging Gardens");
    a.setVictory(20);
    c = new Cost();
    c.setTribe(2);
    c.setGold(15);
    a.setCost(c);
    a.setRequirement( new Requirement("Irrigation and Arts") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.irrigation) && model.hasAdvance(Advance.arts);
      }
    });
    v.add(a);
    
    a = new Advance();
    a.setName("Hall of Justice");
    a.setVictory(30);
    c = new Cost();
    c.setTribe(4);
    c.setGold(15);
    a.setCost(c);
    a.setRequirement( new Requirement("City and Law") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.law) && r.getCityAdvance()>0;
      }
    });
    v.add(a);
    
    a = new Advance();
    a.setName("Great Wall of Solitude");
    a.setVictory(20);
    c = new Cost();
    c.setTribe(6);
    c.setGold(20);
    a.setCost(c);
    a.setRequirement( new Requirement("Region bordering frontier. Don't perform Visitation in this region. Expedition may not start in this region.") {
      public boolean isFulfilled(GameModel model,Region r) {
        return r.hasFrontierAccess();
      }
    });
    v.add(a);
    
    a = new Advance();
    a.setName("Hude Monolith of Impressivness");
    a.setVictory(12);
    c = new Cost();
    c.setTribe(4);
    c.setGold(10);
    a.setCost(c);    
    v.add(a);
    
    a = new Advance();
    a.setName("Amphitheater");
    a.setVictory(20);
    c = new Cost();
    c.setTribe(3);
    c.setGold(10);
    a.setCost(c);
    a.setRequirement( new Requirement("City and Theater") {
      public boolean isFulfilled(GameModel model,Region r) {
        return model.hasAdvance(Advance.theater) && r.getCityAdvance()>0;
      }
    });
    v.add(a);
    
    return v;
  }
}