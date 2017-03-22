package fr.iutinfo.dao;

import fr.iutinfo.beans.User;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.util.List;

/**
 * Database Access Object relative to Users
 *  A user has a name, password, email, and a datetime telling 
 *  the last time the user check his notifications
 * Table users
 * @author vitsem
 */
public interface UserDao {

    @SqlUpdate("create table users ("
            + "id integer primary key autoincrement, "
            + "name varchar(100), "
            + "password text, "
            + "email varchar(50), "
            + "lastNotifChecking DATETIME DEFAULT CURRENT_TIMESTAMP)")
    void createUserTable();

    @SqlUpdate("insert into users (name, password, email) values (:name, :password, :email)")
    @GetGeneratedKeys
    int insert(@BindBean() User user);

    @SqlQuery("select * from users where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    User findById(@Bind("id") int id);

    @SqlQuery("select * from users where name = :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
    User findByName(@Bind("name") String name);

    @SqlQuery("select * from users where name like :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<User> searchUsers(@Bind("name") String name);

    @SqlQuery("select id, name, email from users where name=:name AND password=:password")
    @RegisterMapperFactory(BeanMapperFactory.class)
    User userIsCorrect(@Bind("name") String name, @Bind("password") String password);

    @SqlQuery("select * from users where email=:email")
    @RegisterMapperFactory(BeanMapperFactory.class)
    User findByEmail(@Bind("email") String email);

    @SqlQuery("select * from users")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<User> getAll();

    @SqlQuery("select * from users order by name asc")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<User> getAllNameSort();

    @SqlUpdate("UPDATE users SET lastNotifChecking = CURRENT_TIMESTAMP WHERE id = :id")
    @GetGeneratedKeys
    int updateNotifDate(@Bind("id") int id);

    @SqlUpdate("UPDATE users SET name = :name WHERE id = :id")
    void updateName(@Bind("id") int id, @Bind("name") String name);

    @SqlUpdate("UPDATE users SET email = :email WHERE id = :id")
    void updateEmail(@Bind("id") int id, @Bind("email") String email);

    @SqlUpdate("UPDATE users SET password = :password WHERE id = :id")
    void updatePassword(@Bind("id") int id, @Bind("password") String password);

    @SqlUpdate("drop table if exists users")
    void dropUserTable();

    void close();
}
