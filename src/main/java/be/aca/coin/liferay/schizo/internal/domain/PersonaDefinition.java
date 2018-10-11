package be.aca.coin.liferay.schizo.internal.domain;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class PersonaDefinition {

	private String screenName;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String portrait;
	private String bio;
	private JsonObject dataContext;

	public String getPortraitWithoutMime() {
		if (portrait == null) {
			return null;
		}

		int startIndex = portrait.lastIndexOf(',');

		if (startIndex == -1) {
			return null;
		}

		return portrait.substring(startIndex + 1);
	}

	public String getPrettyPrintedDataContext() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(dataContext);
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public JsonObject getDataContext() {
		return dataContext;
	}

	public void setDataContext(JsonObject dataContext) {
		this.dataContext = dataContext;
	}
}