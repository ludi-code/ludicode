package eu.ludicode.api.v2;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Instruction;
import fr.iutinfo.dao.LevelProgressDao;

public class LevelProgressResourceTest {

	private static LevelProgressDao levelProgressDao = BDDFactory.getDbi().open(LevelProgressDao.class);
	
	@Before
	public void initTableInstructions() {
		levelProgressDao = BDDFactory.getDbi().open(LevelProgressDao.class);
		levelProgressDao.dropLevelProgessTable();
		levelProgressDao.createLevelProgressTable();
	}
	
	@Test
	public void table_must_be_empty() {
		LevelProgressResource levelProgressResource = new LevelProgressResource();
		fail("not implemented");
	}

}
