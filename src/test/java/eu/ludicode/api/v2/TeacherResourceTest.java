package eu.ludicode.api.v2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.dao.TeacherDao;
import fr.iutinfo.utils.Utils;

public class TeacherResourceTest {
	
	TeacherDao teacherDao ;

	@Before
	public void initTableTeacher() {
		teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
		teacherDao.dropTeacherTable();
		teacherDao.createTeacherTable();
	}

	@Test
	public void create_should_return_same_teacher_with_id_not_null() {
		Teacher teacher = new Teacher();
		teacher.setEmail("email@test.com");
		teacher.setName("Napoleon");
		teacher.setPassword("coucou");

		TeacherResource teacherRessource = new TeacherResource();
		Feedback fb=teacherRessource.createTeacher(teacher);

		Assert.assertTrue(fb.isSuccess());
	}

	@Test
	public void test_get_should_return_all_teachers() {
		Teacher teacher = new Teacher();
		teacher.setEmail("email@test.com");
		teacher.setName("Napoleon");
		TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
		teacherDao.insert(teacher);
		
		TeacherResource teacherRessource = new TeacherResource();
		List<Teacher> teachers = teacherRessource.readAll();
		
		Assert.assertEquals(1, teachers.size());
	}
	
	@Test
	public void test_trie_alphabetique() {
		Teacher teacher = new Teacher();
		teacher.setEmail("email@test.com");
		teacher.setName("Napoleon");
		TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
		teacherDao.insert(teacher);
		Teacher teacher2 = new Teacher();
		teacher2.setEmail("email2@test.com");
		teacher2.setName("Alf");
		teacherDao.insert(teacher2);
		
		TeacherResource teacherRessource = new TeacherResource();
		List<Teacher> teachers = teacherRessource.getSortTeachers();
		
		Assert.assertEquals("Alf", teachers.get(0).getName());
		Assert.assertEquals("Napoleon", teachers.get(1).getName());
	}
	
	@Test
	public void test_connection_utilisateur(){
		Teacher teacher = new Teacher();
		teacher.setEmail("email@test.com");
		teacher.setName("Napoleon");
		teacher.setPassword(Utils.hashMD5("mdp"));

		teacherDao.insert(teacher);
		
		TeacherResource teacherRessource = new TeacherResource();
		Feedback fb=teacherRessource.logTeacher(teacher);

		Assert.assertTrue(fb.isSuccess());
	}

}
