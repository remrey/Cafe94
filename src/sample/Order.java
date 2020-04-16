package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

/**
 * Class file for the creation of Order objects. When a customer orders something an
 * Order object will be constructed.
 * @author Luis, Emre.
 * @version 1.0.
 */

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

    /**
     * Constructor for Order class. Constructs an Order object using given parameters.
     * @param orderID this is the order ID.
     * @param orderNo the number of an order, multiple items in the same order share an order number.
     * @param itemID ID of menu item in order.
     * @param itemName name of menu item in order.
     * @param customerID customer ID of customer ordering.
     * @param orderType type of order: eatin, takeaway, or delivery.
     * @param deliveryAddress address order needs to be delivered to.
     * @param delivered boolean, whether order has been delivered or not.
     * @param waiterServed boolean, whether waiter has served the order or not.
     * @param orderDate the date and order was placed on.
     * @param pickupTime pick up time of order if it is a takeaway.
     * @param driverID ID of delivery driver delivering the order.
     * @param tableID ID of table order was ordered from.
     */

    public Order(SimpleIntegerProperty orderID, SimpleIntegerProperty orderNo, SimpleIntegerProperty itemID,
                 SimpleStringProperty itemName, SimpleIntegerProperty customerID, SimpleStringProperty orderType,
                 SimpleStringProperty deliveryAddress, SimpleBooleanProperty delivered,
                 SimpleBooleanProperty waiterServed, LocalDateTime orderDate, LocalDateTime pickupTime,
                 SimpleIntegerProperty driverID, SimpleIntegerProperty tableID) {
        this.orderID = orderID;
        this.orderNo = orderNo;
        this.itemID = itemID;
        this.itemName = itemName;
        this.customerID = customerID;
        this.orderType = orderType;
        this.deliveryAddress = deliveryAddress;
        this.delivered = delivered;
        this.waiterServed = waiterServed;
        this.orderDate = orderDate;
        this.pickupTime = pickupTime;
        this.driverID = driverID;
        this.tableID = tableID;
    }

    /**
     * Default Order constructor for an Order object if no parameters are provided.
     */

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

    /**
     * Gets the item name of current object.
     * @return current objects item name as a String.
     */

    public String getItemName() {
        return itemName.get();
    }

    /**
     * Gets the item name as a SimpleStringProperty.
     * @return name of menu item as a SimpleStringProperty.
     */

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    /**
     * Sets item name of current object.
     * @param itemName takes the value to set item name to.
     */

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    /**
     * Gets the order ID of current object.
     * @return integer value of order ID.
     */

    public int getOrderID() {
        return orderID.get();
    }

    /**
     * Gets the order ID of current object.
     * @return SimpleIntegerProperty value of order ID.
     */

    public SimpleIntegerProperty orderIDProperty() {
        return orderID;
    }

    /**
     * Sets order ID of current object.
     * @param orderID takes the value to set the order ID to.
     */

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    /**
     * Gets the order number of current object.
     * @return integer value of order number.
     */

    public int getOrderNo() {
        return orderNo.get();
    }

    /**
     * Gets the order number of current object.
     * @return SimpleIntegerProperty value of order number.
     */

    public SimpleIntegerProperty orderNoProperty() {
        return orderNo;
    }

    /**
     * Sets order number of current object.
     * @param orderNo takes the value to set the order number to.
     */

    public void setOrderNo(int orderNo) {
        this.orderNo.set(orderNo);
    }

    /**
     *  Gets the item ID of current object.
     * @return integer value of item ID.
     */

    public int getItemID() {
        return itemID.get();
    }

    /**
     * Gets the item ID of current object.
     * @return SimpleIntegerProperty value of item ID.
     */

    public SimpleIntegerProperty itemIDProperty() {
        return itemID;
    }

    /**
     * Sets item ID of current object.
     * @param itemID takes the value to set the item ID to.
     */

    public void setItemID(int itemID) {
        this.itemID.set(itemID);
    }

    /**
     * Gets the customer ID of current object.
     * @return integer value of customer ID.
     */

    public int getCustomerID() {
        return customerID.get();
    }

    /**
     * Gets the customer ID of current object.
     * @return SimpleIntegerProperty value of customer ID.
     */

    public SimpleIntegerProperty customerIDProperty() {
        return customerID;
    }

    /**
     * Sets customer ID of current object.
     * @param customerID takes the value to set the customer ID to.
     */

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    /**
     * Gets the order type of current object.
     * @return String value of order type.
     */

    public String getOrderType() {
        return orderType.get();
    }

    /**
     * Gets the order type of current object.
     * @return SimpleStringProperty value of order type.
     */

    public SimpleStringProperty orderTypeProperty() {
        return orderType;
    }

    /**
     * Sets the order type of current object.
     * @param orderType takes the value to set the order type to.
     */

    public void setOrderType(String orderType) {
        this.orderType.set(orderType);
    }

    /**
     * Gets the delivery address of current object.
     * @return String value of delivery address.
     */

    public String getDeliveryAddress() {
        return deliveryAddress.get();
    }

    /**
     * Gets the delivery address of current object.
     * @return SimpleStringProperty value of delivery address.
     */

    public SimpleStringProperty deliveryAddressProperty() {
        return deliveryAddress;
    }

    /**
     * Sets the delivery address of current object.
     * @param deliveryAddress takes the value to set the delivery address too.
     */

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress.set(deliveryAddress);
    }

    /**
     * Finds whether the order has been delivered or not.
     * @return true of false depending on whether order has been delivered.
     */

    public boolean isDelivered() {
        return delivered.get();
    }

    /**
     * Finds whether the order has been delivered or not.
     * @return SimpleBooleanProperty value of delivered.
     */

    public SimpleBooleanProperty deliveredProperty() {
        return delivered;
    }

    /**
     * Sets the delivered boolean to true or false.
     * @param delivered takes value to set delivered to.
     */

    public void setDelivered(boolean delivered) {
        this.delivered.set(delivered);
    }

    /**
     * Finds whether waiter has served the order or not.
     * @return true ot false depending on whether waiter has served the order.
     */

    public boolean isWaiterServed() {
        return waiterServed.get();
    }

    /**
     * Finds whether waiter has served the order or not.
     * @return SimpleBooleanProperty of waiterServed.
     */

    public SimpleBooleanProperty waiterServedProperty() {
        return waiterServed;
    }

    /**
     * Sets the waiterServed boolean to true or false.
     * @param waiterServed takes the value to set waiterServed too.
     */

    public void setWaiterServed(boolean waiterServed) {
        this.waiterServed.set(waiterServed);
    }

    /**
     * Gets the date and time the order was placed on.
     * @return time and date of order.
     */

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date and time the order was placed on.
     * @param orderDate takes the value to set the order date to.
     */

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the pickup date and time of the order.
     * @return time and date to pick the order up at.
     */

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    /**
     * Sets the date and time the order will picked up at.
     * @param pickupTime takes the value to set the order pickup time to.
     */

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    /**
     * Gets the ID of the delivery driver.
     * @return integer value of driver ID.
     */

    public int getDriverID() {
        return driverID.get();
    }

    /**
     * Gets the ID of the delivery driver.
     * @return SimpleIntegerProperty value of driver ID.
     */

    public SimpleIntegerProperty driverIDProperty() {
        return driverID;
    }

    /**
     * Sets the driver ID of current object.
     * @param driverID takes the value to set driver ID to.
     */

    public void setDriverID(int driverID) {
        this.driverID.set(driverID);
    }

    /**
     * Gets the table ID of current object.
     * @return integer value of table ID.
     */

    public int getTableID() {
        return tableID.get();
    }

    /**
     * Gets the table ID of current object.
     * @return SimpleIntegerProperty value of table ID.
     */

    public SimpleIntegerProperty tableIDProperty() {
        return tableID;
    }

    /**
     * Sets the table ID of current object.
     * @param tableID takes the value to set the table ID to.
     */

    public void setTableID(int tableID) {
        this.tableID.set(tableID);
    }
}
