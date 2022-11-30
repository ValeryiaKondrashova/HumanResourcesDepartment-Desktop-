package hr.hr.entity;

import java.io.Serializable;

public class Vacation implements Serializable {
    private int id;

    private String last_name;
    private String startHoliday;
    private String endHoliday;

    public Vacation(int id, String last_name, String startHoliday, String endHoliday) {
        this.id = id;
        this.last_name = last_name;
        this.startHoliday = startHoliday;
        this.endHoliday = endHoliday;
    }

    public Vacation(String last_name, String startHoliday, String endHoliday) {
        this.last_name = last_name;
        this.startHoliday = startHoliday;
        this.endHoliday = endHoliday;
    }

    public Vacation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStartHoliday() {
        return startHoliday;
    }

    public void setStartHoliday(String startHoliday) {
        this.startHoliday = startHoliday;
    }

    public String getEndHoliday() {
        return endHoliday;
    }

    public void setEndHoliday(String endHoliday) {
        this.endHoliday = endHoliday;
    }
}
