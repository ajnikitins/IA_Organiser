// https://geeksforgeeks.org/serialization-in-java

package com.zalktis.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class IOHandler {

  private IOHandler() {}

  public static <T> T loadObject(String filename, Class<T> type) {
    var file = new File(filename);

    try {
      var objectMapper = new ObjectMapper();

      return objectMapper.readValue(file, type);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Can't access file; exiting; writing blank file");

      try {
        T object = type.getConstructor().newInstance();
        writeObject(filename, object);
        return object;
      } catch (NoSuchMethodException ex) {
        ex.printStackTrace();
        System.out.println("Can't find null constructor for class");
        return null;
      } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
        ex.printStackTrace();
        System.out.println("Failed to instantiate class");
        return null;
      }
    }
  }

  public static void writeObject(String filename, Object object) {
    File file = new File(filename);

    // Create parent directories if they do not exist
    if (!file.getParentFile().isDirectory()) {
      if (!file.getParentFile().mkdirs()) {
        System.out.println("Failed to create directories");
        return;
      };
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
