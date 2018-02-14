package view;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import domain.AgentLoginData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import services.AgentsService;

/**
 * Created by Nicolás on 17/02/2017.
 * Adapted by Alejandro on 14/02/2018 (Agents).
 * Class that handles the login data response. Access the service layer and recovers the agent.
 */
public class AgentResponseAction {
    private final AgentsService agentsService;

    AgentResponseAction(AgentsService part){
        this.agentsService = part;
    }

    public ResponseEntity<AgentInfo> execute(AgentLoginData info){
        Agent user = agentsService.getAgent(info.getLogin(), info.getPassword(), info.getKind());
        AgentInfoAdapter data = new AgentInfoAdapter(user);
        return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) 
        					   : new ResponseEntity<>(data.userToInfo(), HttpStatus.OK);
    }
}
