package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.beans.LeaderboardRow;
import fr.iutinfo.beans.LevelInfo;
import fr.iutinfo.beans.LevelProgress;

/**
 * Database Access Object relative to LevelProgress
 *  LevelProgress associate a user with his progression in the level he succeeded in
 * Table LevelProgress
 * @author Florent
 */
public interface LevelProgressDao {

    @SqlUpdate("CREATE TABLE LevelProgress ("
            + "idUser INT NOT NULL, "
            + "idLevel INT NOT NULL, "
            + "CONSTRAINT pk_LevelProgress PRIMARY KEY (idUser, idLevel), "
            + "CONSTRAINT fk_user FOREIGN KEY (idUser) REFERENCES users(id), "
            + "CONSTRAINT fk_level FOREIGN KEY (idLevel) REFERENCES levels(id))")
    void createLevelProgressTable();

    @SqlUpdate("INSERT INTO LevelProgress(idUser, idLevel) "
            + "VALUES(:idUser, :idLevel)")
    void insert(@Bind("idUser") int idUser, @Bind("idLevel") int idLevel);

    @SqlQuery("SELECT * "
            + "FROM LevelProgress "
            + "WHERE idUser = :idUser AND idLevel = :idLevel")
    @RegisterMapperFactory(BeanMapperFactory.class)
    LevelProgress getLevel(@Bind("idUser") int idUser, @Bind("idLevel") int idLevel);

    @SqlQuery("SELECT idUser, count(idLevel) as countLevel, students.name "
            + "FROM LevelProgress INNER JOIN students "
            + "WHERE idUser = students.id GROUP BY idUser ORDER BY countLevel DESC")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<LeaderboardRow> getLevelsCount();

    @SqlQuery("SELECT idLevel "
            + "FROM LevelProgress "
            + "WHERE idUser = :idUser")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Integer> getLevelsFromUser(@Bind("idUser") int idUser);

    @SqlQuery("SELECT id, name "
            + "FROM LevelProgress INNER JOIN levels "
            + "WHERE idUser = :idUser AND idLevel = id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<LevelInfo> getFinishedLevels(@Bind("idUser") int idUser);

    @SqlUpdate("drop table if exists LevelProgress")
    void dropLevelProgessTable();

}
