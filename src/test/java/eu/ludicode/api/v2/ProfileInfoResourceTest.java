package eu.ludicode.api.v2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.ProfileInfo;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;


public class ProfileInfoResourceTest {
	
    @Before
    public void initTableUser () {
        UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
        userDao.dropUserTable();
        userDao.createUserTable();
    }

	
	@Test
	public void test_getProfileInfo(){
        User user = new User();
        user.setEmail("email de test");
        user.setName("Napoleon");
        ProfileInfo info =  new ProfileInfo();    
        info.setUser(user); 
        Assert.assertTrue((info.getLevelsInfo()==null));
        Assert.assertTrue(info.getUser().getName().equals("Napoleon"));

	}

}

