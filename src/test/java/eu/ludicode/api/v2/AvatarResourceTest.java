package eu.ludicode.api.v2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.beans.User2;
import fr.iutinfo.utils.Session;
public class AvatarResourceTest {

	User2 user;
	
	@Before
	public void initUser(){
		user = new User2();
		user.setName("Artorias");
	}

	@Test
	public void test_get_should_return_good_Feedback(){
		AvatarResource avatar = new AvatarResource();
		Session.addUser("1", user);
		String cookie1 = "1";
		String cookie2 = "2";
		Feedback feed1=new Feedback(true,"src/main/webapp/images/avatars/Artorias.png");
		Feedback feed2=new Feedback(true,"Not logged");
		Assert.assertEquals(feed1.getMessage(), avatar.getAvatar(cookie1).getMessage());
		Assert.assertEquals(feed2.getMessage(), avatar.getAvatar(cookie2).getMessage());
	}
}
