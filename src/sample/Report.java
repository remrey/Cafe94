package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A class to create and store information relating to Reports
 * @author Emre
 * @version 1.0
 */

public class Report {
    private SimpleIntegerProperty id;
    private SimpleStringProperty description;
    private SimpleStringProperty name;
    private SimpleIntegerProperty amount;

    /**
     * Creates a report based on information pulled from various tables.
     * @param id this is the ID of the current report.
     * @param description this is the description of the current report.
     * @param name the name of the current report.
     * @param amount the amount of time the item shows up in reports.
     */
    public Report(Integer id, String description, String name, Integer amount) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
    }

    /**
     * Gets the ID of the report.
     * @return id.
     */
    public Integer getId() {
        return id.get();
    }

    /**
     * Gets the description of the report.
     * @return description.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Gets the name of the report.
     * @return name.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets the amount for the report.
     * @return amount.
     */
    public Integer getAmount() {
        return amount.get();
    }

}
