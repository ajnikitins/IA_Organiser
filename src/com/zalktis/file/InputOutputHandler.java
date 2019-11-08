// https://geeksforgeeks.org/serialization-in-java

package com.zalktis.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class InputOutputHandler {

  public static <T> T loadObject(String filename, Class<T> type) {
    var file = new File(filename);

//    if (!file.isFile()) {
//      System.out.println("Error: file not found; creating blank...");
//      writeObject(filename, null);
//      return null;
//    }

    try {
      var objectMapper = new ObjectMapper();

      return objectMapper.readValue(file, type);
    } catch (IOException e) {
      System.out.println("Can't access file; exiting...");
      return null;
    }
  }

  public static void writeObject(String filename, Object object) {
    File file = new File(filename);

    // Create parent directories if they do not exist
//    if (!file.getParentFile().isDirectory()) {
//      file.getParentFile().mkdirs();
//    }

    try {
      var objectMapper = new ObjectMapper();

      objectMapper.writeValue(file, object);
    } catch (IOException e) {
      System.out.println("Failed to write file");
      e.printStackTrace();
    }
  }
}
