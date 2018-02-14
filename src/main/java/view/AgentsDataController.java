package view;


import domain.AgentInfo;
import domain.AgentLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.AgentsService;

/**
 * This class acts as a controller for the POST requests
 * to query the information of an agent.
 * 
 * Adapted by Alejandro on 14/02/2018 (Agents).
 *
 */
@RestController
public class AgentsDataController {

    private final AgentsService part;

    @Autowired
    AgentsDataController(AgentsService part){
        this.part = part;
    }

    @RequestMapping(value = "/agent", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AgentInfo> userOkJSON(@RequestBody AgentLoginData info){
        AgentResponseAction act = new AgentResponseAction(part);
        return act.execute(info);
    }

}