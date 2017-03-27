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
    
    public CountResource() {}
	
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
