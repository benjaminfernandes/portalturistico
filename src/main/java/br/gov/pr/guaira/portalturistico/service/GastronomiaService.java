package br.gov.pr.guaira.portalturistico.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.portalturistico.model.Gastronomia;
import br.gov.pr.guaira.portalturistico.repository.Gastronomias;
import br.gov.pr.guaira.portalturistico.service.event.gastronomia.GastronomiaSalvaEvent;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class GastronomiaService {

	@Autowired
	private Gastronomias gastronomias;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public void salvar(Gastronomia gastronomia) {
		
		Optional<Gastronomia> gastronomiaOptional = this.gastronomias.findByNomeIgnoreCase(gastronomia.getNome());
		
		if(gastronomiaOptional.isPresent() && gastronomia.isNovo()) {
			throw new EntidadeJaExistenteException("Este estabelecimento já está cadastrado!");
		}
		
		this.gastronomias.saveAndFlush(gastronomia);
		this.publisher.publishEvent(new GastronomiaSalvaEvent(gastronomia));
	}
	
	@Transactional
	public void excluir(Gastronomia gastronomia) {
		
		try {
			this.gastronomias.delete(gastronomia);
			this.gastronomias.flush();
		}catch (PersistenceException e) {
			new ImpossivelExcluirEntidadeException("Não foi possível excluir este estabelecimento! Provavelmente está sendo utilizado por outra entidade!");
		}
	}
}
