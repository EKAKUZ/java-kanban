package Tasks;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> arraySubTaskId;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.arraySubTaskId = new ArrayList<>();
    }

    public Epic(String name, String description, int id) {
        super(name, description, id, TaskStatus.NEW);
        this.arraySubTaskId = new ArrayList<>();
    }

    ArrayList<Integer> getArraySubTaskId() {
        return (ArrayList<Integer>) arraySubTaskId.clone();
    }

    boolean addSubTaskId(int subTaskId) {
        if (subTaskId != this.getId()) {
            arraySubTaskId.add(subTaskId);
            return true;
        }
        return false;
    }

    void removeSubTaskId(int subTaskId) {
        arraySubTaskId.remove((Integer) subTaskId);
    }

    void clearArraySubTaskId() {
        arraySubTaskId.clear();;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", id=" + this.getId() +
                ", status=" + this.getStatus() +
                ", arraySubTaskId=" + arraySubTaskId +
                '}';
    }

}
