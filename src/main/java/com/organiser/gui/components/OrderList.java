package com.organiser.gui.components;

import com.organiser.file.obj.Order;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;

/**
 * Implementation of ObjectList that holds the order panes and their sub-elements.
 *
 * @see OrderList
 * @see com.organiser.gui.components.OrderPane
 * @see com.organiser.gui.components.ObjectList
 */
public class OrderList extends ObjectList {

  public OrderList() {
    super("orderList.fxml");
  }

  public OrderPane findOrderPaneByID(int ID) {
    for (TitledPane pane: getObjectAccordion().getPanes()) {
      OrderPane orderPane = (OrderPane) pane;
      if (orderPane.getOrder().getID() == ID) {
        return orderPane;
      }
    }
    return null;
  }

  @Override
  public void createObjectList() {
    for (Order order : getFileSystem().getSortedOrders()) {
      getObjectAccordion().getPanes().add(new OrderPane(getFileSystem(), order));
    }
  }

  @FXML
  private void onClickAddOrder() {
    new AddOrderDialog().showAndWait().ifPresent(getFileSystem()::addOrder);
  }
}
