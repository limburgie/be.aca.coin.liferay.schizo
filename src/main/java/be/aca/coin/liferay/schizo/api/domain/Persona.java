package be.aca.coin.liferay.schizo.api.domain;

public class Persona {

	private PersonaProfile profile;

	public Persona(PersonaProfile profile) {
		this.profile = profile;
	}

	public PersonaProfile getProfile() {
		return profile;
	}
}