package TODO_Gorest_Posts;

public class Post {
    int id;
    int user_id;
    String title;
    String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "\nid=" + id +
                ", \nuser_id=" + user_id +
                ",\n title='" + title + '\'' +
                ", \nbody='" + body + '\'' +
                '}';
    }
}
