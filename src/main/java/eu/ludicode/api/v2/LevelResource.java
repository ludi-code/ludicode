package eu.ludicode.api.v2;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import eu.ludicode.api.dto.Feedback;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Level;
import fr.iutinfo.beans.NotifLevel;
import fr.iutinfo.beans.NotifLevelCount;
import fr.iutinfo.beans.User2;
import fr.iutinfo.dao.InstructionsDao;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.utils.Session;
/**
 * Cette classe gere les niveaux
 * @author diabatm
 *
 */

@Path("/levels")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LevelResource {
	private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);
	private static InstructionsDao instructionsDao = BDDFactory.getDbi().open(InstructionsDao.class);
	private static LevelListDao levelListDao = BDDFactory.getDbi().open(LevelListDao.class);

	public LevelResource() {}

	@GET
	@Path("{id}")
	public Level getLevel(@PathParam("id") int id) {
		Level level = levelDao.findById(id);
		if(level == null)
			throw new WebApplicationException(404);
		level.setInstructionsList(instructionsDao.getAllId(Arrays.asList(level.getStructuredInstructions())));

		return level;
	}

	@GET
	@Path("list/{idList}/level/{position}")
	public Level getLevelOnList(@PathParam("idList") int idList, @PathParam("position") int position) {
		Level level = levelDao.getLevelOnList(idList, position);
		if(level == null)
			throw new WebApplicationException(404);

		level.setInstructionsList(instructionsDao.getAllId(Arrays.asList(level.getStructuredInstructions())));
		level.setLevelList(levelListDao.findById(idList));
		level.getLevelList().setLevelsAssociation(levelListDao.getAssociationsOf(level.getLevelList().getId()));
		return level;
	}

	@GET
	@Path("notifs/{cookie}")
	public List<NotifLevel> getLevelsNotifs(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			List<NotifLevel> notifs = levelDao.getNewLevelsFor(Session.getUser(cookie).getId());

			if(notifs == null)
				throw new WebApplicationException(404);
			
			return notifs;
		}
		throw new WebApplicationException(404);
	}
	
	@GET
	@Path("notifs/count/{cookie}")
	public NotifLevelCount getLevelsNotifsCount(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			NotifLevelCount notifCount = levelDao.getNewLevelsCountFor(Session.getUser(cookie).getId());

			if(notifCount == null)
				throw new WebApplicationException(404);
			
			return notifCount;
		}
		throw new WebApplicationException(404);
	}
	

	@GET
	public List<Level> getLevels() {
		List<Level> levels = levelDao.getAll();
		if(levels == null)
			throw new WebApplicationException(404);
		return levels;
	}

	@GET
	@Path("/author/{authorId}")
	public List<Level> getLevelsByAuthor(@PathParam("authorId") int authorId) {
		List<Level> levels = levelDao.getAllByAuthor(authorId);
		if(levels == null)
			throw new WebApplicationException(404);
		return levels;
	}

	/**
	 * Insert le niveau si celui ci est valide.
	 * @param level
	 * @return
	 */
	@POST
	@Path("/add/{cookie}")
	public Feedback createLevel(Level level, @PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			// User enregistré, l'envoie du niveau peux être effectué
			if(isValidLevel(level)) {
				// -1 comme prochain niveau de la série = dernier niveau

				levelDao.insert(level.getName(), level.getContent(), level.instructions(), level.getMaxInstructions(), Session.getUser(cookie).getId(), level.getlevelType(), level.getOrientation());

				return new Feedback(true, "Le niveau a bien été enregistré !");
			}
			return new Feedback(false, "Le niveau n'est pas valide ou à été corrompu.");
		}


		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}


	/**
	 * Insert le niveau si celui ci est valide.
	 * @param level
	 * @return
	 */
	@POST
	@Path("/add/{cookie}/{idList}")
	public Feedback addLevelToTeacherList(Level level, @PathParam("cookie") String cookie, @PathParam("idList") int idList) {
		if(Session.isLogged(cookie) && Session.isTeacher(cookie)) {
			// User enregistré, l'envoie du niveau peux être effectué
			if(isValidLevel(level)) {
				// -1 comme prochain niveau de la série = dernier niveau

				int idLevel = levelDao.insert(level.getName(), level.getContent(), level.instructions(), level.getMaxInstructions(), Session.getUser(cookie).getId(),level.getlevelType(),level.getOrientation());
				levelListDao.insertAssociation(idList, idLevel, levelListDao.getNextPosition(idList));

				return new Feedback(true, "Le niveau a bien été enregistré !");
			}
			return new Feedback(false, "Le niveau n'est pas valide ou à été corrompu.");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

	private boolean isValidLevel(Level level) {
		// TODO : Check the level
		return true;
	}

}