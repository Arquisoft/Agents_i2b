package domain_tests;

import domain.AgentLoginData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicolás on 18/02/2017.
 */
public class UserLoginDataTest {

    private AgentLoginData test;

    @Before
    public void setUp() {
        test = new AgentLoginData();
        test.setLogin("hola1");
        test.setPassword("holaPassword");
    }

    @Test
    public void getLogin() throws Exception{
        assertEquals("hola1", test.getLogin());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("holaPassword", test.getPassword());
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

}