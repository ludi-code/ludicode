package eu.ludicode.api.v2;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Session;
import fr.iutinfo.utils.Utils;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;



@Path("/users")
public class UserResource {
    private static UserDao userDao = BDDFactory.getDbi().open(UserDao.class);

    /**
	 * Insert l'utilisateur si celui ci est valide.
	 * @param user
	 * @return
	 */
    @POST
    @Path("/register")
    public Feedback createUser(User user) {
    	Feedback fb = null;
		try {
			fb = isValidUser(user);
			if(fb.isSuccess()) {

				// on hashe le mdp pour le protéger
				String hashedPassword = Utils.hashMD5(user.getPassword());
				if(hashedPassword == null)
					return new Feedback(false, "An error occurs during hashing");
				user.setPassword(hashedPassword);

				// on insert l'utilisateur dans la bdd.
				userDao.insert(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during insertion to database");
		}
		return fb;
    }
    
    @GET
    public List<User> readAll() {
    	return userDao.getAll();
    }
    
    @GET
	@Path("/sortByName")
	public List<User> getSortUsers() {
		return userDao.getAllNameSort();
	}
    
    /**
	 * Vérifie si l'utilisateur existe déjà (pseudo/email), si il est valide (email correcte, pseudo correct, mdp correct...)
	 * @param u
	 * @return
	 */
	private Feedback isValidUser(User user) {

		// On test si le pseudo est correcte et disponible
		Feedback f = isNameValid(user.getName());
		if (!f.isSuccess()) 
			return f;

		// test si le mail est correct 
		f = isMailValid(user.getEmail());
		if (!f.isSuccess())
			return f;

		// test si le mdp est correct
		f = isPasswordValid(user.getPassword());
		if (!f.isSuccess())
			return f;


		return new Feedback(true, "Register OK");
	}
    
    /**
	 * Fonction testant si le pseudo est disponible et valide
	 * @param pseudo
	 * @return
	 */
	private Feedback isNameValid(String pseudo) {
		if(!pseudo.matches("^[a-zA-Z0-9À-ÿ-]{3,20}$")) {
			return new Feedback(false, "Le pseudo est invalide !");
		}

		// Test si le pseudo existe déjà ou non
		User u = userDao.findByName(pseudo);
		if(u != null){
			return new Feedback(false, "Le pseudo est déjà utilisé");
		}

		return new Feedback(true, "Pseudo valide et disponible");
	}

	/**
	 * Fonction testant si l'adresse mail est valide et disponible
	 * @param mail
	 * @return
	 */
	private Feedback isMailValid(String mail) {
		// test si l'email est correcte
		if(!mail.matches("^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$")) {
			return new Feedback(false, "L'adresse mail est incorrecte");
		}

		// Test si l'email a déjà été utilisée
		User u = userDao.findByEmail(mail);
		if(u != null){
			return new Feedback(false, "L'adresse email est déjà utilisée");
		}

		return new Feedback(true, "Mail valide et disponible");
	}

	private Feedback isPasswordValid(String password) {

		if(password.length() < 6) {
			return new Feedback(false, "Le mot de passe doit faire au moins 6 caractères !");
		}

		return new Feedback(true, "Mot de passe correct");
	}
	
	@POST
	@Path("/login")
	public Feedback logUser(User user) {
		String hashedPassword = Utils.hashMD5(user.getPassword());
		if(hashedPassword == null)
			return new Feedback(false, "An error occurs during hashing");
		user.setPassword(hashedPassword);
		User u = null;
		try {
			u = userDao.userIsCorrect(user.getName(), user.getPassword());
			if(u == null) 
				return new Feedback(false, "Mauvais pseudo/mot de passe !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during query to database");
		}

		if(Session.isLogged(user))
			return new Feedback(false, "Vous êtes déjà connecté !");
		// User logged
		// générate uniq id
		UUID id = UUID.randomUUID();
		// add to logged users
		Session.addUser(id.toString(), u);

		return new Feedback(true, id.toString());
	}
}
