package be.aca.coin.liferay.schizo.internal.domain;

import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.util.StringUtil;

import be.aca.coin.liferay.schizo.api.service.Schizo;

public class PersonaDefinition {

	private String screenName;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String portrait;
	private String bio;
	private List<String> sites;
	private List<String> roles;
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
		return dataContext == null ? Schizo.DEFAULT_DATA_CONTEXT : new GsonBuilder().setPrettyPrinting().create().toJson(dataContext);
	}

	public String getConcatenatedSites() {
		return StringUtil.merge(sites, "\r\n");
	}

	public String getConcatenatedRoles() {
		return StringUtil.merge(roles, "\r\n");
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

	public List<String> getSites() {
		return sites;
	}

	public void setSites(List<String> sites) {
		this.sites = sites;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public JsonObject getDataContext() {
		return dataContext;
	}

	public void setDataContext(JsonObject dataContext) {
		this.dataContext = dataContext;
	}
}