package view;


import domain.AgentInfo;
import domain.AgentLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.ParticipantsService;

@RestController
public class ParticipantsDataController {

    private final ParticipantsService part;

    @Autowired
    ParticipantsDataController(ParticipantsService part){
        this.part = part;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AgentInfo> userOkJSON(@RequestBody AgentLoginData info){
        UserResponseAction act = new UserResponseAction(part);
        return act.execute(info);
    }

}