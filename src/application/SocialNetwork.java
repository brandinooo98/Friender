package application;

import java.util.*;

/**
 *
 */

public class SocialNetwork implements SocialNetworkADT {
	private Graph graph;

	/**
	 *
	 */
	public SocialNetwork() {
		graph = new Graph(); // Initializes graph
	}

	/**
	 * Helper method to get all Users in the graph.
	 *
	 * @return Set<String> of all the Users
	 */
	@Override
	public Set<String> getAllUsers() {
		Set<String> names = new HashSet<String>();
		Set<User> users = graph.getAllVertices();

		users.stream().forEach(e -> names.add(e.username));

		return names;
	}

	/**
	 * @param username
	 * @throws UserNotFoundException
	 */
	@Override
	public void addUser(String username) {
		try {
			graph.getNode(username);
		} catch (UserNotFoundException e) {
			graph.addVertex(new User(username));
		}
	}

	/**
	 * @param user
	 * @param friend
	 */
	@Override
	public void addFriend(String user, String friend) {
		User user1;
		User user2;
		try {
			user1 = graph.getNode(user);
		} catch (UserNotFoundException e) {
			user1 = new User(friend);
		}

		try {
			user2 = graph.getNode(friend);
		} catch (UserNotFoundException e) {
			user2 = new User(friend);
		}

		graph.addEdge(user1, user2);

	}

	@Override
	public void removeFriend(String user, String friend)
			throws UserNotFoundException {
		User user1 = graph.getNode(user);
		User user2 = graph.getNode(friend);
		graph.removeEdge(user1, user2);
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public List<User> shortestPath(String user) {
		// Dijsktra
		return null;
	}

	/**
	 * @param username
	 */
	@Override
	public void export(String username) {

	}

	/**
	 * @param username
	 * @return
	 * @throws UserNotFoundException
	 */
	@Override
	public List<User> getFriends(String username) throws UserNotFoundException {
		if (graph.getNode(username) != null) {
			return graph.getAdjacentVerticesOf(graph.getNode(username));
		}
		return null;
	}

	/**
	 * @param user1
	 * @param user2
	 * @return
	 */
	@Override
	public List<User> getMutualFriends(String user1, String user2) {
		return null;
	}

	/**
	 *
	 */
	@Override
	public void displayStatus() {

	}

	/**
	 * @param username
	 * @return
	 */
	@Override
	public List<User> graphTraversal(String username)
			throws UserNotFoundException {
		LinkedList<User> visited = new LinkedList<>(); // Stores vertices
														// already traversed
														// through
		LinkedList<User> queue = new LinkedList<>(); // Used for traversal
		LinkedList<User> ordered = new LinkedList<>(); // Stores ordered
														// traversal

		User centralUser = graph.getNode(username);
		visited.add(centralUser);
		queue.add(centralUser);

		while (queue.size() != 0) {
			User user = queue.poll();
			ordered.add(user);

			List<User> adj = graph.getAdjacentVerticesOf(user);
			for (User adjUser : adj) {
				if (!visited.contains(adjUser)) {
					visited.add(adjUser);
					queue.add(adjUser);
				}
			}
		}
		return ordered;
	}
=======
public class SocialNetwork implements SocialNetworkADT{
    private Graph graph;

    /**
     *
     */
    public SocialNetwork() {
        graph = new Graph(); // Initializes graph
    }

    /**
     * Helper method to get all Users in the graph.
     *
     * @return Set<String> of all the Users
     */
    @Override
    public Set<String> getAllUsers() {
    	Set<String> names = new HashSet<String>();
        Set<User> users = graph.getAllVertices();
        
        users.stream().forEach(e -> names.add(e.username));
        
        return names;
    }

    /**
     * @param username
     */
    @Override
    public void addUser(String username) throws UserNotFoundException {
    	if (findUser(username) == null) {
    		graph.addVertex(new User(username));
    	}
    }

    /**
     * @param user
     * @param friend
     */
    @Override
    public void addFriend(String user, String friend) throws UserNotFoundException {
    	User user1 = findUser(user);
    	User user2 = findUser(friend);
    	
    	if (user1 == null) {
    		user1 = new User(user);
    	}
    	if (user2 == null) {
    		user2 = new User(friend);
    	}
    	
    	graph.addEdge(user1, user2);
    }

    @Override
    public void removeFriend(String user, String friend) throws UserNotFoundException {
    	User user1 = findUser(user);
    	User user2 = findUser(friend);
    	
    }

    /**
     * @param user
     * @return
     */
    @Override
    public List<User> shortestPath(String user){
        // Dijsktra
        return null;
    }

    /**
     * @param username
     */
    @Override
    public void export(String username){

    }

    /**
     * @param username
     * @return
     */
    @Override
    public List<User> getFriends(String username) throws UserNotFoundException {
    	if (findUser(username) != null) {
    		return graph.getAdjacentVerticesOf(findUser(username));
    	}
    	return null;
    }

    /**
     * @param user1
     * @param user2
     * @return
     */
    @Override
    public List<User> getMutualFriends(String user1, String user2){
        return null;
    }

    /**
     *
     */
    @Override
    public void displayStatus(){

    }

    /**
     * @param username
     * @return
     */
    @Override
    public List<User> graphTraversal(String username) throws UserNotFoundException {
        LinkedList<User> visited = new LinkedList<>(); // Stores vertices already traversed through
        LinkedList<User> queue = new LinkedList<>(); // Used for traversal
        LinkedList<User> ordered = new LinkedList<>(); // Stores ordered traversal

        User centralUser = findUser(username);
        visited.add(centralUser);
        queue.add(centralUser);

        while (queue.size() != 0){
            User user = queue.poll();
            ordered.add(user);

            List<User> adj = graph.getAdjacentVerticesOf(user);
            for (User adjUser : adj){
                if (!visited.contains(adjUser)){
                    visited.add(adjUser);
                    queue.add(adjUser);
                }
            }
        }
        return ordered;
    }

    /**
     * @param username
     * @return
     */
    private User findUser(String username) throws UserNotFoundException {
        Set<User> users = graph.getAllVertices();
        for (User user : users){
            if (username.equals(user.username))
                return user;
        }
        throw new UserNotFoundException();
    }
}
