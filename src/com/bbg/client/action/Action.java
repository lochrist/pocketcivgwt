package com.bbg.client.action;

public abstract class Action {  
  public boolean canExecute() {
    return true;
  }
  
  public String getName() {
    return "";
  }
  
  public abstract void execute();
}
