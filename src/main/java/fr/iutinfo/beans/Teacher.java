package fr.iutinfo.beans;

/**
 * Classe rassemblant les données d'un professeur.
 * @author vitsem
 *
 */

public class Teacher {
	private int id = 0;
    private String name;
    private String password;
    private String email;
    private String cookie;

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher() { }

    public Teacher(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCookie() { return cookie; }
    public void setCookie(String cookie) { this.cookie = cookie; }
    
    @Override
    public String toString() { return id + ": " + name; }
    
    public boolean equals(Object other) { return this.id == ((Teacher) other).id; }
	
}
