package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.beans.Teacher;

/**
 * Database Access Object relative to teachers
 *  A teacher has a name, password, email, and a datetime telling 
 *  the last time the user check his notifications
 * Table teachers
 * @author vitsem
 */
public interface TeacherDao {
	
	@SqlUpdate("create table teachers ("
            + "id integer primary key autoincrement, "
            + "name varchar(100), "
            + "password text, "
            + "email varchar(50), "
            + "lastNotifChecking DATETIME DEFAULT CURRENT_TIMESTAMP)")
    void createUserTable();
	
	@SqlUpdate("insert into teachers (name, password, email) values (:name, :password, :email)")
    @GetGeneratedKeys
    int insert(@BindBean() Teacher teacher);
	
	@SqlQuery("select * from teachers where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Teacher findById(@Bind("id") int id);
	
	@SqlQuery("select * from teachers where name = :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Teacher findByName(@Bind("name") String name);
	
	@SqlQuery("select * from teachers where name like :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Teacher> searchUsers(@Bind("name") String name);

    @SqlQuery("select id, name, email from teachers where name=:name AND password=:password")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Teacher userIsCorrect(@Bind("name") String name, @Bind("password") String password);

    @SqlQuery("select * from teachers where email=:email")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Teacher findByEmail(@Bind("email") String email);

    @SqlQuery("select * from teachers")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Teacher> getAll();

    @SqlQuery("select * from teachers order by name asc")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Teacher> getAllNameSort();

    @SqlUpdate("UPDATE teachers SET lastNotifChecking = CURRENT_TIMESTAMP WHERE id = :id")
    @GetGeneratedKeys
    int updateNotifDate(@Bind("id") int id);

    @SqlUpdate("UPDATE teachers SET name = :name WHERE id = :id")
    void updateName(@Bind("id") int id, @Bind("name") String name);

    @SqlUpdate("UPDATE teachers SET email = :email WHERE id = :id")
    void updateEmail(@Bind("id") int id, @Bind("email") String email);

    @SqlUpdate("UPDATE teachers SET password = :password WHERE id = :id")
    void updatePassword(@Bind("id") int id, @Bind("password") String password);

    @SqlUpdate("drop table if exists teachers")
    void dropUserTable();

    void close();

}
