package fr.iutinfo.beans;

import java.util.List;

/**
 * Associate a User with the levels relative to him
 * @author Florent
 */
public class ProfileInfo {

    private User2 user;
    private List<LevelInfo> levelsInfo;

    public ProfileInfo() {
    }

    public User2 getUser() { return user; }
    public void setUser(User2 user) { this.user = user; }

    public List<LevelInfo> getLevelsInfo() { return levelsInfo; }
    public void setLevelsInfo(List<LevelInfo> levelsInfo) { this.levelsInfo = levelsInfo; }

}
