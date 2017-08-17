package com.cortexanonymus.client.ui;

import java.util.Iterator;
import java.util.Vector;

import com.cortexanonymus.client.model.Advance;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CivDialog extends Dialog implements GameUi.GameDialog {
  VerticalPanel mainPanel = new VerticalPanel();
  HorizontalPanel editorPanel = new HorizontalPanel(); 
  HorizontalPanel btnPanel = new HorizontalPanel();
  int returnCode = 0;
  public CivDialog(String title) {
    super(true);
    setText(title);
    mainPanel.add(editorPanel);
    mainPanel.add(btnPanel);
    setWidget(mainPanel);
    
    setPopupPosition(100, 100);
  }
  
  public void addBtn(String name, final Command c) {
    Button btn = new Button(name);
    btn.addClickListener( new ClickListener() {
      public void onClick(Widget w) {
        c.execute();        
      }
    });
    btnPanel.add(btn);
  }
  
  public void addResourceSelector(int startValue,final int minValue,final int maxValue) {
    returnCode = startValue;
    final Label label = new Label(String.valueOf(startValue));
    Button plus = new Button("+");
    plus.addClickListener( new ClickListener() {
      public void onClick(Widget w) {
        if (returnCode < maxValue) {
          ++returnCode;
          label.setText(String.valueOf(returnCode));
        }
      }
    });
    Button minus = new Button("-");
    minus.addClickListener( new ClickListener() {
      public void onClick(Widget w) {
        if (returnCode > 0) {
          --returnCode;
          label.setText(String.valueOf(returnCode));
        }
      }
    });
    
    editorPanel.add(plus);
    editorPanel.add(minus);
    editorPanel.add(label);
  }
  
  public void addAdvanceSelector(Vector advances) {    
    int row = 0;
    int col = 0;    
    final FlexTable advanceTable = new FlexTable(); 
    advanceTable.setText( row, col++, "Name");
    advanceTable.setText( row, col++, "VP");
    advanceTable.setText( row, col++, "Cost");
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
      advanceTable.setText(row, col++, advance.getReduceEffect());
      advanceTable.setText(row, col++, advance.getIncreaseEffect());
      advanceTable.setText(row, col++, advance.getDesc());
      ++row;
    }
    
    advanceTable.addTableListener( new TableListener() {
      public void onCellClicked(SourcesTableEvents sender, int row, int cell) { 
        if (returnCode>0) {
          advanceTable.getRowFormatter().removeStyleName(returnCode, "advance-SelectedRow");
        }
        if (row > 0) {
          advanceTable.getRowFormatter().addStyleName(row, "advance-SelectedRow");
          returnCode = row;
        }
      }
    });
    editorPanel.add(advanceTable);
  }
  
  public int getReturnCode() {
    return returnCode;
  }
  
  public void showModal() {
    show();
  }
  public void hideModal() {
    hide();
  }  
}
