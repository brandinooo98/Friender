package application;

import application.Graph;
import application.User;

import java.util.*;

/**
 *
 */
public class SocialNetwork {
    private Graph graph;
    private Graph graphCopy;

    /*
     * SocialNetwork default no-argument constructor.
     */
    public SocialNetwork() {
        graph = new Graph(); // Initializes graph
        graphCopy = new Graph();
    }

    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it.
     *
     */
    public void constructGraph() {
    }

    /**
     * Helper method to get all Users in the graph.
     *
     * @return Set<String> of all the Users
     */
    public Set<String> getAllUsers() {
        return graph.getAllVertices();
    }

    /**
     * @param username
     */
    public void addUser(String username){

    }

    /**
     * @param user
     * @param friend
     */
    public void addFriend(String user, String friend){

    }

    public void removeFriend(String user, String friend){

    }

    /**
     * @param user
     * @return
     */
    public List<User> shortestPath(String user){
        // Dijsktra
        return null;
    }

    /**
     * @param username
     */
    public void export(String username){

    }

    /**
     * @param username
     * @return
     */
    public List<User> getFriends(String username){
        return null;
    }

    /**
     * @param user1
     * @param user2
     * @return
     */
    public List<User> getMutualFriends(String user1, String user2){
        return null;
    }

    /**
     *
     */
    public void displayStatus(){

    }
}
