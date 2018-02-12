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
        nico = new Agent("Nicolás", "Rivero", "nico@nicomail.com","nico123");
        jorge = new Agent("Jorge", "Zapatero", "jorge@jorgemail.com", "jorge123", "C/ La calle", "España", "111111111A");
        damian = new Agent("Damian", "Rubio", "damian@damianmail.com", "damian123", "C/ The street", "Inglaterra", "222222222B");
    }

    @Test
    public void firstNameTest(){
        Assert.assertEquals("Nicolás", nico.getFirstName());
        Assert.assertEquals("Jorge", jorge.getFirstName());
        Assert.assertEquals("Damian", damian.getFirstName());

        nico.setFirstName("Antonio");
        Assert.assertEquals("Antonio", nico.getFirstName());

        jorge.setFirstName("Pepe");
        Assert.assertEquals("Pepe", jorge.getFirstName());

        damian.setFirstName("Roberto");
        Assert.assertEquals("Roberto", damian.getFirstName());
    }

    @Test
    public void lastNameTest(){

        nico.setLastName(jorge.getLastName());
        Assert.assertEquals("Zapatero", nico.getLastName());

        jorge.setLastName(damian.getLastName());
        Assert.assertEquals("Rubio", jorge.getLastName());

        damian.setLastName("Fernández");
        Assert.assertEquals("Fernández", damian.getLastName());
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
    public void addressTest(){

        nico.setAddress("C/ Su calle");
        Assert.assertEquals("C/ Su calle", nico.getAddress());

        jorge.setAddress(damian.getAddress());
        Assert.assertEquals("C/ The street", jorge.getAddress());

        damian.setAddress(nico.getAddress());
        Assert.assertEquals("C/ Su calle", damian.getAddress());
    }

    @Test
    public void nationalityTest(){

        Assert.assertEquals(null, nico.getNationality());
        nico.setNationality("Swazilandia");
        Assert.assertEquals("Swazilandia", nico.getNationality());

        jorge.setNationality(damian.getNationality());
        Assert.assertEquals("Inglaterra", jorge.getNationality());

        damian.setNationality(nico.getNationality());
        Assert.assertEquals("Swazilandia", damian.getNationality());
    }

    @Test
    public void nifTest(){

        Assert.assertEquals(null, nico.getUserId());

        Assert.assertEquals("111111111A", jorge.getUserId());

        Assert.assertEquals("222222222B", damian.getUserId());
    }

}
