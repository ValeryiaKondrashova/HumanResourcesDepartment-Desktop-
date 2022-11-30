package hr.hr;

import hr.hr.common.ConnectionTCP;
import hr.hr.entity.Command;
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

public class AddVacationController {
    ConnectionTCP connectionTCP;

    @FXML
    private Button add_add;

    @FXML
    private ComboBox employee_ComboBox;

    @FXML
    private TextField startVacation_add;

    @FXML
    private TextField endVacation_add;

    @FXML
    private Label label;

    @FXML
    private TextField qw_field;

    String last_name, startVacation, endVacation;

    @FXML
    void Add(ActionEvent event) {
        startVacation = startVacation_add.getText();
        endVacation = endVacation_add.getText();

        try{
            if(last_name.isEmpty() || startVacation.isEmpty() || endVacation.isEmpty()){
                qw_field.setText("Присутствуют пустые поля!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            } else {
                VacationsController vacationsController = new VacationsController();
                vacationsController.displayNameAddVacation(last_name,
                        startVacation,
                        endVacation);
                qw_field.setText("Данные об отпуске успешно добавлены!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            }
        } catch(RuntimeException e){
            qw_field.setText("Некорректный ввод данных. Повторите попытку!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }


    }

    @FXML
    void SelectEmployee(ActionEvent event) {
        last_name = employee_ComboBox.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    void initialize() throws IOException {

        last_name ="";

        connectionTCP = ConnectionTCP.getInstance();

        connectionTCP.writeObject(Command.READEMPLOYEE);
        List<String> allEmployee = (List<String>) connectionTCP.readObject();
        ObservableList allEmpl = FXCollections.observableArrayList(allEmployee);
        employee_ComboBox.setItems(allEmpl);
    }

}
