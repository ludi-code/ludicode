package eu.ludicode.api.v2;

import fr.iutinfo.BDDFactory;
import fr.iutinfo.beans.Student;
import fr.iutinfo.beans.Teacher;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.*;
import fr.iutinfo.utils.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Classe permettant d'initialiser la BDD.
 * @author vitsem
 */

@Path("/resetDb")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DbResetResource {

    private static FriendsRelationsDao friendDao = BDDFactory.getDbi().open(FriendsRelationsDao.class);
    private static UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
    private static TeacherDao teacherDao = BDDFactory.getDbi().open(TeacherDao.class);
    private static StudentDao studentDao = BDDFactory.getDbi().open(StudentDao.class);
    private static LevelDao levelDao = BDDFactory.getDbi().open(LevelDao.class);
    private static InstructionsDao instructionsDao = BDDFactory.getDbi().open(InstructionsDao.class);
    private static LevelListDao levelListDao = BDDFactory.getDbi().open(LevelListDao.class);
    private static LevelProgressDao levelProgressDAO = BDDFactory.getDbi().open(LevelProgressDao.class);
    
    private void deleteTables() {
    	userDao.dropUserTable();
    	levelListDao.dropLevelListAssociationsTable();
    	levelProgressDAO.dropLevelProgessTable();
    	levelDao.dropLevelsTable();
    	levelListDao.dropLevelListsTable();
    	instructionsDao.dropInstructionsTable();
    	studentDao.dropStudentTable();    
    	teacherDao.dropTeacherTable();	
    }
    
    private void initTables() {
    	teacherDao.createTeacherTable();
    	insertTeachers();
    	studentDao.createStudentTable();
    	insertStudents();
    	instructionsDao.createInstructionsTable();
    	insertInstructions();
    	levelListDao.createLevelListsTable();
    	insertLevelLists();
    	levelDao.createLevelsTable();
    	insertLevels();
    	levelProgressDAO.createLevelProgressTable();
    	insertLevelProgress();
    	levelListDao.createLevelListAssociationsTable();
    	insertLevelListAssociations();
    	userDao.createUserTable();
    	insertUsers();
    }
    
    private void insertTeachers() {
    	teacherDao.insert(new Teacher("totoro", Utils.hashMD5("totoro"), "totoro@totoro.to"));
    }
    
    private void insertStudents(){
    	studentDao.insert(new Student("toto", Utils.hashMD5("toto"), 1));
    	studentDao.insert(new Student("titi", Utils.hashMD5("titi"), 1));
    	studentDao.insert(new Student("tata", Utils.hashMD5("tata"), 1));
    }
    
    private void insertInstructions(){
    	instructionsDao.insert("Avancer", "player.moveForward();", 65, 0, "images/doc/avancer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers avant.", 0);                    // ID 1
        instructionsDao.insert("Reculer", "player.moveBackward();", 65, 0, "images/doc/reculer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers l'arrière.", 0);                    // ID 2
        instructionsDao.insert("Pivoter à gauche", "player.turnLeft();", 65, 0, "images/doc/pivoter_gauche.png", "images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la gauche.", 0);            // ID 3
        instructionsDao.insert("Pivoter à droite", "player.turnRight();", 65, 0, "images/doc/pivoter_droite.png", "images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la droite.", 0);            // ID 4
        instructionsDao.insert("Répeter 3 fois", "for (var i%line% = 0; i%line% < 3; ++i%line%)", 100, 1, "images/doc/répéter_n.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront executées 3 fois.", 1);    // ID 5
        instructionsDao.insert("Si chemin devant", "if (player.canGoForward())", 200, 1, "images/doc/si_devant.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin devant le personnage.", 2);    // ID 6
        instructionsDao.insert("Si chemin à gauche", "if (player.canGoLeft())", 200, 1, "images/doc/si_gauche.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin à gauche du personnage.", 2);    // ID 7
        instructionsDao.insert("Si chemin à droite", "if (player.canGoRight())", 200, 1, "images/doc/si_droite.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin à droite du personnage.", 2);    // ID 8
        instructionsDao.insert("Si chemin derrière", "if (player.canGoBackward())", 200, 1, "images/doc/si_derrière.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin dérrière le personnage.", 2);    // ID 9
        instructionsDao.insert("Répeter jusqu'a l'arrivée", "while (!player.hasArrived())", 100, 1, "images/doc/répéter_arrivée.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront executées jusqu'à ce que le personnage arrive à la fin.", 1); // ID 10	
        instructionsDao.insert("Si PAS de chemin devant", "if (!player.canGoForward())", 200, 1, "images/doc/si_non_devant.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin devant le personnage.", 2);    // ID 11
        instructionsDao.insert("Si PAS de chemin à gauche", "if (!player.canGoLeft())", 200, 1, "images/doc/si_non_gauche.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin à gauche du personnage.", 2);    // ID 12
        instructionsDao.insert("Si PAS de chemin à droite", "if (!player.canGoRight())", 200, 1, "images/doc/si_non_droite.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin à droite du personnage.", 2);    // ID 13
        instructionsDao.insert("Si PAS de chemin derrière", "if (!player.canGoBackward())", 200, 1, "images/doc/si_non_derrière.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin dérrière le personnage.", 2);    // ID 14
        instructionsDao.insert("Si chemin devant", "if (player.canGoForward())", 200, 2, "images/doc/si_devant_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin devant le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 15
        instructionsDao.insert("Si chemin à gauche", "if (player.canGoLeft())", 200, 2, "images/doc/si_gauche_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin à gauche le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 16
        instructionsDao.insert("Si chemin à droite", "if (player.canGoRight())", 200, 2, "images/doc/si_droite_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin à droite le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 17
        instructionsDao.insert("Si chemin derrière", "if (player.canGoBackward())", 200, 2, "images/doc/si_derrière_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin derrière le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 18
        instructionsDao.insert("Si PAS de chemin devant", "if (!player.canGoForward())", 200, 2, "images/doc/si_non_devant_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin devant le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 19
        instructionsDao.insert("Si PAS de chemin à gauche", "if (!player.canGoLeft())", 200, 2, "images/doc/si_non_gauche_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin à gauche le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 20
        instructionsDao.insert("Si PAS de chemin à droite", "if (!player.canGoRight())", 200, 2, "images/doc/si_non_droite_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin à droite le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 21
        instructionsDao.insert("Si PAS de chemin derrière", "if (!player.canGoBackward())", 200, 2, "images/doc/si_non_derrière_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin derrière le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);    // ID 22
    }
    
    private void insertLevelLists() {
    	levelListDao.createList("Tutoriel", 1);
        levelListDao.createList("Intermédiaire", 1);
        levelListDao.createList("Expert", 1);
    }
    
    private void insertLevels(){
    	levelDao.insert("Niveau 1", // name
                		"1 2 1," +            //
                        "1 0 1," +            // Level content
                        "1 3 1",            //
                        "1",                // instructions id list
                        2,                    // max number of instructions
                        1, "blocs",                        // author id
                        true);                // orienté

        levelDao.insert("Niveau 2", // name
        				"1 1 1," +            //
                        "2 0 3," +            // Level content
                        "1 1 1",            //
                        "1,3",                // instructions id list
                        3,                    // max number of instructions
                        1, "blocs",                        // author id
                        true);                // orienté


        levelDao.insert("Niveau 3", // name
        				"1 2 1," +            //
                        "1 0 1," +            // Level content
                        "1 0 1," +
                        "1 3 1",            //
                        "1,10",            // instructions id list
                        2,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 4", // name
        				"2 1 3," +            //
                        "0 1 0," +            // Level content
                        "0 0 0",            //
                        "1,3,10",            // instructions id list
                        4,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 5", // name
        				"2 1 1 1," +        //
                        "0 0 1 1," +        // Level content
                        "1 0 0 1," +
                        "1 1 0 3",            //
                        "1,3,4,10",        // instructions id list
                        5,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 1", // name
                		"2 1 1 1," +        //
                        "0 1 1 1," +        // Level content
                        "0 1 1 1," +
                        "0 0 0 3",            //
                        "1,2,4,5",            // instructions id list
                        5,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 2", // name
                		"3 0 0," +            //
                        "1 1 0," +            // Level content
                        "0 0 0," +
                        "2 1 1",            //
                        "1,2,3,5",            // instructions id list
                        5,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 3", // name
                		"2 1 1 1," +        //
                        "0 1 1 1," +        // Level content
                        "0 1 1 1," +
                        "0 0 0 3",            //
                        "1,3,7,10",        // instructions id list
                        4,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 4", // name
                		"2 1 1 1," +        //
                        "0 0 0 1," +        // Level content
                        "1 1 0 1," +
                        "1 1 0 1," +
                        "0 0 0 1," +
                        "3 1 1 1",            //
                        "1,3,4,7,8,10",        // instructions id list
                        6,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 5", // name
                		"1 1 1 1 2 1," +        //
                        "0 0 3 1 0 1," +        // Level content
                        "0 1 1 1 0 0," +
                        "0 1 1 1 0 1," +
                        "0 0 0 0 0 0," +
                        "1 1 1 0 1 1",            //
                        "1,3,4,7,8,10",        // instructions id list
                        4,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 1", // name
                		"1 1 1 1 1 1," +        //
                        "0 0 0 0 1 1," +        // Level content
                        "0 1 1 0 1 1," +
                        "2 1 0 0 0 3," +
                        "0 1 1 1 1 1," +
                        "0 1 1 1 1 1",            //
                        "1,4,6,11,10",        // instructions id list
                        5,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 2", // name
                		"1 2 1 1 0 3," +    //
                        "1 0 1 1 0 1," +    // Level content
                        "1 0 0 1 0 1," +
                        "0 0 1 1 0 1," +
                        "1 0 1 1 0 1," +
                        "0 0 0 0 0 1",        //
                        "1,3,4,7,8,10,11",    // instructions id list
                        7,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté

        levelDao.insert("Niveau 3", // name
        				"0 0 0 0 0 2 1," +    //
                        "1 0 1 1 1 1 1," +    // Level content
                        "1 0 0 0 0 0 1," +
                        "1 0 1 1 1 0 1," +
                        "1 1 0 0 0 0 1," +
                        "1 3 1 0 1 1 1," +
                        "1 0 0 0 1 1 1",        //
                        "1,10,15,3,4,7",    // instructions id list
                        6,                    // max number of instructions
                        1, "blocs",                    // author id
                        true);                // orienté
    }
    
    private void insertLevelProgress() {
    	levelProgressDAO.insert(1, 1);
        levelProgressDAO.insert(1, 2);
        levelProgressDAO.insert(1, 3);
        levelProgressDAO.insert(2, 1);
        levelProgressDAO.insert(2, 4);
    }
    
    private void insertLevelListAssociations() {
        levelListDao.insertAssociation(1, 1, 0);
        levelListDao.insertAssociation(1, 2, 1);
        levelListDao.insertAssociation(1, 3, 2);
        levelListDao.insertAssociation(1, 4, 3);
        levelListDao.insertAssociation(1, 5, 4);
        levelListDao.insertAssociation(2, 6, 0);
        levelListDao.insertAssociation(2, 7, 1);
        levelListDao.insertAssociation(2, 8, 2);
        levelListDao.insertAssociation(2, 9, 3);
        levelListDao.insertAssociation(2, 10, 4);
        levelListDao.insertAssociation(3, 11, 0);
        levelListDao.insertAssociation(3, 12, 1);
        levelListDao.insertAssociation(3, 13, 2);
    }
    
    private void insertUsers() {
    	userDao.insert(new User("toto", Utils.hashMD5("toto"), "toto@toto.to"));
        userDao.insert(new User("titi", Utils.hashMD5("titi"), "titi@titi.ti"));
        userDao.insert(new User("tata", Utils.hashMD5("tata"), "tata@tata.ta"));
    }

}
