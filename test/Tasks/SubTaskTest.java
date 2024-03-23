package Tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    @Test
    public void shouldNotAddThisSubTaskIdToThisSubTask() {
        SubTask subTask = new SubTask("Подзадача1", "Описание подзадачи1", 1, TaskStatus.NEW);
        assertFalse(subTask.setEpicId(subTask.getId()),
                "Id объекта SubTask нельзя добавить в этот SubTask в epicId");
    }
}