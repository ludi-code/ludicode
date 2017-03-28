package eu.ludicode.api.v2;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.LeaderboardRow;
import fr.iutinfo.beans.Level;
import fr.iutinfo.beans.LevelInfo;
import fr.iutinfo.beans.Student;
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.beans.User2;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.LevelProgressDao;
import fr.iutinfo.dao.StudentDao;
import fr.iutinfo.dao.TeacherDao;

public class LevelProgressResourceTest {

	private static LevelProgressDao levelProgressDao = BDDFactory.getDbi().open(LevelProgressDao.class);
	private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
	private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
	private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);
	
	@Before
	public void initTableInstructions() {
		teacherDao.dropTeacherTable();
		teacherDao.createTeacherTable();
		studentDao.dropStudentTable();
		studentDao.createStudentTable();
		levelDao.dropLevelsTable();
		levelDao.createLevelsTable();
		levelProgressDao.dropLevelProgessTable();
		levelProgressDao.createLevelProgressTable();
	}
	
	@Test
	public void table_must_be_empty() {
		Assert.assertEquals((ArrayList<Integer>)levelProgressDao.getLevelsFromUser(1), new ArrayList<Integer>());
	}
	
	@Test
	public void progress_must_be_found() {
		LevelProgressResource levelProgressResource = new LevelProgressResource();
		new UserResource().createUser(new User2("Georges", "1234"));
		levelDao.insert("Level", "", "", 0, 0, "", true);
		levelProgressDao.insert(1, 1);
		LevelInfo info = new LevelInfo();
		info.setId(1);
		info.setName("Level");
		ArrayList<LevelInfo> progress = (ArrayList<LevelInfo>)levelProgressResource.getLevelsDone(1);
		Assert.assertEquals(progress.get(0).getId(), info.getId());
		Assert.assertEquals(progress.get(0).getName(), info.getName());
	}
	
	@Test
	public void progress_must_not_be_found() {
		LevelProgressResource levelProgressResource = new LevelProgressResource();
		UserResource ur = new UserResource();
		ur.createUser(new User2("Georges", "1234"));
		ur.createUser(new User2("Edward", "7895"));
		new LevelResource().createLevel(new Level(), "");
		levelProgressDao.insert(1, 1);
		Assert.assertEquals(levelProgressResource.getLevelsDone(2).size(), 0);
	}
	
	@Test
	public void leaderboard() {
		LevelProgressResource levelProgressResource = new LevelProgressResource();
		teacherDao.insert(new Teacher());
		studentDao.insert(new Student("Georges", "1234", 1));
		studentDao.insert(new Student("Edward", "7895", 1));
		Level level1 = new Level();
		level1.setName("Level1");
		Level level2 = new Level();
		level2.setName("Level2");
		levelDao.insert("Level1", "", "", 0, 0, "", true);
		levelDao.insert("Level2", "", "", 0, 0, "", true);
		levelProgressDao.insert(1, 1);	
		levelProgressDao.insert(2, 2);	
		levelProgressDao.insert(2, 1);
		ArrayList<LeaderboardRow> lead = (ArrayList<LeaderboardRow>) levelProgressResource.getLeaderboard();
		Assert.assertEquals(lead.get(0).getName(), "Edward");
		Assert.assertEquals(lead.get(1).getName(), "Georges");
	}
}