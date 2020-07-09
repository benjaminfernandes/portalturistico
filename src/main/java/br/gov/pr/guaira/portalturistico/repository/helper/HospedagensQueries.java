package br.gov.pr.guaira.portalturistico.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.repository.filter.HospedagemFilter;

public interface HospedagensQueries {

	public Page<Hospedagem> filtrar(HospedagemFilter hospedagem, Pageable pageable);
}
