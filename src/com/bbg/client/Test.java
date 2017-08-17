package com.bbg.client;

import com.bbg.client.action.Action;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Test {
  boolean isSelected;
    

  private static class MyDialog extends DialogBox {

    public MyDialog() {
      // Set the dialog box's caption.
      setText("My First Dialog");

      // DialogBox is a SimplePanel, so you have to set it's widget property to
      // whatever you want its contents to be.
      Button ok = new Button("OK");
      ok.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {   
          
          // MyDialog.this.hide();
        }
      });
      setWidget(ok);
    }
  } 
    
  public void test() {
    /*
     * // If no city and no tribes: game OVER! if (model.getNumTribe() == 0 &&
     * model.getNumCity() == 0) { engine.gameOver.execute(); } else{
     * ui.setStatus("Select a region from which to move."); final MoveAction
     * moveAction = new MoveAction(); ui.addSelectRegionAction(moveAction);
     * 
     * ui.addEndOfStateAction( new Action() { public void execute() {
     * ui.removeSelectRegionAction(moveAction); ui.removeEndOfStateAction(this);
     * ui.log("Move is over."); engine.eventPhase.execute(); } });
     *  }
     */
    // throw new Exception("fonb");
  }
  
  void execute() {
    /*
    btn.addClickListener(new ClickListener() {
      public void onClick(Widget w) {
      }
    }
    );

    focus.addMouseListener(new MouseListenerAdapter() {
      public void onMouseDown(Widget sender, int x, int y) {
        Coord c = HexUtil.pixelToHex(x, y);
        l.setText(c.toString());
      }
    }

    );

    RootPanel.get().add(panel);
    */
    /*
    final Image img = new Image("frontier_all.png");
    
    */    
    final Button btn2 = new Button( "bitch" );
    isSelected = true;
    btn2.addClickListener( new ClickListener() {
      public void onClick(Widget w) {
        new MyDialog().show();
        
        System.out.println("sdfsaafs");
      }
    });
    RootPanel.get().add(btn2);
    
    Button btn = new Button( "popup" );
    btn.addClickListener( new ClickListener() {
      public void onClick(Widget w) {
        if (isSelected) {
          // btn2.setStyleName(".selected-region");
          btn2.addStyleName("gwt-Test-Btn");
        }
        else{
          // btn2.removeStyleName(".selected-region");
          btn2.removeStyleName("gwt-Test-Btn");
        }
        isSelected = !isSelected;       
      }
    });
            
    RootPanel.get().add(btn);
    
    RootPanel.get().add(new Image("image/hex1.gif"));
  }
}
