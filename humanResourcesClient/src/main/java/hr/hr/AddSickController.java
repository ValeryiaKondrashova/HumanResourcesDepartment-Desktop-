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

public class AddSickController {
    ConnectionTCP connectionTCP;

    @FXML
    private Button add_add;

    @FXML
    private ComboBox employee_ComboBox;

    @FXML
    private TextField startSick_add;

    @FXML
    private TextField endSick_add;

    @FXML
    private Label label;

    @FXML
    private TextField qw_field;

    String last_name, startSick, endSick;

    @FXML
    void Add(ActionEvent event) {
        startSick = startSick_add.getText();
        endSick = endSick_add.getText();

        try{
            if(last_name.isEmpty() || startSick.isEmpty() || endSick.isEmpty()){
                qw_field.setText("Присутствуют пустые поля!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            } else {
                SickController sickController = new SickController();
                sickController.displayNameAddSick(last_name,
                        startSick,
                        endSick);
                qw_field.setText("Данные о больничном успешно добавлены!");
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
