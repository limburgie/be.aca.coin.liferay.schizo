package be.aca.coin.liferay.schizo.internal.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import be.aca.coin.liferay.schizo.api.domain.Persona;
import be.aca.coin.liferay.schizo.api.service.Schizo;

@Path("/personas")
public class PersonasResource {

	private Schizo schizo;

	PersonasResource(Schizo schizo) {
		this.schizo = schizo;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Persona> getPersonas() {
		return schizo.getPersonas();
	}
}