package com.ratedpower.challenge.task;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskGraphTest {

  private TaskGraph systemUnderTest = new TaskGraph();
  
  @BeforeEach
  public void clearSetup() {
    systemUnderTest = new TaskGraph();
  }
  
  @Test
  public void shouldAlwaysPickupTaskWithoutDependenciesAndMaxCostOfDelay() {
    // Given
    Task taskA = new Task(0, "A", 8);
    Task taskB = new Task(1, "B", 6);
    Task taskC = new Task(2, "C", 4);
    Task taskD = new Task(3, "D", 2);
    Task taskE = new Task(4, "E", 1);

    systemUnderTest.addTask(taskA);
    systemUnderTest.addTask(taskB);
    systemUnderTest.addTask(taskC);
    systemUnderTest.addTask(taskD);
    systemUnderTest.addTask(taskE);

    systemUnderTest.addDependency(taskA, taskC);
    systemUnderTest.addDependency(taskA, taskD);
    systemUnderTest.addDependency(taskB, taskD);
    systemUnderTest.addDependency(taskD, taskE);

    // When
    Optional<List<Task>> actualValue = systemUnderTest.topologicalSort();

    // Then
    List<Task> expectedValue = Arrays.asList(taskA, taskB, taskC, taskD, taskE);

    assert actualValue.isPresent();

    actualValue.ifPresent(
        (topologicalSort) -> {
          for (int i = 0; i < topologicalSort.size(); i++) {
            assert topologicalSort.get(i).equals(expectedValue.get(i));
          }
        });
  }
  
  @Test
  public void shouldReturnNullIfGraphIsCyclic() {
    // Given
    Task taskA = new Task(0, "A", 8);
    Task taskB = new Task(1, "B", 6);
    Task taskC = new Task(2, "C", 4);
    
    systemUnderTest.addTask(taskA);
    systemUnderTest.addTask(taskB);
    systemUnderTest.addTask(taskC);
    
    systemUnderTest.addDependency(taskA, taskB);
    systemUnderTest.addDependency(taskB, taskC);
    systemUnderTest.addDependency(taskC, taskA);
    
    // When
    Optional<List<Task>> actualValue = systemUnderTest.topologicalSort();
    
    // Then
    assert actualValue.isEmpty();
  }
}
