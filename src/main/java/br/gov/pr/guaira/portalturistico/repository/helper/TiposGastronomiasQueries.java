package br.gov.pr.guaira.portalturistico.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.repository.filter.TipoGastronomiaFilter;

public interface TiposGastronomiasQueries {

	public Page<Hospedagem> filtrar(TipoGastronomiaFilter tipoGastronomiaFilter, Pageable pageable);
}
