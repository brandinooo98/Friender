package application;

import application.Graph;
import application.User;

import java.util.*;

/**
 *
 */
public class SocialNetwork implements SocialNetworkADT{
    private Graph graph;
    private Graph graphCopy;


    /**
     *
     */
    public SocialNetwork() {
        graph = new Graph(); // Initializes graph
        graphCopy = new Graph();
    }

    /**
     * Helper method to get all Users in the graph.
     *
     * @return Set<String> of all the Users
     */
    @Override
    public Set<String> getAllUsers() {
        return graph.getAllVertices();
    }

    /**
     * @param username
     */
    @Override
    public void addUser(String username){

    }

    /**
     * @param user
     * @param friend
     */
    @Override
    public void addFriend(String user, String friend){

    }

    @Override
    public void removeFriend(String user, String friend){

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
    public List<User> getFriends(String username){
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
}
