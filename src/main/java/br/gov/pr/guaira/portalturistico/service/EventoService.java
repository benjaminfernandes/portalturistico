package br.gov.pr.guaira.portalturistico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.portalturistico.model.Evento;
import br.gov.pr.guaira.portalturistico.repository.Eventos;

@Service
public class EventoService {

	@Autowired
	private Eventos eventos;
	
	
	public void salvar(Evento evento) {
		this.eventos.save(evento);
	}
}
