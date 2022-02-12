package models;

public class News {
    private String title;
    private String news_type;
    private  String description;
    private int department_id;
    private int id;
    private int user_id;
    private final String TYPE_OF_NEWS="general";

    public News(String title, String description, int user_id) {
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.news_type=TYPE_OF_NEWS;
        this.department_id=0;

    }

    public News(String title, String description, int department_id, int user_id) {
        this.title = title;
        this.description = description;
        this.department_id = department_id;
        this.user_id = user_id;
        this.news_type="department";
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description ;
    }

    public int getDepartment_id() {
        return department_id;
    }

}
