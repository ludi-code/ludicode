package eu.ludicode.api.v2;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Student;
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.beans.User2;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;
import fr.iutinfo.utils.Utils;

public class StudentResourceTest {
	
	StudentDao studentDao;

	@Before
	public void initTableStudent() {
		studentDao = BDDFactory.getDbi().open(StudentDao.class);
		studentDao.dropStudentTable();
		studentDao.createStudentTable();

		TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
		teacherDao.dropTeacherTable();
		teacherDao.createTeacherTable();
		Teacher teacher = new Teacher();
		teacher.setName("Prof");
		teacherDao.insert(teacher);
	}
	
	@Test
	public void create_student_should_add_a_student_in_table_students() {
		int nbStudents = studentDao.getAll().size();
		
		Student student = new Student();
		student.setName("Napoleon");
		student.setPassword(Utils.hashMD5("coucou"));
		new StudentResource().createStudent(student);
		
		Assert.assertEquals(nbStudents+1, studentDao.getAll().size());
	}
	
	@Test
	public void create_should_return_same_student_with_id_not_null() {
		Student student = new Student();
		student.setName("Napoleon");
		student.setPassword("coucou");

		StudentResource studentRessource = new StudentResource();
		Feedback fb=studentRessource.createStudent(student);

		Assert.assertTrue(fb.isSuccess());
	}

	@Test
	public void test_get_should_return_all_students() {
		Student student = new Student();
		student.setName("Napoleon");
		StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
		studentDao.insert(student);
		
		StudentResource studentRessource = new StudentResource();
		List<Student> students = studentRessource.readAll();
		
		Assert.assertEquals(1, students.size());
	}
	
	@Test
	public void test_get_by_idTeacher_should_return_all_his_students() {
		Student student1 = new Student();
		student1.setName("Napoleon");
		student1.setIdTeacher(1);
		Student student2 = new Student();
		student2.setName("Edward");
		student2.setIdTeacher(1);
		Student student3 = new Student();
		student3.setName("Jacques");
		student3.setIdTeacher(2);
		StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
		studentDao.insert(student1);
		studentDao.insert(student2);
		studentDao.insert(student3);
		
		StudentResource studentRessource = new StudentResource();
		List<Student> students = studentRessource.getStudentsOf(1);
		
		Assert.assertEquals(2, students.size());
	}
	
	@Test
	public void test_trie_alphabetique() {
		Student student = new Student();
		student.setName("Napoleon");
		StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
		studentDao.insert(student);
		Student student2 = new Student();
		student2.setName("Alf");
		studentDao.insert(student2);
		
		StudentResource studentRessource = new StudentResource();
		List<Student> students = studentRessource.getSortStudents();
		
		Assert.assertEquals("Alf", students.get(0).getName());
		Assert.assertEquals("Napoleon", students.get(1).getName());
	}
	
	@Test
	public void test_connection_utilisateur(){
		Student student = new Student();
		student.setName("Napoleon");
		student.setPassword(Utils.hashMD5("coucou"));

		studentDao.insert(student);
		
		StudentResource studentRessource = new StudentResource();
		Feedback fb=studentRessource.logStudent(student);

		Assert.assertTrue(fb.isSuccess());
	}

}
