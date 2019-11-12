// TODO: https://stackoverflow.com/questions/1119385

package com.zalktis.file;

import static org.junit.jupiter.api.Assertions.*;

import com.zalktis.file.obj.Order;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputOutputHandlerTest {

  private static final String PATH_NAME = System.getProperty("user.home")  + "\\AppData\\Roaming\\Organiser\\test.json";

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
    System.setErr(originalErr);
    System.out.println(outContent);
    System.err.println(errContent);
  }

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