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
        user1 = new Agent("User1", "User1@hola.com", "user1Password", "112233", 123);
        user2 = new Agent("User2", "User2@hola.com", "user2Password", "4455566", 456);
    }

    @Test
    public void testAdapter(){
        AgentInfoAdapter adapter = new AgentInfoAdapter(user1);
        AgentInfo info = adapter.userToInfo();
        assertEquals(info.getName(), user1.getName());
        assertEquals(info.getKind(), user1.getKind());
        assertEquals(info.getEmail(), user1.getEmail());
        assertEquals(info.getIdentifier(), user1.getIdentifier());
    }

    @Test
    public void testToString(){
        AgentInfoAdapter adapter = new AgentInfoAdapter(user2);
        AgentInfo info = adapter.userToInfo();
        String toString = info.toString();
        String test = "AgentInfo{name='User2', identifier='4455566', " +
                		  "email='User2@hola.com', kind='456'}";
        assertEquals(toString, test);
    }

}
