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

	/*
	 * Para este test se necesita el siguiente documento en la base de datos: {
	 * 	"_id": ObjectId("5893a06ace8c8e1b79d8a9a9"), 
	 * 	"_class": "Model.User",
	 * 	"firstName": "Maria", 
	 * 	"lastName": "MamaMia", 
	 * 	"password": "9gvHm9TI57Z9ZW8/tTu9Nk10NDZayLIgKcFT8WdCVXPeY5gF57AFjS/l4nKNY1Jq",
	 * 	"dateOfBirth": ISODate("1982-12-27T23:00:00.000Z"),
	 *  "address": "Hallo",
	 * 	"nationality": "Core", 
	 * 	"userId": "321", 
	 * 	"email": "asd" 
	 * }
	 */
	@Before
	public void setUp() {
		testedUser = new Agent("Luis", "Gracia", "LGracia@gmail.com", "Luis123", "Calle alfonso", "Spain", "147");
		repo.insert(testedUser);

		testedUser2 = new Agent("Maria", "MamaMia", "asd", "pass14753", "Hallo", "Core", "158");
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
		Agent user = dat.getParticipant("LGracia@gmail.com");
		user.setNationality("USA");
		Assert.assertEquals(user.getNationality(), "USA");
		Assert.assertNotEquals(testedUser.getNationality(), user.getNationality());
		Agent DBUser = dat.getParticipant("LGracia@gmail.com"); // just in case, same as before.
		// Should be different from as we changed a transient one.
		Assert.assertNotEquals(user.getNationality(), DBUser.getNationality()); 
	}

	@Test
	public void testUpdateInfoWithPassword() {
		// It should be previously encoded if the DB is given so this may be changed.
		Agent user = dat.getParticipant("LGracia@gmail.com");
		user.setPassword("confidencial");
		JasyptEncryptor encryptor = new JasyptEncryptor();
		dat.updateInfo(user);
		Agent userAfter = dat.getParticipant("LGracia@gmail.com");
		// They should be the same when we introduce the password.
		Assert.assertTrue(encryptor.checkPassword("confidencial", userAfter.getPassword())); 
		Assert.assertEquals(user, userAfter); // They should be the same user by the equals.

	}

	@Test
	public void testUpdateInfoAndAdaptation() {
		Agent user = dat.getParticipant("asd");
		Assert.assertEquals("Maria", user.getFirstName());
		Assert.assertEquals("MamaMia", user.getLastName());
		Assert.assertEquals("Hallo", user.getAddress());
		Assert.assertEquals("Core", user.getNationality());
		Assert.assertEquals("158", user.getUserId());
		Assert.assertEquals("asd", user.getEmail());

		AgentInfoAdapter userAdapter = new AgentInfoAdapter(user);

		AgentInfo userInfo = userAdapter.userToInfo();

		Assert.assertEquals(user.getFirstName(), userInfo.getFirstName());
		Assert.assertEquals(user.getLastName(), userInfo.getLastName());
		Assert.assertEquals(user.getEmail(), userInfo.getEmail());
		Assert.assertEquals(user.getUserId(), userInfo.getUserId());

		user.setFirstName("Pepa");
		user.setLastName("Trump");

		dat.updateInfo(user);
		Agent updatedUser = dat.getParticipant("asd");
		Assert.assertEquals("Pepa", updatedUser.getFirstName());
		Assert.assertEquals("Trump", updatedUser.getLastName());
		Assert.assertEquals("Hallo", updatedUser.getAddress());
		Assert.assertEquals("Core", updatedUser.getNationality());
		Assert.assertEquals("158", updatedUser.getUserId());
		Assert.assertEquals("asd", updatedUser.getEmail());

	}

}
