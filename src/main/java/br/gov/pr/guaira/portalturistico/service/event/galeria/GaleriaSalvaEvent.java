package br.gov.pr.guaira.portalturistico.service.event.galeria;

import br.gov.pr.guaira.portalturistico.model.Galeria;
import lombok.Data;

@Data
public class GaleriaSalvaEvent {

	public GaleriaSalvaEvent(Galeria galeria) {
		this.galeria = galeria;
	}
	
	private Galeria galeria;
	
	public boolean novaFoto() {
		return this.galeria.isFotoNova();
	}
}
