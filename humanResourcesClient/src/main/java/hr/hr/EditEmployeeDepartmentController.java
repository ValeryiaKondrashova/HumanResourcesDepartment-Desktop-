package hr.hr;

import hr.hr.common.ConnectionTCP;
import hr.hr.entity.Command;
import hr.hr.entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import static java.lang.String.valueOf;

public class EditEmployeeDepartmentController {
    ConnectionTCP connectionTCP;

    @FXML
    private Button add_add;

    @FXML
    private ComboBox department_ComboBox;

    @FXML
    private ComboBox employee_ComboBox;

    @FXML
    private Label label;

    @FXML
    private ComboBox position_ComboBox;

    @FXML
    private TextField qw_field;

    String department, position, employee;

    @FXML
    void Add(ActionEvent event) {

        MainUserController mainUserController = new MainUserController();
        mainUserController.displayNameEditDepartment(employee, position);
        qw_field.setText("Данные о сотруднике успешно обновлены!");
        qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");

    }

    @FXML
    void SelectEmployee(ActionEvent event) {
        employee = employee_ComboBox.getSelectionModel().getSelectedItem().toString();

        connectionTCP.writeObject(Command.READ1);
        List<Employee> allEmployees = (List<Employee>) connectionTCP.readObject();

        for(int i=0; i<allEmployees.size();i++){
            if(employee.equals(allEmployees.get(i).getLast_name())){
                //position_ComboBox.setValue(allEmployees.get(i).getPosition());

                connectionTCP.writeObject(Command.READDEPARTMENT);
                List<String> allDepartmets = (List<String>) connectionTCP.readObject();
                ObservableList allDep = FXCollections.observableArrayList(allDepartmets);
                department_ComboBox.setItems(allDep);

                connectionTCP.writeObject(Command.RECEIVEDEPARTMENT);
                connectionTCP.writeObject(allEmployees.get(i).getPosition());
                department = (String) connectionTCP.readObject();

                System.out.println("department = " + department);
                department_ComboBox.setValue(department);

                break;
            }
        }
    }

    @FXML
    void SelectDepartment(ActionEvent event) {

        department = department_ComboBox.getSelectionModel().getSelectedItem().toString();

        connectionTCP.writeObject(Command.READPOSITIONUNIQUE);
        connectionTCP.writeObject(department);
        List<String> allPositions = (List<String>) connectionTCP.readObject();
        ObservableList allPr = FXCollections.observableArrayList(allPositions);
        position_ComboBox.setItems(allPr);

    }

    @FXML
    void SelectPosition(ActionEvent event) {
        position = position_ComboBox.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    void initialize() throws IOException {

        department = "";
        position = "";
        employee = "";

        connectionTCP = ConnectionTCP.getInstance();

        connectionTCP.writeObject(Command.READEMPLOYEE);
        List<String> allEmployee = (List<String>) connectionTCP.readObject();
        ObservableList allEmpl = FXCollections.observableArrayList(allEmployee);
        employee_ComboBox.setItems(allEmpl);

    }
}
