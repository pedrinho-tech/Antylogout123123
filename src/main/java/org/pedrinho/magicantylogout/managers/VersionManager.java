package org.pedrinho.magicantylogout.managers;

import org.pedrinho.magicantylogout.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionManager {

    public boolean checkVersion() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/MAGICSOLUTIONS-PL/Antylogout/main/version.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String version = reader.readLine();
            reader.close();
            connection.disconnect();

            System.out.println("Pobrana wersja: " + version);


            if (version.equals(getActualVersion())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLatestVersion() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/MAGICSOLUTIONS-PL/Antylogout/main/version.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String version = reader.readLine();
            reader.close();
            connection.disconnect();

            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getActualVersion() {
        return Main.version;
    }
}
