package hr.hr.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VacationProperty {

    private IntegerProperty id;

    private StringProperty last_name;
    private StringProperty startHoliday;
    private StringProperty endHoliday;

    public VacationProperty (Vacation vacation) {
        id = new SimpleIntegerProperty(vacation.getId());
        last_name = new SimpleStringProperty(vacation.getLast_name());
        startHoliday = new SimpleStringProperty(vacation.getStartHoliday());
        endHoliday = new SimpleStringProperty(vacation.getEndHoliday());
    }

    public VacationProperty(IntegerProperty id, StringProperty last_name, StringProperty startHoliday, StringProperty endHoliday) {
        this.id = id;
        this.last_name = last_name;
        this.startHoliday = startHoliday;
        this.endHoliday = endHoliday;
    }

    public Vacation toHRVacation(){
        return new Vacation(
                id.intValue(),
                last_name.getValue(),
                startHoliday.getValue(),
                endHoliday.getValue()
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

    public String getStartHoliday() {
        return startHoliday.get();
    }

    public StringProperty startHolidayProperty() {
        return startHoliday;
    }

    public void setStartHoliday(String startHoliday) {
        this.startHoliday.set(startHoliday);
    }

    public String getEndHoliday() {
        return endHoliday.get();
    }

    public StringProperty endHolidayProperty() {
        return endHoliday;
    }

    public void setEndHoliday(String endHoliday) {
        this.endHoliday.set(endHoliday);
    }
}
