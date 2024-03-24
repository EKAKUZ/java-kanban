package Tasks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    private static TaskManager taskManager;
    private static HistoryManager historyManager;
    @BeforeAll
    static void createEnvironment() {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void taskManagerShouldBeInMemoryTaskManager() {
        assertNotNull(taskManager, "TaskManager проинициализирован");
        assertEquals(InMemoryTaskManager.class, taskManager.getClass(), "TaskManager готов к работе");

        assertNotNull(historyManager, "HistoryManager проинициализирован");
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass(), "HistoryManager готов к работе");
    }

}