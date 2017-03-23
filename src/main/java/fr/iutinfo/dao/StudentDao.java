package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.beans.Student;

/**
 * Database Access Object relative to students
 *  A student has a name, password, and a datetime telling 
 *  the last time the student check his notifications.
 *  He also has an id representing his student.
 * Table teachers
 * @author vitsem
 */

public interface StudentDao {
	
	@SqlUpdate("create table students ("
            + "id integer primary key autoincrement, "
            + "name varchar(100), "
            + "password text, "
            + "idTeacher integer, "
            + "lastNotifChecking DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "CONSTRAINT fk_teacher FOREIGN KEY (idTeacher) REFERENCES teachers(id))")
    void createStudentTable();
	
	@SqlUpdate("insert into students (name, password, idTeacher) values (:name, :password, :idTeacher)")
    @GetGeneratedKeys
    int insert(@BindBean() Student student);
	
	@SqlQuery("select * from students where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Student findById(@Bind("id") int id);
	
	@SqlQuery("select * from students where name = :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Student findByName(@Bind("name") String name);
	
	@SqlQuery("select * from students where name like :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Student> searchStudents(@Bind("name") String name);

    @SqlQuery("select id, name, idTeacher from students where name = :name AND password = :password")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Student studentIsCorrect(@Bind("name") String name, @Bind("password") String password);

    @SqlQuery("select * from students where idTeacher = :idTeacher")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Student> findByIdTeacher(@Bind("idTeacher") int idTeacher);

    @SqlQuery("select * from students")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Student> getAll();

    @SqlQuery("select * from students order by name asc")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Student> getAllNameSort();

    @SqlUpdate("UPDATE students SET lastNotifChecking = CURRENT_TIMESTAMP WHERE id = :id")
    @GetGeneratedKeys
    int updateNotifDate(@Bind("id") int id);

    @SqlUpdate("UPDATE students SET name = :name WHERE id = :id")
    void updateName(@Bind("id") int id, @Bind("name") String name);

    @SqlUpdate("UPDATE students SET idTeacher = :idTeacher WHERE id = :id")
    void updateIdTeacher(@Bind("id") int id, @Bind("idTeacher") int idTeacher);

    @SqlUpdate("UPDATE students SET password = :password WHERE id = :id")
    void updatePassword(@Bind("id") int id, @Bind("password") String password);

    @SqlUpdate("drop table if exists students")
    void dropStudentTable();

    void close();

}
