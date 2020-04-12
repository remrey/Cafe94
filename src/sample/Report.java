package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {
    private SimpleIntegerProperty id;
    private SimpleStringProperty description;
    private SimpleStringProperty name;
    private SimpleIntegerProperty amount;

    public Report(Integer id, String description, String name, Integer amount) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
    }

    public Integer getId() {
        return id.get();
    }

    public String getDescription() {
        return description.get();
    }


    public String getName() {
        return name.get();
    }


    public Integer getAmount() {
        return amount.get();
    }

}
