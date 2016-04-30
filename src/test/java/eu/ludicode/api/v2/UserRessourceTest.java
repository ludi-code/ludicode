package eu.ludicode.api.v2;


import fr.iutinfo.beans.User;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

public class UserRessourceTest {
    @Test
    public void create_should_return_same_user_with_id_not_null () {
        User user = new User();
        user.setEmail("email de test");
        user.setName("Napoleon");

        UserRessource userRessource = new UserRessource();
        User createdUser = userRessource.createUser(user);

        Assert.assertTrue(createdUser.getId() > 0);
    }

}
