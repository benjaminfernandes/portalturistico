package br.gov.pr.guaira.portalturistico.service.event.galeria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.gov.pr.guaira.portalturistico.storage.FotoStorage;

@Component
public class GaleriaListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#evento.novaFoto()")
	public void atrativoSalvaFotoGaleria(GaleriaSalvaEvent evento) {
		System.out.println("Chamou para salvar as fotos permanentemente!");
		this.fotoStorage.salvarFotoGaleria(evento.getGaleria().getNome());
	}
}
