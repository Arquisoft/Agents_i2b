package view;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import domain.AgentLoginData;
import org.springframework.web.bind.annotation.*;
import services.AgentsService;
import util.JasyptEncryptor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;


/**
 * Created by Nicolás on 08/02/2017.
 * Adapted by Alejandro on 14/02/2018 (Agents).
 */
@Controller
public class AgentsController {

    private final AgentsService agentsService;

    AgentsController(AgentsService part){
        this.agentsService = part;
    }

    //The first page shown will be login.html.
    @GetMapping(value="/")
    public String getAgentInfo(Model model) {
        model.addAttribute("userinfo", new AgentLoginData());
        return "login";
    }

    //This method process an POST html request once fulfilled the login.html form (clicking in the "Enter" button).
    @RequestMapping(value = "/agentForm", method = RequestMethod.POST)
    public String showInfo(Model model, @ModelAttribute AgentLoginData data, HttpSession session){
        Agent agent = agentsService.getAgent(data.getLogin(), data.getPassword(), data.getKind());
        
        if(agent == null){
            throw new UserNotFoundException();
        } else {
            AgentInfoAdapter adapter = new AgentInfoAdapter(agent);
            AgentInfo info = adapter.userToInfo();
            
            model.addAttribute("name", info.getName());
            model.addAttribute("identifier", info.getIdentifier());
            model.addAttribute("email", info.getEmail());
            model.addAttribute("user", agent);
            session.setAttribute("user", agent);
            return "data";
        }
    }

    @RequestMapping(value="/passMenu", method = RequestMethod.GET)
    public String showMenu(Model model){
        //Just in case there must be more processing.
        return "changePassword";
    }

    @RequestMapping(value="/userChangePassword",method = RequestMethod.POST)
    public String changePassword(Model model, @RequestParam String password
            , @RequestParam String newPassword
            , @RequestParam String newPasswordConfirm
            , HttpSession session){
        JasyptEncryptor encryptor= new JasyptEncryptor();
        Agent loggedUser = (Agent) session.getAttribute("user");
        if(encryptor.checkPassword(password, loggedUser.getPassword()) &&
                newPassword.equals(newPasswordConfirm)){
            agentsService.updateInfo(loggedUser, newPassword);
            return "data";
        }
        return "changePassword";
    }

}


