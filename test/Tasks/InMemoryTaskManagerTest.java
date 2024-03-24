package Tasks;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static TaskManager taskManager;
    @BeforeEach
    void createEnvironment() {
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldNotAddEpicLikeSubTaskToEpic() {
        Task epic1 = new Epic("Эпик1", "Описание эпик1");
        Task epic2 = new Epic("Эпик2", "Описание эпик2");
        taskManager.addEpic((Epic) epic1);
        assertThrows(Exception.class, () -> taskManager.addSubTask((SubTask) epic1),
                "Попытка добавить Epic в тот жес Epic вызывет исключение");
        assertThrows(Exception.class, () -> taskManager.addSubTask((SubTask) epic2),
                "Попытка добавить Epic в Epic вызывет исключение");
    }

    @Test
    void shouldNotAddSubTaskToSubTaskLikeEpic() {
        Task subTask1 = new SubTask("Подзадача1", "Описание подзадачи1", TaskStatus.NEW);
        ((SubTask) subTask1).setEpicId(subTask1.getId());
        taskManager.addSubTask((SubTask) subTask1);
        assertNull(taskManager.getSubTaskById(subTask1.getId()),
                "SubTask не должна была добавиться в TaskManager ");

        Task epic1 = new Epic("Эпик1", "Описание эпик1");
        taskManager.addEpic((Epic) epic1);
        ((SubTask) subTask1).setEpicId(epic1.getId());
        taskManager.addSubTask((SubTask) subTask1);
        Task subTask2 = new SubTask("Подзадача2", "Описание подзадачи2",TaskStatus.NEW);
        ((SubTask) subTask2).setEpicId(subTask1.getId());
        int subTaskCount = taskManager.getSubTasks().size();
        taskManager.addSubTask((SubTask) subTask2);
        assertEquals(subTaskCount, taskManager.getSubTasks().size(),
                "SubTask не должна была добавиться в TaskManager ");
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        taskManager.addTask(task);

        final Task savedTask = taskManager.getTaskById(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void checkTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        taskManager.addTask(task);

        final Task savedTask = taskManager.getTaskById(task.getId());

        assertEquals(task.getName(), savedTask.getName(), "Имена задач не совпадают.");
        assertEquals(task.getDescription(), savedTask.getDescription(), "Описания задач не совпадают.");
        assertEquals(task.getStatus(), savedTask.getStatus(), "Статусы задач не совпадают.");
    }

    @Test
    void addNewSubTask() {
        Epic epic = new Epic("Test Epic", "Test Epic description");
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                TaskStatus.NEW);
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);

        SubTask savedSubTask = taskManager.getSubTaskById(subTask.getId());
        assertNull(savedSubTask, "Задача найдена.");

        taskManager.addEpic(epic);
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        savedSubTask = taskManager.getSubTaskById(subTask.getId());

        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test Epic", "Test addNewEpic description");
        taskManager.addEpic(epic);
        Epic savedepic = taskManager.getEpicById(epic.getId());

        assertNotNull(savedepic, "Задача не найдена.");
        assertEquals(epic, savedepic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void changeIdInsideManager() {
        Epic epic = new Epic("Test Epic", "Test addNewEpic description", 70);
        taskManager.addEpic(epic);
        Epic savedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(savedEpic, "Задача не найдена.");
        assertNotEquals(70, epic.getId(), "При добавлении Epic с установленным Id, Id не изменился");

        Task task = new Task("Test addNewTask", "Test addNewTask description", 80, TaskStatus.NEW);
        taskManager.addTask(task);
        Task savedTask = taskManager.getTaskById(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertNotEquals(80, task.getId(), "При добавлении Task с установленным Id, Id не изменился");

        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", 90,
                TaskStatus.NEW);
        taskManager.addSubTask(subTask);
        SubTask savedSubTask = taskManager.getSubTaskById(subTask.getId());
        assertNull(savedSubTask, "Задача найдена.");

        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        savedSubTask = taskManager.getSubTaskById(subTask.getId());
        assertNotNull(savedSubTask, "Задача не найдена.");
        assertNotEquals(90, subTask.getId(), "При добавлении Task с установленным Id, Id не изменился");

    }



}