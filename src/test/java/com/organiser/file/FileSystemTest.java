package com.organiser.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.organiser.file.obj.Order;
import com.organiser.file.obj.Task;
import com.organiser.file.util.TimeMachine;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSystemTest {

  private static final LocalDate LOCAL_DATE = LocalDate.of(2020, 12, 14);

  private FileSystem fileSystem;

  @BeforeEach
  void setUp() {
    TimeMachine.useFixedClockAt(LOCAL_DATE);
    fileSystem = new FileSystem();
  }

  @Test
  void load() {
    fileSystem = FileSystem.load();
    assertNotNull(fileSystem);
  }

  @Test
  void addAndFindOrderByID() {
    fileSystem.addOrder(new Order("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7)));
    assertEquals(fileSystem.getOrders().get(0), fileSystem.findOrderByID(0));
  }

  @Test
  void getImminentTasks() {
    Order order = new Order("Notebooks", "VELVE", "Red and blue", TimeMachine.now().plusDays(4));
    fileSystem.addOrder(order);
    order.addTask(new Task("Order forms", "1x1", 4));

    assertEquals(1, fileSystem.getImminentTasks().size());
  }

  @Test
  void getSortedOrders() {
    fileSystem.addOrder(new Order("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7)));
    fileSystem.addOrder(new Order("Notebooks", "VELVE", "Red and blue", TimeMachine.now().plusDays(4)));

    assertTrue(fileSystem.getSortedOrders().get(0).getCompletionDate().isBefore(fileSystem.getSortedOrders().get(1).getCompletionDate()));
  }

  @Test
  void removeOrder() {
    fileSystem.addOrder(new Order("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7)));

    fileSystem.removeOrder(0);
    assertEquals(0, fileSystem.getOrders().size());
  }

  @Test
  void removeTask() {
    Order order = new Order("Ad page", "Latv. val", "none", TimeMachine.now().plusDays(7));
    fileSystem.addOrder(order);
    order.addTask(new Task("Order forms", "1x1", 4));

    order.removeTask(0);
    assertEquals(0, fileSystem.getTasks().size());
  }

  @AfterEach
  void tearDown() {
    TimeMachine.useSystemDefaultZoneClock();
  }
}