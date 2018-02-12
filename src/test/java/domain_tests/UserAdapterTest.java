package domain_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;

/**
 * Created by Nicolás on 18/02/2017.
 */
public class UserAdapterTest {

    private Agent user1;
    private Agent user2;

    @Before
    public void setUp(){
        user1 = new Agent("User1", "User1Apellido", "User1@hola.com", "user1Password",
                			 "C/ hola", "spanish", "112233");
        user2 = new Agent("User2", "User2Apellido", "User2@hola.com", "user2Password",
                          "C/ hola", "spanish", "112233");
    }

    @Test
    public void testAdapter(){
        AgentInfoAdapter adapter = new AgentInfoAdapter(user1);
        AgentInfo info = adapter.userToInfo();
        assertEquals(info.getFirstName(), user1.getFirstName());
        assertEquals(info.getLastName(), user1.getLastName());
        assertEquals(info.getEmail(), user1.getEmail());
        assertEquals(info.getUserId(), user1.getUserId());
    }

    @Test
    public void testToString(){
        AgentInfoAdapter adapter = new AgentInfoAdapter(user2);
        AgentInfo info = adapter.userToInfo();
        String toString = info.toString();
        String test = "UserInfo{firstName='User2', lastName='User2Apellido', " +
                		  "userId='112233', email='User2@hola.com'}";
        assertEquals(toString, test);
    }

}
