package com.zalktis.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class FileSystemTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private FileSystem fileSystem;

  @BeforeEach
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
    fileSystem = new FileSystem();
  }

  @Test
  @Order(1)
  void load() {
    fileSystem = FileSystem.load();
    assertNotNull(fileSystem);
  }

  @Test
  @Order(2)
  void addAndGetOrderWithRawData() {
    fileSystem.addOrder("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
    assertEquals(1, fileSystem.getOrders().size());
  }

  @Test
  @Order(3)
  void findOrderByID() {
    fileSystem.addOrder("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
    assertEquals(fileSystem.getOrders().get(0), fileSystem.findOrderByID(0));
  }

  @Test
  @Order(4)
  void addAndGetTask() {
    fileSystem.addOrder("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
    fileSystem.addTask(0, "Print paper", "none", 3);
    assertEquals( 1, fileSystem.getTasks().size());
  }

  @Test
  @Order(5)
  void findTaskByID() {
    fileSystem.addOrder("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
    fileSystem.addTask(0, "Print paper", "none", 3);
    assertEquals(fileSystem.getTasks().get(0), fileSystem.findTaskByID(0, 0));
  }

  @Test
  @Order(5)
  void getImminentTasks() {
    fileSystem.addOrder("Notebooks", "VELVE", "Red and blue", TimeMachine.now().plusDays(4));
    fileSystem.addTask(0, "Order forms", "1x1", 4);

    assertEquals(1, fileSystem.getImminentTasks().size());
  }

  @Test
  @Order(6)
  void removeOrder() {
    fileSystem.addOrder("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));

    fileSystem.removeOrder(0);
    assertEquals(0, fileSystem.getOrders().size());
  }

  @Test
  @Order(7)
  void removeTask() {
    fileSystem.addOrder("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
    fileSystem.addTask(0, "Order forms", "1x1", 4);

    fileSystem.removeTask(1, 0);
    assertEquals(0, fileSystem.getTasks().size());
  }

  @AfterEach
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}