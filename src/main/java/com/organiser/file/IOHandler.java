// https://geeksforgeeks.org/serialization-in-java
// TODO: Forward exceptions upwards

package com.organiser.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Contains methods for interacting with the filesystem.
 */
public class IOHandler {

  private IOHandler() {}

  /**
   * Uses Jackson's {@link ObjectMapper} to read a file and extract a Java
   * object of a specified type.
   * @param filename The path to the file.
   * @param type Class of the object to be loaded.
   * @param <T> Type of the object to be loaded.
   * @return An instance of T with field values from the file. If can't read
   * the file and create an empty object null is returned.
   */
  public static <T> T loadObject(String filename, Class<T> type) {
    var file = new File(filename);

    try {
      var objectMapper = new ObjectMapper();
      return objectMapper.readValue(file, type);
    } catch (IOException e) {
      // If the file doesn't exist, an empty file with the same path is created.
      e.printStackTrace();
      System.out.println("Cannot access file; exiting; writing blank file");

      try {
        T object = type.getConstructor().newInstance();
        writeObject(filename, object);
        return object;
      } catch (NoSuchMethodException ex) {
        // If T doesn't have an empty constructor
        ex.printStackTrace();
        System.out.println("Cannot find null constructor for class");
        return null;
      } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
        // Invalid data or other exception
        ex.printStackTrace();
        System.out.println("Failed to instantiate class");
        return null;
      }
    }
  }

  /**
   * Uses Jackson's {@link ObjectMapper} to write a file.
   * @param filename Path of file to write.
   * @param object Object whose contents to write.
   */
  public static void writeObject(String filename, Object object) {
    File file = new File(filename);

    // Create parent directories if they do not exist
    if (!file.getParentFile().isDirectory()) {
      if (!file.getParentFile().mkdirs()) {
        System.out.println("Failed to create directories");
        return;
      }
    }

    try {
      var objectMapper = new ObjectMapper();

      objectMapper.writeValue(file, object);
      System.out.println("Successfully wrote file");
    } catch (IOException e) {
      System.out.println("Failed to write file");
      e.printStackTrace();
    }
  }
}
