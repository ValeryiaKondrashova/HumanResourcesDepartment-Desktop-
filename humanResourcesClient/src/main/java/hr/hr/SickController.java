package hr.hr;

import hr.hr.common.ConnectionTCP;
import hr.hr.entity.*;
import hr.hr.singleton.ProgramLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SickController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<SickProperty> tableSickProperties = FXCollections.observableArrayList();// вызовет конструктор

    @FXML
    private Button button_add_sick;

    @FXML
    private Button button_back;

    @FXML
    private Button button_delete_sick;

    @FXML
    private Button button_edit_sick;

    @FXML
    private Button button_exit_byUser;

    @FXML
    private Button button_otchet;

    @FXML
    private Button button_view_sick;

    @FXML
    private TextField field_unCorrect_byUser;

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<SickProperty, Integer> id_column_vacation;

    @FXML
    private TableColumn<SickProperty, String> last_name_column;

    @FXML
    private TableColumn<SickProperty, String> startSick_column;

    @FXML
    private TableColumn<SickProperty, String> endSick_column;

    @FXML
    private TableView<SickProperty> table_sick;

    @FXML
    void ShowDialogAdd(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddSick.fxml"));
            stage.setTitle("Оформление больничного");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayNameAddSick(String last_name, String startSick, String endSick){
        String lastName1 = last_name;
        String startSick1 = startSick;
        String endSick1 = endSick;

        // добавление сотрудника в БД
        try {
            connectionTCP = ConnectionTCP.getInstance();
        } catch (IOException e) {
            // System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            Sick employee = new Sick(lastName1,
                    startSick1,
                    endSick1);
            connectionTCP.writeObject(Command.CREATE3);
            connectionTCP.writeObject(employee);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            connectionTCP = ConnectionTCP.getInstance();   // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
            System.out.println("Нашел клиента и сокет готов для передачи! :)0");
        } catch (IOException e) {
            System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }

        id_column_vacation.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        last_name_column.setCellValueFactory(cellValue -> cellValue.getValue().last_nameProperty());
        startSick_column.setCellValueFactory(cellValue -> cellValue.getValue().startSickProperty());
        endSick_column.setCellValueFactory(cellValue -> cellValue.getValue().endSickProperty());


        button_view_sick.setOnAction(event -> {
            System.out.println("Клиент нажал на  ПРОСМОТРЕТЬ (SickController)");
            try {
                ProgramLogger.getProgramLogger().addLogInfo("Сотрудник нажал на ПРОСМОТРЕТЬ ОТПУСКА");
            } catch (IOException e) {
                e.printStackTrace();
            }

            tableSickProperties.clear();// чтобы не добавлять каждый раз к существующему списку

            try {
                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.READ): запись списка пользователей в поток... Получение всех пользователей из БД");
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectionTCP.writeObject(Command.READ3);
            List<Sick> allSick = (List<Sick>) connectionTCP.readObject();
            for (int i = 0; i < allSick.size(); i++) {
                SickProperty e = new SickProperty(allSick.get(i));
                tableSickProperties.add(e);
            }
            table_sick.setItems(tableSickProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });

        button_back.setOnAction(event -> {
//            System.out.println("Клиент нажал на  НАЗАД (VacationController)");

            Stage stage = (Stage) button_back.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("MainWindowUser.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        button_delete_sick.setOnAction(event -> {
            System.out.println("Клиент нажал на  УДАЛИТЬ (MainUserController)");
            try {
                field_unCorrect_byUser.setText("");
                int id = table_sick.getSelectionModel().getSelectedItem().getId();
                connectionTCP.writeObject(Command.DELETE2);
                connectionTCP.writeObject(id);
            } catch (NullPointerException e) {// если 0
                field_unCorrect_byUser.setText("Выберите строку!");
            }
        });

        button_exit_byUser.setOnAction(event -> {
            System.out.println("Клиент нажал на  ВЫХОД (VacationController)");
            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);
        });

        FilteredList<SickProperty> filterData = new FilteredList<>(tableSickProperties, b->true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(sick -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(sick.getLast_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else { return false; }
            });
            SortedList<SickProperty> sortedDate = new SortedList<>(filterData);
            sortedDate.comparatorProperty().bind(table_sick.comparatorProperty());

            System.out.println("sortedDate = " + sortedDate);
            table_sick.setItems(sortedDate);
        });
    }
}
