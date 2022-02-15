package dao;

import models.Departments;
import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {

    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oUsersDao sql2oUsersDao;
    private static Connection conn;

    @Before
    public void setUp() throws Exception {

        //uncomment the two lines below to run locally and change to your  credentials
       // String connectionString = "jdbc:postgresql://localhost:5432/organisational_news_portal";
       // Sql2o sql2o = new Sql2o(connectionString, "karani-dev", "felixmaina");
        String connectionString = "jdbc:postgresql://ec2-44-193-188-118.compute-1.amazonaws.com:5432/de8ovej3am2c3a?sslmode=require";
        Sql2o sql2o = new Sql2o(connectionString, "bfecpdlpqbkzmj", "096a8b46a8f9c631ac9398a9ad187dd32c7d5c12055e04f48d5da707357f782e");
        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);
        System.out.println("connected to database");
        conn=sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        sql2oDepartmentsDao.clearAll();
        sql2oUsersDao.clearAll();
        System.out.println("clearing database");
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void addingUserToDbSetsUserId() {
        User user = setUpNewUser();
        int originalId= user.getId();
        sql2oUsersDao.save(user);
        assertNotEquals(originalId,user.getId());
    }

    @Test
    public void addedUserIsReturnedCorrectly() {
        User user = setUpNewUser();
        sql2oUsersDao.save(user);
        assertEquals(user.getName(),sql2oUsersDao.findById(user.getId()).getName());
    }

    @Test
    public void allInstancesAreReturned() {

        User users=setUpNewUser();
        User otherUser= new User("trivedy","intern","Paper work");
        sql2oUsersDao.save(users);
        sql2oUsersDao.save(otherUser);
        assertEquals(users.getName(),sql2oUsersDao.getAll().get(0).getName());
        assertEquals(otherUser.getName(),sql2oUsersDao.getAll().get(1).getName());
    }
    @Test
    public void getDepartmentsUserIsIn() {
        Departments department=setUpNewDepartment();
        Departments otherDepartment=new Departments("printing","printing of books");
        sql2oDepartmentsDao.save(department);
        sql2oDepartmentsDao.save(otherDepartment);
        User user=setUpNewUser();
        User otherUser= new User("trivedy","intern","Paper work");
        sql2oUsersDao.save(user);
        sql2oUsersDao.save(otherUser);
        sql2oDepartmentsDao.addUserToDepartment(user,department);
        sql2oDepartmentsDao.addUserToDepartment(otherUser,department);
        sql2oDepartmentsDao.addUserToDepartment(user,otherDepartment);
        assertEquals(2,sql2oUsersDao.getAllUserDepartments(user.getId()).size());
        assertEquals(1,sql2oUsersDao.getAllUserDepartments(otherUser.getId()).size());
    }

    //helper
    private User setUpNewUser() {
        return new User("Felix maina","manager","Editor");
    }
    private Departments setUpNewDepartment() {
        return new Departments("Editing","editing of newspaper");
    }
}