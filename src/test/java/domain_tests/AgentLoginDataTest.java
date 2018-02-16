package domain_tests;

import domain.AgentLoginData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicolás on 18/02/2017.
 * Adapted by Alejandro on 15/02/2017 (Agents).
 */
public class AgentLoginDataTest {

    private AgentLoginData test;
    private AgentLoginData test2;

    @Before
    public void setUp() {
        test = new AgentLoginData();
        test2 = new AgentLoginData("hola1", "holaPassword", 2);
        test.setLogin("hola1");
        test.setPassword("holaPassword");
        test.setKind(2);
    }

    @Test
    public void getLogin() throws Exception{
        assertEquals("hola1", test.getLogin());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("holaPassword", test.getPassword());
        assertEquals("holaPassword", test2.getPassword());
    }
    
    @Test
    public void getKind() throws Exception {
    		assertEquals(2, test.getKind());
    		assertEquals(2, test2.getKind());
    }

    @Test
    public void setLogin() throws Exception {
        test.setLogin("HOLAAAAAAAA");
        assertEquals("HOLAAAAAAAA", test.getLogin());
    }

    @Test
    public void setPassword() throws Exception {
        test.setPassword("PASWOOOOOOOORD");
        assertEquals("PASWOOOOOOOORD", test.getPassword());
    }
    
    @Test
    public void setKind() throws Exception {
    		test.setKind(3);
    		assertEquals(3, test.getKind());
    }

}