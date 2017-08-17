package com.cortexanonymus.client.data;

import java.util.HashMap;
import java.util.Vector;

import com.cortexanonymus.client.State.GameEngine;
import com.cortexanonymus.client.State.event.Anarchy;
import com.cortexanonymus.client.State.event.Bandits;
import com.cortexanonymus.client.State.event.CivilWar;
import com.cortexanonymus.client.State.event.Corruption;
import com.cortexanonymus.client.State.event.Earthquake;
import com.cortexanonymus.client.State.event.Epidemic;
import com.cortexanonymus.client.State.event.Famine;
import com.cortexanonymus.client.State.event.Flood;
import com.cortexanonymus.client.State.event.Sandstorm;
import com.cortexanonymus.client.State.event.Superstition;
import com.cortexanonymus.client.State.event.TribalWar;
import com.cortexanonymus.client.State.event.Uprising;
import com.cortexanonymus.client.State.event.Visitation;
import com.cortexanonymus.client.State.event.Volcano;
import com.cortexanonymus.client.model.Event;
import com.cortexanonymus.client.model.EventCard;
import com.cortexanonymus.client.model.GameModel;
import com.cortexanonymus.client.ui.GameUi;

public class Events {
  static private HashMap events = new HashMap();

  static public Vector generateEventList() {
    Vector v = new Vector();
    // id, red, green, blue, gold, alliance
    EventCard c = new EventCard(1, 1, 7, 6, 0, true);
    c.getEvent()[1] = createEvent(Event.TRIBAL_WAR, 0, 0, 0, "");
    c.getEvent()[2] = createEvent(Event.EPIDEMIC, 0, 0, 1, "");
    c.getEvent()[3] = createEvent(Event.FAMINE, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.UPRISING, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.VISITATION, 0, 0, 2,
        "Floren");
    c.getEvent()[8] = createEvent(Event.CORRUPTION, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(2, 2, 4, 7, 2, false);
    c.getEvent()[1] = createEvent(Event.VOLCANO, 0, 0, 0, "");
    c.getEvent()[2] = createEvent(Event.TRIBAL_WAR, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.CORRUPTION, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.CIVIL_WAR, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.VISITATION, 1, 1, 1,
        "Gilda");
    c.getEvent()[7] = createEvent(Event.EARTHQUAKE, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.VISITATION, 0, 0, 3,
        "Atlantea");
    v.add(c);

    c = new EventCard(3, 3, 5, 8, 1, false);
    c.getEvent()[2] = createEvent(Event.BANDITS, 0, 1, 1, "");
    c.getEvent()[4] = createEvent(Event.EPIDEMIC, 1, 1, 1, "");
    c.getEvent()[5] = createEvent(Event.VISITATION, 0, 3, 0,
        "Atlantea");
    c.getEvent()[7] = createEvent(Event.VISITATION, 1, 1, 1,
        "Gilda");
    c.getEvent()[8] = createEvent(Event.CIVIL_WAR, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(4, 4, 6, 7, 2, false);
    c.getEvent()[1] = createEvent(Event.EARTHQUAKE, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.VISITATION, 0, 0, 2,
        "Floren");
    c.getEvent()[4] = createEvent(Event.SUPERSTITION, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.FAMINE, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.UPRISING, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.VISITATION, 0, 1, 2,
        "Nordic");
    v.add(c);

    c = new EventCard(5, 5, 3, 9, 1, true);
    c.getEvent()[3] = createEvent(Event.EPIDEMIC, 0, 0, 1, "");
    c.getEvent()[4] = createEvent(Event.VISITATION, 0, 0, 2,
        "Gilda");
    c.getEvent()[5] = createEvent(Event.CORRUPTION, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.TRIBAL_WAR, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.CORRUPTION, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.FLOOD, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(6, 6, 4, 6, 0, true);
    c.getEvent()[1] = createEvent(Event.FLOOD, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.VISITATION, 0, 0, 2,
        "Atlantea");
    c.getEvent()[4] = createEvent(Event.CIVIL_WAR, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.SANDSTORM, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.EPIDEMIC, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.EARTHQUAKE, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(7, 7, 5, 7, 1, false);
    c.getEvent()[2] = createEvent(Event.VISITATION, 0, 1, 1,
        "Nordic");
    c.getEvent()[3] = createEvent(Event.ANARCHY, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.VISITATION, 0, 0, 2,
        "Floren");
    c.getEvent()[5] = createEvent(Event.UPRISING, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.ANARCHY, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.BANDITS, 0, 0, 2, "");
    v.add(c);

    c = new EventCard(8, 8, 6, 8, 0, false);
    c.getEvent()[1] = createEvent(Event.SANDSTORM, 0, 0, 0, "");
    c.getEvent()[2] = createEvent(Event.TRIBAL_WAR, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.VISITATION, 0, 0, 2,
        "Floren");
    c.getEvent()[4] = createEvent(Event.FLOOD, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.VISITATION, 0, 1, 2,
        "Atlantea");
    c.getEvent()[8] = createEvent(Event.UPRISING, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(9, 1, 7, 9, 0, true);
    c.getEvent()[2] = createEvent(Event.VOLCANO, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.VISITATION, 0, 3, 0,
        "Gilda");
    c.getEvent()[5] = createEvent(Event.VOLCANO, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.CORRUPTION, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.SUPERSTITION, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.SUPERSTITION, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(10, 2, 4, 10, 0, true);
    c.getEvent()[1] = createEvent(Event.FAMINE, 0, 0, 0, "");
    c.getEvent()[2] = createEvent(Event.FAMINE, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.SANDSTORM, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.VISITATION, 0, 3, 0,
        "Atlantea");
    c.getEvent()[6] = createEvent(Event.UPRISING, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.BANDITS, 1, 1, 1, "");
    v.add(c);

    c = new EventCard(11, 3, 5, 7, 1, false);
    c.getEvent()[2] = createEvent(Event.VISITATION, 0, 1, 1,
        "Nordic");
    c.getEvent()[4] = createEvent(Event.BANDITS, 0, 0, 2, "");
    c.getEvent()[5] = createEvent(Event.EARTHQUAKE, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.BANDITS, 0, 1, 2, "");
    c.getEvent()[7] = createEvent(Event.CIVIL_WAR, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.CORRUPTION, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(12, 4, 6, 8, 3, false);
    c.getEvent()[1] = createEvent(Event.VISITATION, 0, 0, 1,
        "Gilda");
    c.getEvent()[3] = createEvent(Event.VOLCANO, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.ANARCHY, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.FLOOD, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.VISITATION, 0, 0, 0,
        "Atlantea");
    c.getEvent()[7] = createEvent(Event.VOLCANO, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(13, 5, 3, 9, 3, true);
    c.getEvent()[2] = createEvent(Event.EARTHQUAKE, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.TRIBAL_WAR, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.BANDITS, 0, 0, 2, "");
    c.getEvent()[6] = createEvent(Event.CIVIL_WAR, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.VISITATION, 0, 1, 2,
        "Nordic");
    c.getEvent()[8] = createEvent(Event.SUPERSTITION, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(14, 6, 4, 7, 0, false);
    c.getEvent()[1] = createEvent(Event.VISITATION, 0, 0, 1,
        "Nordic");
    c.getEvent()[3] = createEvent(Event.EARTHQUAKE, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.VISITATION, 0, 0, 2,
        "Floren");
    c.getEvent()[6] = createEvent(Event.VOLCANO, 0, 0, 0, "");
    c.getEvent()[7] = createEvent(Event.FAMINE, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.ANARCHY, 0, 0, 0, "");
    v.add(c);

    c = new EventCard(15, 7, 5, 8, 2, false);
    c.getEvent()[2] = createEvent(Event.SANDSTORM, 0, 0, 0, "");
    c.getEvent()[3] = createEvent(Event.FLOOD, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.SANDSTORM, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.ANARCHY, 0, 0, 0, "");
    c.getEvent()[6] = createEvent(Event.VISITATION, 1, 1, 1,
        "Gilda");
    c.getEvent()[7] = createEvent(Event.VISITATION, 1, 1, 1,
        "Floren");
    v.add(c);

    c = new EventCard(16, 8, 6, 6, 2, false);
    c.getEvent()[1] = createEvent(Event.EPIDEMIC, 1, 0, 0, "");
    c.getEvent()[2] = createEvent(Event.FLOOD, 0, 0, 0, "");
    c.getEvent()[4] = createEvent(Event.SUPERSTITION, 0, 0, 0, "");
    c.getEvent()[5] = createEvent(Event.VISITATION, 0, 0, 2,
        "Nordic");
    c.getEvent()[7] = createEvent(Event.ANARCHY, 0, 0, 0, "");
    c.getEvent()[8] = createEvent(Event.VISITATION, 0, 1, 2,
        "Atlantea");
    v.add(c);

    return v;
  }

  public static HashMap generateEventMap(GameEngine engine, GameModel model,
      GameUi ui) {
    HashMap map = new HashMap();
    map.put(Event.eventIdToString(Event.ANARCHY), new Anarchy(Event.ANARCHY,
        engine, model, ui));
    map.put(Event.eventIdToString(Event.BANDITS), new Bandits(Event.BANDITS,
        engine, model, ui));
    map.put(Event.eventIdToString(Event.CIVIL_WAR), new CivilWar(
        Event.CIVIL_WAR, engine, model, ui));
    map.put(Event.eventIdToString(Event.CORRUPTION), new Corruption(
        Event.CORRUPTION, engine, model, ui));
    map.put(Event.eventIdToString(Event.EARTHQUAKE), new Earthquake(
        Event.EARTHQUAKE, engine, model, ui));
    map.put(Event.eventIdToString(Event.EPIDEMIC), new Epidemic(Event.EPIDEMIC,
        engine, model, ui));
    map.put(Event.eventIdToString(Event.FAMINE), new Famine(Event.FAMINE,
        engine, model, ui));
    map.put(Event.eventIdToString(Event.FLOOD), new Flood(Event.FLOOD, engine,
        model, ui));
    map.put(Event.eventIdToString(Event.SANDSTORM), new Sandstorm(
        Event.SANDSTORM, engine, model, ui));
    map.put(Event.eventIdToString(Event.SUPERSTITION), new Superstition(
        Event.SUPERSTITION, engine, model, ui));
    map.put(Event.eventIdToString(Event.TRIBAL_WAR), new TribalWar(
        Event.TRIBAL_WAR, engine, model, ui));
    map.put(Event.eventIdToString(Event.UPRISING), new Uprising(Event.UPRISING,
        engine, model, ui));
    map.put(Event.eventIdToString(Event.VISITATION), new Visitation(
        Event.VISITATION, engine, model, ui));
    map.put(Event.eventIdToString(Event.VOLCANO), new Volcano(Event.VOLCANO,
        engine, model, ui));

    return map;
  }

  
  public static Event createEvent(int id, int red, int green, int blue, String desc) {
    String key = id + red + blue + green + desc;
    Event e = (Event) events.get(key);
    if (e == null) {
      e = new Event(id, desc, red, green, blue);
      events.put(key, e);
    }
    return e;
  }
}
