package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 *  Holds methods that preform operations and traversals on the graph
 *
 *  @author ATeam 169
 */
public class SocialNetwork implements SocialNetworkADT {
    private Graph graph;

    /**
     * Constructor of the SocialNetwork
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
     * Adds a user to the graph
     *
     * @param username - User to be added
     * @throws UserNotFoundException - If user is not found
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
     * Removes a user from a graph
     *
     * @param username - User to be removed
     * @throws UserNotFoundException - If user is not found
     */
    @Override
    public void removeUser(String username) throws UserNotFoundException {
        User user = graph.getNode(username);
        graph.removeVertex(user);
    }

    /**
     * Adds a friendship between a user and a friend
     *
     * @param user - User to add a friendship from
     * @param friend - User to become a friend
     */
    @Override
    public void addFriend(String user, String friend) {
        User user1;
        User user2;
        try {
            user1 = graph.getNode(user);
        } catch (UserNotFoundException e) {
            user1 = new User(friend);
            graph.addVertex(user1);
        }

        try {
            user2 = graph.getNode(friend);
        } catch (UserNotFoundException e) {
            user2 = new User(friend);
            graph.addVertex(user2);
        }

        graph.addEdge(user1, user2);
    }

    /**
     * Removes a friendship from the graph
     *
     * @param user - User to remove friend of
     * @param friend - Friend to be removed
     * @throws UserNotFoundException - If the user or friend are not found
     */
    @Override
    public void removeFriend(String user, String friend) throws UserNotFoundException {
        User user1 = graph.getNode(user);
        User user2 = graph.getNode(friend);
        graph.removeEdge(user1, user2);
    }

    /**
     * Preforms Dijsktra's shortest path traversal
     *
     * @param username - User to start traversal from
     * @return - List of the path
     */
    @Override
    public List<User> shortestPath(String username) {
        // Dijsktra
        return null;
    }

    /**
     * Prints commands to a file of a given name
     *
     * @param filename - Name of file to print to
     * @param commands - List of commands to print
     * @throws FileNotFoundException - If file to print to is not found
     */
    @Override
    public void export(String filename, ArrayList<String> commands) throws FileNotFoundException {
        File file = new File(filename); // Creates instance of given file
        PrintWriter printWriter = new PrintWriter(file);
        // Prints all commands onto file
        for (String command : commands)
            printWriter.println(command);
        printWriter.close();
    }

    /**
     * Imports and parses commands from a given file
     *
     * @param fileName - Name of file
     * @return - ArrayList of commands
     * @throws FileNotFoundException - If file of given filename is not found
     */
    public ArrayList<String> importCommands(String fileName) throws FileNotFoundException {
        File file = new File(fileName); // Creates instance of given file
        Scanner input = new Scanner(file);
        ArrayList<String> commands = new ArrayList<>(); // Stores commands to be read
        // If no commands
        if (!input.hasNext()) {
            input.close();
            return commands;
        } else {
            // While text is still on the file, adds to commands ArrayList
            while (input.hasNext()) {
                String command = input.nextLine();
                commands.add(command);
            }
            input.close();
            return commands;
        }
    }

    /**
     * Returns a list of friends of a User
     *
     * @param username - User to find friends of
     * @return - List of friends
     * @throws UserNotFoundException - If given user is not found
     */
    @Override
    public List<User> getFriends(String username) throws UserNotFoundException {
        if (graph.getNode(username) != null)
            return graph.getAdjacentVerticesOf(graph.getNode(username));
        return null;
    }

    /**
     * Returns a list of mutual friends between two Users
     *
     * @param username1 - First user
     * @param username2 - Second user
     * @return - List of mutual friends
     */
    @Override
    public List<User> getMutualFriends(String username1, String username2) throws UserNotFoundException {
        List<User> mutualFriends = new ArrayList<>(); // List of mutual friends to be returned

        // Iterates through each user's friends list and checks for mutual friends
        for (User user1Friend : getFriends(username1)) {
            for (User user2Friend : getFriends(username2)) {
                if (!mutualFriends.contains(user1Friend) && user1Friend.equals(user2Friend))
                    mutualFriends.add(user1Friend);
            }
        }
        return mutualFriends;
    }

    /**
     *  Returns a depth first search of the graph
     *
     * @param username - User to be the central user of the traversal
     * @return - List of the depth first search traversal
     */
    @Override
    public List<User> graphTraversal(String username) throws UserNotFoundException {
        LinkedList<User> visited = new LinkedList<>(); // Stores vertices already traversed through
        LinkedList<User> queue = new LinkedList<>(); // Stores vertices used for traversal
        LinkedList<User> ordered = new LinkedList<>(); // Stores vertices in BFS order

        // Starts traversal from given user
        User centralUser = graph.getNode(username);
        visited.add(centralUser);
        queue.add(centralUser);

        // Iterates until all nodes have been traversed
        while (queue.size() != 0) {
            User user = queue.poll();
            ordered.add(user); // Adds node to list to be returned

            // Adds all adjacent nodes to queue if not visited already
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
}