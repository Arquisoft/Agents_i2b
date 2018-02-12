package view;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import domain.AgentLoginData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import services.ParticipantsService;

/**
 * Created by Nicolás on 17/02/2017.
 * Class that handles the login data response. Access the service layer and recovers the user
 */
public class UserResponseAction {
    private final ParticipantsService part;

    UserResponseAction(ParticipantsService part){
        this.part = part;
    }

    public ResponseEntity<AgentInfo> execute(AgentLoginData info){
        Agent user = part.getParticipant(info.getLogin(), info.getPassword());
        AgentInfoAdapter data = new AgentInfoAdapter(user);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(data.userToInfo(), HttpStatus.OK);
    }
}
