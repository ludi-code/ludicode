package fr.iutinfo;

import fr.iutinfo.resources.*;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1/")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(LevelResource.class);
        s.add(UserResource.class);
        s.add(FriendsRelationsResource.class);
        s.add(DbResetResource.class);
        s.add(InstructionsResource.class);
        s.add(ProfileInfoResource.class);
        s.add(LevelListResource.class);
        s.add(LevelProgressRessource.class);
        s.add(AvatarResource.class);
        s.add(MultiPartFeature.class);
        return s;
    }
}
