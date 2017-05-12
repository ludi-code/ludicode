package eu.ludicode.api.v2;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.beans.LevelListAssociation;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.utils.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

public class LevelListResourceTest {

    private static LevelListDao levelListDao;

    @Before
    public void initTableInstructions() {
        levelListDao = BDDFactory.getDbi().open(LevelListDao.class);
        levelListDao.dropLevelListsTable();
        levelListDao.createLevelListsTable();
        levelListDao.dropLevelListAssociationsTable();
        levelListDao.createLevelListAssociationsTable();
    }

    @Test
    public void after_creation_list_of_levels_should_be_empty() {
        LevelListResource levelListResource = new LevelListResource();
        ArrayList<LevelList> levels = (ArrayList<LevelList>) levelListResource.getLevelLists();
        Assert.assertEquals(0, levels.size());
    }

    @Test(expected = WebApplicationException.class)
    public void must_not_find_level() {
        LevelListResource levelListResource = new LevelListResource();
        LevelList level = levelListResource.findLevelListById(3);
    }

    @Test
    public void must_find_level() {
        levelListDao.createList("toto", 1);
        levelListDao.createList("tata", 2);
        LevelListResource levelListResource = new LevelListResource();
        LevelList level = levelListResource.findLevelListById(1);
        Assert.assertEquals(level.getName(), "toto");
    }

    @Test
    public void must_contains_1_instructions() {
        LevelListAssociation levelListAssociation = new LevelListAssociation();
        LevelList levelList1 = new LevelList();
        List<LevelListAssociation> listOfLevelListAssociations = new ArrayList<>();
        listOfLevelListAssociations.add(levelListAssociation);
        levelList1.setLevelsAssociation(listOfLevelListAssociations);

        List<LevelList> listOfLevelLists = new ArrayList<>();
        listOfLevelLists.add(levelList1);

        LevelListResource levelListResource = new LevelListResource();
        levelList1.setId(levelListDao.createList(levelList1.getName(), 0));

        levelListResource.updateLevelLists(listOfLevelLists);

        ArrayList<LevelList> levels = (ArrayList<LevelList>) levelListResource.getLevelLists();
        Assert.assertEquals(1, levels.size());
    }
}