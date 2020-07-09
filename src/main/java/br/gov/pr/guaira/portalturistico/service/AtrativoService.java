package br.gov.pr.guaira.portalturistico.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import br.gov.pr.guaira.portalturistico.model.Atrativo;
import br.gov.pr.guaira.portalturistico.model.Galeria;
import br.gov.pr.guaira.portalturistico.repository.Atrativos;
import br.gov.pr.guaira.portalturistico.service.event.atrativo.AtrativoSalvaEvent;
import br.gov.pr.guaira.portalturistico.service.event.galeria.GaleriaSalvaEvent;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class AtrativoService {
	
	@Autowired
	private Atrativos atrativos;
	@Autowired
	private ApplicationEventPublisher publisher;

	public void salvar(Atrativo atrativo) {
		Optional<Atrativo> atrativoOptional = this.atrativos.findByNomeIgnoreCase(atrativo.getNome());

		if (atrativoOptional.isPresent() && atrativo.isNovo()) {
			throw new EntidadeJaExistenteException("Este atrativo já está cadastrado!");
		}
		
		if(!StringUtils.isEmpty(atrativo.getFotosGaleria())) {
			vinculaFotos(atrativo);
		}
		
		this.atrativos.saveAndFlush(atrativo);
		this.publisher.publishEvent(new AtrativoSalvaEvent(atrativo));
	}
	
	@Transactional
	public void excluir(Atrativo atrativo) {

		try {
			this.atrativos.delete(atrativo);
			this.atrativos.flush();
		} catch (PersistenceException e) {
			new ImpossivelExcluirEntidadeException(
					"Não foi possível excluir este atrativo! Provavelmente está sendo utilizado por outra entidade!");
		}
	}
	
	public Galeria[] geraGaleria(Atrativo atrativo) {
		Gson gson = new Gson();
		Galeria[] galeria = gson.fromJson(atrativo.getFotosGaleria(), Galeria[].class);
		return galeria;
	}
	
	private void vinculaFotos(Atrativo atrativo) {
		Gson gson = new Gson();
		Galeria[] data = gson.fromJson(atrativo.getFotosGaleria(), Galeria[].class);
		
		for(int i = 0; i < data.length; i++) {
			this.publisher.publishEvent(new GaleriaSalvaEvent(data[i]));
			data[i].setFotoNova(false);
		}
		atrativo.setFotosGaleria(gson.toJson(data));
	}
	
}
