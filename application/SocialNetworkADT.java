import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A interface for the SocialNetwork
 *
 * @author ATeam 169
 */
public interface SocialNetworkADT {

	/**
	 * Helper method to get all Users in the graph.
	 *
	 * @return Set<String> of all the Users
	 */
	public Set<String> getAllUsers();

	/**
	 * Adds a user to the graph
	 *
	 * @param username - User to be added
	 * @throws UserNotFoundException - If user is not found
	 */
	public void addUser(String username) throws UserNotFoundException;

	/**
	 * Removes a user from a graph
	 *
	 * @param username - User to be removed
	 * @throws UserNotFoundException - If user is not found
	 */
	public void removeUser(String username) throws UserNotFoundException;

	/**
	 * Adds a friendship between a user and a friend
	 *
	 * @param user - User to add a friendship from
	 * @param friend - User to become a friend
	 */
	public void addFriend(String user, String friend) throws UserNotFoundException;

	/**
	 * Removes a friendship from the graph
	 *
	 * @param user - User to remove friend of
	 * @param friend - Friend to be removed
	 * @throws UserNotFoundException - If the user or friend are not found
	 */
	public void removeFriend(String user, String friend) throws UserNotFoundException;

	/**
	 * Preforms Dijsktra's shortest path traversal
	 *
	 * @param username - User to start traversal from
	 * @return - List of the path
	 */
	public List<User> shortestPath(String user);

	/**
	 * Prints commands to a file of a given name
	 *
	 * @param filename - Name of file to print to
	 * @param commands - List of commands to print
	 * @throws FileNotFoundException - If file to print to is not found
	 */
	public void export(String filename, ArrayList<String> commands) throws FileNotFoundException;

	/**
	 * Imports and parses commands from a given file
	 *
	 * @param fileName - Name of file
	 * @return - ArrayList of commands
	 * @throws FileNotFoundException - If file of given filename is not found
	 */
	public ArrayList<String> importCommands(String fileName) throws FileNotFoundException;

	/**
	 * Returns a list of friends of a User
	 *
	 * @param username - User to find friends of
	 * @return - List of friends
	 * @throws UserNotFoundException - If given user is not found
	 */
	public List<User> getFriends(String username) throws UserNotFoundException;

	/**
	 * Returns a list of mutual friends between two Users
	 *
	 * @param username1 - First user
	 * @param username2 - Second user
	 * @return - List of mutual friends
	 */
	public List<User> getMutualFriends(String user1, String user2) throws UserNotFoundException;

	/**
	 *  Returns a depth first search of the graph
	 *
	 * @param username - User to be the central user of the traversal
	 * @return - List of the depth first search traversal
	 */
	public List<User> graphTraversal(String username) throws UserNotFoundException;
}