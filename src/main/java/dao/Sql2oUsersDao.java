package dao;

import org.sql2o.Sql2o;

public class Sql2oUsersDao  implements  UsersDao{
private Sql2o sql2o;
    public Sql2oUsersDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

}

