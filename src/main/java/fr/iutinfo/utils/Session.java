package fr.iutinfo.utils;

import java.util.HashMap;

import fr.iutinfo.beans.Student;
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.beans.User2;

public class Session {
	private static HashMap<String, User2> loggedUsers = new HashMap<String, User2>();
	
	public static void addUser(String cookie, User2 u) {
		loggedUsers.put(cookie, u);
	}
	
	public static void removeUser(String cookie) {
		loggedUsers.remove(cookie);
	}
	
	public static User2 getUser(String cookie) {
		return loggedUsers.get(cookie);
	}
	
	public static boolean isLogged(String cookie) {
		return loggedUsers.containsKey(cookie);
	}
	
	public static boolean isLogged(User2 u) {
		return loggedUsers.containsValue(u);
	}
	
	public static boolean isTeacher(String cookie) {
		return isLogged(cookie) && loggedUsers.get(cookie) instanceof Teacher;
	}
	
	public static boolean isStudent(String cookie) {
		return isLogged(cookie) && loggedUsers.get(cookie) instanceof Student;
	}
}
