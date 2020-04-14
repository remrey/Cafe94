package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class item<SimpleDoublePropertyrice> {
    private SimpleIntegerProperty id;
    private SimpleStringProperty itemName;
    private SimpleDoubleProperty price;
    private SimpleBooleanProperty isDailySpecial;
    private SimpleStringProperty type;

    public item(SimpleIntegerProperty id, SimpleStringProperty itemName, SimpleDoubleProperty price, SimpleBooleanProperty isDailySpecial, SimpleStringProperty type) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.isDailySpecial = isDailySpecial;
        this.type = type;
    }

    public item(){
        this.id = new SimpleIntegerProperty(0);
        this.itemName = new SimpleStringProperty("zero");
        this.price = new SimpleDoubleProperty(0.0);
        this.isDailySpecial = new SimpleBooleanProperty(false);
        this.type = new SimpleStringProperty("zero");
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public boolean isIsDailySpecial() {
        return isDailySpecial.get();
    }

    public SimpleBooleanProperty isDailySpecialProperty() {
        return isDailySpecial;
    }

    public void setIsDailySpecial(boolean isDailySpecial) {
        this.isDailySpecial.set(isDailySpecial);
    }

    @Override
    public String toString() {
        return this.getItemName();
    }
}
