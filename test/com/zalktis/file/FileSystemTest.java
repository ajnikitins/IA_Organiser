// TODO: Update getImminentTasks with national holidays

package com.zalktis.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zalktis.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class FileSystemTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private FileSystem fileSystem;

  @BeforeAll
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
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
  @Order(4)
  void findOrderByID() {
    assertEquals(fileSystem.getOrders().get(0), fileSystem.findOrderByID(0));
  }

  @Test
  @Order(5)
  void addAndGetTask() {
    fileSystem.addTask(0, "Print paper", "none", 3);
    assertEquals( 1, fileSystem.getTasks().size());
  }

  @Test
  @Order(6)
  void findTaskByID() {
    assertEquals(fileSystem.getTasks().get(0), fileSystem.findTaskByID(0, 0));
  }

  @Test
  @Order(7)
  void getImminentTasks() {
    fileSystem.addOrder("Notebooks", "VELVE", "Red and blue", TimeMachine.now().plusDays(4));
    fileSystem.addTask(1, "Order forms", "1x1", 4);

    assertEquals(1, fileSystem.getImminentTasks().size());
  }

  @Test
  @Order(8)
  void removeOrder() {
    fileSystem.removeOrder(0);
    assertEquals(1, fileSystem.getOrders().size());
  }

  @Test
  @Order(9)
  void removeTask() {
    fileSystem.removeTask(1, 0);
    assertEquals(0, fileSystem.getTasks().size());
  }

  @AfterAll
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}