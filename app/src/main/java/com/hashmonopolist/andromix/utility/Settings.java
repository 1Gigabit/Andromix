package com.hashmonopolist.andromix.utility;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Settings {
    @Expose
    String arl;
    @Expose
    String server;
    File file;

    public Settings(File file) {
        this.file = file;
    }

    public void saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            String json = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .toJson(this);
            fileWriter.write(json);
            System.out.println(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadFromFile() {
        try {
            FileReader fileReader = new FileReader(file);
            Scanner reader = new Scanner(fileReader);
            if(reader.hasNextLine()) {
                Settings settings = new GsonBuilder().create().fromJson(reader.nextLine(),Settings.class);
                this.setArl(settings.arl);
                this.setServer(settings.server);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Settings{" +
                "arl='" + arl + '\'' +
                ", server='" + server + '\'' +
                '}';
    }

    public String getArl() {
        return this.arl;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setArl(String arl) {
        this.arl = arl;
    }
}
