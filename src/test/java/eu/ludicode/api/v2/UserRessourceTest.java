package eu.ludicode.api.v2;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;
import java.util.List;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserRessourceTest {
	@Before
	public void initTableUser() {
		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
		userDao.dropUserTable();
		userDao.createUserTable();
	}

	@Test
	public void create_should_return_same_user_with_id_not_null() {
		User user = new User();
		user.setEmail("email de test");
		user.setName("Napoleon");

		UserRessource userRessource = new UserRessource();
		User createdUser = userRessource.createUser(user);

		Assert.assertTrue(createdUser.getId() > 0);
	}

	@Test
	public void test_get_should_return_all_users() {
		User user = new User();
		user.setEmail("email de test");
		user.setName("Napoleon");
		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
		userDao.insert(user);
		
		UserRessource userRessource = new UserRessource();
		List<User> users = userRessource.readAll();
		
		Assert.assertEquals(1, users.size());
	}
}
