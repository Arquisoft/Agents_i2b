package domain;

/**
 * Created by Nicolás on 15/02/2017.
 * Class in charge of translating a User object into the response format
 * Note: this class only creates a model class that contains a subset of the fields in the User class
 */
public class AgentInfoAdapter {

    private Agent user;

    public AgentInfoAdapter(Agent user) {
        this.user = user;
    }

    public AgentInfo userToInfo(){
        return new AgentInfo(user.getFirstName(), user.getLastName(),
        					    user.getUserId(), user.getEmail());
    }
}
