package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class order {

    private SimpleIntegerProperty orderId;
    private SimpleStringProperty itemName;
    private SimpleStringProperty orderType;
    private SimpleIntegerProperty tableId;
    private SimpleStringProperty orderStatus;

    public order(SimpleIntegerProperty orderId, SimpleStringProperty itemName, SimpleStringProperty orderType, SimpleIntegerProperty tableId, SimpleStringProperty orderStatus) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderType = orderType;
        this.tableId = tableId;
        this.orderStatus = orderStatus;
    }

    public order(){
        this.orderId = new SimpleIntegerProperty(0);;
        this.itemName = new SimpleStringProperty("zero");
        this.orderType = new SimpleStringProperty("zero");
        this.tableId = new SimpleIntegerProperty(0);
        this.orderStatus = new SimpleStringProperty("zero");
    }

    public int getOrderId() {
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getOrderType() {
        return orderType.get();
    }

    public SimpleStringProperty orderTypeProperty() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType.set(orderType);
    }

    public int getTableId() {
        return tableId.get();
    }

    public SimpleIntegerProperty tableIdProperty() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId.set(tableId);
    }

    public String getOrderStatus() {
        return orderStatus.get();
    }

    public SimpleStringProperty orderStatusProperty() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus.set(orderStatus);
    }
}
