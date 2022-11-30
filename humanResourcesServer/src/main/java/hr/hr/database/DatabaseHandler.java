package hr.hr.database;


import hr.hr.entity.Employee;
import hr.hr.entity.Sick;
import hr.hr.entity.User;
import hr.hr.entity.Vacation;
import hr.hr.singleton.ProgramLogger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {   //класс DatabaseHandler наследуется от Configs
    // Extends это ключевое слово, предназначенное для расширения реализации какого-то существующего класса
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException{
        String сonnectionString = "jdbc:mysql://" + dbHost + ":"             // jdbc - то такоей плагин, который позволяет связать Java и MySQL
                + dbPort + "/" + dbName;                                    //в этой строке (connectionString) будет содержаться все то (все данные), что поможет нам подключиться к бд
        Class.forName("com.mysql.cj.jdbc.Driver");                             // указываем какой драйвер будем использовать

        //dbConnection = DriverManager.getConnection(сonnectionString,dbUser,dbPass);  //здесь заключаем само соединение
        //  DriverManager класс попытается загрузить классы драйверов, указанные в системном свойстве "jdbc.drivers".
        //  Это позволяет пользователю настраивать драйверы JDBC, используемые их приложениями.
        try{
            return DriverManager.getConnection(сonnectionString,dbUser,dbPass);
        } catch (SQLException e){
            throw new RuntimeException("can't provide connection :(");
        }

    }

    public void signUpUser(User user) throws IOException { // Добавление пользователя в таблицу
        String insert = "INSERT INTO " + Const.USER_TABLE + "("  /* запрос добавления в бд (запрос insert) */
                + Const.USER_NAME + ","
                + Const.USER_LOGIN + ","
                + Const.USER_PASSWORD + ","
                + Const.USER_STATUS + ")" +
                "VALUES(?, ?, ?, (Select idstatus from statususer where statusName = ?))";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,user.getName());
            prSt.setString(2,user.getLogin());
            prSt.setString(3,user.getPassword());
            prSt.setString(4,user.getStatus());


            prSt.executeUpdate(); //метод executeUpdate() позволяет занести что-либо в бд (в данном случае заносим в бд объект prSt
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке добавления пользователя в БД введены некорректные данные.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void signUpEmployee(Employee employee){
        //Добавляем сам товар
        String insert2 = "INSERT INTO employees(first_name, last_name, patronymic, position, experience, startWork, telephone, email, timeWork) \n" +
                "VALUES(?, ?, ?, (SELECT idposition FROM positions WHERE positionName = ?), ?, ?, ?, ?, ?);";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert2);
            prSt.setString(1, employee.getFirst_name());
            prSt.setString(2, employee.getLast_name());
            prSt.setString(3, employee.getPatronymic());
            prSt.setString(4, employee.getPosition());
            prSt.setInt(5, employee.getExperience());
            prSt.setString(6, employee.getStartWork());
            prSt.setString(7, employee.getTelephone());
            prSt.setString(8, employee.getEmail());
            prSt.setInt(9, employee.getTimeWork());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void signUpVacation(Vacation vacation){
        //Добавляем
        String insert2 = "INSERT INTO holidays(employee, startHoliday, endHoliday) \n" +
                "VALUES((SELECT idemployee FROM employees WHERE last_name = ?), ?, ?);";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert2);
            prSt.setString(1, vacation.getLast_name());
            prSt.setString(2, vacation.getStartHoliday());
            prSt.setString(3, vacation.getEndHoliday());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void signUpSick(Sick sick){
        //Добавляем
        String insert2 = "INSERT INTO sickleave(employee, startSickLeave, endSickLeave) \n" +
                "VALUES((SELECT idemployee FROM employees WHERE last_name = ?), ?, ?);";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert2);
            prSt.setString(1, sick.getLast_name());
            prSt.setString(2, sick.getStartSick());
            prSt.setString(3, sick.getEndHoSick());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws IOException {  //создаем метод getAllUsers() который вернет  List<User> (список юзеров)  //метод который отвечает за считываение из БД
        //List<User> - это массив( точнее список) User (это как массив стрингов, только у нас список users)
        List<User> users = new ArrayList<>();  //это как int a = new int();

        // Connection - Соединение (сеанс) с определенной базой данных. Выполняются инструкции SQL, и результаты возвращаются в контексте соединения.
        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            // метод ResultSet - извлекается значения из источника данных
            ResultSet resultSet = statement.executeQuery("select users.iduser, users.name_user, users.login_user,users.password_user, statusName from users JOIN statususer ON users.statusUser=statususer.idstatus"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                int id = resultSet.getInt("iduser");
                String name = resultSet.getString("name_user");
                String login = resultSet.getString("login_user");
                String password = resultSet.getString("password_user");
                String statusUser = resultSet.getString("statusName");

                //считали строку из result состоящую из элементов id, name, number и тд
                User userr = new User(id, name, login, password, statusUser);
                //теперь создали объект userr и поместили считанные данные в users
                users.add(userr);
                //  и добавили страну(userr) в массив users
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке просмотра пользователей из БД возникла ошибка");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users; //вернули считанный массив
    }

    public List<Employee> getAllEmployees() throws IOException {  //создаем метод getAllTech() который вернет  List<Techh> (список стран)  //метод который отвечает за считываение из БД
        List<Employee> products = new ArrayList<>();  //это как int a = new int();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT idemployee, first_name, last_name, patronymic, positionName, experience, startWork, telephone, email, timeWork\n" +
                    "FROM employees\n" +
                    "JOIN positions ON employees.position = positions.idposition;"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                int id = resultSet.getInt("idemployee");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String patronymic = resultSet.getString("patronymic");
                String positionName = resultSet.getString("positionName");
                int experience = resultSet.getInt("experience");
                String startWork = resultSet.getString("startWork");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                int timeWork = resultSet.getInt("timeWork");

                //считали строку из result состоящую из элементов id, name, number и тд
                Employee productt = new Employee(id, first_name, last_name, patronymic, positionName, experience, startWork, telephone, email, timeWork);
                //теперь создали объект techniques и поместили туда считанные данные
                products.add(productt);
                //  и добавили страну(userr) в массив users
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке просмотра пользователей из БД возникла ошибка");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products; //вернули считанный массив
    }

    public List<Vacation> getAllVacations() throws IOException {  //создаем метод getAllTech() который вернет  List<Techh> (список стран)  //метод который отвечает за считываение из БД
        List<Vacation> products = new ArrayList<>();  //это как int a = new int();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT holidays.idholiday, employees.last_name, holidays.startHoliday, holidays.endHoliday FROM holidays JOIN employees ON holidays.employee = employees.idemployee"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                int id = resultSet.getInt("idholiday");
                String employee = resultSet.getString("last_name");
                String startHoliday = resultSet.getString("startHoliday");
                String endHoliday = resultSet.getString("endHoliday");
                //считали строку из result состоящую из элементов id, name и тд
                Vacation vacation = new Vacation(id, employee, startHoliday, endHoliday);
                products.add(vacation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке просмотра пользователей из БД возникла ошибка");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products; //вернули считанный массив
    }

    public List<Sick> getAllSicks() throws IOException {  //создаем метод getAllTech() который вернет  List<Techh> (список стран)  //метод который отвечает за считываение из БД
        List<Sick> sicks = new ArrayList<>();  //это как int a = new int();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT sickleave.idsickLeave, employees.last_name, sickleave.startSickLeave, sickleave.endSickLeave FROM sickleave JOIN employees ON sickleave.employee = employees.idemployee"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                int id = resultSet.getInt("idsickLeave");
                String employee = resultSet.getString("last_name");
                String startSick = resultSet.getString("startSickLeave");
                String endSick = resultSet.getString("endSickLeave");
                //считали строку из result состоящую из элементов id, name и тд
                Sick sick = new Sick(id, employee, startSick, endSick);
                sicks.add(sick);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке просмотра пользователей из БД возникла ошибка");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sicks; //вернули считанный массив
    }

    public List<String> getAllPositions(){

        List<String> positions = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT positionName from positions"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String position = resultSet.getString("positionName");

                //теперь создали объект techniques и поместили туда считанные данные
                positions.add(position);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public List<String> getAllDepartments(){

        List<String> departments = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT departmentName from departments"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String department = resultSet.getString("departmentName");

                //теперь создали объект techniques и поместили туда считанные данные
                departments.add(department);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public List<String> getAllSalary(){

        List<String> salaries = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT first_name, last_name, patronymic, positionName, experience, startWork, telephone, email, timeWorK, \n" +
                    "(SELECT salary FROM positions WHERE positions.idposition = employees.position)\n" +
                    "+\n" +
                    "((SELECT salary FROM positions WHERE positions.idposition = employees.position)\n" +
                    "*\n" +
                    "(SELECT percentOfExperience FROM experience WHERE employees.experience BETWEEN experienceMIN AND experienceMAX)) / 100\n" +
                    "+\n" +
                    "((SELECT salary FROM positions WHERE positions.idposition = employees.position)\n" +
                    "*\n" +
                    "(SELECT percentOfBonus FROM bonus WHERE employees.timeWork BETWEEN numberOfWorkingHoursMIN AND numberOfWorkingHoursMAX)) / 100\n" +
                    "AS 'ЗАРПЛАТА'\n" +
                    "FROM employees\n" +
                    "JOIN positions ON employees.position = positions.idposition;"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String salary = resultSet.getString("ЗАРПЛАТА");

                //теперь создали объект techniques и поместили туда считанные данные
                salaries.add(salary);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return salaries;
    }

    public String getDepartment(String position){

        System.out.println("position = " + position);

        String department = "";
        int idDepartment = 0;

        try(Connection connection1 = getDbConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement( "SELECT id_department from positions where positionName = ?" )) {

            preparedStatement1.setString(1, position);

            ResultSet rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                idDepartment = rs.getInt("id_department");
            }

            System.out.println("idDepartment = " + idDepartment);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection1 = getDbConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement( "SELECT departmentName from departments WHERE iddepartment = ?" )) {

            preparedStatement1.setInt(1, idDepartment);

            ResultSet rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                department = rs.getString("departmentName");
            }

            System.out.println("department = " + department);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return department;
    }

    public List<String> getPositionsUnique(String department){

        List<String> positions = new ArrayList<>();

        System.out.println("department = " + department);

        int idDepartment = 0;

        try(Connection connection1 = getDbConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement( "SELECT iddepartment from departments where departmentName = ?" )) {

            preparedStatement1.setString(1, department);

            ResultSet rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                idDepartment = rs.getInt("iddepartment");
            }

            System.out.println("idDepartment = " + idDepartment);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection1 = getDbConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement( "SELECT positionName from positions WHERE id_department = ?" )) {

            preparedStatement1.setInt(1, idDepartment);

            ResultSet rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                String positionName = rs.getString("positionName");
                positions.add(positionName);
                System.out.println("positionName = " + positionName);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return positions;
    }

    public List<String> getAllEmployeesLastName(){

        List<String> employeesLastName = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT last_name from employees"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String employeeLastName = resultSet.getString("last_name");

                //теперь создали объект techniques и поместили туда считанные данные
                employeesLastName.add(employeeLastName);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return employeesLastName;
    }

    public List<String> getAllEmployeesSick(){

        List<String> employeeSick = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT employees.last_name from sickleave JOIN employees on sickleave.employee = employees.idemployee"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String employeeLastName = resultSet.getString("last_name");

                //теперь создали объект techniques и поместили туда считанные данные
                employeeSick.add(employeeLastName);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return employeeSick;
    }

    public List<String> getAllEmployeesHoliday(){

        List<String> employeeVacation = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT employees.last_name from holidays JOIN employees on holidays.employee = employees.idemployee"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String employeeLastName = resultSet.getString("last_name");

                //теперь создали объект techniques и поместили туда считанные данные
                employeeVacation.add(employeeLastName);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return employeeVacation;
    }


    public void updateUser(User userr) throws IOException { // !!!!!!!!!!!!!
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update users JOIN statususer ON users.statusUser=statususer.idstatus set name_user = ?, login_user = ?, password_user = ?, statusUser = (Select idstatus from statususer where statusName = ?) where iduser = ?")) { //не правильный statusUser (в колонке прописано "пользователь", а не 0 либо 1

            preparedStatement.setString(1, userr.getName());
            preparedStatement.setString(2, userr.getLogin());
            preparedStatement.setString(3, userr.getPassword());
            preparedStatement.setString(4, userr.getStatus());
            preparedStatement.setInt(5, userr.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке редактирования пользователя возникла ошибка! Введены некорректные данные.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) throws SQLException {

        String employeeID="";

        try(Connection connection1 = getDbConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement( "SELECT * FROM employees WHERE last_name = ?" )) {

            preparedStatement1.setString(1, employee.getLast_name());

            ResultSet rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                System.out.println("rs = " + rs.getRow() + ". " + "id = " + rs.getString("idemployee"));
                employeeID = rs.getString("idemployee");
            }

            System.out.println("employeeID = " + employeeID);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = getDbConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update employees set first_name = ?, last_name = ?, patronymic = ?, " +
                             "position=(Select idposition from positions where positionName = ?), " +
                             "experience = ?, startWork = ?, telephone = ?, email = ?, timeWork = ? WHERE idemployee = ?")) {;
            preparedStatement.setString(1, employee.getFirst_name());
            preparedStatement.setString(2, employee.getLast_name());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setString(4, employee.getPosition());
            //preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.setInt(5, employee.getExperience());
            preparedStatement.setString(6, employee.getStartWork());
            preparedStatement.setString(7, employee.getTelephone());
            preparedStatement.setString(8, employee.getEmail());
            preparedStatement.setInt(9, employee.getTimeWork());
            preparedStatement.setString(10, employeeID);

            System.out.println("first name = " + employee.getFirst_name());

            System.out.println("last name = " + employee.getLast_name());
            System.out.println("position = " + employee.getPosition());
            System.out.println("employeeID = " +employeeID);

            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeDepartment(Employee employee) throws SQLException {

        String employeeID="";

        try(Connection connection1 = getDbConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement( "SELECT * FROM employees WHERE last_name = ?" )) {

            preparedStatement1.setString(1, employee.getLast_name());

            ResultSet rs = preparedStatement1.executeQuery();
            while (rs.next()) {
                System.out.println("rs = " + rs.getRow() + ". " + "id = " + rs.getString("idemployee"));
                employeeID = rs.getString("idemployee");
            }

            System.out.println("employeeID = " + employeeID);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = getDbConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update employees set position=(Select idposition from positions where positionName = ?) WHERE idemployee = ?")) {;
            preparedStatement.setString(1, employee.getPosition());
            preparedStatement.setString(2, employeeID);

            System.out.println("position = " + employee.getId());
            System.out.println("position = " + employee.getPosition());
            System.out.println("employeeID = " +employeeID);

            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByID(int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from users where iduser = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить пользователя.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployeeByID (int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from employees where idemployee = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить сотрудника.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteSickByID (int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from sickleave where idsickLeave = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить больничный.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteVacationByID (int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from holidays where idholiday = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить больничный.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


