package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad para leer listas desde archivos JSON.
 */
public class JsonUtils {

    private static final Gson gson = new Gson();

    /**
     * Lee una lista de objetos desde un archivo JSON.
     *
     * @param <T>          Tipo de objetos en la lista.
     * @param filePath     Ruta al archivo JSON.
     * @param propertyName Nombre de la propiedad que contiene la lista.
     * @param listType     Tipo de la lista (TypeToken).
     * @return Lista de objetos de tipo T, o lista vacía si hay error.
     */
    public static <T> List<T> leerListaDesdeJson(String filePath, String propertyName, Type listType) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            if (jsonObject != null && jsonObject.has(propertyName)) {
                JsonArray jsonArray = jsonObject.getAsJsonArray(propertyName);
                List<T> result = gson.fromJson(jsonArray, listType);
                return result != null ? result : new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Escribe una lista de objetos en un archivo JSON, envolviéndola en una
     * propiedad.
     *
     * @param <T>          Tipo de objetos en la lista.
     * @param filePath     Ruta al archivo JSON.
     * @param propertyName Nombre de la propiedad que contendrá la lista.
     * @param list         Lista de objetos a guardar.
     */
    public static <T> void escribirListaEnJson(String filePath, String propertyName, List<T> list) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add(propertyName, gson.toJsonTree(list));
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}