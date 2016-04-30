package fr.iutinfo.beans;

public class FriendRelation {
	private int idUser = 0;
	private int idFriend = 0;
	
	public FriendRelation() {
	}
	
	public FriendRelation(int idUser, int idFriend) {
		this.idUser = idUser;
		this.idFriend = idFriend;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public int getIdFriend() {
		return idFriend;
	}
	
	public void setIdFriend(int idFriend) {
		this.idFriend = idFriend;
	}
}
