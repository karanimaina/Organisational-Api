package models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void User_instantiatesCorrectly(){
        User user = setupUser();
        Assert.assertTrue(user instanceof  User);
    }

    @Test
    public void User_getsNameCorrectly() {
        User user = setupUser();
        Assert.assertEquals("James",user.getName());
    }
    @Test
    public void User_getsPositionCorrectly() {
        User user = setupUser();
        Assert.assertEquals("HR",user.getPosition());
    }

    @Test
    public void User_getsRoleCorrectly() {
        User user = setupUser();
        Assert.assertEquals("Manage employess", user.getRole());
    }

    private User setupUser(){
        return new User("James","HR","Manage employess");
    }
}