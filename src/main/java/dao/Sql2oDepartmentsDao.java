package dao;

import models.Departments;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao {
    private final Sql2o sql2o;

    public Sql2oDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Departments department) {
        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO departments (name,description,size) VALUES (:name,:description,:size)";
            int id=(int) con.createQuery(sql,true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);

        }catch (Sql2oException e){
            System.out.println(e);
        }
    }
    @Override
    public void addUserToDepartment(User user, Departments department) {

        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO users_departments (user_id,department_id) VALUES (:user_id,:department_id)";
            con.createQuery(sql)
                    .addParameter("user_id",user.getId())
                    .addParameter("department_id",department.getId())
                    .executeUpdate();
            String sizeQuery="SELECT user_id FROM users_departments";
            List<Integer> size=con.createQuery(sizeQuery)
                    .executeAndFetch(Integer.class);
            String updateDepartmentSize="UPDATE departments SET size=:size WHERE id=:id";
            con.createQuery(updateDepartmentSize).addParameter("id",department.getId())
                    .addParameter("size",size.size())
                    .executeUpdate();

        }catch (Sql2oException e){
            System.out.println(e);
        }

    }

    @Override
    public List<Departments> getAll() {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments";
            return con.createQuery(sql)
                    .executeAndFetch(Departments.class);

        }
    }
    @Override
    public Departments findById(int id) {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Departments.class);

        }
    }
