package TZ4;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskCounter = 0;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubTasksList() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
        //очистка подзадачь из эпиков и изменение статуса
        for (Epic epic : epics.values()) {
            epic.setArraySubTaskId(new ArrayList<>());
            epic.setStatus(TaskStatus.NEW);
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void addTask(Task task) {
        if (task != null) {
            task.setId(++taskCounter);
            tasks.put(task.getId(), task);
        }
    }

    public void addSubTask(int epicId, SubTask subTask) {
        Epic epic = getEpicById(epicId);
        if (subTask != null && epic != null) {
            subTask.setId(++taskCounter);
            subTask.setEpicId(epicId);
            subTasks.put(subTask.getId(), subTask);

            ArrayList<Integer> arraySubTaskId = epic.getArraySubTaskId();
            arraySubTaskId.add(subTask.getId());
            epic.setArraySubTaskId(arraySubTaskId);
            setEpicStatus(epic);
        }
    }

    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(++taskCounter);
            epics.put(epic.getId(), epic);
        }
    }

    public void updateTask(Task task) {
        if (task != null) {
            if (tasks.containsKey(task.getId())) {
                tasks.put(task.getId(), task);
            }
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (subTask != null) {
            if (subTasks.containsKey(subTask.getId())) {
                int epicId = getSubTaskById(subTask.getId()).getEpicId();
                subTask.setEpicId(epicId);
                subTasks.put(subTask.getId(), subTask);
                setEpicStatus(getEpicById(epicId));
            }
        }
    }

    public void updateEpic(Epic epic) {
        if (epic != null) {
            if (epics.containsKey(epic.getId())) {
                epics.put(epic.getId(), epic);
            }
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            Epic epic = getEpicById(getSubTaskById(id).getEpicId());
            ArrayList<Integer> arraySubTaskId= epic.getArraySubTaskId();
            arraySubTaskId.remove((Integer)id);
            epic.setArraySubTaskId(arraySubTaskId);
            subTasks.remove(id);
        }
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = getEpicById(id);
            ArrayList<Integer> epicSubTaskIds = epic.getArraySubTaskId();
            for(int subId: epicSubTaskIds) {
                subTasks.remove(subId);
            }
            epics.remove(id);
        }
    }

    public ArrayList<SubTask> getEpicSubTask(Epic epic) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        for (SubTask subTask: subTasks.values()) {
            if (subTask.getEpicId() == epic.getId()) {
                epicSubTasks.add(subTask);
            }
        }
        return epicSubTasks;
    }



    private void setEpicStatus(Epic epic) {
        ArrayList<SubTask> epicSubTasks = getEpicSubTask(epic);
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
