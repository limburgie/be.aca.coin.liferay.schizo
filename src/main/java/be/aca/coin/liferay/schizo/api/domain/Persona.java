package be.aca.coin.liferay.schizo.api.domain;

public class Persona {

	private String firstName;
	private String portrait;
	private String bio;
	private String loginUrl;
	private String dataContext;

	public Persona(String firstName, String portrait, String bio, String loginUrl, String dataContext) {
		this.firstName = firstName;
		this.portrait = portrait;
		this.bio = bio;
		this.loginUrl = loginUrl;
		this.dataContext = dataContext;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getPortrait() {
		return portrait;
	}

	public String getBio() {
		return bio;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getDataContext() {
		return dataContext;
	}
}