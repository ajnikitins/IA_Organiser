package com.zalktis.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
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

  private FileSystem fileSystem;
  private com.zalktis.file.obj.Order order;

  @BeforeAll
  void setUp() {
    order = new com.zalktis.file.obj.Order("Ad page", "Latv. val", "none", LocalDate.of(2020, 12, 5));
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
    fileSystem.addOrder("Ad page", "Latv. val", "none", LocalDate.of(2020, 12, 5));
    assertEquals(1, fileSystem.getOrders().size());
  }

  @Test
  @Order(3)
  void addAndGetOrderWithExisting() {
    fileSystem.addOrder(order);
    assertEquals(2, fileSystem.getOrders().size());
  }

  @Test
  @Order(4)
  void findOrderByID() {
    assertEquals(order, fileSystem.findOrderByID(1));
  }

  @Test
  @Order(5)
  void addAndGetTask() {
    fileSystem.addTask(fileSystem.findOrderByID(1), "Print paper", "none", 5);
    assertEquals(1, fileSystem.getTasks().size());
  }

  @Test
  @Order(6)
  void findTaskByID() {
    assertEquals(fileSystem.getTasks().get(0), fileSystem.findTaskByID(1, 0));
  }

  @Test
  @Order(7)
  void removeOrder() {
    fileSystem.removeOrder(0);
    assertEquals(1, fileSystem.getOrders().size());
  }

  @Test
  @Order(8)
  void removeTask() {
    fileSystem.removeTask(1, 0);
    assertEquals(0, fileSystem.getTasks().size());
  }
}