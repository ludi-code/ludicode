package eu.ludicode.api.v2;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.beans.LevelListAssociation;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.utils.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Cette classe permet de reccuperer et d'inserer une liste de level
 *
 * @author diabatm
 */

@Path("/levelLists")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LevelListResource {
    final static Logger logger = LoggerFactory.getLogger(LevelListResource.class);

    private static LevelListDao levelListDao = BDDFactory.getDbi().open(LevelListDao.class);
    private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);

    @GET
    @Path("{id}")
    public LevelList findLevelListById(@PathParam("id") int id) {
        LevelList list = levelListDao.findById(id);

        if (list == null)
            throw new WebApplicationException(404);

        list.setLevelsAssociation(levelListDao.getAssociationsOf(id));

        if (list.getLevelsAssociation() == null)
            throw new WebApplicationException(404);

        return list;
    }

    @GET
    public List<LevelList> getLevelLists() {
        List<LevelList> list = levelListDao.getAllLevelListWithCount();

        if (list == null)
            throw new WebApplicationException(404);

        return list;
    }

    @GET
    @Path("/me/{cookie}")
    public List<LevelList> getLevelListsOf(@PathParam("cookie") String cookie) {
        if (Session.isLogged(cookie)) {
            List<LevelList> list = levelListDao.findByIdAuthor(Session.getUser(cookie).getId());
            if (list == null)
                throw new WebApplicationException(404);

            return list;
        }
        throw new WebApplicationException(404);
    }

    @GET
    @Path("/me/full/{cookie}")
    public List<LevelList> getLevelListsWithContentOf(@PathParam("cookie") String cookie) {
        if (Session.isLogged(cookie)) {
            List<LevelList> list = levelListDao.findByIdAuthor(Session.getUser(cookie).getId());

            if (list == null)
                throw new WebApplicationException(404);

            for (LevelList levelList : list) {
                levelList.setLevels(levelDao.getLevelsOnList(levelList.getId()));
            }

            return list;
        }
        throw new WebApplicationException(404);
    }

    /**
     * crée une liste de niveaux vide
     *
     * @param levelList
     * @return
     */
    @POST
    @Path("/create/{cookie}")
    public Feedback createList(LevelList levelList, @PathParam("cookie") String cookie) {
        if (Session.isLogged(cookie)) {
            levelListDao.createList(levelList.getName(), Session.getUser(cookie).getId());

            return new Feedback(true, "La liste a bien été créée !");
        }

        return new Feedback(false, "Vous n'êtes pas enregistré !");
    }

    /**
     * crée une liste de niveaux vide
     *
     * @param levelList
     * @return
     */
    @PUT
    @Path("/me/{cookie}")
    public Feedback updateLevelLists(List<LevelList> levelLists, @PathParam("cookie") String cookie) {
        if (Session.isLogged(cookie)) {
            updateLevelLists(levelLists);
            return new Feedback(true, "Les listes ont bien été modifiés !");
        }

        return new Feedback(false, "Vous n'êtes pas enregistré !");
    }

    void updateLevelLists(List<LevelList> levelLists) {
        logger.debug("Update " + levelLists.size() + " livelList");
        for (LevelList list : levelLists) {
            levelListDao.deleteAssociationsOf(list.getId());
            List<LevelListAssociation> levelsAssociations = list.getLevelsAssociation();
            logger.debug("Update list : " + list.getId() + " with " + levelsAssociations.size() + " levelAssociations");
            for (int i = 0; i < levelsAssociations.size(); i++) {
                LevelListAssociation levelListAssociation = levelsAssociations.get(i);
                levelListDao.insertAssociation(list.getId(), levelListAssociation.getIdLevel(), i);
            }
        }
    }
}
