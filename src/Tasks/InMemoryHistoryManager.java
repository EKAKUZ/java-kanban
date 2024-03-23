package Tasks;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> historyTasks;
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
                Task newTask = new Task(task.getName(), task.getDescription(), task.getId(), task.getStatus());
                historyTasks.add(newTask);
            } else if (task.getClass().equals(SubTask.class)) {
                Task newTask = new SubTask(task.getName(), task.getDescription(), task.getId(), task.getStatus());
                ((SubTask) newTask).setEpicId(((SubTask) task).getEpicId());
                historyTasks.add(newTask);
            } else if (task.getClass().equals(Epic.class)) {
                Task newTask = new Epic(task.getName(), task.getDescription(), task.getId());
                ArrayList<Integer> ArraySubTaskId = ((Epic) task).getArraySubTaskId();
                for (Integer id: ArraySubTaskId) {
                    ((Epic) newTask).addSubTaskId(id);
                }
                historyTasks.add(newTask);
            }

            if (historyTasks.size() > tasksLimit) {
                historyTasks.remove(0);
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory(){
        return historyTasks;
    }
}
