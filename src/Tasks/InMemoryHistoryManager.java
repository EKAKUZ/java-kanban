package Tasks;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Task> historyTasks;
    private final int tasksLimit = 10;

    public InMemoryHistoryManager () {
        historyTasks = new ArrayList<>(tasksLimit);
    }
    @Override
    public void add(Task task) {
        if (task != null) {
            /*раскоментировать в след. спринте
            if (historyTasks.contains(task)) {
                historyTasks.remove(task);
            }*/
            if (task.getClass().equals(Task.class)) {
                historyTasks.add(new Task(task));
            } else if (task.getClass().equals(SubTask.class)) {
                historyTasks.add(new SubTask(task));
            } else if (task.getClass().equals(Epic.class)) {
                historyTasks.add(new Epic(task));
            }

            if (historyTasks.size() > tasksLimit) {
                historyTasks.removeFirst();
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory(){
        return historyTasks;
    }
}
