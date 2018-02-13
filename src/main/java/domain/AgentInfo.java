package domain;

import java.io.Serializable;

/**
 * Created by Nicolás on 15/02/2017.
 * Class that serves as a response for the service, providing a subset of the User class' fields
 */
public class AgentInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6796982835270172418L;
		
	private String name;
	private double[] location = new double[2];
	private String email;
	private String identifier;
	private int kind;
	
    AgentInfo(){

    }
    
    public AgentInfo(String name, String email, String id, int kind)
    {
    	this.name = name;
    	this.email = email;
    	this.identifier = id;
    	this.kind = kind;
    }
    
    public AgentInfo(String name, double longitude, double latitude, String email, String id, int kind)
    {
    	this(name, email, id, kind);
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
		this.location = coordinates;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", location='").append(location[0]).append(", ").append(location[1]).append('\'');
        sb.append(", identifier='").append(identifier).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", kind='").append(kind).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentInfo userInfo = (AgentInfo) o;

        return identifier.equals(userInfo.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
