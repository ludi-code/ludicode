package fr.iutinfo.beans;

import java.util.List;

/**
 * Allows to know the simultanous connected Users on the web site
 * Uses in MyServerEndpoint
 * @author Florent
 */
public class WebsocketConnectedUsers extends WebsocketObject {

    private List<User2> users;

    public WebsocketConnectedUsers() {
        setType(1);
    }

    public List<User2> getUsers() {
        return users;
    }

    public void setUsers(List<User2> users) {
        this.users = users;
    }

}
