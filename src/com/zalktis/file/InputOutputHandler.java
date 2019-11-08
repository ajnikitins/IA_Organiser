// https://geeksforgeeks.org/serialization-in-java

package com.zalktis.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InputOutputHandler {

  public static Object loadObject(String filename) {
    var file = new File(filename);

    if (!file.isFile()) {
      System.out.println("Error: file not found; creating blank...");
      writeObject(filename, null);
      return null;
    }

    try {
      var fileStream = new FileInputStream(file);
      var in = new ObjectInputStream(fileStream);

      Object object = in.readObject();

      in.close();
      fileStream.close();

      return object;
    } catch (IOException e) {
      System.out.println("Can't access file; exiting...");
      return null;
    } catch (ClassNotFoundException e) {
      System.out.println("Invalid file; exiting...");
      return null;
    }
  }

  public static void writeObject(String filename, Object object) {
    File file = new File(filename);

    // Create parent directories if they do not exist
    if (!file.getParentFile().isDirectory()) {
      file.getParentFile().mkdirs();
    }

    try {
      var fileStream = new FileOutputStream(filename);
      var out = new ObjectOutputStream(fileStream);

      out.writeObject(object);

      out.close();
      fileStream.close();
    } catch (IOException e) {
      System.out.println("Failed to write file");
      e.printStackTrace();
    }
  }
}
