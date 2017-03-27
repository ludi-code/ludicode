package eu.ludicode.api.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Student;
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.beans.User2;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;
import fr.iutinfo.utils.Session;
import fr.iutinfo.utils.Utils;

/**
 * Classe permettant de gérer le fait que l'utilisateur soit un professeur ou un élève.
 * @author vitsem
 *
 */

@Path("/users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserResource {
    private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
    private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);

    public UserResource() {}
    
    /**
	 * Insert l'utilisateur si celui ci est valide.
	 * 	Cette méthode n'est pas utilisé car on accède directement à createTeacher ou Student
	 * 	étant donné que l'on sait si l'utilisateur est un élève ou un professeur grâce à une
	 * 	case à cocher sur la page html.
	 * @param user
	 * @return
	 */
    @POST
    @Path("/register")
    public Feedback createUser(User2 user) {
    	Feedback fb = null;
		if(user instanceof Teacher) {
			fb = new TeacherResource().createTeacher((Teacher)user);
		} else {
			fb = new StudentResource().createStudent((Student)user);
		}
		return fb;
    }

	/**
	 * Connecte un utilisateur
	 * @param user
	 * @return
	 */
	@POST
	@Path("/login")
	public Feedback logUser(User2 user) {
		String hashedPassword = Utils.hashMD5(user.getPassword());
		if (hashedPassword == null)
			return new Feedback(false, "An error occurs during hashing");		
		Feedback fb = null;
		if(teacherDao.teacherIsCorrect(user.getName(), hashedPassword) != null) {
			Teacher t = new Teacher();
			t.setName(user.getName());
			t.setPassword(hashedPassword);
			fb = new TeacherResource().logTeacher(t);
		} else if(studentDao.studentIsCorrect(user.getName(), hashedPassword) != null) {
			Student s = new Student();
			s.setName(user.getName());
			s.setPassword(hashedPassword);
			fb = new StudentResource().logStudent(s);
		} else {
			return new Feedback(false, "L'utilisateur n'existe pas.");
		}
		return fb;
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
