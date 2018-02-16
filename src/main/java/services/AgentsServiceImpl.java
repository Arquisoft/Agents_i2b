package services;

import domain.Agent;
import repositories.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.JasyptEncryptor;

/**
 * Created by Nicolás on 14/02/2017.
 * Adapted by Alejandro on 14/02/2018 (Agents).
 */
@Service
public class AgentsServiceImpl implements AgentsService {

    private final Database dat;
    private final JasyptEncryptor encryptor = new JasyptEncryptor();

    @Autowired
    AgentsServiceImpl(Database dat){
        this.dat = dat;
    }

    @Override
    public Agent getAgent(String username, String password, int kind) {
        Agent user = dat.getAgent(username);
        if(user != null && encryptor.checkPassword(password, user.getPassword()))
            return user;
        else return null;
    }

    @Override
    public void updateInfo(Agent agent, String newPassword) {
	    	//It is not necessary, done by the domain class itself.
	    	agent.setPassword(newPassword);
	    	dat.updateInfo(agent);
    }
}
