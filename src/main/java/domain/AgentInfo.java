package domain;

import java.io.Serializable;

/**
 * Created by Nicolás on 15/02/2017.
 * Adapted by Carmen on 13/02/2018.
 * 
 * Class that serves as a response for the service, providing a subset of the User class' fields
 */
public class AgentInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6796982835270172418L;
		
	private String name;
	private double[] location;
	private String email;
	private String identifier;
	private int kindCode;
	private String kindName;

	AgentInfo(){ }
    
    public AgentInfo(String name, String email, String id, int kind, String kindName) {
	    	this.name = name;
	    	this.email = email;
	    	this.identifier = id;
	    	this.kindCode = kind;
	    	this.kindName = kindName;
    }
    
    public AgentInfo(String name, double longitude, double latitude, String email, String id, int kind, String kindName) {
	    	this(name, email, id, kind, kindName);
	    	this.location = new double[2];
	    	this.location[0] = latitude;
	    	this.location[1] = longitude;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] coordinates) {
		if (location==null)
			this.location = new double[2];
		this.location = coordinates;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getKind() {
		return kindCode;
	}

	public void setKind(int kind) {
		this.kindCode = kind;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
    public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + kindCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentInfo other = (AgentInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (kindCode != other.kindCode)
			return false;
		return true;
	}

	@Override
	public String toString() {
        final StringBuilder sb = new StringBuilder("AgentInfo{");
        sb.append("name='").append(name).append('\'');
        if (location!=null) {
        		sb.append(", location='").append(location[0]).append(", ").append(location[1]).append('\'');
        } else {
        		sb.append(", location='[]'");
        }
        
        sb.append(", identifier='").append(identifier).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", kindCode='").append(kindCode).append('\'');
        sb.append(", kind='").append(kindName).append('\'');
        sb.append('}');
        return sb.toString();
	}

}
