package hr.hr.entity;

import java.io.Serializable;

public class Sick implements Serializable {
    private int id;

    private String last_name;
    private String startSick;
    private String endHoSick;

    public Sick(int id, String last_name, String startSick, String endHoSick) {
        this.id = id;
        this.last_name = last_name;
        this.startSick = startSick;
        this.endHoSick = endHoSick;
    }

    public Sick(String last_name, String startSick, String endHoSick) {
        this.last_name = last_name;
        this.startSick = startSick;
        this.endHoSick = endHoSick;
    }

    public Sick() {

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

    public String getStartSick() {
        return startSick;
    }

    public void setStartSick(String startSick) {
        this.startSick = startSick;
    }

    public String getEndHoSick() {
        return endHoSick;
    }

    public void setEndHoSick(String endHoSick) {
        this.endHoSick = endHoSick;
    }
}
