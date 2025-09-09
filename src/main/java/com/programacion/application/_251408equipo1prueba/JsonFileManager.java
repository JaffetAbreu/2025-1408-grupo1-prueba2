package com.programacion.application._251408equipo1prueba;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonFileManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JsonFileManager() {}

    public static <T> boolean saveToJson(List<T> data, String filename) {
        try {
            createParentDirectoryIfNeeded(filename);

            try (FileWriter writer = new FileWriter(filename)) {
                gson.toJson(data, writer);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error al guardar JSON: " + e.getMessage());
            return false;
        }
    }

    public static <T> List<T> loadFromJson(String filename, Type typeOfT) {
        try {
            if (!Files.exists(Paths.get(filename))) {
                return null;
            }

            try (FileReader reader = new FileReader(filename)) {
                return gson.fromJson(reader, typeOfT);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar JSON: " + e.getMessage());
            return null;
        }
    }

    public static <T> boolean saveObjectToJson(T object, String filename) {
        try {
            createParentDirectoryIfNeeded(filename);

            try (FileWriter writer = new FileWriter(filename)) {
                gson.toJson(object, writer);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error al guardar objeto JSON: " + e.getMessage());
            return false;
        }
    }

    public static <T> T loadObjectFromJson(String filename, Class<T> classOfT) {
        try {
            if (!Files.exists(Paths.get(filename))) {
                return null;
            }

            try (FileReader reader = new FileReader(filename)) {
                return gson.fromJson(reader, classOfT);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar objeto JSON: " + e.getMessage());
            return null;
        }
    }

    public static <T> String toJsonString(T object) {
        return gson.toJson(object);
    }

    public static <T> T fromJsonString(String jsonString, Class<T> classOfT) {
        try {
            return gson.fromJson(jsonString, classOfT);
        } catch (Exception e) {
            System.err.println("Error al convertir JSON string: " + e.getMessage());
            return null;
        }
    }

    private static void createParentDirectoryIfNeeded(String filename) {
        try {
            java.nio.file.Path filePath = java.nio.file.Paths.get(filename);
            java.nio.file.Path parentDir = filePath.getParent();

            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
        } catch (IOException e) {
            System.err.println("Error al crear directorio: " + e.getMessage());
        }
    }

    public static boolean jsonFileExists(String filename) {
        return Files.exists(Paths.get(filename));
    }
}