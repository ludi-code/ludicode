package eu.ludicode.api.v2;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Instruction;
import fr.iutinfo.dao.InstructionsDao;

public class InstructionsResourceTest {
	
	private static InstructionsDao instructionsDao;

	@Before
	public void initTableInstructions() {
		instructionsDao = BDDFactory.getDbi().open(InstructionsDao.class);
		instructionsDao.dropInstructionsTable();
		instructionsDao.createInstructionsTable();
	}
	
	@Test
	public void table_must_be_empty() {
		InstructionsResource instructionsResource = new InstructionsResource();
		ArrayList<Instruction> instructions = (ArrayList<Instruction>) instructionsResource.getInstructions();
		Assert.assertEquals(0, instructions.size());
	}
	
	@Test
	public void table_must_not_be_empty_after_insert() {
		instructionsDao.insert("Avancer", "player.moveForward();", 65, 0, "images/doc/avancer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers avant.", 0);                    // ID 1
        InstructionsResource instructionsResource = new InstructionsResource();
		ArrayList<Instruction> instructions = (ArrayList<Instruction>) instructionsResource.getInstructions();
		Assert.assertTrue(instructions.size() > 0);
	}
	
	@Test 
	public void must_not_find_instruction() {
		Assert.assertEquals(instructionsDao.findById(2), null);
	}
	
	@Test
	public void must_find_instruction() {
		instructionsDao.insert("Avancer", "player.moveForward();", 65, 0, "images/doc/avancer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers avant.", 0);                    // ID 1
        instructionsDao.insert("Reculer", "player.moveBackward();", 65, 0, "images/doc/reculer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers l'arrière.", 0);                    // ID 2
        InstructionsResource instructionsResource = new InstructionsResource();
		Instruction instruction = instructionsResource.getInstruction(1);
		Assert.assertEquals(instruction.getName(), "Avancer");
	}
	
	@Test
	public void must_contains_3_instructions() {
		instructionsDao.insert("Avancer", "player.moveForward();", 65, 0, "images/doc/avancer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers avant.", 0);                    // ID 1
        instructionsDao.insert("Reculer", "player.moveBackward();", 65, 0, "images/doc/reculer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers l'arrière.", 0);                    // ID 2
        instructionsDao.insert("Pivoter à gauche", "player.turnLeft();", 65, 0, "images/doc/pivoter_gauche.png", "images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la gauche.", 0);            // ID 3
        InstructionsResource instructionsResource = new InstructionsResource();
        ArrayList<Instruction> instructions = (ArrayList<Instruction>) instructionsResource.getInstructions();
		Assert.assertTrue(instructions.size() == 3);
	}
	

}
