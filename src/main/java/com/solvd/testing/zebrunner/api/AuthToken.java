package com.solvd.testing.zebrunner.api;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AuthToken
{
    private static AuthToken instance;

    private String authToken;

    private AuthToken(){}

    public String getAuthToken() {
        return authToken;
    }

    public static synchronized AuthToken getInstance(){
        if(instance == null){
            //Read the auth details from the property file
            FileReader reader= null;
            Properties p = new Properties();
            try {
                reader = new FileReader("src/main/resources/config.properties");
                p.load(reader);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            instance = new AuthToken();
            Path filePath = Paths.get( p.getProperty("api_url"), p.getProperty("auth_endpoint"));
            instance.authToken = RestApiWrapper.getAuthToken(filePath.toString(), p.getProperty("api_token"));

        }
        return instance;
    }

}