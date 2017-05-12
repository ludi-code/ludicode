package eu.ludicode.api.v2;

import eu.ludicode.api.v1.LevelResource;
import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Level;
import fr.iutinfo.dao.LevelDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class LevelResourceTest {
    private static LevelDao levelDao;

    @Before
    public void initTableDao() {
        levelDao = BDDFactory.getDbi().open(LevelDao.class);
        levelDao.dropLevelsTable();
        levelDao.createLevelsTable();
    }

    @Test
    public void table_must_be_empty() {
        LevelResource levelResource = new LevelResource();
        ArrayList<Level> levels = (ArrayList<Level>) levelResource.getLevels();
        Assert.assertEquals(0, levels.size());
    }

    @Test
    public void table_must_not_be_empty_after_insert() {
        Level l1 = new Level();
        levelDao.insert(l1.getName(), l1.getContent(), l1.instructions(), l1.getMaxInstructions(), l1.getAuthorId(), l1.getlevelType(), l1.getOrientation());
        LevelResource levelResource = new LevelResource();
        ArrayList<Level> levels = (ArrayList<Level>) levelResource.getLevels();
        Assert.assertTrue(levels.size() > 0);
    }

    @Test
    public void must_not_find_level() {
        Assert.assertEquals(levelDao.findById(2), null);
    }

    @Test
    public void must_find_level() {
        levelDao.insert("toto", "gggf", "2", 5, 0, "type", true);
        levelDao.insert("titi", "aze", "5", 8, 7, "type2", false);
        LevelResource levelResource = new LevelResource();
        Level level = levelResource.getLevel(2);
        Assert.assertEquals(level.getName(), "titi");
    }

    @Test
    public void must_contains_3_instructions() {
        levelDao.insert("toto", "gggf", "2", 5, 0, "type", true);
        levelDao.insert("titi", "aze", "5", 8, 7, "type2", false);
        levelDao.insert("tata", "qwe", "8", 4, 2, "type3", false);
        LevelResource levelResource = new LevelResource();
        ArrayList<Level> levels = (ArrayList<Level>) levelResource.getLevels();
        Assert.assertTrue(levels.size() == 3);
    }
}