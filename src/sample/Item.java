package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Stores information regarding an item on the menu.
 * @author Emre.
 * @version 1.0
 * @param <SimpleDoublePropertyrice>
 */
public class Item<SimpleDoublePropertyrice> {
    private SimpleIntegerProperty id;
    private SimpleStringProperty itemName;
    private SimpleDoubleProperty price;
    private SimpleBooleanProperty isDailySpecial;
    private SimpleStringProperty type;

    /**
     * Creates a menu item with the arguments passed to the method.
     * @param id The id of the menu item.
     * @param itemName The name of the menu item.
     * @param price The price of the menu item.
     * @param isDailySpecial States whether the item is the daily special.
     * @param type Determines the course type of the menu item.
     */
    public Item(final SimpleIntegerProperty id, final SimpleStringProperty itemName, final SimpleDoubleProperty price,
                final SimpleBooleanProperty isDailySpecial, final SimpleStringProperty type) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.isDailySpecial = isDailySpecial;
        this.type = type;
    }

    /**
     * A default constructor to create a menu item.
     */
    public Item(){
        this.id = new SimpleIntegerProperty(0);
        this.itemName = new SimpleStringProperty("zero");
        this.price = new SimpleDoubleProperty(0.0);
        this.isDailySpecial = new SimpleBooleanProperty(false);
        this.type = new SimpleStringProperty("zero");
    }

    /**
     * Returns the course type of the menu item.
     * @return Returns the course type of the menu item.
     */
    public String getType() {
        return type.get();
    }

    /**
     * Returns the course type of the menu item.
     * @return Returns the course type of the menu item.
     */
    public SimpleStringProperty typeProperty() {
        return type;
    }

    /**
     * Sets the course type of the menu item.
     * @param type Sets the course type of the menu item.
     */
    public void setType(final String type) {
        this.type.set(type);
    }

    /**
     * Returns the id of the menu item.
     * @return Returns the id of the menu item.
     */
    public int getId() {
        return id.get();
    }

    /**
     * Returns the id of the menu item.
     * @return Returns the id of the menu item.
     */
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     * Sets the id of the menu item.
     * @param id Sets the id of the menu item.
     */
    public void setId(final int id) {
        this.id.set(id);
    }

    /**
     * Returns the item name of the menu item.
     * @return Returns the item name of the menu item.
     */
    public String getItemName() {
        return itemName.get();
    }

    /**
     * Returns the item name of the menu item.
     * @return Returns the item name of the menu item.
     */
    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    /**
     * Sets the item name of the menu item.
     * @param itemName Sets the item name of the menu item.
     */
    public void setItemName(final String itemName) {
        this.itemName.set(itemName);
    }

    /**
     * Returns the price of the menu item.
     * @return Returns the price of the menu item.
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Returns the price of the menu item.
     * @return Returns the price of the menu item.
     */
    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    /**
     * Sets the price of the menu item.
     * @param price Sets the price of the menu item.
     */
    public void setPrice(final double price) {
        this.price.set(price);
    }

    /**
     * Returns whether the menu item is the dailySpecial.
     * @return Returns whether the menu item is the dailySpecial.
     */
    public boolean isIsDailySpecial() {
        return isDailySpecial.get();
    }

    /**
     * Returns whether the menu item is the dailySpecial.
     * @return Returns whether the menu item is the dailySpecial.
     */
    public SimpleBooleanProperty isDailySpecialProperty() {
        return isDailySpecial;
    }

    /**
     * Sets whether the menu item is the dailySpecial.
     * @param isDailySpecial Sets whether the menu item is the dailySpecial.
     */
    public void setIsDailySpecial(final boolean isDailySpecial) {
        this.isDailySpecial.set(isDailySpecial);
    }

    /**
     * Returns the item name of the menu item.
     * @return Returns the item name of the menu item.
     */
    @Override
    public String toString() {
        return this.getItemName();
    }
}
