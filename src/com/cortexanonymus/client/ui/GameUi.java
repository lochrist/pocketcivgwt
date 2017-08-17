package com.cortexanonymus.client.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


import com.cortexanonymus.client.action.Action;
import com.cortexanonymus.client.action.RegionAction;
import com.cortexanonymus.client.model.Advance;
import com.cortexanonymus.client.model.GameModel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GameUi {
  public static interface GameDialog {
    void addBtn(String name, Command c);
        
    void addResourceSelector(int startValue,int minValue,int maxValue);
    void addAdvanceSelector(Vector advance);
        
    int getReturnCode();
    void showModal();
    void hideModal();
  }
  
  class SelectRegionAdapter extends MouseListenerAdapter {
    RegionAction action;
    public SelectRegionAdapter(RegionAction action) {
      this.action = action;
    }
    
    public void onMouseDown(Widget w, int x, int y) {
      RegionDesc d = getMap().selectRegion(x,y);
      action.execute(d);
    }
  }
  
  class ActionAdapter implements ClickListener {
    Action action;
    public ActionAdapter(Action a) {
      action = a;
    }
    
    public void onClick(Widget w) {
      action.execute();
    }
  }
  
  class RegionAdapter implements ClickListener {
    RegionAction action;
    public RegionAdapter(RegionAction a) {
      action = a;
    }
    
    public void onClick(Widget w) {
      action.execute(activeRegion);
    }
  }
  
  WorldMap map;
  Label status = new Label();
  Label phase = new Label();
  DockPanel mainPanel = new DockPanel();
  Label vp = new Label();
  Label gold = new Label();
  Label cityAdvance = new Label();
  Label era = new Label();
  Label tribe = new Label();
  Label deck = new Label();
  Label turn = new Label();
  Label goldMined = new Label();
  Label surveying = new Label();    
  TabPanel tabPanel = new TabPanel(); 
  
  FlexTable advanceTable = new FlexTable(); 
  FlexTable wonderTable = new FlexTable();
  
  FocusPanel mapHandler = new FocusPanel();
  Button doneBtn = new Button("Done");
  TextArea log = new TextArea();
  StringBuffer logBuf = new StringBuffer();
  Image currentEvent = new Image();
  Image eventTopDiscard = new Image();
  VerticalPanel actionPanel = new VerticalPanel(); 
    
  HashMap selectRegionToListener = new HashMap();
  HashMap endOfStateToListener = new HashMap();
  
  HashMap regionActionToListener = new HashMap();
  HashMap regionActionToEditor = new HashMap();
  RegionDesc activeRegion;
  GameModel model;
  
  public GameUi(GameModel model,WorldMap map) {
    this.map = map;    
    this.model = model;
    {
      VerticalPanel panel = new VerticalPanel();      
      {
        HorizontalPanel statusPanel = new HorizontalPanel();
        statusPanel.setSpacing(5);
        statusPanel.add(vp);
        statusPanel.add(gold);       
        statusPanel.add(tribe);
        statusPanel.add(cityAdvance);
        statusPanel.add(era);
        statusPanel.add(turn);
        statusPanel.add(deck);        
        statusPanel.add(goldMined);
        statusPanel.add(surveying);
        
        panel.add(statusPanel);
      }
      
      mainPanel.add(panel, DockPanel.NORTH);
    }
    
    {
      HorizontalPanel centerPanel = new HorizontalPanel(); 
            
      mapHandler.setWidget(map.getUi());
      centerPanel.add(mapHandler);
    
      {
        VerticalPanel panel = new VerticalPanel();
        panel.add(phase);
        panel.add(status);
        {
          HorizontalPanel p = new HorizontalPanel();
          p.add(actionPanel);
          p.add(currentEvent);
          p.add(eventTopDiscard);
          
          panel.add(p);
        }
      
        panel.add(log);
      
        panel.add(doneBtn);
        doneBtn.setEnabled(false);
        
        centerPanel.add(panel);
      }
      
      mainPanel.add(centerPanel, DockPanel.CENTER);
    }
    
    tabPanel.add(mainPanel,"Game");
    
    tabPanel.add(advanceTable,"Advances");
    tabPanel.add(wonderTable,"Wonders");
    
    createInfoTable(model.getAdvanceAvailable(),model.getWonderAvailable());
    
    tabPanel.selectTab(0);
    
    // Create Log dialog   
    log.setVisibleLines(25);
    log.setCharacterWidth(50);
  }
  
  public Widget getUi() {
    return tabPanel;
  }
  
  public void setPhase(String s) {
    log("***" + s + "***");
    this.phase.setText(s);
  }
  
  public void setStatus(String s) {
    this.status.setText(s);
    this.status.setVisible(s.length()>0);    
  }
  
  public void createInfoTable(Vector advances, Vector wonders) {
    int row = 0;
    int col = 0;    
    advanceTable.setText( row, col++, "Name");
    advanceTable.setText( row, col++, "VP");
    advanceTable.setText( row, col++, "Cost");
    advanceTable.setText( row, col++, "Requirement");
    advanceTable.setText( row, col++, "Reduce Effect");
    advanceTable.setText( row, col++, "Increase Effect");
    advanceTable.setText( row, col++, "Rules");
    advanceTable.setStyleName("advance-List");
    advanceTable.getRowFormatter().setStyleName(0, "advance-ListHeader");
    
    Iterator it = advances.iterator();
    row = 1;
    while (it.hasNext()) {
      col = 0;
      Advance advance = (Advance) it.next();
      advanceTable.setText(row, col++, advance.getName());
      advanceTable.setText(row, col++, String.valueOf(advance.getVictory()));
      advanceTable.setText(row, col++, advance.getCost().toString());
      advanceTable.getRowFormatter().setStyleName(0, "advance-ListHeader");
      
      if (advance.getRequirement() != null) {
        advanceTable.setText(row, col, advance.getRequirement().toString());
      }
      col++;
      advanceTable.setText(row, col++, advance.getReduceEffect());
      advanceTable.setText(row, col++, advance.getIncreaseEffect());
      advanceTable.setText(row, col++, advance.getDesc());
      ++row;
    }

    row = 0;
    col = 0;
    wonderTable.setText( row, col++, "Name");
    wonderTable.setText( row, col++, "VP");
    wonderTable.setText( row, col++, "Cost");
    wonderTable.setText( row, col++, "Requirement");    
    wonderTable.setStyleName("advance-List");
    wonderTable.getRowFormatter().setStyleName(0, "advance-ListHeader");
    
    it = wonders.iterator();
    row = 1;
    while (it.hasNext()) {
      col = 0;
      Advance advance = (Advance) it.next();
      wonderTable.setText(row, col++, advance.getName());
      wonderTable.setText(row, col++, String.valueOf(advance.getVictory()));
      wonderTable.setText(row, col++, advance.getCost().toString());
      if (advance.getRequirement() != null) {
        wonderTable.setText(row, col, advance.getRequirement().toString());
      }      
      ++row;
    }
  }
  
  public void updateModel() {
    vp.setText("Vp: " + model.getVp());
    gold.setText("Gold: " + model.getGold());
    era.setText("Era: " + model.getEra());
    turn.setText("Turn: " + model.getTurn());
    tribe.setText("Tribe: " + model.getNumTribe());
    
    cityAdvance.setText( 
        "Advance: " + 
        model.getAdvanceAcquired().size() + 
        " / " + 
        model.getNumAdvance() );
    
    deck.setText( 
        "Event Deck: " +
        model.getEventDeck().getNumDeck() + 
        " / " + 
        model.getEventDeck().getNumCard());
    
    if (model.getCurrentEvent() != null) {
      currentEvent.setUrl(model.getCurrentEvent().getImg());
    }
    currentEvent.setVisible(model.getCurrentEvent() != null);
    
    if (model.getDrawEvent() != null) {
      eventTopDiscard.setUrl(model.getDrawEvent().getImg());
    }
    eventTopDiscard.setVisible(model.getDrawEvent() != null);
    
    goldMined.setVisible(model.getGoldMined()>0);    
    goldMined.setText("Gold mined: " + model.getGoldMined());
    
    surveying.setVisible(model.hasAdvance(Advance.surveying));
    if (model.hasAdvance(Advance.surveying)) {
      surveying.setText("Surveying: " + model.getSurveying());
    }    
  }
  
  public void updateAdvance() {
    for (int i = 0; i < advanceTable.getRowCount(); ++i) {
      String advanceName = advanceTable.getText(i, 0);
      if (model.hasAdvance(advanceName)) {
        advanceTable.getRowFormatter().addStyleName(i, "advance-SelectedRow");
      }
    }
  }
  
  public void updateWonder() {
    // TODO: show wonder acquired in a region
  }
  
  public void log(String s) {
    s += "\n";
    System.out.print(s);
    logBuf.append(s);
    String toLog = logBuf.toString(); 
    log.setText(toLog);
    log.setCursorPos(toLog.length());
  }
    
  public WorldMap getMap() {
    return map;
  }
  
  public void addSelectRegionAction(RegionAction sel) {
    SelectRegionAdapter l = new SelectRegionAdapter(sel);
    mapHandler.addMouseListener( l );
    selectRegionToListener.put(sel, l);
  }
  public void removeSelectRegionAction(RegionAction action) {
    SelectRegionAdapter l = (SelectRegionAdapter)selectRegionToListener.get(action);
    if (l != null) {
      mapHandler.removeMouseListener(l);
      selectRegionToListener.remove(action);
    }
  }
  
  public void addGlobalAction(Action c) {
    
  }
  
  public void removeGlobalAction(Action c) {
    
  }
    
  public void addEndOfStateAction(Action action) {    
    ActionAdapter l = new ActionAdapter(action);
    doneBtn.addClickListener( l );
    if ( action.getName().length() > 0 )
    {
      doneBtn.setText(action.getName());
    }    
    endOfStateToListener.put(action,l);
    doneBtn.setEnabled(endOfStateToListener.size()>0);
  }
  
  public void removeEndOfStateAction(Action action) {
    ActionAdapter ad = (ActionAdapter)endOfStateToListener.get(action);
    if (ad != null) {
      doneBtn.removeClickListener(ad);
      endOfStateToListener.remove(doneBtn);      
    }
    doneBtn.setEnabled(endOfStateToListener.size()==0);
  }
  
  public void addRegionAction(RegionAction action) {
    Button btn = (Button)regionActionToEditor.get(action);
    if (btn == null) {
      btn = new Button(action.getName());
      actionPanel.add(btn);
      RegionAdapter adapter = new RegionAdapter(action);
      btn.addClickListener(adapter);
      
      regionActionToEditor.put(action, btn);
      regionActionToListener.put(action, adapter);
    }
  }
  
  public void removeRegionAction(RegionAction action) {
    Button btn = (Button)regionActionToEditor.get(action);    
    if (btn != null) {      
      RegionAdapter adapter = (RegionAdapter)regionActionToListener.get(action);      
      btn.removeClickListener(adapter);
      actionPanel.remove(btn);
      
      regionActionToListener.remove(action);
      regionActionToEditor.remove(action);
    }
  }
  
  public void enableRegionAction(boolean isEnable, RegionAction action) {
    Button btn = (Button)regionActionToEditor.get(action);
    if (btn != null) {
      btn.setEnabled(isEnable);
    }
  }
  
  public void setActiveRegion(RegionDesc desc) {
    if (activeRegion != null) {
      activeRegion.setSelected(false);
    }
    activeRegion = desc;
    if (activeRegion != null) {
      activeRegion.setSelected(true);
    }
  }
  
  public GameDialog createDialog(String title) {
    return new CivDialog(title);
  }
}
