package br.gov.pr.guaira.portalturistico.service.event.gastronomia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.gov.pr.guaira.portalturistico.storage.FotoStorage;

@Component
public class GastronomiaListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#evento.temFoto() and #evento.novaFoto")
	public void gastronomiaSalva(GastronomiaSalvaEvent evento) {
		this.fotoStorage.salvar(evento.getGastronomia().getFoto());
	}
}
