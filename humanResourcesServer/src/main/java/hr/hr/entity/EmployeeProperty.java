package hr.hr.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeProperty {
    private IntegerProperty id;

    private StringProperty first_name;
    private StringProperty last_name;
    private StringProperty patronymic;
    private StringProperty position;
    private IntegerProperty experience;
    private StringProperty startWork;
    private StringProperty telephone;
    private StringProperty email;
    private IntegerProperty timeWork;

    public EmployeeProperty(Employee employee){
        id = new SimpleIntegerProperty(employee.getId());
        first_name = new SimpleStringProperty(employee.getFirst_name());
        last_name = new SimpleStringProperty(employee.getLast_name());
        patronymic = new SimpleStringProperty(employee.getPatronymic());
        position = new SimpleStringProperty(employee.getPosition());
        experience = new SimpleIntegerProperty(employee.getExperience());
        startWork = new SimpleStringProperty(employee.getStartWork());
        telephone = new SimpleStringProperty(employee.getTelephone());
        email = new SimpleStringProperty(employee.getEmail());
        timeWork = new SimpleIntegerProperty(employee.getTimeWork());
    }

    public EmployeeProperty(IntegerProperty id, StringProperty first_name, StringProperty last_name, StringProperty patronymic, StringProperty position, IntegerProperty experience, StringProperty startWork, StringProperty telephone, StringProperty email, IntegerProperty timeWork) {
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

    public Employee toHumanResource(){
        return new Employee(
                id.intValue(),
                first_name.getValue(),
                last_name.getValue(),
                patronymic.getValue(),
                position.getValue(),
                experience.intValue(),
                startWork.getValue(),
                telephone.getValue(),
                email.getValue(),
                timeWork.intValue()
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

    public String getFirst_name() {
        return first_name.get();
    }

    public StringProperty first_nameProperty() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
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

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public int getExperience() {
        return experience.get();
    }

    public IntegerProperty experienceProperty() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience.set(experience);
    }

    public String getStartWork() {
        return startWork.get();
    }

    public StringProperty startWorkProperty() {
        return startWork;
    }

    public void setStartWork(String startWork) {
        this.startWork.set(startWork);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getTimeWork() {
        return timeWork.get();
    }

    public IntegerProperty timeWorkProperty() {
        return timeWork;
    }

    public void setTimeWork(int timeWork) {
        this.timeWork.set(timeWork);
    }
}
