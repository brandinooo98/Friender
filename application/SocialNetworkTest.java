import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
/**
 *
 */
public class SocialNetworkTest {
    SocialNetwork test;
    @Test
    public void test001_UserAdd() {
        test = new SocialNetwork();
        test.addUser("Jeff");
        if (test.getAllUsers().isEmpty())
            fail("Did not correclty add user");
    }

    @Test
    public void test002_removeUser() {
        test = new SocialNetwork();
        test.addUser("Jeff");
        test.addUser("Greg");
        test.addUser("Nick");
        test.addUser("Carl");
        try {
            test.removeUser("Jeff");
        } catch (UserNotFoundException c) {
            fail("Threw unexpected UserNotFoundException when trying to remove user");
        }

        if (test.getAllUsers().contains("Jeff"))
            fail("did not remove user");
    }

    @Test
    public void test003_addFriend() {
        test = new SocialNetwork();
        test.addUser("Jeff");
        test.addUser("Carl");
        test.addFriend("Jeff", "Carl");
        try {
            if (!test.getFriends("Jeff").contains("Carl"))
                fail("Adding friend did not work");
        } catch (UserNotFoundException e) {
            fail("Threw unexpected UserNotFoundException exception");
        }
    }

    @Test
    public void test004_mutualFriend() {
        test = new SocialNetwork();
        test.addUser("Jeff");
        test.addUser("Carl");
        test.addUser("Fred");
        test.addFriend("Jeff", "Fred");
        test.addFriend("Carl", "Fred");

        try {
            if (!test.getMutualFriends("Jeff", "Carl").contains("Fred"))
                fail("Did not add a mutual friend to list");
        } catch (UserNotFoundException e) {
            fail("Unexpected UserNotFoundException thrown");
        }
    }

    @Test
    public void test005_removeFriend() {
        test = new SocialNetwork();
        test.addUser("Jeff");
        test.addUser("Fred");
        test.addFriend("Jeff", "Fred");
        try {
            test.removeFriend("Jeff", "Fred");
            if (test.getFriends("Jeff").contains("Fred"))
                fail("Removing friend did not work");
        } catch (UserNotFoundException e) {
            fail("Unexpected UserNotFoundException exception thrown");
        }
    }

    @Test
    public void test006_getAllUsers() {
        test = new SocialNetwork();
        test.addUser("Jeff");
        test.addUser("Carl");
        test.addUser("Fred");
        test.addUser("Bob");
        Set<String> users = new HashSet<>();
        users.add("Jeff");
        users.add("Carl");
        users.add("Fred");
        users.add("Bob");

        if (!users.equals(test.getAllUsers()))
            fail("Did not return correct set of users");
    }
}