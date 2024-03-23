package Tasks;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TaskTest {
    @Test
    public void tasksShouldBeEqualIfIdIsEqual() {
        Task task1 = new Task("Задача1", "Описание задачи1", 1, TaskStatus.DONE);
        Task task2 = new Task("Задача1", "Описание задачи1", 2, TaskStatus.DONE);
        Task task3 = new Task("Задача3", "Описание задачи3", 1, TaskStatus.IN_PROGRESS);

        assertNotEquals(task1, task2, "Задачи с разными ID не должны быть равны друг другу!");
        assertEquals(task1, task3, "Задачи с равными ID должны быть равны друг другу!");

        Task task4 = new SubTask("Задача1", "Описание задачи1", 1, TaskStatus.DONE);
        Task task5 = new SubTask("Задача1", "Описание задачи1", 2, TaskStatus.DONE);
        Task task6 = new SubTask("Задача3", "Описание задачи3", 1, TaskStatus.IN_PROGRESS);

        assertNotEquals(task4, task5, "Подзадачи с разными ID не должны быть равны друг другу!");
        assertEquals(task4, task6, "Подзадачи с равными ID должны быть равны друг другу!");

        Task task7 = new Epic("Задача1", "Описание задачи1", 1);
        Task task8 = new Epic("Задача1", "Описание задачи1", 2);
        Task task9 = new Epic("Задача3", "Описание задачи3", 1);

        assertNotEquals(task7, task8, "Эпики с разными ID не должны быть равны друг другу!");
        assertEquals(task7, task9, "Эпики с равными ID должны быть равны друг другу!");

        assertNotEquals(task4, task1, "Эпик и задача не должны быть равны друг другу, даже при равных ID");
        assertNotEquals(task4, task7, "Эпик и подзадача не должны быть равны друг другу, даже при равных ID");
        assertNotEquals(task1, task7, "Задача и подзадача не должны быть равны друг другу, даже при равных ID");
    }

}