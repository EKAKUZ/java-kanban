import TZ4.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача1", "Описание задачи1", TaskStatus.NEW);
        Task task2 = new Task("Задача2", "Описание задачи2", TaskStatus.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("Эпик1", "Описание эпик1");
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи1", TaskStatus.IN_PROGRESS);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадачи2", TaskStatus.DONE);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(epic1.getId(),subTask1);
        taskManager.addSubTask(epic1.getId(),subTask2);


        Epic epic2 = new Epic("Эпик2", "Описание эпик2");
        SubTask subTask3= new SubTask("Подзадача 3", "Описание подзадачи3", TaskStatus.NEW);
        taskManager.addEpic(epic2);
        taskManager.addSubTask(epic2.getId(), subTask3);

        System.out.println(taskManager.getTasksList());
        System.out.println(taskManager.getEpicsList());
        System.out.println(taskManager.getSubTasksList());

        System.out.println("-------------------------------");

        task1 = new Task("Задача1", "Описание задачи1", task1.getId(), TaskStatus.DONE);
        subTask1 = new SubTask("Подзадача 1", "Описание подзадачи1", subTask1.getId(), TaskStatus.IN_PROGRESS);
        epic2 = new Epic("Эпик2", "Описание эпик2", epic2.getId(), epic2.getArraySubTaskId());

        taskManager.updateTask(task1);
        taskManager.updateSubTask(subTask1);
        taskManager.updateEpic(epic2);

        System.out.println(taskManager.getTasksList());
        System.out.println(taskManager.getEpicsList());
        System.out.println(taskManager.getSubTasksList());

        System.out.println("-------------------------------");

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic2.getId());
        taskManager.deleteSubTaskById(subTask2.getId());

        System.out.println(taskManager.getTasksList());
        System.out.println(taskManager.getEpicsList());
        System.out.println(taskManager.getSubTasksList());
    }
}
