package application;

import java.util.ArrayList;

/**
 *
 */
public class User {
    String username;
    ArrayList<User> friendsList;

    public User(String username){
        this.username = username;
        this.friendsList = new ArrayList<>();
    }

    /**
     * @param username
     * @return
     */
    public User getFriend(String username){
        return null;
    }

    /**
     * @param friend
     */
    public void addFriend(User friend){

    }

    /**
     * @param friend
     */
    public void removeFriend(User friend){

    }
}
