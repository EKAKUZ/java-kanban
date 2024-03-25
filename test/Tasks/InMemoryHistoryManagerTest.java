package Tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private static HistoryManager historyManager;
    @BeforeEach
     void createEnvironment() {
        historyManager = Managers.getDefaultHistory();
    }
    @Test
    void addTaskToHistory() {
        Task task1 = new Task("Задача1", "Описание задачи1", TaskStatus.NEW);

        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");

        task1.setDescription("Описание задачи1_1");
        historyManager.add(task1);
        assertEquals(history.getFirst(), task1, "Task не совпадают");
        assertNotEquals(history.getFirst().getDescription(), task1.getDescription(), "Описания Task совпадают");
        assertEquals(history.getLast().getDescription(), task1.getDescription(), "Описания Task не совпадают");

        task1.setStatus(TaskStatus.IN_PROGRESS);
        historyManager.add(task1);
        assertEquals(history.get(1), task1, "Task не совпадают");
        assertNotEquals(history.get(1).getStatus(), task1.getStatus(), "Статусы Task совпадают");
        assertEquals(history.getLast().getStatus(), task1.getStatus(), "Статусы Task не совпадают");

        task1.setId(1);
        historyManager.add(task1);
        assertNotEquals(history.get(2), task1, "Task совпадают");
        assertNotEquals(history.get(2).getId(), task1.getId(), "Статусы Id совпадают");

        Task task2 = new Task("Задача2", "Описание задачи2", 1, TaskStatus.DONE);
        historyManager.add(task2);

        assertEquals(history.getLast(), task2, "Task не совпадают");
        assertNotEquals(history.getLast().getDescription(), task1.getDescription(), "Описания Task совпадают");
        assertNotEquals(history.getLast().getStatus(), task1.getStatus(), "Статусы Task совпадают");
        assertNotEquals(history.getLast().getName(), task1.getName(), "Имена Task совпадают");

    }

    @Test
    void addSubTaskToHistory() {
        SubTask task1 = new SubTask("Задача1", "Описание задачи1", TaskStatus.NEW);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");

        task1.setEpicId(1000);
        historyManager.add(task1);
        SubTask historyFirstTask = (SubTask) history.getFirst();
        SubTask historySecondTask = (SubTask) history.getLast();
        assertEquals(historySecondTask, task1, "SubTask не совпадают");
        assertNotEquals(historyFirstTask.getEpicId(), task1.getEpicId(), "epicId SubTask совпадают");
        assertEquals(historySecondTask.getEpicId(), task1.getEpicId(), "epicId SubTask не совпадают");
    }

    @Test
    void addEpicToHistory() {
        Epic task1 = new Epic("Задача1", "Описание задачи1");
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");

        task1.addSubTaskId(1);
        historyManager.add(task1);

        Epic historyFirstTask = (Epic) history.getFirst();
        Epic historySecondTask = (Epic) history.getLast();
        assertEquals(historySecondTask, task1, "Epic не совпадают");

        assertNotEquals(historyFirstTask.getArraySubTaskId().size(), task1.getArraySubTaskId().size(), "Массивы Id SubTask в Epic совпадают");
        assertArrayEquals(historySecondTask.getArraySubTaskId().toArray(), task1.getArraySubTaskId().toArray(), "Массивы Id SubTask в Epic не совпадают");
    }
}