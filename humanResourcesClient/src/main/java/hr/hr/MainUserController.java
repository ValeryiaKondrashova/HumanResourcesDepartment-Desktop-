package hr.hr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import hr.hr.common.ConnectionTCP;

import hr.hr.entity.Command;
import hr.hr.entity.Employee;
import hr.hr.entity.EmployeeProperty;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainUserController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<EmployeeProperty> tableEmployeesProperties = FXCollections.observableArrayList();// вызовет конструктор 0

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_view_byUser;

    @FXML
    private Button button_edit_byUser;

    @FXML
    private Button button_delete_byUser;

    @FXML
    private Button button_exit_byUser;


    @FXML
    private TextField field_unCorrect_byUser;

    @FXML
    private TableView<EmployeeProperty> table_employees;

    @FXML
    private TableColumn<EmployeeProperty, Integer> id_column_employee;

    @FXML
    private TableColumn<EmployeeProperty, String> first_name_column;

    @FXML
    private TableColumn<EmployeeProperty, String> last_name_column;

    @FXML
    private TableColumn<EmployeeProperty, String> patronymic_column;

    @FXML
    private TableColumn<EmployeeProperty, String> position_column;

    @FXML
    private TableColumn<EmployeeProperty, Integer> experience_column;

    @FXML
    private TableColumn<EmployeeProperty, String> startWork_column;

    @FXML
    private TableColumn<EmployeeProperty, String> telephone_column;

    @FXML
    private TableColumn<EmployeeProperty, String> email_column;

    @FXML
    private TableColumn<EmployeeProperty, Integer> timeWork_column;

    @FXML
    private Button button_calc_salary;

    @FXML
    private Button button_change_department;

    @FXML
    private Button button_list_sick;

    @FXML
    private Button button_vacation;

    @FXML
    private TextField filterField;

    public void ShowDialogForSaving(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SavingFileEmployee.fxml"));
            stage.setTitle("Создание текстового отчета");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ShowDialog(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
            stage.setTitle("Найм сотрудника");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void ShowDialogSalary(ActionEvent event) {

        System.out.println("Клиент нажал на  ПОЛУЧЕНИЕ ВЫПИСКИ О ЗАРАБОТНОЙ ПЛАТЕ (MainUserController)");

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("DialogSalary.fxml"));
            stage.setTitle("Выписка о заработной плате");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionTCP.writeObject(Command.READ1);
        List<Employee> allEmployee = (List<Employee>) connectionTCP.readObject();

        connectionTCP.writeObject(Command.READSALARY);
        List<String> allSalary = (List<String>) connectionTCP.readObject();

        try {
            FileWriter fileWriter = new FileWriter("Выписка о заработной плате сотрудников.txt", false);
            for (int i = 0; i < allEmployee.size(); i++) {

                String last_name = allEmployee.get(i).getLast_name();
                String position = allEmployee.get(i).getPosition();
                int experience = allEmployee.get(i).getExperience();
                int timeWork = allEmployee.get(i).getTimeWork();
                String salary = allSalary.get(i);


                fileWriter.write("Сотрудник : " + last_name + "\n" +
                        "\tДолжность : " + position + "\n" +
                        "\tОпыт : " + experience + "\n" +
                        "\tВремя работы = " + timeWork + "\n" +
                        "\tЗарплата = " + salary + ".\n\n");
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ShowDialogSick(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("DialogSick.fxml"));
            stage.setTitle("Выписка больничных");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ShowDialogForEdit(ActionEvent event) throws IOException {
        try {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("EditEmployee.fxml"));
        stage.setTitle("Редактирование сотрудника");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ShowDialogChangeDepartment(ActionEvent event) throws IOException {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("EditEmployeeDepartment.fxml"));
            stage.setTitle("Кадровые переводы");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayNameAdd(String first_name, String last_name, String patronymic1, String position1, int experience1, String startWork1, String telephone1, String email1, int timeWork1){
        String firstName = first_name;
        String lastName = last_name;
        String patronymic = patronymic1;
        String position = position1;
        int experience = experience1;
        String startWork = startWork1;
        String telephone = telephone1;
        String email = email1;
        int timeWork = timeWork1;

        System.out.println(firstName + " " + lastName + " " + patronymic + " " + position + " " + experience + " " + startWork + " " + telephone + " " + email + " " + timeWork);

        // добавление сотрудника в БД
        try {
            connectionTCP = ConnectionTCP.getInstance();
        } catch (IOException e) {
            // System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            Employee employee = new Employee(firstName,
                    lastName,
                    patronymic,
                    position,
                    experience,
                    startWork,
                    telephone,
                    email,
                    timeWork);
            System.out.println("employee = " + employee);
            System.out.println("product manufacturer_product  = " + employee.getFirst_name());

            connectionTCP.writeObject(Command.CREATE1);
            connectionTCP.writeObject(employee);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void displayNameEdit(String first_name, String last_name, String patronymic1, String position1, int experience1, String startWork1, String telephone1, String email1, int timeWork1){
        String firstName = first_name;
        String lastName = last_name;
        String patronymic = patronymic1;
        String position = position1;
        int experience = experience1;
        String startWork = startWork1;
        String telephone = telephone1;
        String email = email1;
        int timeWork = timeWork1;

        System.out.println(firstName + " " + lastName + " " + patronymic + " " + position + " " + experience + " " + startWork + " " + telephone + " " + email + " " + timeWork);

        // редактирование сотрудника в БД
        try {
            connectionTCP = ConnectionTCP.getInstance();
        } catch (IOException e) {
            // System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            Employee employee = new Employee(firstName,
                    lastName,
                    patronymic,
                    position,
                    experience,
                    startWork,
                    telephone,
                    email,
                    timeWork);
            System.out.println("employee = " + employee);
            System.out.println("product manufacturer_product  = " + employee.getFirst_name());

            connectionTCP.writeObject(Command.UPDATE1);
            connectionTCP.writeObject(employee);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void displayNameEditDepartment(String employeeLastName, String position){
        String position1 = position;
        String employee1 = employeeLastName;

        System.out.println("position1 = " + position1);
        System.out.println("employee1 = " + employee1);

        // редактирование отдела сотрудника в БД
        try {
            connectionTCP = ConnectionTCP.getInstance();
        } catch (IOException e) {
            // System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            Employee employee = new Employee(employee1, position1);

            connectionTCP.writeObject(Command.UPDATEEMPLOYEEDEPARTMENT);
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

        id_column_employee.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        first_name_column.setCellValueFactory(cellValue -> cellValue.getValue().first_nameProperty());
        last_name_column.setCellValueFactory(cellValue -> cellValue.getValue().last_nameProperty());
        patronymic_column.setCellValueFactory(cellValue -> cellValue.getValue().patronymicProperty());
        position_column.setCellValueFactory(cellValue -> cellValue.getValue().positionProperty());
        experience_column.setCellValueFactory(cellValue -> cellValue.getValue().experienceProperty().asObject());
        startWork_column.setCellValueFactory(cellValue -> cellValue.getValue().startWorkProperty());
        telephone_column.setCellValueFactory(cellValue -> cellValue.getValue().telephoneProperty());
        email_column.setCellValueFactory(cellValue -> cellValue.getValue().emailProperty());
        timeWork_column.setCellValueFactory(cellValue -> cellValue.getValue().timeWorkProperty().asObject());

        button_view_byUser.setOnAction(event -> {

            System.out.println("Клиент нажал на  ПРОСМОТРЕТЬ (MainUserController)");
            try {
                ProgramLogger.getProgramLogger().addLogInfo("Пользователь нажал на ПРОСМОТРЕТЬ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            tableEmployeesProperties.clear();// чтобы не добавлять каждый раз к существующему списку

            try {
                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.READ): запись списка пользователей в поток... Получение всех пользователей из БД");
            } catch (IOException e) {
                e.printStackTrace();
            }

            connectionTCP.writeObject(Command.READ1);
            List<Employee> allProductss = (List<Employee>) connectionTCP.readObject();
            for (int i = 0; i < allProductss.size(); i++) {
                EmployeeProperty e = new EmployeeProperty(allProductss.get(i));
                tableEmployeesProperties.add(e);
            }
            table_employees.setItems(tableEmployeesProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });

        button_delete_byUser.setOnAction(event -> {
            System.out.println("Клиент нажал на  УДАЛИТЬ (MainUserController)");
            try {
                field_unCorrect_byUser.setText("");
                int id = table_employees.getSelectionModel().getSelectedItem().getId();
                connectionTCP.writeObject(Command.DELETE1);
                connectionTCP.writeObject(id);
            } catch (NullPointerException e) {// если 0
                field_unCorrect_byUser.setText("Выберите строку!");
            }
        });
        button_exit_byUser.setOnAction(event -> {
            System.out.println("Клиент нажал на  ВЫХОД (MainUserController)");
                connectionTCP.writeObject(Command.EXIT);
                connectionTCP.close();
                System.exit(0);
        });

//        button_list_sick.setOnAction(event -> {
//            System.out.println("Клиент нажал на  ВЫПИСКА О БОЛЬНИЧНЫХ (MainUserController)");
//        });

        button_vacation.setOnAction(event -> {
            System.out.println("Клиент нажал на  ПЕРЕХОД НА ОКНО С ОТПУСКАМИ (MainUserController)");

            Stage stage = (Stage) button_vacation.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("Vacations.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();


        });

        button_list_sick.setOnAction(event -> {
            System.out.println("Клиент нажал на  ПЕРЕХОД НА ОКНО С БОЛЬНИЧНЫМИ (MainUserController)");

            Stage stage = (Stage) button_list_sick.getScene().getWindow();
            stage.close();

            Stage primaryStage = new Stage();
            Parent path = null;
            try {
                path = FXMLLoader.load(getClass().getResource("Sick.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(path);
            primaryStage.setScene(scene);
            primaryStage.show();


        });

        FilteredList<EmployeeProperty> filterData = new FilteredList<>(tableEmployeesProperties, b->true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(employee.getFirst_name().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        employee.getLast_name().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        employee.getPatronymic().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        employee.getPosition().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        String.valueOf(employee.getExperience()).indexOf(lowerCaseFilter) != -1 ||
                        employee.getStartWork().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        employee.getTelephone().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        employee.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1 ||
                        String.valueOf(employee.getTimeWork()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else { return false; }
            });
            SortedList<EmployeeProperty> sortedDate = new SortedList<>(filterData);
            sortedDate.comparatorProperty().bind(table_employees.comparatorProperty());

            System.out.println("sortedDate = " + sortedDate);
            table_employees.setItems(sortedDate);
        });
    }
}
