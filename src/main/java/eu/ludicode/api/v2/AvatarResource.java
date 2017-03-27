package eu.ludicode.api.v2;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.beans.User2;
import fr.iutinfo.utils.Session;

@Path("/avatars")
public class AvatarResource {
	
	public AvatarResource() {}

	 @GET
	 @Path("get/{cookie}")
	 public Feedback getAvatar(@PathParam("cookie") String cookie){

		 if(!Session.isLogged(cookie)){
			 return new Feedback(false,"Not logged");
	     }

	     User2 user = Session.getUser(cookie);

	     String filename = "src/main/webapp/images/avatars/" + user.getName() + ".png";
	     File file = new File(filename);
	     System.out.println(file.getAbsolutePath());
	     if (!file.exists()){
	    	 filename = "src/main/webapp/images/profil.png";
	    	 File file2 = new File(filename);
		     if (!file2.exists()){
		    	 filename = "tamere.png";
		     }
	     }
	     return new Feedback(true, filename);
	 }
}