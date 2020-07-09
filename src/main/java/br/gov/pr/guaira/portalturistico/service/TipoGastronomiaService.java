package br.gov.pr.guaira.portalturistico.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;
import br.gov.pr.guaira.portalturistico.repository.TiposGastronomias;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class TipoGastronomiaService {

	@Autowired
	private TiposGastronomias tiposGastronomia;
	
	public void salvar(TipoGastronomia tipoGastronomia) {
		
		Optional<TipoGastronomia> tipoGastronomiaOptional = this.tiposGastronomia.findByNomeIgnoreCase(tipoGastronomia.getNome());
		
		if(tipoGastronomiaOptional.isPresent() && tipoGastronomia.isNovo()) {
			throw new EntidadeJaExistenteException("Este tipo de gastronomia já existe!");
		}
		this.tiposGastronomia.saveAndFlush(tipoGastronomia);
	}

	@Transactional
	public void excluir(TipoGastronomia tipoGastronomia) {
		
		try {
			this.tiposGastronomia.delete(tipoGastronomia);
			this.tiposGastronomia.flush();
		}catch (PersistenceException e) {
			new ImpossivelExcluirEntidadeException("Não foi possível excluir este tipo! Provavelmente é utilizado por outra entidade!");
		}
		
	}
}
