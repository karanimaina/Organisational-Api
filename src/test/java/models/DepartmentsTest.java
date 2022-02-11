package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void Departments_InstantiateCorrectly() {
        Departments departments= setupDepartments();
        assertTrue(departments instanceof Departments);
    }

    @Test
    public void Departments_GetsName() {
        Departments departments = setupDepartments();
        assertEquals("Sales", departments.getName());
    }

    private Departments setupDepartments (){
        return new Departments("Sales","Carryout marketing");
    }

}