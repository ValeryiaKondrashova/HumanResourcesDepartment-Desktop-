package hr.hr;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import hr.hr.common.ConnectionTCP;
import hr.hr.entity.Command;

public class AddEmployeeController {
    ConnectionTCP connectionTCP;

    @FXML
    private Button add_add;

    @FXML
    private TextField email_add;

    @FXML
    private TextField experience_add;

    @FXML
    private TextField first_name_add;

    @FXML
    private Label label;

    @FXML
    private TextField last_name_add;

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

    //@FXML
    //private TextField timeWork_add;


    String position;
    String first_name, last_name, patronymic;
    String startWork, telephone, email;
    int experience, timeWork;
    @FXML
    void Add(ActionEvent event) {

        first_name = first_name_add.getText();
        last_name = last_name_add.getText();
        patronymic = patronymic_add.getText();

        experience = Integer.parseInt(experience_add.getText());
        startWork = startWork_add.getText();
        telephone = telephone_add.getText();
        email = email_add.getText();
        timeWork = 0;

        try{
            if(first_name.isEmpty() || last_name.isEmpty() || patronymic.isEmpty() || position.isEmpty() || startWork.isEmpty() || telephone.isEmpty() || email.isEmpty()){
                qw_field.setText("Присутствуют пустые поля!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            } else {
                System.out.println(first_name + " " + last_name + " " + patronymic + " " + position + " " + experience + " " + startWork  + " " + telephone + " " + email + " " + timeWork);

                MainUserController mainUserController = new MainUserController();
                mainUserController.displayNameAdd(first_name,
                        last_name,
                        patronymic,
                        position,
                        experience,
                        startWork,
                        telephone,
                        email,
                        timeWork);
                qw_field.setText("Сотрудник успешно добавлен!");
                qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            }
        } catch(RuntimeException e){
            qw_field.setText("Некорректный ввод данных. Повторите попытку!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }
    }

    @FXML
    void SelectPosition(ActionEvent event) {
        position = position_ComboBox.getSelectionModel().getSelectedItem().toString();
    }



    @FXML
    void initialize() throws IOException {

        position ="";

        connectionTCP = ConnectionTCP.getInstance();


        connectionTCP.writeObject(Command.READPOSITION);
        List<String> allPositions = (List<String>) connectionTCP.readObject();
        ObservableList allPr = FXCollections.observableArrayList(allPositions);
        position_ComboBox.setItems(allPr);
    }
}
