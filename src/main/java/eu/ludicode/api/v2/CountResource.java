package eu.ludicode.api.v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;

/**
 * Classe permettant de récupérer le nombre de lignes dans chaque table
 * @author vitsem
 */

@Path("/count")
public class CountResource {

    private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
    private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
    private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);
	
    @GET
    @Path("/nbTeachers")
    public int getNbTeachers() {
    	return teacherDao.getCount();
    }
    
    @GET
    @Path("/nbStudents")
    public int getNbStudents() {
    	return studentDao.getCount();
    }
    
    @GET
    @Path("/nbLevels")
    public int getNbLevels() {
    	return levelDao.getCount();
    }

}
