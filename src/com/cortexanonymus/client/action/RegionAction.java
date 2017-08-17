package com.cortexanonymus.client.action;

import com.cortexanonymus.client.ui.RegionDesc;

public abstract class RegionAction {  
  String name;
  public RegionAction() {
    name = "action";
  }
  public RegionAction(String name) {
    this.name = name;
  }
  
  public boolean canExecute(RegionDesc r) {
    return true;
  }
  public abstract void execute(RegionDesc r);
  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }
  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }
}
