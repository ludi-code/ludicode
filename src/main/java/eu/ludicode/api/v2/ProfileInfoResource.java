package eu.ludicode.api.v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.ProfileInfo;
import fr.iutinfo.beans.User2;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;
import fr.iutinfo.utils.Session;

/**
 * Cette classe permet de recuperer les infos d'un profile
 * @author diabatm
 *
 */
@Path("/profile")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ProfileInfoResource {
	private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
	private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
	private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);

	
	@GET
	@Path("{table}/{idUser}")
	public ProfileInfo getProfileInfo(@PathParam("table") String table, @PathParam("idUser") int idUser) {
		ProfileInfo profileInfo = new ProfileInfo();
		if(table.equals("students")) {
			profileInfo.setUser((User2) studentDao.findById(idUser));
			profileInfo.setLevelsInfo(levelDao.getLevelInfoByAuthor(idUser));
		} else {
			profileInfo.setUser((User2) teacherDao.findById(idUser));
		}
		
		
		return profileInfo;
	}
	
	@GET
	@Path("me/{cookie}")
	public ProfileInfo getProfileInfo(@PathParam("cookie") String cookie) {
		ProfileInfo profileInfo = new ProfileInfo();
		if(Session.isLogged(cookie)) {
			int idUser = Session.getUser(cookie).getId();
			if(Session.isStudent(cookie)) {
				profileInfo.setUser((User2) studentDao.findById(idUser));
				profileInfo.setLevelsInfo(levelDao.getLevelInfoByAuthor(idUser));
			} else if (Session.isTeacher(cookie)){
				profileInfo.setUser((User2) teacherDao.findById(idUser));
			}
			return profileInfo;
		}
		throw new WebApplicationException(404);
	}

}

