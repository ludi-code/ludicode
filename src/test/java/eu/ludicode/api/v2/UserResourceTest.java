package eu.ludicode.api.v2;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;

import java.util.List;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.ludicode.api.dto.Feedback;

public class UserResourceTest {
	@Before
	public void initTableUser() {
		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
		userDao.dropUserTable();
		userDao.createUserTable();
	}

	@Test
	public void create_should_return_same_user_with_id_not_null() {
		User user = new User();
		user.setEmail("email@test.com");
		user.setName("Napoleon");
		user.setPassword("coucou");

		UserResource userRessource = new UserResource();
		Feedback fb=userRessource.createUser(user);

		Assert.assertTrue(fb.isSuccess());
	}

	@Test
	public void test_get_should_return_all_users() {
		User user = new User();
		user.setEmail("email@test.com");
		user.setName("Napoleon");
		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
		userDao.insert(user);
		
		UserResource userRessource = new UserResource();
		List<User> users = userRessource.readAll();
		
		Assert.assertEquals(1, users.size());
	}
	
	@Test
	public void test_trie_alphabetique() {
		User user = new User();
		user.setEmail("email@test.com");
		user.setName("Napoleon");
		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
		userDao.insert(user);
		User user2 = new User();
		user2.setEmail("email2@test.com");
		user2.setName("Alf");
		userDao.insert(user2);
		
		UserResource userRessource = new UserResource();
		List<User> users = userRessource.getSortUsers();
		
		Assert.assertEquals("Alf", users.get(0).getName());
		Assert.assertEquals("Napoleon", users.get(1).getName());
	}
	
	@Test
	public void test_connection_utilisateur(){
		User user = new User();
		user.setEmail("email@test.com");
		user.setName("Napoleon");
		user.setPassword("coucou");

		UserResource userRessource = new UserResource();
		userRessource.createUser(user);
		
		User user2 = new User();
		user2.setName("Napoleon");
		user2.setPassword("coucou");
		Feedback fb=userRessource.logUser(user2);

		Assert.assertTrue(fb.isSuccess());
	}
	
	
}
