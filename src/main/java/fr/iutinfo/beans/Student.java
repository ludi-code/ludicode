package fr.iutinfo.beans;

/**
 * Classe rassemblant les données d'un élève.
 * @author vitsem
 *
 */

public class Student {
	
	private int id = 0;
    private String name;
    private String password;
    private int idTeacher;
    private String cookie;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() { }

    public Student(String name, String password, int idTeacher) {
        this.name = name;
        this.password = password;
        this.idTeacher = idTeacher;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getIdTeacher() { return idTeacher; }
    public void setIdTeacher(int idTeacher) { this.idTeacher = idTeacher; }

    public String getCookie() { return cookie; }
    public void setCookie(String cookie) { this.cookie = cookie; }
    
    @Override
    public String toString() { return id + ": " + name; }
    
    public boolean equals(Object other) { return this.id == ((Student) other).id; }

}
