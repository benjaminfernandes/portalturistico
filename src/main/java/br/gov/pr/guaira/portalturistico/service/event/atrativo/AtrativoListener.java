package br.gov.pr.guaira.portalturistico.service.event.atrativo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.gov.pr.guaira.portalturistico.storage.FotoStorage;

@Component
public class AtrativoListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#evento.temFotoChamada() and #evento.novaFotoChamada")
	public void atrativoSalvaFotoChamada(AtrativoSalvaEvent evento) {
		this.fotoStorage.salvar(evento.getAtrativo().getFotoChamada());
	}
	
	@EventListener(condition = "#evento.temFotoCapa() and #evento.novaFotoCapa")
	public void atrativoSalvaFotoCapa(AtrativoSalvaEvent evento) {
		this.fotoStorage.salvarFotoCapa(evento.getAtrativo().getFotoCapa());
	}
}
