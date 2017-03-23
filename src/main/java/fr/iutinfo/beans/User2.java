package fr.iutinfo.beans;

/**
 * Classe rassemblant les données d'un utilisateur.
 * Super classe de Student et Teacher.
 * @author vitsem
 */

public class User2 {
	
	private int id = 0;
    private String name;
    private String password;
    private String cookie;
    
    public User2(int id, String name){
    	this.id = id;
        this.name = name;
    }
    
    public User2(){ }
    
    public User2(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getCookie() { return cookie; }
    public void setCookie(String cookie) { this.cookie = cookie; }
    
    @Override
    public String toString() { return id + ": " + name; }
    
    public boolean equals(Object other) { return this.id == ((User2) other).id; }

}
