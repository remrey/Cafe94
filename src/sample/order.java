package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Order {

    private SimpleIntegerProperty orderID;
    private SimpleIntegerProperty orderNo;
    private SimpleIntegerProperty itemID;
    private SimpleStringProperty itemName;
    private SimpleIntegerProperty customerID;
    private SimpleStringProperty orderType;
    private SimpleStringProperty deliveryAddress;
    private SimpleBooleanProperty delivered;
    private SimpleBooleanProperty waiterServed;
    private LocalDateTime orderDate;
    private LocalDateTime pickupTime;
    private SimpleIntegerProperty driverID;
    private SimpleIntegerProperty tableID;

    public Order(SimpleIntegerProperty orderID, SimpleIntegerProperty orderNo, SimpleIntegerProperty itemID, SimpleStringProperty itemName, SimpleIntegerProperty customerID, SimpleStringProperty orderType, SimpleStringProperty deliveryAddress, SimpleBooleanProperty delivered, SimpleBooleanProperty waiterServed, LocalDateTime orderData, LocalDateTime pickupTime, SimpleIntegerProperty driverID, SimpleIntegerProperty tableID) {
        this.orderID = orderID;
        this.orderNo = orderNo;
        this.itemID = itemID;
        this.itemName = itemName;
        this.customerID = customerID;
        this.orderType = orderType;
        this.deliveryAddress = deliveryAddress;
        this.delivered = delivered;
        this.waiterServed = waiterServed;
        this.orderDate = orderData;
        this.pickupTime = pickupTime;
        this.driverID = driverID;
        this.tableID = tableID;
    }

    public Order(){
        this.orderID = new SimpleIntegerProperty(-1);
        this.orderNo = new SimpleIntegerProperty(-1);
        this.itemID = new SimpleIntegerProperty(-1);
        this.itemName = new SimpleStringProperty("zero");
        this.customerID = new SimpleIntegerProperty(-1);
        this.orderType = new SimpleStringProperty("zero");
        this.deliveryAddress = new SimpleStringProperty("zero");
        this.delivered = new SimpleBooleanProperty(false);
        this.waiterServed = new SimpleBooleanProperty(false);
        this.orderDate = null;
        this.pickupTime = null;
        this.driverID = new SimpleIntegerProperty(-1);
        this.tableID = new SimpleIntegerProperty(-1);

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

    public int getOrderID() {
        return orderID.get();
    }

    public SimpleIntegerProperty orderIDProperty() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    public int getOrderNo() {
        return orderNo.get();
    }

    public SimpleIntegerProperty orderNoProperty() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo.set(orderNo);
    }

    public int getItemID() {
        return itemID.get();
    }

    public SimpleIntegerProperty itemIDProperty() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID.set(itemID);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
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

    public String getDeliveryAddress() {
        return deliveryAddress.get();
    }

    public SimpleStringProperty deliveryAddressProperty() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress.set(deliveryAddress);
    }

    public boolean isDelivered() {
        return delivered.get();
    }

    public SimpleBooleanProperty deliveredProperty() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered.set(delivered);
    }

    public boolean isWaiterServed() {
        return waiterServed.get();
    }

    public SimpleBooleanProperty waiterServedProperty() {
        return waiterServed;
    }

    public void setWaiterServed(boolean waiterServed) {
        this.waiterServed.set(waiterServed);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderData) {
        this.orderDate = orderData;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getDriverID() {
        return driverID.get();
    }

    public SimpleIntegerProperty driverIDProperty() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID.set(driverID);
    }

    public int getTableID() {
        return tableID.get();
    }

    public SimpleIntegerProperty tableIDProperty() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID.set(tableID);
    }
}
