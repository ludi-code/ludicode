package fr.iutinfo;

import org.skife.jdbi.v2.DBI;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;
import org.slf4j.Logger;

import javax.inject.Singleton;

@Singleton
public class BDDFactory {
    final static Logger logger = LoggerFactory.getLogger(BDDFactory.class);
    private static DBI dbi = null;

    public static DBI getDbi() {
        if (dbi == null) {
            SQLiteDataSource ds = new SQLiteDataSource();
            String dbFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "ludicode.db";
            ds.setUrl("jdbc:sqlite:" + dbFile);
            dbi = new DBI(ds);
            logger.debug("database file : " + dbFile);
        }
        return dbi;
    }
}
