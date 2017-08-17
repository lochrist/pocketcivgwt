package com.cortexanonymus.client.model;

public abstract class Requirement {
  String desc;
  public Requirement(String desc) {
    this.desc = desc;
  }
  
  public String toString() {
    return desc;
  }
  
  public abstract boolean isFulfilled(GameModel model,Region r); 
}
