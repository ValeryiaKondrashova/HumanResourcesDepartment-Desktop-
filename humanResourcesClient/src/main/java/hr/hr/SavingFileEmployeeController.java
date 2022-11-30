package hr.hr;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import hr.hr.common.ConnectionTCP;
import hr.hr.entity.Command;
import hr.hr.entity.Employee;

public class SavingFileEmployeeController {
    private ConnectionTCP connectionTCP;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_saveOtchet;

    @FXML
    private TextField field_nameFile;

    @FXML
    private TextField field_unCorrect_nameFile;

    @FXML
    void SaveFile(ActionEvent event) throws IOException {

        String fileName;
        Boolean q,w,o,r,t,y,u,p;
        fileName = field_nameFile.getText();

        q=fileName.contains("/");
        w =fileName.contains("\"");
        o=fileName.contains(":");
        r=fileName.contains("*");
        t=fileName.contains("<");
        y=fileName.contains(">");
        u=fileName.contains("|");
        p=fileName.contains("?");

        if(q==true ||w==true || p==true || r==true || t==true || y==true|| u==true || o==true){

            System.out.println(q+ " " + w +" " +  o + " " + r + " " + t + " " + y + " " + u + " " +  p);
            field_unCorrect_nameFile.setText("Некорректное имя файла!");
            field_unCorrect_nameFile.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            return;
        }
        else{
            field_unCorrect_nameFile.setText("Файл успешно сохранен!");
            field_unCorrect_nameFile.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            fileName+=".txt";
        }


        try {
            connectionTCP = ConnectionTCP.getInstance();  // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        connectionTCP.writeObject(Command.READ1);
        List<Employee> allEmployee = (List<Employee>) connectionTCP.readObject();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);
            for (int i = 0; i < allEmployee.size(); i++) {

                String first_name = allEmployee.get(i).getFirst_name();
                String last_name = allEmployee.get(i).getLast_name();
                String patronymic = allEmployee.get(i).getPatronymic();
                String  position = allEmployee.get(i).getPosition();
                int experience = allEmployee.get(i).getExperience();
                String startWork = allEmployee.get(i).getStartWork();
                String telephone = allEmployee.get(i).getTelephone();
                String email = allEmployee.get(i).getEmail();
                int timeWork = allEmployee.get(i).getTimeWork();

                fileWriter.write(
                        "Фамилия : " + first_name + "\n" +
                        "\tИмя : " + last_name + "\n" +
                        "\tОтчество : " + patronymic + "\n" +

                        "\tДолжность : " + position + "\n" +
                        "\tОпыт работы : " + experience + "\n" +
                        "\tНачало работы : " + startWork + "\n" +
                        "\tТелефон : " + telephone + "\n" +
                        "\tЭл.почта : " + email + "\n" +

                        "\tЧасов работы : " + timeWork + ".\n\n");


            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
