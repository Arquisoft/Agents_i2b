package domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class AgentLoginData {

	private String login;
	private String password;
	private int kind;

	public AgentLoginData() {}

	public AgentLoginData(String login, String password, int kind) {
		this.login = login;
		this.password = password;
		this.kind = kind;
	}

	public String getLogin() {
		return login;
	}

    public String getPassword() {
		return password;
	}
    
    public int getKind() {
    		return kind;
    }

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setKind(int kind) {
		this.kind = kind;
	}
}
