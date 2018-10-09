package be.aca.coin.liferay.schizo.api.domain;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Persona {

	private PersonaProfile profile;
	private JsonObject dataContext;

	public Persona(PersonaProfile profile, JsonObject dataContext) {
		this.profile = profile;
		this.dataContext = dataContext;
	}

	public String getPrettyPrintedDataContext() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(dataContext);
	}

	public PersonaProfile getProfile() {
		return profile;
	}

	public void setProfile(PersonaProfile profile) {
		this.profile = profile;
	}

	public JsonObject getDataContext() {
		return dataContext;
	}

	public void setDataContext(JsonObject dataContext) {
		this.dataContext = dataContext;
	}
}