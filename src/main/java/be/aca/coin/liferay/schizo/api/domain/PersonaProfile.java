package be.aca.coin.liferay.schizo.api.domain;

public class PersonaProfile {

	private String screenName;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String portrait;

	public PersonaProfile(String screenName, String emailAddress, String firstName, String lastName, String portrait) {
		this.screenName = screenName;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.portrait = portrait;
	}

	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}

	public String getScreenName() {
		return screenName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPortrait() {
		return portrait;
	}
}