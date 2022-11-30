package hr.hr.entity;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;

    private String first_name;
    private String last_name;
    private String patronymic;
    private String position;
    private Integer experience; // Был Double, стал Int
    private String startWork;
    private String telephone;
    private String email;
    private Integer timeWork;

    public Employee(String first_name, String last_name, String patronymic, String position, Integer experience, String startWork, String telephone, String email, Integer timeWork) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.position = position;
        this.experience = experience;
        this.startWork = startWork;
        this.telephone = telephone;
        this.email = email;
        this.timeWork = timeWork;
    }

    public Employee(){

    }

    public Employee(int id, String first_name, String last_name, String patronymic, String position, Integer experience, String startWork, String telephone, String email, Integer timeWork) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.position = position;
        this.experience = experience;
        this.startWork = startWork;
        this.telephone = telephone;
        this.email = email;
        this.timeWork = timeWork;
    }

    public Employee(String last_name, String position) {
        this.last_name = last_name;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getStartWork() {
        return startWork;
    }

    public void setStartWork(String startWork) {
        this.startWork = startWork;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(Integer timeWork) {
        this.timeWork = timeWork;
    }
}