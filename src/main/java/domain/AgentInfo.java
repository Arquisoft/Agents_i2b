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
	
	private String firstName;
    private String lastName;
    private String userId;
    private String email;

    AgentInfo(){

    }

    public AgentInfo(String firstName, String lastName, String userId, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentInfo userInfo = (AgentInfo) o;

        return userId.equals(userInfo.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
