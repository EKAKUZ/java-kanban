package Tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int taskCounter = 0;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    private final HistoryManager historyManager;


    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        //очистка подзадачь из эпиков и изменение статуса
        for (Epic epic : epics.values()) {
            epic.clearArraySubTaskId();
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public void addTask(Task task) {
        if (task != null) {
            task.setId(++taskCounter);
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void addSubTask(SubTask subTask) {
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            if (epic != null) {
                if (epic.addSubTaskId(subTask.getId())) {
                    subTask.setId(++taskCounter);
                    subTasks.put(subTask.getId(), subTask);
                    setEpicStatus(epic);
                }
            }
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(++taskCounter);
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            if (tasks.containsKey(task.getId())) {
                tasks.put(task.getId(), task);
            }
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTask != null) {
            if (subTasks.containsKey(subTask.getId())) {
                int epicId = subTasks.get(subTask.getId()).getEpicId();
                if (subTask.setEpicId(epicId)) {
                    subTasks.put(subTask.getId(), subTask);
                    setEpicStatus(epics.get(epicId));
                }
            }
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic != null) {
            if (epics.containsKey(epic.getId())) {
                ArrayList<Integer> epicSubTaskIds = epics.get(epic.getId()).getArraySubTaskId();
                for(int subId: epicSubTaskIds) {
                    subTasks.remove(subId);
                }
                epics.put(epic.getId(), epic);
            }
        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            Epic epic = epics.get(subTasks.get(id).getEpicId());
            epic.removeSubTaskId(id);
            subTasks.remove(id);
            setEpicStatus(epic);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Integer> epicSubTaskIds = epic.getArraySubTaskId();
            for(int subId: epicSubTaskIds) {
                subTasks.remove(subId);
            }
            epics.remove(id);
        }
    }

    @Override
    public ArrayList<SubTask> getEpicSubTask(int epicId) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId() == epicId) {
                    epicSubTasks.add(subTask);
                }
            }
        }
        return epicSubTasks;
    }

    @Override
    public ArrayList<Task>  getHistory() {
        return historyManager.getHistory();
    }

    private void setEpicStatus(Epic epic) {
        ArrayList<SubTask> epicSubTasks = getEpicSubTask(epic.getId());
        TaskStatus newStatus = TaskStatus.NEW;
        if (epicSubTasks.size() > 0) {
            newStatus  = epicSubTasks.get(0).getStatus();
            for (SubTask subTask : epicSubTasks) {
                switch (subTask.getStatus()) {
                    case NEW:
                        if (newStatus != TaskStatus.NEW) {
                            newStatus = TaskStatus.IN_PROGRESS;
                        }
                        break;
                    case IN_PROGRESS:
                        newStatus = TaskStatus.IN_PROGRESS;
                        break;
                    case DONE:
                        if (newStatus != TaskStatus.DONE) {
                            newStatus = TaskStatus.IN_PROGRESS;
                        }
                        break;
                }
            }
        }
        epic.setStatus(newStatus);
    }

}
