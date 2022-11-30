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

public class VacationsController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<VacationProperty> tableVacationsProperties = FXCollections.observableArrayList();// вызовет конструктор 0

    @FXML
    private Button button_add_vacation;

    @FXML
    private Button button_back;

    @FXML
    private Button button_delete_vacation;

    @FXML
    private Button button_edit_byUser;

    @FXML
    private Button button_exit_byUser;

    @FXML
    private Button button_otchet;

    @FXML
    private Button button_view_vacation;

    @FXML
    private TextField field_unCorrect_byUser;

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<VacationProperty, Integer> id_column_vacation;

    @FXML
    private TableColumn<VacationProperty, String> last_name_column;

    @FXML
    private TableColumn<VacationProperty, String> startVacation_column;

    @FXML
    private TableColumn<VacationProperty, String> endVacation_column;

    @FXML
    private TableView<VacationProperty> table_vacations;

    @FXML
    void ShowDialogAdd(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddVacation.fxml"));
            stage.setTitle("Отпуск");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayNameAddVacation(String last_name, String startVacation, String endVacation){
        String lastName1 = last_name;
        String startVacation1 = startVacation;
        String endVacation1 = endVacation;

        try {
            connectionTCP = ConnectionTCP.getInstance();
        } catch (IOException e) {
            // System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            Vacation employee = new Vacation(lastName1,
                    startVacation1,
                    endVacation1);
            connectionTCP.writeObject(Command.CREATE2);
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
        startVacation_column.setCellValueFactory(cellValue -> cellValue.getValue().startHolidayProperty());
        endVacation_column.setCellValueFactory(cellValue -> cellValue.getValue().endHolidayProperty());


        button_view_vacation.setOnAction(event -> {
            System.out.println("Клиент нажал на  ПРОСМОТРЕТЬ (VacationController)");
            try {
                ProgramLogger.getProgramLogger().addLogInfo("Сотрудник нажал на ПРОСМОТРЕТЬ ОТПУСКА");
            } catch (IOException e) {
                e.printStackTrace();
            }

            tableVacationsProperties.clear();// чтобы не добавлять каждый раз к существующему списку

            try {
                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.READ): запись списка пользователей в поток... Получение всех пользователей из БД");
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectionTCP.writeObject(Command.READ2);
            List<Vacation> allVacations = (List<Vacation>) connectionTCP.readObject();
            for (int i = 0; i < allVacations.size(); i++) {
                VacationProperty e = new VacationProperty(allVacations.get(i));
                tableVacationsProperties.add(e);
            }
            table_vacations.setItems(tableVacationsProperties);// устанавливаем значение обсёрвабл листа в таблицу
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

        button_delete_vacation.setOnAction(event -> {
            System.out.println("Клиент нажал на  УДАЛИТЬ (MainUserController)");
            try {
                field_unCorrect_byUser.setText("");
                int id = table_vacations.getSelectionModel().getSelectedItem().getId();
                connectionTCP.writeObject(Command.DELETE3);
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

        FilteredList<VacationProperty> filterData = new FilteredList<>(tableVacationsProperties, b->true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(vacation -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(vacation.getLast_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else { return false; }
            });
            SortedList<VacationProperty> sortedDate = new SortedList<>(filterData);
            sortedDate.comparatorProperty().bind(table_vacations.comparatorProperty());

            System.out.println("sortedDate = " + sortedDate);
            table_vacations.setItems(sortedDate);
        });
    }
}
