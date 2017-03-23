package fr.iutinfo.beans;

/**
 * Classe rassemblant les données d'un élève.
 * @author vitsem
 */

public class Student extends User2 {
	
	private int idTeacher;

    public Student(int id, String name) {
        super(id, name);
    }

    public Student() { super(); }

    public Student(String name, String password, int idTeacher) {
        super(name, password);
        this.idTeacher = idTeacher;
    }

    public int getId() { return super.getId(); }
    public void setId(int id) { super.setId(id); }

    public String getName() { return super.getName(); }
    public void setName(String name) { super.setName(name); }

    public String getPassword() { return super.getPassword(); }
    public void setPassword(String password) { super.setPassword(password); }

    public int getIdTeacher() { return idTeacher; }
    public void setIdTeacher(int idTeacher) { this.idTeacher = idTeacher; }

    public String getCookie() { return super.getCookie(); }
    public void setCookie(String cookie) { super.setCookie(cookie); }
    
    @Override
    public String toString() { return super.toString(); }
    
    public boolean equals(Object other) { return super.equals(other); }

}
