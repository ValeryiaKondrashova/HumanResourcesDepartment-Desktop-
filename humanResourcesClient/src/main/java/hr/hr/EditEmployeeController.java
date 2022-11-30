package hr.hr;

import hr.hr.common.ConnectionTCP;
import hr.hr.entity.Command;
import hr.hr.entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

import static java.lang.String.valueOf;

public class EditEmployeeController {
    ConnectionTCP connectionTCP;

    @FXML
    private Button add_add;

    @FXML
    private TextField email_add;

    @FXML
    private ComboBox employee_ComboBox;

    @FXML
    private TextField experience_add;

    @FXML
    private TextField first_name_add;

    @FXML
    private Label label;

    //@FXML
    //private TextField last_name_add;

    @FXML
    private TextField patronymic_add;

    @FXML
    private ComboBox position_ComboBox;

    @FXML
    private TextField qw_field;

    @FXML
    private TextField startWork_add;

    @FXML
    private TextField telephone_add;

    @FXML
    private TextField timeWork_add;

    String employee;

    String position;
    String first_name, last_name, patronymic;
    String startWork, telephone, email;
    int experience, timeWork;

    @FXML
    void Add(ActionEvent event) {
        first_name = first_name_add.getText();
        //last_name = last_name_add.getText();
        patronymic = patronymic_add.getText();

        experience = Integer.parseInt(experience_add.getText());
        startWork = startWork_add.getText();
        telephone = telephone_add.getText();
        email = email_add.getText();
        timeWork = Integer.parseInt(timeWork_add.getText());;

        try{
            if(first_name.isEmpty() || patronymic.isEmpty() || position.isEmpty() || startWork.isEmpty() || telephone.isEmpty() || email.isEmpty()){
                qw_field.setText("Присутствуют пустые поля!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            } else {
                System.out.println(first_name + " " + last_name + " " + patronymic + " " + position + " " + experience + " " + startWork  + " " + telephone + " " + email + " " + timeWork);

                MainUserController mainUserController = new MainUserController();
                mainUserController.displayNameEdit(first_name,
                        employee,
                        patronymic,
                        position,
                        experience,
                        startWork,
                        telephone,
                        email,
                        timeWork);
                qw_field.setText("Данные о сотруднике успешно обновлены!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            }
        } catch(RuntimeException e){
            qw_field.setText("Некорректный ввод данных. Повторите попытку!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }

    }

    @FXML
    void SelectEmployee(ActionEvent event) {
        employee = employee_ComboBox.getSelectionModel().getSelectedItem().toString();

        connectionTCP.writeObject(Command.READ1);
        List<Employee> allEmployees = (List<Employee>) connectionTCP.readObject();
        for(int i=0; i<allEmployees.size();i++){
            if(employee.equals(allEmployees.get(i).getLast_name())){
                first_name_add.setText(allEmployees.get(i).getFirst_name());
                //last_name_add.setText(allEmployees.get(i).getLast_name());
                patronymic_add.setText(allEmployees.get(i).getPatronymic());
                position_ComboBox.setValue(allEmployees.get(i).getPosition());
                experience_add.setText(valueOf(allEmployees.get(i).getExperience()));
                startWork_add.setText(allEmployees.get(i).getStartWork());
                telephone_add.setText(allEmployees.get(i).getTelephone());
                email_add.setText(allEmployees.get(i).getEmail());
                timeWork_add.setText(valueOf(allEmployees.get(i).getTimeWork()));
            }
        }
    }

    @FXML
    void SelectPosition(ActionEvent event) {
        position = position_ComboBox.getSelectionModel().getSelectedItem().toString();
    }


    @FXML
    void initialize() throws IOException {

        position = "";
        employee = "";

        connectionTCP = ConnectionTCP.getInstance();


        connectionTCP.writeObject(Command.READPOSITION);
        List<String> allPositions = (List<String>) connectionTCP.readObject();
        ObservableList allPr = FXCollections.observableArrayList(allPositions);
        position_ComboBox.setItems(allPr);

        connectionTCP.writeObject(Command.READEMPLOYEE);
        List<String> allEmployee = (List<String>) connectionTCP.readObject();
        ObservableList allEmpl = FXCollections.observableArrayList(allEmployee);
        employee_ComboBox.setItems(allEmpl);
    }
}
