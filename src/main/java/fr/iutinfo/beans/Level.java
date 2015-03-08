package fr.iutinfo.beans;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private int id;
	private String name;
	private int authorId;
	private String content;
	private String instructions;
	private int maxInstructions;
	private List<Instruction> instructionsList;
	private int nextLevelId;

	public Level() {
		this(0);
	}

	public Level(int id) {
		this.id = id;
		instructionsList = new ArrayList<Instruction>();
	}

	public String content() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private List<List<Integer>> parseLevel(String content) {
		List<List<Integer>> structuredContent = new ArrayList<List<Integer>>();
		String[] lines = content.split(",");
		for(String line : lines) {
			String[] cells = line.split("\\s+");
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(String cell : cells) {
				list.add(Integer.parseInt(cell));
			}
			structuredContent.add(list);
		}
		return structuredContent;
	}

	private String serializeContent(List<List<Integer>> structuredContent) {
		String content = "";
		for(int i = 0 ; i < structuredContent.size() ; i++) {
			if(i != 0)
				content += ",";
			for(int j = 0 ; j < structuredContent.get(i).size() ; j++) {
				if(j != 0)
					content += " ";
				content += structuredContent.get(i).get(j);
			}
		}
		return content;
	}

	public void setStructuredContent(List<List<Integer>> structuredContent) {
		content = serializeContent(structuredContent);
	}

	public List<List<Integer>> getStructuredContent() {
		return parseLevel(content);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int i) {
		this.authorId = i;
	}

	public String instructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}


	private List<Integer> parseInstructions(String instructions) {
		ArrayList<Integer> structuredInstructions = new ArrayList<Integer>();
		String[] cells = instructions.split(",");
		for(String cell : cells) {
			structuredInstructions.add(Integer.parseInt(cell));
		}
		
		return structuredInstructions;
	}

	
	private String serializeInstructions(List<Integer> structuredInstructions) {
		String instructions = "";
		for(int i = 0 ; i < structuredInstructions.size() ; i++) {
			if(i != 0)
				instructions += ",";
			instructions += structuredInstructions.get(i);
		}
		return instructions;
	}
	
	public List<Integer> structuredInstructions() {
		return parseInstructions(instructions);
	}

	public void setStructuredInstructions(List<Integer> structuredInstructions) {
		this.instructions = serializeInstructions(structuredInstructions);
	}


	public List<Instruction> getInstructionsList() {
		return instructionsList;
	}

	public void setInstructionsList(List<Instruction> list) {
		this.instructionsList = list;
	}
	
	public int getMaxInstructions() {
		return maxInstructions;
	}

	public void setMaxInstructions(int maxInstructions) {
		this.maxInstructions = maxInstructions;
	}

	public int getNextLevelId() {
		return nextLevelId;
	}

	public void setNextLevelId(int nextLevelId) {
		this.nextLevelId = nextLevelId;
	}
}
