package Tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    public void shouldNotAddThisEpicIdToThisEpic() {
        Epic epic1 = new Epic("Эпик1", "Описание эпик1", 1);
        assertFalse(epic1.addSubTaskId(epic1.getId()),
                "Id объекта Epic нельзя добавить в Epic в список Id подзадач");
    }
}