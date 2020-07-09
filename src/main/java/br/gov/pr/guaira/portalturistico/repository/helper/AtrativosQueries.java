package br.gov.pr.guaira.portalturistico.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.portalturistico.model.Atrativo;
import br.gov.pr.guaira.portalturistico.repository.filter.AtrativoFilter;

public interface AtrativosQueries {

	public Page<Atrativo> filtrar(AtrativoFilter filtro, Pageable pageable);
}
