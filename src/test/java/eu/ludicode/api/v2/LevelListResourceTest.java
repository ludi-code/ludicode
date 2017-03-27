/*package eu.ludicode.api.v2;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.dao.LevelListDao;

public class LevelListResourceTest {

	private static LevelListDao levelListDao;
	
	@Before
	public void initTableInstructions() {
		levelListDao = BDDFactory.getDbi().open(LevelListDao.class);
		levelListDao.dropLevelListsTable();
		levelListDao.createLevelListsTable();
	}
	@Test
	public void table_must_be_empty() {
		LevelListResource levelListResource = new LevelListResource();
		ArrayList<LevelList> levels = (ArrayList<LevelList>) levelListResource.getLevelLists();
		Assert.assertEquals(0, levels.size());
	}
	
	@Test
	public void table_must_not_be_empty_after_insert() {
		levelListDao.createList("toto", 1);
		LevelListResource levelListResource = new LevelListResource();
		ArrayList<LevelList> levels = (ArrayList<LevelList>) levelListResource.getLevelLists();
		Assert.assertTrue(levels.size()>0);
	}
	@Test 
	public void must_not_find_level() {
		Assert.assertEquals(levelListDao.findById(2), null);
	}
	@Test
	public void must_find_level() {
		levelListDao.createList("toto", 1);
		levelListDao.createList("tata", 2);
		LevelListResource levelListResource = new LevelListResource();
		LevelList level = levelListResource.getLevelList(1);
		Assert.assertEquals(level.getName(), "toto");
	}
	
	@Test
	public void must_contains_3_instructions() {
		levelListDao.createList("toto", 1);
		levelListDao.createList("tata", 2);
		levelListDao.createList("titi", 3);
		LevelListResource levelListResource = new LevelListResource();
		ArrayList<LevelList> levels = (ArrayList<LevelList>) levelListResource.getLevelLists();
		Assert.assertTrue(levels.size() == 3);
	}

}
*/