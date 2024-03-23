import Tasks.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Задача1", "Описание задачи1", TaskStatus.NEW);
        Task task2 = new Task("Задача2", "Описание задачи2", TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик1", "Описание эпик1");
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи1", TaskStatus.DONE);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи2", TaskStatus.NEW);
        SubTask subTask4 = new SubTask("Подзадача 4", "Описание подзадачи4", TaskStatus.DONE);
        taskManager.addEpic(epic1);
        subTask1.setEpicId(epic1.getId());
        subTask2.setEpicId(epic1.getId());
        subTask4.setEpicId(epic1.getId());
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask4);


        Epic epic2 = new Epic("Эпик2", "Описание эпик2");
        SubTask subTask3= new SubTask("Подзадача 3", "Описание подзадачи3", TaskStatus.NEW);

        taskManager.addEpic(epic2);
        subTask3.setEpicId(epic2.getId());
        taskManager.addSubTask(subTask3);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        System.out.println("-------------------------------");

        task1 = new Task("Задача1", "Описание задачи1", task1.getId(), TaskStatus.DONE);
        subTask3 = new SubTask("Подзадача 3", "Описание подзадачи3", subTask3.getId(), TaskStatus.IN_PROGRESS);
        epic2 = new Epic("Эпик2", "Описание эпик2", epic2.getId());

        taskManager.updateTask(task1);
        taskManager.updateSubTask(subTask3);
        taskManager.updateEpic(epic2);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        System.out.println("-------------------------------");

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic2.getId());
        taskManager.deleteSubTaskById(subTask2.getId());

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        System.out.println("-------------------------------");
        System.out.println("-------------------------------");

        System.out.println(taskManager.getHistory());

        taskManager.getSubTaskById(6);
        printAllTasks(taskManager);

        taskManager.getEpicById(3);
        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("-------------------------------");
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubTask(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
