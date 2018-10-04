package be.aca.coin.liferay.schizo.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(
		id = "be.aca.coin.liferay.schizo.internal.configuration.SchizoConfiguration",
		name = "Schizo"
)
public interface SchizoConfiguration {

	@Meta.AD(
			name = "Persona definition",
			description = "JSON representation of a persona.",
			required = false
	)
	String[] personaDefinitions();
}