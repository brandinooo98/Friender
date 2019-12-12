import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * implementation of a graph
 * 
 * @author ATeam 169
 */
public class Graph implements GraphADT {

	Map<User, List<User>> graph; // data structure

	/**
	 * default constructor to initialize the graph
	 */
	public Graph() {
		graph = new Hashtable<User, List<User>>(); // instanciation
	}

	/**
	 * adds a new vertex to the graph
	 * 
	 * @param vertex is the vertex to be added to the graph
	 */
	@Override
	public void addVertex(User vertex) {
		// check if the vertex was empty
		if (vertex != null) { // add if it doesn't exist already
			graph.putIfAbsent(vertex, new LinkedList<User>());
		}
	}

	/**
	 * removes a vertex from the graph and all associated edges
	 * 
	 * @param vertex is the vertex to be removed from the graph
	 */
	@Override
	public void removeVertex(User vertex) {
		// checks if vertex is null and that it exists
		if (vertex != null && graph.containsKey(vertex)) {
			graph.remove(vertex); // removes it

			// loops through each vertex and checks the edges
			for (Map.Entry<User, List<User>> entry : graph.entrySet()) {
				User key = entry.getKey();
				// loops through each edge in the vertex
				for (User value : entry.getValue()) {
					// if the edge has this vertex in it, removes it
					if (value.username.equals(vertex.username)) {
						this.removeEdge(key, vertex);
					}
				}
			}
		}
	}

	/**
	 * adds a new edge to the graph
	 * 
	 * @param vertex1 is the first vertex in the edge
	 * @param vertex2 is the second vertex in the edge
	 */
	@Override
	public void addEdge(User vertex1, User vertex2) {
		// checks if verticies are null
		if (vertex1 != null && vertex2 != null) {
			// checks if verticies exist in graph
			if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
				// checks if the edge already exist
				if (!graph.get(vertex1).contains(vertex2) && !graph.get(vertex2).contains(vertex1)) {
					// adds them to the graph
					graph.get(vertex1).add(vertex2);
					graph.get(vertex2).add(vertex1);
				}
			}
		}
	}

	/**
	 * removes an edge from the graph
	 * 
	 * @param vertex1 is the first vertex in the graph
	 * @param vertex2 is the second vertex in the graph
	 */
	@Override
	public void removeEdge(User vertex1, User vertex2) {
		// checks if the verticies are null
		if (vertex1 != null && vertex2 != null) {
			// checks if the verticies exist in the graph
			if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
				// checks if the edge already exists
				if (graph.get(vertex1).contains(vertex2) && graph.get(vertex2).contains(vertex1)) {
					// removes them from the graph
					graph.get(vertex1).remove(vertex2);
					graph.get(vertex2).remove(vertex1);
				}
			}
		}
	}

	/**
	 * gets all verticies as a list
	 * 
	 * @return a set of all Users in the graph
	 */
	@Override
	public Set<User> getAllVertices() {
		return graph.keySet();
	}

	/**
	 * gets all adjacent verticies of given vertex
	 * 
	 * @param vertex is the vertex that we want to find the adjacent verticies of
	 * @return a list of all Users that have an edge to or from the parameter vertex
	 */
	@Override
	public List<User> getAdjacentVerticesOf(User vertex) {
		return graph.get(vertex);
	}

	/**
	 * utility method to loop through the graph and find the User object that
	 * corresponds to the String username that is given as a parameter
	 * 
	 * @param username - username of the User object we want to find
	 * @return the user object that corresponds the the parameter string
	 * @throws UserNotFoundException if null is sent as a parameter or if there is
	 *                               no user with the given username in the graph
	 */
	@Override
	public User getNode(String username) throws UserNotFoundException {
		// checks if the username is null
		if (username == null)
			throw new UserNotFoundException();

		// loops through each user in the graph and checks the username
		Set<User> users = this.graph.keySet();
		for (User user : users) {
			if (username.equals(user.username))
				return user;
		} // if no user is found, throws exception
		throw new UserNotFoundException();
	}
}