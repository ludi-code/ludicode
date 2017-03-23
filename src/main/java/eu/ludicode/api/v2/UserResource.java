package eu.ludicode.api.v2;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.User;
import fr.iutinfo.beans.User2;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;
import fr.iutinfo.utils.Session;

/**
 * Classe permettant de gérer le fait que l'utilisateur soit un professeur ou un élève.
 * @author vitsem
 *
 */

@Path("/users")
public class UserResource {
    private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
    private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);

    /**
	 * Insert l'utilisateur si celui ci est valide.
	 * @param user
	 * @return
	 */
    @POST
    @Path("/register")
    public Feedback createUser(User user) {
    	//TODO
		return new Feedback();
    }

	/**
	 * Connecte un utilisateur
	 * @param user
	 * @return
	 */
	@POST
	@Path("/login")
	public Feedback logUser(User2 user) {
		//TODO
		return new Feedback();
	}
    
    /**
     * Lit tout les utilisateurs
     * @return liste d'utilisateur
     */
    @GET
    public List<User2> readAll() {
    	ArrayList<User2> users = new ArrayList<>();
    	users.addAll(teacherDao.getAll());
    	users.addAll(studentDao.getAll());
    	return users;
    }
	
	/**
	 * Vérifie si un utilisateur est connecté
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/isLogged/{cookie}")
	public Feedback isLogged(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie))
			return new Feedback(true, "connected");
		return new Feedback(false, "not connected");
	}
	
	/**
	 * Déconnecte l'utilisateur
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/logout/{cookie}")
	public Feedback logout(@PathParam("cookie") String cookie) {
		Session.removeUser(cookie);
		return new Feedback(true, "Vous avez bien été déconnecté");
	}


	/**
	 * Recherche un utilisateur d'id "cookie"
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/me/{cookie}")
	public User2 getUser(@PathParam("cookie") String cookie) {
		return Session.getUser(cookie);
	}


	/**
	 *  recherche tout les utilisateurs contenant "term"
	 * @param user
	 * @return
	 */
	@GET
	@Path("/search")
	public List<User2> searchUsers(@QueryParam("user") String user) {
		ArrayList<User2> users = new ArrayList<>();
    	users.addAll(teacherDao.searchTeachers(user));
    	users.addAll(studentDao.searchStudents(user));
		return users;
	}
	
	/**
	 * Change la Date de dernière connection
	 * @param cookie
	 * @return
	 */
	@PUT
	@Path("/updateNotifDate/{cookie}")
	public Feedback updateNotifDate(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			if(Session.isTeacher(cookie)) {
				teacherDao.updateNotifDate(Session.getUser(cookie).getId());
			} else {
				studentDao.updateNotifDate(Session.getUser(cookie).getId());
			}
			return new Feedback(true, "update done");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}
}
