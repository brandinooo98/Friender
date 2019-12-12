import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * class to test the graph implementation
 * 
 * @author Nate Wiltzius
 *
 */
public class GraphTest {

	static Graph graph; // graph

	/**
	 * initialize the graph
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		graph = new Graph();
	}

	/**
	 * delete the graph
	 * 
	 * @throws Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
		graph = null;
	}

	/**
	 * makes sure that the User was added to the graph correctly.
	 */
	@Test
	public void test_add_vertex() {
		User testUser = new User("testUser1");
		graph.addVertex(testUser); // add user to graph

		// check that its in the graph
		assertTrue(graph.getAllVertices().contains(testUser));
	}

	/**
	 * makes sure that the graph is empty after adding a null user.
	 */
	@Test
	public void test_add_null_vertex() {
		graph.addVertex(null); // add null to graph

		assertTrue(graph.getAllVertices().isEmpty());
	}

	/**
	 * make sure that the graph only contains one instance of a user if more than
	 * one is added
	 */
	@Test
	public void test_add_duplicate_vertex() {
		User testUser = new User("testUser1");
		// add 2 of the same user
		graph.addVertex(testUser);
		graph.addVertex(testUser);

		assertTrue(graph.getAllVertices().size() == 1);
	}

	/**
	 * makes sure that the graph does not contain a user after it has been removed
	 */
	@Test
	public void test_remove_vertex() {
		User testUser = new User("testUser1");

		// add and remove user
		graph.addVertex(testUser);
		graph.removeVertex(testUser);

		assertFalse(graph.getAllVertices().contains(testUser));
	}

	/**
	 * make sure that the graph contains the original user after removing null
	 */
	@Test
	public void test_remove_null_user() {
		User testUser = new User("testUser1");
		graph.addVertex(testUser);

		// remove null user
		graph.removeVertex(null);
		assertTrue(graph.getAllVertices().contains(testUser));
	}

	/**
	 * makes sure that removing a user that is not in the graph doesn't change
	 * anything
	 */
	@Test
	public void test_remove_fake_user() {
		// add one user
		User testUser = new User("testUser1");
		User fakeUser = new User("testUser2");
		// remove user that doesn't exist
		graph.addVertex(testUser);
		graph.removeVertex(fakeUser);

		assertTrue(graph.getAllVertices().contains(testUser) && !graph.getAllVertices().contains(fakeUser));
	}

	/**
	 * makes sure that the graph correctly adds undirected edges between the two
	 * verticies
	 */
	@Test
	public void test_add_edge() {
		User testUser1 = new User("testUser1");
		User testUser2 = new User("testUser2");

		// add verticies
		graph.addVertex(testUser1);
		graph.addVertex(testUser2);

		// add edge
		graph.addEdge(testUser1, testUser2);

		assertTrue(graph.getAdjacentVerticesOf(testUser1).contains(testUser2)
				&& graph.getAdjacentVerticesOf(testUser2).contains(testUser1));
	}

	/**
	 * makes sure that the graph does not add edges when given null verticies
	 */
	@Test
	public void test_add_edge_with_null_user() {
		User testUser1 = new User("testUser1");

		// add one vertex and create edge with null user
		graph.addVertex(testUser1);
		graph.addEdge(testUser1, null);

		assertTrue(graph.getAdjacentVerticesOf(testUser1).isEmpty());
	}

	/**
	 * makes sure that the graph correctly removes edges
	 */
	@Test
	public void test_remove_edge() {
		User testUser1 = new User("testUser1");
		User testUser2 = new User("testUser2");

		// add verticies
		graph.addVertex(testUser1);
		graph.addVertex(testUser2);

		// add edge
		graph.addEdge(testUser1, testUser2);
		// remove edge
		graph.removeEdge(testUser1, testUser2);

		assertFalse(graph.getAdjacentVerticesOf(testUser1).contains(testUser2)
				&& graph.getAdjacentVerticesOf(testUser2).contains(testUser1));
	}

	/**
	 * makes sure that the graph does not remove edges when given null verticies
	 */
	@Test
	public void test_remove_edge_with_null_user() {
		User testUser1 = new User("testUser1");
		User testUser2 = new User("testUser2");

		// add one vertex and create edge with null user
		graph.addVertex(testUser1);
		graph.addVertex(testUser2);
		graph.addEdge(testUser1, testUser2);
		// remove edge with null vertex
		graph.removeEdge(testUser1, null);

		assertTrue(graph.getAdjacentVerticesOf(testUser1).contains(testUser2)
				&& graph.getAdjacentVerticesOf(testUser2).contains(testUser1));
	}

	/**
	 * makes sure that removing an edge that doesn't exist doesn't change the graph
	 * or any existing edges
	 */
	@Test
	public void test_remove_fake_edge() {
		User testUser1 = new User("testUser1");
		User testUser2 = new User("testUser2");
		User testUser3 = new User("testUser3");
		User testUser4 = new User("testUser4");

		// add two verticies and create edge with existing users
		graph.addVertex(testUser1);
		graph.addVertex(testUser2);
		graph.addEdge(testUser1, testUser2);
		graph.removeEdge(testUser3, testUser4);

		assertTrue(graph.getAdjacentVerticesOf(testUser1).contains(testUser2)
				&& graph.getAdjacentVerticesOf(testUser2).contains(testUser1));
	}

	/**
	 * makes sure that getting all verticies of the graph returns and contains the
	 * correct verticies
	 */
	@Test
	public void test_get_all_verticies() {
		// creates 4 users
		User testUser1 = new User("testUser1");
		User testUser2 = new User("testUser2");
		User testUser3 = new User("testUser3");
		User testUser4 = new User("testUser4");

		// adds the users to the graph
		graph.addVertex(testUser1);
		graph.addVertex(testUser2);
		graph.addVertex(testUser3);
		graph.addVertex(testUser4);

		assertTrue(graph.getAllVertices().contains(testUser1) && graph.getAllVertices().contains(testUser2)
				&& graph.getAllVertices().contains(testUser3) && graph.getAllVertices().contains(testUser4));
	}

	/**
	 * makes sure that we can retrieve the adjacent verticies to the gtiven node. In
	 * other words, makes sure that edges are created correctly, and the references
	 * to other nodes are correctly stored in the graph.
	 */
	@Test
	public void test_get_adjacent_verticies_of() {
		User testUser1 = new User("testUser1");
		User testUser2 = new User("testUser2");

		// add one vertex and create edge with null user
		graph.addVertex(testUser1);
		graph.addVertex(testUser2);
		graph.addEdge(testUser1, testUser2);

		assertTrue(graph.getAdjacentVerticesOf(testUser1).contains(testUser2)
				&& graph.getAdjacentVerticesOf(testUser2).contains(testUser1));
	}

	/**
	 * utility method in the graph that lets us find User objects in the graph from
	 * the string username
	 */
	@Test
	public void test_get_node() {
		User testUser1 = new User("testUser1");

		graph.addVertex(testUser1);

		try {
			assertEquals(testUser1, graph.getNode("testUser1"));
		} catch (UserNotFoundException e) {

		}
	}

	/**
	 * makes sure that getNode throws an error when the user doesn't exist
	 */
	@Test
	public void test_get_node_fake_user() {
		User testUser1 = new User("testUser1");
		// adds vertex
		graph.addVertex(testUser1);

		// tries to get a fake user
		try {
			graph.getNode("fakeUser");
			fail("An exception should have been thrown");
		} catch (UserNotFoundException e) {
			// there should be an exception thrown
		}
	}

	/**
	 * makes sure that getNode throws an error when the user is null
	 */
	@Test
	public void test_get_node_null_user() {
		User testUser1 = new User("testUser1");
		// adds a vertex
		graph.addVertex(testUser1);

		// tries to get a null User
		try {
			graph.getNode(null);
			fail("An exception should have been thrown");
		} catch (UserNotFoundException e) {
			// there should be an exception thrown
		}
	}

}