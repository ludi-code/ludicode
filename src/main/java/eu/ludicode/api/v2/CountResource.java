package eu.ludicode.api.v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;

/**
 * Classe permettant de récupérer le nombre de lignes dans chaque table
 * @author vitsem
 */

@Path("/count")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CountResource {

	private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
	private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
	private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);
	private int numTeachers;
	private int numStudents;
	private int numLevels;

	public CountResource(){
		numTeachers = teacherDao.getCount();
		numStudents = studentDao.getCount();
		numLevels = levelDao.getCount();
	}

	@GET
	@Path("/countResource")
	public CountResource getCountResource() {
		return new CountResource();
	}

	public int getNumTeachers() {
		return numTeachers;
	}

	public void setNumTeachers(int numTeachers) {
		this.numTeachers = numTeachers;
	}

	public int getNumStudents() {
		return numStudents;
	}

	public void setNumStudents(int numStudents) {
		this.numStudents = numStudents;
	}

	public int getNumLevels() {
		return numLevels;
	}

	public void setNumLevels(int numLevels) {
		this.numLevels = numLevels;
	}

}
