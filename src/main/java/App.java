import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import exceeptions.ApiExceptions;
import models.Departments;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.*;

import static spark.Spark.staticFileLocation;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        Sql2oNewsDao sql2oNewsDao;
        Sql2oUsersDao sql2oUsersDao;
        Sql2oDepartmentsDao sql2oDepartmentsDao;
        Connection conn;
        Gson gson = new Gson();
        staticFileLocation("/public");
        //uncomment the line below to run locally
        //String connectionString = "jdbc:postgresql://localhost:5432/organisational_news_portal";
        //uncomment the line below to run locally,change the following line of code to your credentials
        //Sql2o sql2o = new Sql2o(connectionString, "wangui", "33234159");

        //the two lines below are used when using heroku but if you want to run locally comment them
        String connectionString = "jdbc:postgresql://ec2-50-17-21-170.compute-1.amazonaws.com:5432/d8b8ehu0safpui"; //!
        Sql2o sql2o = new Sql2o(connectionString, "mihpivzxyyqmlv", "5b4f9d76874ad368465a325b3993140263c6d254771908c3d283842d54fcad11");

        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oNewsDao=new Sql2oNewsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);
        conn=sql2o.open();
         //resds  users , news ,departments
        get("/users", "application/json", (request, response) -> {

            if(sql2oDepartmentsDao.getAll().size() > 0){
                return gson.toJson(sql2oUsersDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no users are currently listed in the database.\"}";
            }
        });
        get("/users", "application/json", (request, response) -> {

            if(sql2oDepartmentsDao.getAll().size() > 0){
                return gson.toJson(sql2oUsersDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no users are currently listed in the database.\"}";
            }
        });

        get("/departments","application/json",(request, response) -> {
            if(sql2oDepartmentsDao.getAll().size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }
        });
        get("/news/general","application/json",(request, response) -> {
            if(sql2oNewsDao.getAll().size()>0){
                return gson.toJson(sql2oNewsDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no news are currently listed in the database.\"}";
            }
        });
        get("/user/:id/departments","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUsersDao.getAllUserDepartments(id).size()>0){
                return gson.toJson(sql2oUsersDao.getAllUserDepartments(id));
            }
            else {
                return "{\"message\":\"I'm sorry, but user is in no department.\"}";
            }
        });
        get("/user/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUsersDao.findById(id)==null){
                throw new ApiExceptions.ApiException(404, String.format("No user with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oUsersDao.findById(id));
            }
        });
        get("/department/:id/users","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentsDao.getAllUsersInDepartment(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAllUsersInDepartment(id));
            }
            else {
                return "{\"message\":\"I'm sorry, but department has no users.\"}";
            }
        });
        get("/department/:id","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentsDao.findById(id)==null){
                throw new ApiExceptions.ApiException(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oDepartmentsDao.findById(id));
            }
        });
        get("/news/department/:id","application/json",(request, response) -> {

            int id=Integer.parseInt(request.params("id"));
            Departments departments=sql2oDepartmentsDao.findById(id);
            if(departments==null){
                throw new ApiExceptions.ApiException(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            if(sql2oDepartmentsDao.getDepartmentNews(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getDepartmentNews(id));
            }
            else {
                return "{\"message\":\"I'm sorry, but no news in this department.\"}";
            }
        });
        //create users
        post("/users/new","application/json",(request, response) -> {
            User user=gson.fromJson(request.body(),User.class);
            sql2oUsersDao.add(user);
            response.status(201);
            return gson.toJson(user);
        });

        post("/departments/new","application/json",(request, response) -> {
            Departments departments =gson.fromJson(request.body(),Departments.class);
            sql2oDepartmentsDao.add(departments);
            response.status(201);
            return gson.toJson(departments);
        });

        post("/news/new/general","application/json",(request, response) -> {

            News news =gson.fromJson(request.body(),News.class);
            sql2oNewsDao.addNews(news);
            response.status(201);
            return gson.toJson(news);
        });
        post("/news/new/department","application/json",(request, response) -> {
            News department_news =gson.fromJson(request.body(),News.class);
            Departments departments=sql2oDepartmentsDao.findById(department_news.getDepartment_id());
            User users=sql2oUsersDao.findById(department_news.getUser_id());
            if(departments==null){
                throw new ApiExceptions.ApiException(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            if(users==null){
                throw new ApiExceptions.ApiException(404, String.format("No user with the id: \"%s\" exists",
                        request.params("id")));
            }
            sql2oNewsDao.addNews(department_news);
            response.status(201);
            return gson.toJson(department_news);
        });

        post("/news/new/general","application/json",(request, response) -> {

            News news =gson.fromJson(request.body(),News.class);
            sql2oNewsDao.addNews(news);
            response.status(201);
            return gson.toJson(news);
        });
        post("/add/user/:user_id/department/:department_id","application/json",(request, response) -> {

            int user_id=Integer.parseInt(request.params("user_id"));
            int department_id=Integer.parseInt(request.params("department_id"));
            Departments departments=sql2oDepartmentsDao.findById(department_id);
            User users=sql2oUsersDao.findById(user_id);
            if(departments==null){
                throw new ApiExceptions.ApiException(404, String.format("No department with the id: \"%s\" exists",
                        request.params("department_id")));
            }
            if(users==null){
                throw new ApiExceptions.ApiException(404, String.format("No user with the id: \"%s\" exists",
                        request.params("user_id")));
            }
            sql2oDepartmentsDao.addUserToDepartment(users,departments);

            List<User> departmentUsers=sql2oDepartmentsDao.getAllUsersInDepartment(departments.getId());

            response.status(201);
            return gson.toJson(departmentUsers);
        });


