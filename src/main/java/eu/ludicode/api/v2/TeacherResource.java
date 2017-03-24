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
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.dao.TeacherDao;
import fr.iutinfo.utils.Session;
import fr.iutinfo.utils.Utils;

@Path("/teachers")
public class TeacherResource {
	private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);

	/**
	 * Insert le professeur si celui ci est valide.
	 * 
	 * @param teacher
	 * @return
	 */
	@POST
	@Path("/register")
	public Feedback createTeacher(Teacher teacher) {
		Feedback fb = null;
		try {
			fb = isValidTeacher(teacher);
			if (fb.isSuccess()) {

				// on hashe le mdp pour le protéger
				String hashedPassword = Utils.hashMD5(teacher.getPassword());
				if (hashedPassword == null)
					return new Feedback(false, "An error occurs during hashing");
				teacher.setPassword(hashedPassword);

				// on insert le professeur dans la bdd.
				teacherDao.insert(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during insertion to database");
		}
		return fb;
	}

	/**
	 * Liste tous les professeurs
	 * 
	 * @return liste des professeurs
	 */
	@GET
	public List<Teacher> readAll() {
		return teacherDao.getAll();
	}

	/**
	 * trie les professeurs
	 * 
	 * @return liste triée des professeurs
	 */
	@GET
	@Path("/sortByName")
	public List<Teacher> getSortTeachers() {
		return teacherDao.getAllNameSort();
	}

	/**
	 * Vérifie si le professeur existe déjà (pseudo/email), si il est valide
	 * (email correcte, pseudo correct, mdp correct...)
	 * 
	 * @param teacher
	 * @return
	 */
	private Feedback isValidTeacher(Teacher teacher) {

		// On test si le pseudo est correcte et disponible
		Feedback f = isNameValid(teacher.getName());
		if (!f.isSuccess())
			return f;

		// test si le mail est correct
		f = isMailValid(teacher.getEmail());
		if (!f.isSuccess())
			return f;

		// test si le mdp est correct
		f = isPasswordValid(teacher.getPassword());
		if (!f.isSuccess())
			return f;

		return new Feedback(true, "Register OK", "teacher");
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
		Teacher u = teacherDao.findByName(pseudo);
		if (u != null) {
			return new Feedback(false, "Le pseudo est déjà utilisé");
		}

		return new Feedback(true, "Pseudo valide et disponible", "teacher");
	}

	/**
	 * Fonction testant si l'adresse mail est valide et disponible
	 * 
	 * @param mail
	 * @return
	 */
	private Feedback isMailValid(String mail) {
		// test si l'email est correcte
		if (!mail.matches("^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$")) {
			return new Feedback(false, "L'adresse mail est incorrecte");
		}

		// Test si l'email a déjà été utilisée
		Teacher t = teacherDao.findByEmail(mail);
		if (t != null) {
			return new Feedback(false, "L'adresse email est déjà utilisée");
		}

		return new Feedback(true, "Mail valide et disponible", "teacher");
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

		return new Feedback(true, "Mot de passe correct", "teacher");
	}

	/**
	 * Connecte un professeur
	 * 
	 * @param teacher
	 * @return
	 */
	@POST
	@Path("/login")
	public Feedback logTeacher(Teacher teacher) {
		/*String hashedPassword = Utils.hashMD5(teacher.getPassword());
		if (hashedPassword == null)
			return new Feedback(false, "An error occurs during hashing");
		teacher.setPassword(hashedPassword);*/
		Teacher t = null;
		try {
			t = teacherDao.teacherIsCorrect(teacher.getName(), teacher.getPassword());
			if (t == null)
				return new Feedback(false, "Mauvais pseudo/mot de passe !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during query to database");
		}

		if (Session.isLogged(teacher))
			return new Feedback(false, "Vous êtes déjà connecté !");
		// Teacher logged
		// générate uniq id
		UUID id = UUID.randomUUID();
		// add to logged teachers
		Session.addUser(id.toString(), t);

		return new Feedback(true, id.toString(), "teacher");
	}

	/**
	 * Vérifie si un professeur est connecté
	 * 
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/isLogged/{cookie}")
	public Feedback isLogged(@PathParam("cookie") String cookie) {
		if (Session.isLogged(cookie))
			return new Feedback(true, "connected", "teacher");
		return new Feedback(false, "not connected");
	}

	/**
	 * Déconnecte le professeur
	 * 
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/logout/{cookie}")
	public Feedback logout(@PathParam("cookie") String cookie) {
		Session.removeUser(cookie);
		return new Feedback(true, "Vous avez bien été déconnecté", "teacher");
	}

	/**
	 * Return le professeur avec comme id "id"
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public Teacher getTeacher(@PathParam("id") int id) {
		return teacherDao.findById(id);
	}

	/**
	 * Recherche un professeur d'id "cookie"
	 * 
	 * @param cookie
	 * @return
	 */
	@GET
	@Path("/me/{cookie}")
	public Teacher getTeacher(@PathParam("cookie") String cookie) {
		if (Session.isTeacher(cookie)) {
			return (Teacher) Session.getUser(cookie);
		}
		return null;
	}

	/**
	 * Recherche tout les professeurs contenant "term"
	 * 
	 * @param term
	 * @return
	 */
	@GET
	@Path("/search")
	public List<Teacher> searchTeachers(@QueryParam("term") String term) {
		return teacherDao.searchTeachers(term + "%");
	}

	/**
	 * Cherche le professeur avec pour psedo "name"
	 * 
	 * @param name
	 * @return
	 */
	@GET
	@Path("/getId/{name}")
	public Teacher getId(@PathParam("name") String name) {
		return teacherDao.findByName(name);
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
			teacherDao.updateNotifDate(getTeacher(cookie).getId());
			return new Feedback(true, "update done", "teacher");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

	/**
	 * Permet de modifier son pseudo du professeur "cookie"
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

			teacherDao.updateName(getTeacher(cookie).getId(), pseudo);

			return new Feedback(true, "Nom change !", "teacher");
		}

		return new Feedback(false, "Vous n'êtes pas enregistre");
	}

	/**
	 * Met à jour l'email du professeur "cookie"
	 * 
	 * @param cookie
	 * @param email
	 * @return
	 */
	@PUT
	@Path("/updateEmail/{cookie}/{email}")
	public Feedback updateEmail(@PathParam("cookie") String cookie, @PathParam("email") String email) {

		if (Session.isLogged(cookie)) {
			Feedback f = isMailValid(email);

			if (!f.isSuccess())
				return f;

			teacherDao.updateEmail(getTeacher(cookie).getId(), email);
			return new Feedback(true, "Email change !", "teacher");

		}

		return new Feedback(false, "Vous n'êtes pas enregistre");
	}

	/**
	 * Met à jour le mot de passe du professeur "cookie"
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
			Teacher t;
			t = teacherDao.teacherIsCorrect(getTeacher(cookie).getName(), Utils.hashMD5(oldPassword));

			if (t == null) {
				return new Feedback(false, "L'ancien mot de passe est invalide !");
			}

			String hashedPassword = Utils.hashMD5(password);
			Feedback f = isPasswordValid(hashedPassword);
			if (!f.isSuccess())
				return f;

			teacherDao.updatePassword(getTeacher(cookie).getId(), hashedPassword);
			return new Feedback(true, "Mot de passe change !", "teacher");
		}

		return new Feedback(false, "Vous n'êtes pas enregistre");
	}
}