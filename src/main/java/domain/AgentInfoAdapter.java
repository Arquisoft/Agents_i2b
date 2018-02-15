package domain;

/**
 * Created by Nicolás on 15/02/2017.
 * Class in charge of translating an Agent object into the response format
 * Note: this class only creates a model class that contains a subset of the fields in the Agent class
 */
public class AgentInfoAdapter {

    private Agent user;

    public AgentInfoAdapter(Agent user) {
        this.user = user;
    }

    public AgentInfo userToInfo(){
    	if (user.getLocation()!=null)
    		return new AgentInfo(user.getName(), user.getLocation()[0],
        		user.getLocation()[1], user.getEmail(), user.getUsername(),
        		user.getKind());
    	else
    		return new AgentInfo(user.getName(), user.getEmail(), 
    				user.getUsername(), user.getKind());
    }
}
