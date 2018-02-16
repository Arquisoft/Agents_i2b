package domain;

import repositories.CSVFileParser;
import repositories.MasterFileParser;

/**
 * Created by Nicolás on 15/02/2017.
 * Adapted by Alejandro on 16/02/2018 (Agents).
 * 
 * Class in charge of translating an Agent object into the response format
 * Note: this class only creates a model class that contains a subset of the fields in the Agent class
 */
public class AgentInfoAdapter {

    private Agent user;
    
	private MasterFileParser parser = new CSVFileParser();

    public AgentInfoAdapter(Agent user) {
        this.user = user;
    }

    public AgentInfo agentToInfo() {
    		int kindCode = user.getKind();
    		String kindName;
    		try {
			kindName = parser.getKindNameOf(kindCode);
		} catch (Exception e) {
			return null;
		}
    		
	    	if (user.getLocation() != null) {
	    		return new AgentInfo(user.getName(), user.getLocation()[0],
	        		user.getLocation()[1], user.getEmail(), user.getUsername(),
	        		kindCode, kindName);
    		} else {
	    		return new AgentInfo(user.getName(), user.getEmail(), 
	    				user.getUsername(), kindCode, kindName);
	    	}
    }
}
