package Configurations;

import Event.Events;
import com.google.gson.Gson;

import java.io.FileReader;
import java.nio.file.Paths;

public class AppSettings {
    public String telegramToken;
    public String telegramBotName;
    public String databaseConnectionUrl;

    private static AppSettings instance;

    public static AppSettings getInstance()
    {
        if (instance != null)
            return instance;

        Gson gson = new Gson();
        String filePath = Paths.get(Paths.get("").toAbsolutePath().toString(), "appsettings.json").toString();
        try (FileReader reader = new FileReader(filePath)) {
            instance = gson.fromJson(reader, AppSettings.class);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AppSettings(){}
}
