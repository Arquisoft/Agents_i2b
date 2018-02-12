package view;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import domain.AgentLoginData;
import org.springframework.web.bind.annotation.*;
import services.ParticipantsService;
import util.JasyptEncryptor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;


/**
 * Created by Nicolás on 08/02/2017.
 */
@Controller
public class ParticipantsController {

    private final ParticipantsService part;

    ParticipantsController(ParticipantsService part){
        this.part = part;
    }

    //The first page shown will be login.html.
    @GetMapping(value="/")
    public String getParticipantInfo(Model model) {
        model.addAttribute("userinfo", new AgentLoginData());
        return "login";
    }

    //This method process an POST html request once fulfilled the login.html form (clicking in the "Enter" button).
    @RequestMapping(value = "/userForm", method = RequestMethod.POST)
    public String showInfo(Model model, @ModelAttribute AgentLoginData data, HttpSession session){
        Agent user = part.getParticipant(data.getLogin(), data.getPassword());
        if(user == null){
            throw new UserNotFoundException();
        }
        else {
            AgentInfoAdapter adapter = new AgentInfoAdapter(user);
            AgentInfo info = adapter.userToInfo();
            model.addAttribute("fName", info.getFirstName());
            model.addAttribute("lName", info.getLastName());
            model.addAttribute("email", info.getEmail());
            model.addAttribute("user", user);
            session.setAttribute("user", user);
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
            part.updateInfo(loggedUser, newPassword);
            return "data";
        }
        return "changePassword";
    }

}


