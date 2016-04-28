package fr.iutinfo;

import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;

@Singleton
public class BDDFactory {
    private static DBI dbi = null;

    public static DBI getDbi() {
        if (dbi == null) {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:" + System.getProperty("user.home") + System.getProperty("file.separator") + "ludicode.db");
            dbi = new DBI(ds);
        }
        return dbi;
    }
}
