package Tasks;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> arraySubTaskId;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.arraySubTaskId = new ArrayList<>();
    }

    public Epic(String name, String description, int id, ArrayList<Integer> arraySubTasksId) {
        super(name, description, id, TaskStatus.NEW);
        this.arraySubTaskId = arraySubTasksId;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id, TaskStatus.NEW);
        this.arraySubTaskId = new ArrayList<>();
    }

    public ArrayList<Integer> getArraySubTaskId() {
        return arraySubTaskId;
    }

    protected void setArraySubTaskId(ArrayList<Integer> arraySubTaskId) {
        this.arraySubTaskId = arraySubTaskId;
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
