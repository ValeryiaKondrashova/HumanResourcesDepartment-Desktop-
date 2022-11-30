package hr.hr.database;

import hr.hr.common.ConnectionTCP;
import hr.hr.entity.*;
import hr.hr.singleton.ProgramLogger;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class RequestHandler implements Runnable {
    private final ConnectionTCP connectionTCP;  //создали объекта КЛАССА ConnectionTCP

    public RequestHandler(Socket socket) {
        System.out.println("Перед connectionTCP = new ConnectionTCP(socket)");
        connectionTCP = new ConnectionTCP(socket);//сокет соединения с клиентом
    } //connectionTCP - шнур

    @Override
    public void run() {
        DatabaseHandler userRepository = new DatabaseHandler();
        DatabaseHandler employeeRepository = new DatabaseHandler();

        while (true) {
            Command command = (Command) connectionTCP.readObject();
            System.out.println(command);
            switch (command) {
                case READ: {
                    List<User> userr = null;
                    System.out.println("in READ here");

                    try {
                        userr = userRepository.getAllUsers();
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные из БД о пользователях просмотрены!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for(int i = 0; i<userr.size(); i++){
                        System.out.println(userr.get(i).getLogin());
                        System.out.println(userr.get(i).getPassword());
                        System.out.println("---");
                    }
                    connectionTCP.writeObject(userr); // с помощью writeObject отправляем клиенту массив юзеров
                }
                break;
                case READ1: {
                    List<Employee> employees = null;
                    try {
                        employees = employeeRepository.getAllEmployees();
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные из БД о сотрудниках просмотрены!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectionTCP.writeObject(employees); // с помощью writeObject отправляем клиенту массив юзеров
                }
                break;
                case READ2: {
                    List<Vacation> vacations = null;
                    try {
                        vacations = employeeRepository.getAllVacations();
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные из БД об отпусках просмотрены!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectionTCP.writeObject(vacations); // с помощью writeObject отправляем клиенту массив юзеров
                }
                break;
                case READ3: {
                    List<Sick> sicks = null;
                    try {
                        sicks = employeeRepository.getAllSicks();
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные из БД об отпусках просмотрены!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectionTCP.writeObject(sicks); // с помощью writeObject отправляем клиенту массив юзеров
                }
                break;
                case READPOSITION: {
                    List<String> positions = null;
                    positions = employeeRepository.getAllPositions();
                    connectionTCP.writeObject(positions);
                }
                break;
                case READEMPLOYEE: {
                    List<String> employees = null;
                    employees = employeeRepository.getAllEmployeesLastName();
                    connectionTCP.writeObject(employees);
                }
                break;
                case READDEPARTMENT: {
                    List<String> derartments = null;
                    derartments = employeeRepository.getAllDepartments();
                    connectionTCP.writeObject(derartments);
                }
                break;
                case RECEIVEDEPARTMENT: {
                    String position = (String) connectionTCP.readObject();
                    String receivePosition = employeeRepository.getDepartment(position);
                    connectionTCP.writeObject(receivePosition);
                }
                break;
                case READPOSITIONUNIQUE: {
                    String department = (String) connectionTCP.readObject();
                    List<String> positions = null;
                    positions = employeeRepository.getPositionsUnique(department);
                    connectionTCP.writeObject(positions);
                }
                break;
                case READSALARY: {
                    List<String> salaries = null;
                    salaries = employeeRepository.getAllSalary();
                    connectionTCP.writeObject(salaries);
                }
                break;
                case CREATE: {
                    User userr = (User) connectionTCP.readObject();
                    try {
                        userRepository.signUpUser(userr);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Пользователь добавлен в БД (Table: users)!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CREATE1: {
                    Employee employee = (Employee) connectionTCP.readObject();
                    try {
                        userRepository.signUpEmployee(employee);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Сотрудник добавлен в БД (Table: products)!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CREATE2: {
                    Vacation vacation = (Vacation) connectionTCP.readObject();
                    try {
                        userRepository.signUpVacation(vacation);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Сотрудник добавлен в БД (Table: products)!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CREATE3: {
                    Sick sick = (Sick) connectionTCP.readObject();
                    try {
                        userRepository.signUpSick(sick);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Сотрудник добавлен в БД (Table: products)!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case UPDATE: {
                    User userr = (User) connectionTCP.readObject();

                    try {
                        userRepository.updateUser(userr);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные пользователя отредактированы!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case UPDATE1: {
                    Employee employee = (Employee) connectionTCP.readObject();

                    try {
                        employeeRepository.updateEmployee(employee);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные сотрудника отредактированы!");
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case UPDATEEMPLOYEEDEPARTMENT: {
                    Employee employee = (Employee) connectionTCP.readObject();

                    try {
                        employeeRepository.updateEmployeeDepartment(employee);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные сотрудника(отдел) отредактированы!");
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETE: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        userRepository.deleteUserByID(id);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Пользователь удален!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETE1: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        employeeRepository.deleteEmployeeByID(id);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Сотрудник удален!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETE2: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        employeeRepository.deleteSickByID(id);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Больничный удален!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETE3: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        employeeRepository.deleteVacationByID(id);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Больничный удален!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case EXIT: {
                    connectionTCP.close();
                    return;
                }
            }
        }
    }
}

