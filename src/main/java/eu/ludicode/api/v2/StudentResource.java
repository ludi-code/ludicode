package eu.ludicode.api.v2;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Student;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.utils.Session;
import fr.iutinfo.utils.Utils;

@Path("/students")
public class StudentResource {
	private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
	
	/**
	 * Insert l'élève si celui ci est valide.
	 * 
	 * @param student
	 * @return
	 */
	@POST
	@Path("/register")
	public Feedback createStudent(Student student) {
		Feedback fb = null;
		try {
			fb = isValidStudent(student);
			if (fb.isSuccess()) {

				// on hashe le mdp pour le protéger
				String hashedPassword = Utils.hashMD5(student.getPassword());
				if (hashedPassword == null)
					return new Feedback(false, "An error occurs during hashing");
				student.setPassword(hashedPassword);

				// on insert le élève dans la bdd.
				studentDao.insert(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during insertion to database");
		}
		return fb;
	}

	/**
	 * Liste tous les élèves
	 * 
	 * @return liste des élèves
	 */
	@GET
	public List<Student> readAll() {
		return studentDao.getAll();
	}

	/**
	 * trie les élèves
	 * 
	 * @return liste triée des élèves
	 */
	@GET
	@Path("/sortByName")
	public List<Student> getSortStudents() {
		return studentDao.getAllNameSort();
	}

	/**
	 * Vérifie si l'élève existe déjà (pseudo/email), si il est valide
	 * (pseudo correct et mdp correct)
	 * 
	 * @param student
	 * @return
	 */
	private Feedback isValidStudent(Student student) {

		// On test si le pseudo est correcte et disponible
		Feedback f = isNameValid(student.getName());
		if (!f.isSuccess())
			return f;

		// test si le mdp est correct
		f = isPasswordValid(student.getPassword());
		if (!f.isSuccess())
			return f;

		return new Feedback(true, "Register OK", "student");
	}

	/**
	 * Fonction testant si le pseudo est disponible et valide
	 * 
	 * @param pseudo
	 * @return
	 */
	private Feedback isNameValid(String pseudo) {
		if (!pseudo.matches("^[a-zA-Z0-9À-ÿ-]{3,20}$")) {
			return new Feedback(false, "Le pseudo est invalide !");
		}

		// Test si le pseudo existe déjà ou non
		Student u = studentDao.findByName(pseudo);
		if (u != null) {
			return new Feedback(false, "Le pseudo est déjà utilisé");
		}

		return new Feedback(true, "Pseudo valide et disponible", "student");
	}

	/**
	 * Fonction testant si le mot de passe est valide (6 caractères ou plus).
	 * 
	 * @param password
	 * @return
	 */
	private Feedback isPasswordValid(String password) {

		if (password.length() < 6) {
			return new Feedback(false, "Le mot de passe doit faire au moins 6 caractères !");
		}

		return new Feedback(true, "Mot de passe correct", "student");
	}

	/**
	 * Connecte un élève
	 * 
	 * @param student
	 * @return
	 */
	@POST
	@Path("/login")
	public Feedback logStudent(Student student) {
		/*String hashedPassword = Utils.hashMD5(student.getPassword());
		if (hashedPassword == null)
			return new Feedback(false, "An error occurs during hashing");
		student.setPassword(hashedPassword);*/
		Student s = null;
		try {
			s = studentDao.studentIsCorrect(student.getName(), student.getPassword());
			if (s == null)
				return new Feedback(false, "Mauvais pseudo/mot de passe !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during query to database");
		}

		if (Session.isLogged(student))
			return new Feedback(false, "Vous êtes déjà connecté !");
		// Student logged
		// générate uniq id
		UUID id = UUID.randomUUID();
		// add to logged students
		Session.addUser(id.toString(), s);

		return new Feedback(true, id.toString(), "student");
	}

	/**
	 * Vérifie si un élève est connecté
	 * 
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/isLogged/{cookie}")
	public Feedback isLogged(@PathParam("cookie") String cookie) {
		if (Session.isLogged(cookie))
			return new Feedback(true, "connected", "student");
		return new Feedback(false, "not connected");
	}

	/**
	 * Déconnecte l'élève
	 * 
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/logout/{cookie}")
	public Feedback logout(@PathParam("cookie") String cookie) {
		Session.removeUser(cookie);
		return new Feedback(true, "Vous avez bien été déconnecté", "student");
	}

	/**
	 * Return l'élève avec pour id "id"
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public Student getStudent(@PathParam("id") int id) {
		return studentDao.findById(id);
	}

	/**
	 * Recherche un élève d'id "cookie"
	 * 
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/me/{cookie}")
	public Student getStudent(@PathParam("cookie") String cookie) {
		if (Session.isStudent(cookie)) {
			return (Student) Session.getUser(cookie);
		}
		return null;
	}

	/**
	 * Recherche tout les élèves contenant "term"
	 * 
	 * @param term
	 * @return
	 */
	@GET
	@Path("/search")
	public List<Student> searchStudents(@QueryParam("term") String term) {
		return studentDao.searchStudents(term + "%");
	}

	/**
	 * Cherche l'élève avec pour pseudo "name"
	 * 
	 * @param name
	 * @return
	 */
	@GET
	@Path("/getId/{name}")
	public Student getId(@PathParam("name") String name) {
		return studentDao.findByName(name);
	}
	
	/**
	 * Recherche tout les élèves du professeur d'id "idTeacher"
	 * 
	 * @param term
	 * @return
	 */
	@GET
	@Path("/getStudentsOf/{idTeacher}")
	public List<Student> getStudentsOf(@QueryParam("idTeacher") int idTeacher) {
		return studentDao.findByIdTeacher(idTeacher);
	}

	/**
	 * Change la date de dernière connexion
	 * 
	 * @param cookie
	 * @return
	 */
	@PUT
	@Path("/updateNotifDate/{cookie}")
	public Feedback updateNotifDate(@PathParam("cookie") String cookie) {
		if (Session.isLogged(cookie)) {
			studentDao.updateNotifDate(getStudent(cookie).getId());
			return new Feedback(true, "update done", "student");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

	/**
	 * Permet de modifier son pseudo de l'élève "cookie"
	 * 
	 * @param cookie
	 * @param pseudo
	 * @return
	 */
	@PUT
	@Path("/updateName/{cookie}/{pseudo}")
	public Feedback updatePseudo(@PathParam("cookie") String cookie, @PathParam("pseudo") String pseudo) {

		if (Session.isLogged(cookie)) {

			// Si le pseudo est valide on le met à jour
			Feedback f = isNameValid(pseudo);
			if (!f.isSuccess())
				return f;

			studentDao.updateName(getStudent(cookie).getId(), pseudo);

			return new Feedback(true, "Nom changé !", "student");
		}

		return new Feedback(false, "Vous n'êtes pas enregistré");
	}

	/**
	 * Met à jour le professeur de l'élève "cookie"
	 * 
	 * @param cookie
	 * @param email
	 * @return
	 */
	@PUT
	@Path("/updateEmail/{cookie}/{idTeacher}")
	public Feedback updateIdTeacher(@PathParam("cookie") String cookie, @PathParam("idTeacher") int idTeacher) {
		if (Session.isLogged(cookie)) {
			TeacherResource tr = new TeacherResource();
			if(tr.getTeacher(idTeacher) == null) {
				return new Feedback(false, "Cet id ne correspond à aucun professeur.");
			}
			studentDao.updateIdTeacher(getStudent(cookie).getId(), idTeacher);
			return new Feedback(true, "Professeur changé !");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré.", "student");
	}

	/**
	 * Met à jour le mot de passe de l'élève "cookie"
	 * 
	 * @param cookie
	 * @param oldPassword
	 * @param password
	 * @return
	 */
	@PUT
	@Path("/updatePassword/{cookie}/{oldPassword}/{newPassword}")
	public Feedback updatePassword(@PathParam("cookie") String cookie, @PathParam("oldPassword") String oldPassword,
			@PathParam("newPassword") String password) {

		if (Session.isLogged(cookie)) {
			Student s;
			s = studentDao.studentIsCorrect(getStudent(cookie).getName(), Utils.hashMD5(oldPassword));

			if (s == null) {
				return new Feedback(false, "L'ancien mot de passe est invalide !");
			}

			String hashedPassword = Utils.hashMD5(password);
			Feedback f = isPasswordValid(hashedPassword);
			if (!f.isSuccess())
				return f;

			studentDao.updatePassword(getStudent(cookie).getId(), hashedPassword);
			return new Feedback(true, "Mot de passe changé !", "student");
		}

		return new Feedback(false, "Vous n'êtes pas enregistré");
	}

}
