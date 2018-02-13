package domain_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Agent;
import util.JasyptEncryptor;

/**
 * Created by Damian on 15/02/2017.
 */
public class UserTest {

    private Agent nico;
    private Agent jorge;
    private Agent damian;

    @Before
    public void setUp(){
        nico = new Agent("Nicolás Rivero", "nico@nicomail.com","nico123");
        jorge = new Agent("Jorge Zapatero", "jorge@jorgemail.com", "jorge123", "111111111A", 1);
        damian = new Agent("Damian Rubio", "damian@damianmail.com", "damian123", "222222222B", 2);
    }

    @Test
    public void firstNameTest(){
        Assert.assertEquals("Nicolás Rivero", nico.getName());
        Assert.assertEquals("Jorge Zapatero", jorge.getName());
        Assert.assertEquals("Damian Rubio", damian.getName());

        nico.setName("Antonio A");
        Assert.assertEquals("Antonio A", nico.getName());

        jorge.setName("Pepe P");
        Assert.assertEquals("Pepe P", jorge.getName());

        damian.setName("Roberto R");
        Assert.assertEquals("Roberto R", damian.getName());
    }

    @Test
    public void emailTest(){

        nico.setEmail(damian.getEmail());
        Assert.assertEquals("damian@damianmail.com", nico.getEmail());

        jorge.setEmail("pepe@pepemail.com");
        Assert.assertEquals("pepe@pepemail.com", jorge.getEmail());

        damian.setEmail(jorge.getEmail());
        Assert.assertEquals("pepe@pepemail.com", damian.getEmail());
    }

    @Test
    public void passwordTest(){

    	JasyptEncryptor encryptor = new JasyptEncryptor();
    	
        nico.setPassword("1234");
        
    
        Assert.assertTrue(encryptor.checkPassword( "1234",nico.getPassword()));

        jorge.setPassword("abcd");
        Assert.assertTrue(encryptor.checkPassword( "abcd",jorge.getPassword()));

        damian.setPassword("yay");
        Assert.assertTrue(encryptor.checkPassword( "yay",damian.getPassword()));
    }

    @Test
    public void nifTest(){

        Assert.assertEquals(null, nico.getIdentifier());

        Assert.assertEquals("111111111A", jorge.getIdentifier());

        Assert.assertEquals("222222222B", damian.getIdentifier());
    }

}
