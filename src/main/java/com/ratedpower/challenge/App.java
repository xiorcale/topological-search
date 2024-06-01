package com.ratedpower.challenge;

import com.ratedpower.challenge.task.Task;
import com.ratedpower.challenge.task.TaskGraph;

public class App {
  public static void main(String[] args) {
    TaskGraph taskGraph = new TaskGraph();

    createGraph(taskGraph);

    taskGraph.topologicalSort();
  }

  public static void createGraph(TaskGraph taskGraph) {
    Task taskA = new Task(0, "A", 8);
    Task taskB = new Task(1, "B", 6);
    Task taskC = new Task(2, "C", 4);
    Task taskD = new Task(3, "D", 2);
    Task taskE = new Task(4, "E", 1);

    taskGraph.addTask(taskA);
    taskGraph.addTask(taskB);
    taskGraph.addTask(taskC);
    taskGraph.addTask(taskD);
    taskGraph.addTask(taskE);

    taskGraph.addDependency(taskA, taskC);
    taskGraph.addDependency(taskA, taskD);
    taskGraph.addDependency(taskB, taskD);
    taskGraph.addDependency(taskD, taskE);
  }
}
