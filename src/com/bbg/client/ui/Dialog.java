package com.bbg.client.ui;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DialogBox;

public class Dialog extends DialogBox {
  boolean isModal;
  public Dialog(boolean isModal) {
    this.isModal = isModal;
  }
  
  public boolean onEventPreview(Event event) {
    if (isModal) {
      return super.onEventPreview(event);
    }
    else {
      return true;
    }    
  }
  
}
