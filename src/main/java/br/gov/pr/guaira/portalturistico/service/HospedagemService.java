package br.gov.pr.guaira.portalturistico.service;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.repository.Hospedagens;
import br.gov.pr.guaira.portalturistico.service.event.hospedagem.HospedagemSalvaEvent;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.portalturistico.storage.FotoStorage;

@Service
public class HospedagemService {

	@Autowired
	private Hospedagens hospedagens;
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private FotoStorage fotoStorage;
	
	public void salvar(Hospedagem hospedagem) {
		
		Optional<Hospedagem> hospedagemOptional = this.hospedagens.findByNomeIgnoreCase(hospedagem.getNome());
		
		if(hospedagemOptional.isPresent() && hospedagem.getCodigo() == null) {
			throw new EntidadeJaExistenteException("Esta hospedagem já está cadastrada!");
		}
		
		ajustaUrl(hospedagem);
		this.hospedagens.save(hospedagem);
		publisher.publishEvent(new HospedagemSalvaEvent(hospedagem));
	}
	
	@Transactional
	public void excluir(Hospedagem hospedagem) {
		try {
			String foto = hospedagem.getFoto();
			hospedagens.delete(hospedagem);
			hospedagens.flush();
			fotoStorage.excluirFoto(foto);
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível apagar a hospedagem!");
		}
	}
	
	private void ajustaUrl(Hospedagem hospedagem) {
		
		if(hospedagem.getSite() != null) {
			
			if(!hospedagem.getSite().contains("http://")) {
				hospedagem.setSite("http://"+hospedagem.getSite());
			}
		}
	}
}
