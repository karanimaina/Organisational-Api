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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
       // String connectionString = "jdbc:postgresql://ec2-44-193-188-118.compute-1.amazonaws.com:5432/de8ovej3am2c3a?sslmode=require";
        //Sql2o sql2o = new Sql2o(connectionString, "bfecpdlpqbkzmj", "096a8b46a8f9c631ac9398a9ad187dd32c7d5c12055e04f48d5da707357f782e");
         String connectionString = "jdbc:postgresql://localhost:5432/organisational_api";
          Sql2o sql2o = new Sql2o(connectionString, "karani-dev", "felixmaina");
        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oNewsDao=new Sql2oNewsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);
        conn=sql2o.open();

//gets all the userrs saved into the database
        get("/users", "application/json", (request, response) -> {

            if(sql2oUsersDao.getAll().size() > 0){
                System.out.println(sql2oUsersDao.getAll().toString());
                return gson.toJson(sql2oUsersDao.getAll());

            }
            else {
                return "{\"message\":\"OOPS,  no users are currently listed in the database.\"}";
            }
        });
       // gets the records of the saved departments in the database
        get("/departments","application/json",(request, response) -> {
            if(sql2oDepartmentsDao.getAll().size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }
        });

        //retrieves all the news objects saved in to the database
        get("/news/general","application/json",(request, response) -> {
            if(sql2oNewsDao.getAll().size()>0){
                return gson.toJson(sql2oNewsDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no news are currently listed in the database.\"}";
            }
        });
//gets a specific user from the department
        get("/user/:id/departments","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUsersDao.getAllUserDepartments(id).size()>0){
                return gson.toJson(sql2oUsersDao.getAllUserDepartments(id));
            }
            else {
                return "{\"message\":\"OOPs, enroll the user to the department.\"}";
            }

        });
//returns a user with the specific id
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
        //returns a list of all the users in the department
        get("/department/:id/users","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentsDao.getAllUsersInDepartment(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAllUsersInDepartment(id));
            }
            else {
                return "{\"message\":\"  department has no users. add users to this department\"}";
            }
        });
        //find a specific department based on Id from the database
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
       // gets the departments news from  a specific department
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
       //adds new users to the database
        post("/users/new","application/json",(request, response) -> {
            User user=gson.fromJson(request.body(),User.class);
            System.out.println(user.toString());
            sql2oUsersDao.save(user);
            response.status(201);
            return gson.toJson(user);
        });
        //create new departments and adds them to the database
        post("/departments/new","application/json",(request, response) -> {
            Departments departments =gson.fromJson(request.body(),Departments.class);
            sql2oDepartmentsDao.save(departments);
            response.status(201);
            return gson.toJson(departments);
        });
        //create news
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
            if(departments==null){//checks if departments are present
                throw new ApiExceptions.ApiException(404, String.format("OOPs,there is no departments with the listed id ",
                        request.params("department_id")));
            }
            if(users==null){//checks if users have been added
                throw new ApiExceptions.ApiException(404, String.format("OOps, we cannot find the user specified using that id",
                        request.params("user_id")));
            }
            sql2oDepartmentsDao.addUserToDepartment(users,departments);// adds users to the departments

            List<User> departmentUsers=sql2oDepartmentsDao.getAllUsersInDepartment(departments.getId());//gets all the user in the selected departments
            response.status(201); //created
            return gson.toJson(departmentUsers);
        });

        //FILTERS
        exception(ApiExceptions.ApiException.class, (exception, request, response) -> {
            ApiExceptions.ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });


        after((request, response) ->{
            response.type("application/json");
        });


    }}

