package application;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph implements GraphADT {

	Map<String, List<String>> graph; // data structure

	/**
	 * default constructor to initialize the graph
	 */
	public Graph() {
		graph = new Hashtable<String, List<String>>(); // instanciation
	}

	/**
	 * adds a new vertex to the graph
	 */
	@Override
	public void addVertex(String vertex) {
		// check if the vertex was empty
		if (vertex != null) { // add if it doesn't exist already
			graph.putIfAbsent(vertex, new LinkedList<String>());
		}
	}

	/**
	 * removes a vertex from the graph and all associated edges
	 */
	@Override
	public void removeVertex(String vertex) {
		// checks if vertex is null and that it exists
		if (vertex != null && graph.containsKey(vertex)) {
			graph.remove(vertex); // removes it

			// loops through each vertex and checks the edges
			for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
				String key = entry.getKey();
				// loops through each edge in the vertex
				for (String value : entry.getValue()) {
					// if the edge has this vertex in it, removes it
					if (value.equals(vertex)) {
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
	public void addEdge(String vertex1, String vertex2) {
		if (vertex1 != null && vertex2 != null) {
			if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
				if (!graph.get(vertex1).contains(vertex2)
						&& !graph.get(vertex2).contains(vertex1)) {
					graph.get(vertex1).add(vertex2);
					graph.get(vertex2).add(vertex1);
				}
			}
		}
	}

	@Override
	public void removeEdge(String vertex1, String vertex2) {
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
	public Set<String> getAllVertices() {
		return graph.keySet();
	}

	/**
	 * gets all adjacent verticies of given vertex
	 */
	@Override
	public List<String> getAdjacentVerticesOf(String vertex) {
		return graph.get(vertex);
	}

	/**
	 * gets number of edges in graph
	 */
	@Override
	public int size() {
		int size = 0;
		for (List<String> value : graph.values()) {
			size += value.size();
		}
		return size;
	}

	/**
	 * gets number of verticies in the graph
	 */
	@Override
	public int order() {
		return graph.keySet().size();
	}
}