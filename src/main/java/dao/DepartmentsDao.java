package dao;

import models.Departments;
import models.News;
import models.User;

import java.util.List;

public interface DepartmentsDao {
    //create
    void save(Departments department);
    void addUserToDepartment(User user, Departments department);
    //read

    List<Departments> getAll();
    Departments findById(int id);
    List<User> getAllUsersInDepartment(int department_id);
    List<News> getDepartmentNews(int id);
    //update
    //delete
    void clearAll();

}