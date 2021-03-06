package eu.ludicode.api.v1;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Instruction;
import fr.iutinfo.dao.InstructionsDao;

@Path("/instructions")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class InstructionsResource {

	private static InstructionsDao instructionsDao = BDDFactory.getDbi().open(InstructionsDao.class);


	public InstructionsResource() {}

	@GET
	@Path("{id}")
	public Instruction getInstruction(@PathParam("id") Integer id) {
		Instruction instruction = instructionsDao.findById(id);
		if(instruction == null)
			throw new WebApplicationException(404);
		
		return instruction;
	}

	@GET
	public List<Instruction> getInstructions() {
		List<Instruction> instructions = instructionsDao.getAll();
		if(instructions == null)
			throw new WebApplicationException(404);
		return instructions;
	}
}
