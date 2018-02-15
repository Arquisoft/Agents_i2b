package domain;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import util.JasyptEncryptor;

/**
 * Created by Damian on 06/02/2017.
 * Adapted by Carmen on 13/02/2018.
 */

@Document(collection ="users")
public class Agent {

    @Id
    private ObjectId id;

    private String name;
    private double[] location;
    private String email;
    private String password;
    private String username;
    private int kind;

    Agent(){ }

    public Agent(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = encryptPass(password); 
    }

    public Agent(String name, String email, String password, 
    				 String username, int kind) {
        this(name, email, password);
        this.username = username;
        this.kind = kind;
    }
    
    public Agent(String name, String email, String password, 
    		String username, int kind, double latitude, double longitude) {
    	
	    	this(name, email, password, username, kind);
	    	this.location = new double[2];
	    	this.location[0] = latitude;
	    	this.location[1] = longitude;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent user = (Agent) o;

        return username.equals(user.username);

    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Agent [id=").append(id).append(", name=").append(name);
		if (location!=null)
			builder.append(", location=").append(Arrays.toString(location));
		builder.append(", email=").append(email);
		builder.append(", password=").append(password);
		builder.append(", username=").append(username);
		builder.append(", kind=").append(kind).append("]");
		return builder.toString();
	}

	@Override
    public int hashCode() {
        return username.hashCode();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = encryptPass(password);
    }
    
    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public void setLocation(double[] location) {
		if (this.location==null)
			this.location = new double[2];
		this.location = location;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	private String encryptPass(String password){
    		JasyptEncryptor encryptor = new JasyptEncryptor();
        return encryptor.encryptPassword(password);
    }
}
