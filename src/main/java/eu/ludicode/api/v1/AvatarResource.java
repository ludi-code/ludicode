package eu.ludicode.api.v1;


import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.beans.User;
import fr.iutinfo.utils.Session;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Cette classe permet d'ajouter et de recuperer un avatar d'un joueur
 * @author diabatm
 *
 */
@Path("/avatars")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AvatarResource {

    @POST
    @Path("add/{cookie}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Feedback getLevel(@FormDataParam("file") InputStream uploadedInputStream,
                             @FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("cookie") String cookie) {

        if (!Session.isLogged(cookie))
            return new Feedback(false, "Not logged");

        if (fileDetail.getSize() > 42000)
            return new Feedback(false, "Image trop volumineuse (> 42Ko)");


        User user = Session.getUser(cookie);

        try {
            saveToFile(uploadedInputStream, "src/main/webapp/images/avatars/" + user.getName() + ".png");
        } catch (Exception e) {
            return new Feedback(false, e.getMessage());
        }

        return new Feedback(true, "images/avatars/" + user.getName() + ".png");
    }


    @GET
    @Path("get/{cookie}")
    public Feedback getAvatar(@PathParam("cookie") String cookie) {

        if (!Session.isLogged(cookie)) {
            return new Feedback(false, "Not logged");
        }

        User user = Session.getUser(cookie);

        String filename = "images/avatars/" + user.getName() + ".png";
        File file = new File(filename);

        if (!file.exists())
            filename = "images/profil.png";

        return new Feedback(true, filename);
    }


    /**
     * Sauvegarde un flux dans un fichier
     */
    private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws Exception {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[10244];
        int size = 0;

        File file = new File(uploadedFileLocation + "tmp");
        // On s'assure que le repertoire existe, sinon, on le crée
        file.getParentFile().mkdirs();
        out = new FileOutputStream(file);
        while ((read = uploadedInputStream.read(bytes)) != -1) {
            size += read;
            if (size > 42000) {
                // On supprime le fichier
                out.close();
                file.delete();
                throw new Exception("Image trop volumineuse (> 42Ko)");
            }
            out.write(bytes, 0, read);
        }

        out.flush();
        out.close();

        // On renome l'image temporaire
        File dest = new File(uploadedFileLocation);
        file.renameTo(dest);
    }


}
