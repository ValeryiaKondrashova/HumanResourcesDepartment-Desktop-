package hr.hr.server;

import hr.hr.singleton.ProgramLogger;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {

        ProgramLogger.getProgramLogger().addLogInfo("\n\n\n=============== Запуск сервера... (Переход в ServerApplication) ================");
          new ServerApplication().run();
    }
}
