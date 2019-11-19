// TODO: https://stackoverflow.com/questions/1119385

package com.zalktis.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.zalktis.file.obj.Order;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class InputOutputHandlerTest {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\test.json";

  @Test
  void loadObjectNonExistentFile() {
    try {
      Files.deleteIfExists(Paths.get(PATH_NAME));
    } catch (IOException e) {
      fail("Unexpected IO Exception");
    }
    var system = InputOutputHandler.loadObject(PATH_NAME, FileSystem.class);
    assertEquals(new FileSystem(), system);
  }

  @Test
  void writeAndLoadObject() {
    var system = new FileSystem();
    var order = new Order("Ad page", "Latv. val", "", LocalDate.now().plusDays(10));
    order.addTask("Print paper", "Many", 5);
    system.addOrder(order);
    InputOutputHandler.writeObject(PATH_NAME, system);
    assertEquals(system, InputOutputHandler.loadObject(PATH_NAME, FileSystem.class));
  }
}