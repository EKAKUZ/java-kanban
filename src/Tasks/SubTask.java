package Tasks;

public class SubTask extends Task{
    private int epicId;

    public SubTask(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public SubTask(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
    }

    public SubTask(Task subTask) {
        super(subTask);
        this.epicId = ((SubTask) subTask).getEpicId();
    }

    public boolean setEpicId(int epicId) {
        if (epicId != this.getId()) {
            this.epicId = epicId;
            return true;
        }
        return false;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", id=" + this.getId() +
                ", status=" + this.getStatus()+
                ", epicId=" + epicId +
                '}';
    }
}
