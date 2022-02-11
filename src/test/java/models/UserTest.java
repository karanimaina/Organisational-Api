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
        assertTrue(user instanceof  User);
    }

    @Test
    public void User_getsNameCorrectly() {
        User user = setupUser();
        assertEquals("James",user.getName());
    }
    @Test
    public void User_getsPositionCorrectly() {
        User user = setupUser();
        assertEquals("HR",user.getPosition());
    }

    @Test
    public void User_getsRoleCorrectly() {
        User user = setupUser();
        assertEquals("Manage employess", user.getRole());
    }

    private User setupUser(){
        return new User("James","HR","Manage employess");
    }
}