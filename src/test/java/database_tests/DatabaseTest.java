package database_tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dbmanagement.Database;
import dbmanagement.UsersRepository;
import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import main.Application;
import util.JasyptEncryptor;

/**
 * Created by Nicolás on 15/02/2017.
 */
@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseTest {

	@Autowired
	private UsersRepository repo;

	// User to use as reference for test
	private Agent testedUser;
	private Agent testedUser2;

	@Autowired
	private Database dat;

	@Before
	public void setUp() {
		testedUser = new Agent("Luis Gracia", "LGracia@gmail.com", "Luis123", "147986", 12);
		repo.insert(testedUser);

		testedUser2 = new Agent("Maria MamaMia", "asd", "pass14753", "363636H", 25);
		repo.insert(testedUser2);
	}

	@After
	public void tearDown() {
		repo.delete(testedUser);
		repo.delete(testedUser2);
	}

	@Test
	public void testGetParticipant() {
		// It should be previously encoded if the DB is given so this may be changed.
		Agent user = dat.getAgent("LGracia@gmail.com");
		user.setIdentifier("USA");
		Assert.assertEquals(user.getIdentifier(), "USA");
		Assert.assertNotEquals(testedUser.getIdentifier(), user.getIdentifier());
		Agent DBUser = dat.getAgent("LGracia@gmail.com"); // just in case, same as before.
		// Should be different from as we changed a transient one.
		Assert.assertNotEquals(user.getIdentifier(), DBUser.getIdentifier()); 
	}

	@Test
	public void testUpdateInfoWithPassword() {
		// It should be previously encoded if the DB is given so this may be changed.
		Agent user = dat.getAgent("LGracia@gmail.com");
		user.setPassword("confidencial");
		JasyptEncryptor encryptor = new JasyptEncryptor();
		dat.updateInfo(user);
		Agent userAfter = dat.getAgent("LGracia@gmail.com");
		// They should be the same when we introduce the password.
		Assert.assertTrue(encryptor.checkPassword("confidencial", userAfter.getPassword())); 
		Assert.assertEquals(user, userAfter); // They should be the same user by the equals.

	}

	@Test
	public void testUpdateInfoAndAdaptation() {
		Agent user = dat.getAgent("asd");
		Assert.assertEquals("Maria MamaMia", user.getName());
		Assert.assertEquals(25, user.getKind());
		Assert.assertEquals("363636H", user.getIdentifier());
		Assert.assertEquals("asd", user.getEmail());

		AgentInfoAdapter userAdapter = new AgentInfoAdapter(user);

		AgentInfo userInfo = userAdapter.userToInfo();

		Assert.assertEquals(user.getName(), userInfo.getName());
		Assert.assertEquals(user.getKind(), userInfo.getKind());
		Assert.assertEquals(user.getEmail(), userInfo.getEmail());
		Assert.assertEquals(user.getIdentifier(), userInfo.getIdentifier());

		user.setName("Pepa Trump");

		dat.updateInfo(user);
		Agent updatedUser = dat.getAgent("asd");
		Assert.assertEquals("Pepa Trump", updatedUser.getName());
		Assert.assertEquals(25, updatedUser.getKind());
		Assert.assertEquals("363636H", updatedUser.getIdentifier());
		Assert.assertEquals("asd", updatedUser.getEmail());

	}

}
