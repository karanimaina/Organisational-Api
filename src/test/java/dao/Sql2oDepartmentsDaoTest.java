package dao;

import models.Department_News;
import models.Departments;
import models.News;
import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oDepartmentsDaoTest {
    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oUsersDao sql2oUsersDao;
    private static Sql2oNewsDao sql2oNewsDao;
    private static Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organisational_api";
        Sql2o sql2o = new Sql2o(connectionString, "karani-dev", "felixmaina");
       // String connectionString = "jdbc:postgresql://ec2-44-193-188-118.compute-1.amazonaws.com:5432/de8ovej3am2c3a?sslmode=require";
       // Sql2o sql2o = new Sql2o(connectionString, "bfecpdlpqbkzmj", "096a8b46a8f9c631ac9398a9ad187dd32c7d5c12055e04f48d5da707357f782e");
        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);
        sql2oNewsDao=new Sql2oNewsDao(sql2o);
        System.out.println("connected to database");
        conn=sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        sql2oDepartmentsDao.clearAll();
        sql2oUsersDao.clearAll();
        sql2oNewsDao.clearAll();
        System.out.println("clearing database");
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void idSetForAddedDepartment() {
        Departments department=setUpNewDepartment();
        int originalId=department.getId();
        sql2oDepartmentsDao.save(department);
        assertNotEquals(originalId,department.getId());
    }



    @Test
    public void addUserToDepartment() {
        Departments department=setUpNewDepartment();
        sql2oDepartmentsDao.save(department);
        User user=setUpNewUser();
        User otherUser= new User("James","intern","junior developer");
        sql2oUsersDao.save(user);
        sql2oUsersDao.save(otherUser);
        sql2oDepartmentsDao.addUserToDepartment(user,department);
        sql2oDepartmentsDao.addUserToDepartment(otherUser,department);
        assertEquals(2,sql2oDepartmentsDao.getAllUsersInDepartment(department.getId()).size());
        assertEquals(2,sql2oDepartmentsDao.findById(department.getId()).getSize());
    }

    @Test
    public void getAll() {
        Departments department=setUpNewDepartment();
        Departments otherDepartment=new Departments("printing","printing of books");
        sql2oDepartmentsDao.save(department);
        sql2oDepartmentsDao.save(otherDepartment);
        assertEquals(department,sql2oDepartmentsDao.getAll().get(0));
        assertEquals(otherDepartment,sql2oDepartmentsDao.getAll().get(1));
    }

    @Test
    public void correctDepartmentIsReturnedFindById() {
        Departments department=setUpNewDepartment();
        Departments otherDepartment=new Departments("printing","printing of books");
        sql2oDepartmentsDao.save(department);
        sql2oDepartmentsDao.save(otherDepartment);
        assertEquals(department,sql2oDepartmentsDao.findById(department.getId()));
        assertEquals(otherDepartment,sql2oDepartmentsDao.findById(otherDepartment.getId()));

    }

    @Test
    public void getAllUsersInDepartment() {
        Departments department=setUpNewDepartment();
        sql2oDepartmentsDao.save(department);
        User user=setUpNewUser();
        User otherUser= new User("Mwayrs","intern","Secretary");
        sql2oUsersDao.save(user);
        sql2oUsersDao.save(otherUser);
        sql2oDepartmentsDao.addUserToDepartment(user,department);
        sql2oDepartmentsDao.addUserToDepartment(otherUser,department);
        assertEquals(2,sql2oDepartmentsDao.getAllUsersInDepartment(department.getId()).size());
        assertEquals(2,sql2oDepartmentsDao.findById(department.getId()).getSize());
    }
    @Test
    public void getDepartmentNews() {
        User users=setUpNewUser();
        sql2oUsersDao.save(users);
        Departments departments=setUpNewDepartment();
        sql2oDepartmentsDao.save(departments);
        Department_News department_news =new Department_News("Meeting","To nominate new chairman",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        News news=new News("Meeting","Meeting to set activities for team building",users.getId());
        sql2oNewsDao.addNews(news);

        assertEquals(department_news.getTitle(),sql2oDepartmentsDao.getDepartmentNews(department_news.getId()).get(0).getTitle());
    }

    //helper
    private Departments setUpNewDepartment() {
        return new Departments("Editing","editing of newspaper");
    }
    private User setUpNewUser() {
        return new User("Felix Maina","manager","Editor");
    }

}

