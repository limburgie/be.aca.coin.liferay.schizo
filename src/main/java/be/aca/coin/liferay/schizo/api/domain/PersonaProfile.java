package be.aca.coin.liferay.schizo.api.domain;

public class PersonaProfile {

	private String screenName;
	private String firstName;
	private String lastName;

	public PersonaProfile(String screenName, String firstName, String lastName) {
		this.screenName = screenName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
