package com.cortexanonymus.client.model;

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import com.cortexanonymus.client.util.Array;
import com.google.gwt.user.client.Random;


public class Deck {
  private Stack deck = new Stack();

  private Stack discardPile = new Stack();

  private Vector deckList;

  public Deck(Vector cardList) {
    deckList = cardList;
    
    for (int  i = cardList.size() - 1; i >=0; --i) {
      deck.push(cardList.elementAt(i));
    }
    
  }

  public EventCard drawDeck() {
    if (deck.isEmpty()) {
      return null;
    }
    EventCard drawn = (EventCard) deck.pop();
    discardPile.push(drawn);
    return drawn;
  }

  public void shuffle() {
    discardPile.removeAllElements();
    deck.removeAllElements();

    Vector list = new Vector(deckList);
    while (list.size() > 0) {
      int rand = Math.abs(Random.nextInt()) % list.size();
      deck.push(list.elementAt(rand));
      list.removeElementAt(rand);
    }
  }

  public int getNumCard() {
    return deckList.size();
  }
  
  public int getNumDeck() {
    return deck.size();
  }

  public int getNumDiscard() {
    return discardPile.size();
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("Deck of " + deckList.size() + " cards\n");
    sb.append("DrawPile").append(Array.toString(deck));
    sb.append("DiscardPile=").append(Array.toString(discardPile));
    return sb.toString();
  }
  
  public Iterator getDiscardIterator() {
    return discardPile.iterator();
  }
}
