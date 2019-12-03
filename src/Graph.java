
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 */
	@Override
	public void addEdge(User vertex1, User vertex2) {
		if (vertex1 != null && vertex2 != null) {
			if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
				if (!graph.get(vertex1).contains(vertex2) && !graph.get(vertex2).contains(vertex1)) {
					graph.get(vertex1).add(vertex2);
					graph.get(vertex2).add(vertex1);
				}
			}
		}
	}

	@Override
	public void removeEdge(User vertex1, User vertex2) {
		if (vertex1 != null && vertex2 != null) {
			if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
				if (graph.get(vertex1).contains(vertex2) && graph.get(vertex2).contains(vertex1)) {
					graph.get(vertex1).remove(vertex2);
					graph.get(vertex2).remove(vertex1);
				}
			}
		}
	}

	/**
	 * gets all verticies as a list
	 */
	@Override
	public Set<User> getAllVertices() {
		return graph.keySet();
	}

	/**
	 * gets all adjacent verticies of given vertex
	 */
	@Override
	public List<User> getAdjacentVerticesOf(User vertex) {
		return graph.get(vertex);
	}

	@Override
	public User getNode(String username) throws UserNotFoundException {
		Set<User> users = this.graph.keySet();
		for (User user : users) {
			if (username.equals(user.username))
				return user;
		}
		throw new UserNotFoundException();
	}
}