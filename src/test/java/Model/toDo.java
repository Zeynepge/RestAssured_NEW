package Model;

public class toDo {
    int userId;
    int id;
    String title;
    Boolean completed;
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t\"userId\": " + userId +
                ", \n\t\"id\": " + id +
                ", \n\t\"title\": \"" + title + '\"' +
                ", \n\t\"completed\": " + completed +
                "\n}";
    }
}

