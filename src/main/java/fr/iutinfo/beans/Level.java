package fr.iutinfo.beans;

import java.util.ArrayList;
import java.util.List;

public class Level{

	private int id; // id du niveau
	private String name; //nom du niveau
	private int authorId; // id de l'auteur du niveau
	private String content; //Board mis en chaine de caractère possibilité de le parser.
	private String levelType; // Type du level (Carte, blocks, Python) #Pas sur pour Carte et Python
	private boolean orientation; // si niveau orienté ou pas
	private String instructions;
	private int maxInstructions; // nombre d'instruction maximum acceptées
	private List<Instruction> instructionsList;// liste d'instruction acceptées
	private LevelList levelList;// séquence contenant le level
	private String explications;//explcation du level (par default :"trouver le chemain") [utile en Python]
	private String solution;//solution du niveau [utile en Python]
	
	public Level() {
		this(0);
	}

	public Level(int id) {
		this.id = id;
		instructionsList = new ArrayList<Instruction>();
	}

	public Level(int id, String name, int authorId, String content, String levelType, boolean orientation, String instructions,
			int maxInstructions, List<Instruction>  instructionsList, String explications, String solution){
		this.id = id;
		this.name =name;
		this.authorId=authorId;
		this.content=content;
		this.levelType=levelType;
		this.orientation=orientation;
		this.instructions=instructions;
		this.maxInstructions=maxInstructions;
		this.instructionsList = instructionsList;
		this.levelList=null;
		this.explications=explications;
		this.solution=solution;
	}
	
	public String getContent() {return content;}

	public void setContent(String content) { this.content = content;}

	private Integer[][] parseLevel(String content) {
		ArrayList<ArrayList<Integer>> structuredContent = new ArrayList<ArrayList<Integer>>();
		String[] lines = content.split(",");
		
		for(String line : lines) {
			String[] cells = line.split("\\s+");
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			for(String cell : cells) {
				list.add(Integer.parseInt(cell));
			}
			
			structuredContent.add(list);
		}
		
		Integer[][] array = new Integer[structuredContent.size()][structuredContent.get(0).size()];
		
		for(int i = 0 ; i < structuredContent.size() ; i++) {
			structuredContent.get(i).toArray(array[i]);
		}
		
		return array;
	}

	private String serializeContent(Integer[][] structuredContent) {
		String content = "";
		for(int i = 0 ; i < structuredContent.length ; i++) {
			if(i != 0)
				content += ",";
			for(int j = 0 ; j < structuredContent[i].length ; j++) {
				if(j != 0)
					content += " ";
				content += structuredContent[i][j];
			}
		}
		return content;
	}

	public void setStructuredContent(Integer[][] structuredContent) {
		content = serializeContent(structuredContent);
	}

	public Integer[][] getStructuredContent() {
		if(levelType.equals("Python")){
			return null;
		}
		return parseLevel(content);
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	
	public String getlevelType() {return levelType;}
	
	public void setlevelType(String type){this.levelType = type;}

	public int getAuthorId() {return authorId;}
	public void setAuthorId(int i) {this.authorId = i;}

	public String instructions() {return instructions;}
	public void setInstructions(String instructions) {this.instructions = instructions;}

	private Integer[] parseInstructions(String instructions) {
		ArrayList<Integer> structuredInstructions = new ArrayList<Integer>();
		String[] cells = instructions.split(",");
		for(String cell : cells) {
			structuredInstructions.add(Integer.parseInt(cell));
		}
		Integer[] array = new Integer[structuredInstructions.size()];
		structuredInstructions.toArray(array);
		return array;
	}

	
	private String serializeInstructions(Integer[] structuredInstructions) {
		String instructions = "";
		for(int i = 0 ; i < structuredInstructions.length ; i++) {
			if(i != 0)
				instructions += ",";
			instructions += structuredInstructions[i];
		}
		return instructions;
	}
	
	public Integer[] getStructuredInstructions() {return parseInstructions(instructions);}
	public void setStructuredInstructions(Integer[] structuredInstructions) {this.instructions = serializeInstructions(structuredInstructions);}


	public List<Instruction> getInstructionsList() {return instructionsList;}
	public void setInstructionsList(List<Instruction> list) {this.instructionsList = list;}
	
	public int getMaxInstructions() {return maxInstructions;}
	public void setMaxInstructions(int maxInstructions) {this.maxInstructions = maxInstructions;}

	public LevelList getLevelList() {return levelList;}
	public void setLevelList(LevelList levelList) {this.levelList = levelList;}

	/**
	 * return true if the hero is oriented, false either way
	 * @return boolean
	 */
	public boolean getOrientation() {return this.orientation;}
	/**
	 * Setter for the orientation of the hero, true -> oriented, false -> unoriented
	 * @param orientation
	 */
	public void setOrientation(boolean orientation) {this.orientation=orientation;}
	
	public String getExplications(){return this.explications;}
	
	public void setExplications(String explications){this.explications=explications;}
	
	public String getSolution(){return this.solution;}
	
	public void setSolution(String solution){this.solution=solution;}
	
	public String toString(){
		return "Id du niveau: "+getId()+"\nNom du niveau: "+getName()+"\nId de l'auteur: "+getAuthorId()+"\nContenu: "+getContent()+"\nType de niveau: "+getlevelType()
		+"\nOrienté: "+getOrientation()+"\nInstructions: "+instructions()+"\nNombre max d'instructions: "+getMaxInstructions();
	}
	
}
