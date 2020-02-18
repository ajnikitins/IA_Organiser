// TODO: https://stackoverflow.com/questions/1119385

package com.organiser.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import com.organiser.file.util.TimeMachine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IOHandlerTest {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\test.json";

  @BeforeEach
  void setUp() {
    try {
      Files.deleteIfExists(Paths.get(PATH_NAME));
    } catch (IOException e) {
      fail("Unexpected IO Exception");
    }
  }

  @Test
  void loadObjectNonExistentFile() {
    var system = IOHandler.loadObject(PATH_NAME, FileSystem.class);
    assertEquals(new FileSystem(), system);
  }

  @Test
  void writeAndLoadObject() {
    var fileSystem = new FileSystem();
    Order order = new Order("Ad page", "Latv. val", "", TimeMachine.now().plusDays(10));
    fileSystem.addOrder(order);
    order.addTask(new Task("Print paper", "Many", 5));
    IOHandler.writeObject(PATH_NAME, fileSystem);
    assertEquals(fileSystem, IOHandler.loadObject(PATH_NAME, FileSystem.class));
  }
}