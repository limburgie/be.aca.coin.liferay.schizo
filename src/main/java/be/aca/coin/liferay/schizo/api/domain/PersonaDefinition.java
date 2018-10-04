package be.aca.coin.liferay.schizo.api.domain;

public class PersonaDefinition {

	private PersonaProfile profile;

	public PersonaDefinition(PersonaProfile profile) {
		this.profile = profile;
	}

	public PersonaProfile getProfile() {
		return profile;
	}
}
