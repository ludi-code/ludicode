package eu.ludicode.api.v2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import fr.iutinfo.beans.User2;
import fr.iutinfo.beans.WebsocketConnectedUsers;
import fr.iutinfo.beans.WebsocketMessage;
import fr.iutinfo.beans.WebsocketObject;

/*
 * Permet la création/gestion de cookie avec les utilisateurs connecté au serveur
 * @author morelf
 */

public class MyServerEndpoint{
	private static Map<String, Session> connectedUsers = new HashMap<String, Session>();
	private String cookie;
	
	/*
	 * @param Session session, la session en cours
	 * @param String cookie, un cookie
	 * 
	 * Rentre dans la liste des utilisateurs connectés les utilisateurs présents dans cookie s'il est accepté pour cette session
	 * Ferme la session sinon
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("cookie") String cookie){
		if(fr.iutinfo.utils.Session.isLogged(cookie)) {
			connectedUsers.put(cookie, session);
			this.cookie = cookie;
			
			broadcastObject(getConnectedUsers());
		} else {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * @return WebSocketConnectedUsers, la liste des utilisateurs connectés
	 * 
	 * Récupére la liste des utilisateurs simultanés
	 */
	private WebsocketConnectedUsers getConnectedUsers() {
		List<User2> users = new ArrayList<User2>();
		for(Map.Entry<String, Session> entry : connectedUsers.entrySet()) {
			users.add(fr.iutinfo.utils.Session.getUser(entry.getKey()));
		}
		WebsocketConnectedUsers wcu = new WebsocketConnectedUsers();
		wcu.setFormServer(true);
		wcu.setUsers(users);
		return wcu;
	}

	/*
	 *@param WebsocketObject o, socket web de type objet
	 *
	 *Appelle la fonction sendObjectTo pour la session actuelle 
	 */
	private void broadcastObject(WebsocketObject wo) {
		for(Map.Entry<String, Session> entry : connectedUsers.entrySet())
			sendObjectTo(wo, entry.getValue());
	}
	
	/*
	 *@param WebsocketObject o, socket web de type objet
	 *
	 *Appelle la fonction sendObjectTo pour la session actuelle si le cookie n'est pas celui sauvegardé
	 */
	private void broadcastExceptUserObject(WebsocketObject wo) {
		for(Map.Entry<String, Session> entry : connectedUsers.entrySet())
			if(!entry.getKey().equals(cookie))
				sendObjectTo(wo, entry.getValue());
	}

	/*
	 * @param WebsocketObject o, socket web de type objet
	 * @param Session session, la session en cours
	 * 
	 * Affiche dans session le contenu de o
	 */
	private void sendObjectTo(WebsocketObject o, Session session) {
		try {
			session.getBasicRemote().sendText(o.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @param String message, le message a envoyé
	 * @param Session session, la session 
	 * 
	 * Envoie message aux utilisateurs sauvegardés dans cookie s'il est accepté par session
	 * Ferme la session sinon
	 */
	@OnMessage
	public void onMessage(String message, Session session){
		if(fr.iutinfo.utils.Session.isLogged(cookie)) {
			WebsocketMessage websocketMessage = new WebsocketMessage();
			websocketMessage.setContent(message);
			websocketMessage.setFormServer(false);
			websocketMessage.setFrom(fr.iutinfo.utils.Session.getUser(cookie).getName());

			broadcastExceptUserObject(websocketMessage);
		} else {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * @param Session session, la session en cours
	 * 
	 * Retire tout les utlisateurs sauvegardés dans le cookie de la liste des utilisateurs connectés
	 */
	@OnClose
	public void onClose(Session session){
		connectedUsers.remove(cookie);
		
		broadcastObject(getConnectedUsers());
	}
}
