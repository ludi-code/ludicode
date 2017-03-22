package eu.ludicode.api.v2;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;



@Path("/users")
public class UserRessource {
    private static UserDao userDao = BDDFactory.getDbi().open(UserDao.class);

    @POST
    @Path("/register")
    public User createUser(User user) {
        user.setId(userDao.insert(user));
        return user;
    }
    
    @GET
    public List<User> readAll() {
    	return userDao.getAll();
    }
}
