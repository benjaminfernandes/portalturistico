package br.gov.pr.guaira.portalturistico.service.event.hospedagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.gov.pr.guaira.portalturistico.storage.FotoStorage;

@Component
public class HospedagemListener {
	
	@Autowired
	private FotoStorage fotoStorage;

	@EventListener(condition = "#evento.temFoto() and #evento.novaFoto")
	public void hospedagemSalva(HospedagemSalvaEvent evento) {
		fotoStorage.salvar(evento.getHospedagem().getFoto());
	}
}
