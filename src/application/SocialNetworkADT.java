package application;

import java.util.List;
import java.util.Set;

public interface SocialNetworkADT{
	
    /**
     * Helper method to get all Users in the graph.
     *
     * @return Set<String> of all the Users
     */
    public Set<String> getAllUsers();

    /**
     * @param username
     * @throws UserNotFoundException 
     */
    public void addUser(String username);

    /**
     * @param user
     * @param friend
     * @throws UserNotFoundException 
     */
    public void addFriend(String user, String friend);

    /**
     * @param user
     * @param friend
     * @throws UserNotFoundException 
     */
    public void removeFriend(String user, String friend) throws UserNotFoundException;

    /**
     * @param user
     * @return
     */
    public List<User> shortestPath(String user);

    /**
     * @param username
     */
    public void export(String username);

    /**
     * @param username
     * @return
     * @throws UserNotFoundException 
     */
    public List<User> getFriends(String username) throws UserNotFoundException;

    /**
     * @param user1
     * @param user2
     * @return
     */
    public List<User> getMutualFriends(String user1, String user2);

    /**
     *
     */
    public void displayStatus();

    public List<User> graphTraversal(String username) throws UserNotFoundException;
}