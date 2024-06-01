package com.ratedpower.challenge.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Implementation based on https://www.geeksforgeeks.org/topological-sorting/
 * @author quentin.vaucher
 */
public class TaskGraph {
  private final Map<Task, List<Task>> adjacencyList;

  public TaskGraph() {
    adjacencyList = new HashMap<>();
  }

  public void addTask(Task task) {
    adjacencyList.put(task, new ArrayList<>());
  }

  public void addDependency(Task independentTask, Task dependentTask) {
    adjacencyList.get(independentTask).add(dependentTask);
  }

  public Optional<List<Task>> topologicalSort() {
    int count = 0;
    int accumulatedCost = 0;
    int[] dependencies = new int[adjacencyList.size()];
    Queue<Task> queue = new PriorityQueue<>(adjacencyList.size(), new TaskComparator());
    List<Task> topologicalOrder = new ArrayList<>();

    // 1. Count dependencies for each task
    for (List<Task> neighbors : adjacencyList.values()) {
      for (Task neighbor : neighbors) {
        dependencies[neighbor.id()]++;
      }
    }

    // 2. Initialize queue with tasks without dependencies
    adjacencyList
        .keySet()
        .forEach(
            (task) -> {
              if (dependencies[task.id()] == 0) {
                queue.add(task);
              }
            });

    // 3. iterate until all tasks are ordered
    while (!queue.isEmpty()) {
      Task task = queue.poll();
      topologicalOrder.add(task);

      count++;
      for (Task t : queue) {
        accumulatedCost += t.costOfDelay();
      }

      adjacencyList
          .get(task)
          .forEach(
              (dependent) -> {
                if (--dependencies[dependent.id()] == 0) {
                  queue.add(dependent);
                }
              });
    }

    if (count != adjacencyList.size()) {
      System.out.println("Graph contains cycle");
      return Optional.empty();
    }

    // 4. print results
    System.out.print("Scheduling order: ");
    for (int i = 0; i < adjacencyList.size(); i++) {
      System.out.print(topologicalOrder.get(i).name());
      if (i != adjacencyList.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();

    System.out.println("Accumulated cost: " + accumulatedCost);

    return Optional.of(topologicalOrder);
  }
}
