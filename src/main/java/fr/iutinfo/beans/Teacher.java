package fr.iutinfo.beans;

/**
 * Classe rassemblant les données d'un professeur.
 * @author vitsem
 */

public class Teacher extends User2 {
	
	private String email;

    public Teacher(int id, String name) {
        super(id, name);
    }

    public Teacher() { super(); }

    public Teacher(String name, String password, String email) {
        super(name, password);
        this.email = email;
    }

    public int getId() { return super.getId(); }
    public void setId(int id) { super.setId(id); }

    public String getName() { return super.getName(); }
    public void setName(String name) { super.setName(name); }

    public String getPassword() { return super.getPassword(); }
    public void setPassword(String password) { super.setPassword(password); }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCookie() { return super.getCookie(); }
    public void setCookie(String cookie) { super.setCookie(cookie); }
    
    @Override
    public String toString() { return super.toString(); }
    
    public boolean equals(Object other) { return super.equals(other); }
	
}
