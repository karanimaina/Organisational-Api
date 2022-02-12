package dao;

import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oUsersDao  implements  UsersDao{
private Sql2o sql2o;
    public Sql2oUsersDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(User user) {
        try (Connection con=sql2o.open()){
            String sql ="INSERT INTO staff (name,position,staff_role) VALUES (:name,:position,:role) ";
            int id=(int) con.createQuery(sql,true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);


        }catch (Sql2oException e){
            System.out.println(e);
        }

    }

}

