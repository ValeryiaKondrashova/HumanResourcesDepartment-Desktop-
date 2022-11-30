package hr.hr.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SickProperty {

    private IntegerProperty id;

    private StringProperty last_name;
    private StringProperty startSick;
    private StringProperty endSick;

    public SickProperty (Sick sick){
        id = new SimpleIntegerProperty(sick.getId());
        last_name = new SimpleStringProperty(sick.getLast_name());
        startSick = new SimpleStringProperty(sick.getStartSick());
        endSick = new SimpleStringProperty(sick.getEndHoSick());
    }

    public Sick toHRSick(){
        return new Sick(
                id.intValue(),
                last_name.getValue(),
                startSick.getValue(),
                endSick.getValue()
        );
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public StringProperty last_nameProperty() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public String getStartSick() {
        return startSick.get();
    }

    public StringProperty startSickProperty() {
        return startSick;
    }

    public void setStartSick(String startSick) {
        this.startSick.set(startSick);
    }

    public String getEndSick() {
        return endSick.get();
    }

    public StringProperty endSickProperty() {
        return endSick;
    }

    public void setEndSick(String endSick) {
        this.endSick.set(endSick);
    }
}
