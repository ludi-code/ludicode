package fr.iutinfo.beans;

public class LeaderboardRow {
	private String name;
	private int countLevel;
	private int idUser;
	
	public LeaderboardRow(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		System.out.println("setName");
		this.name = name;
	}
	public int getCountLevel() {
		return countLevel;
	}
	public void setCountLevel(int countLevel) {
		this.countLevel = countLevel;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		System.out.println("setIdUser");
		this.idUser = idUser;
	}
}
